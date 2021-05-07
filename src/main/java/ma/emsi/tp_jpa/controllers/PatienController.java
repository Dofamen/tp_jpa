package ma.emsi.tp_jpa.controllers;


import ma.emsi.tp_jpa.entities.Patient;
import ma.emsi.tp_jpa.repositories.PatiensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
public class PatienController {

    @Autowired
    private PatiensRepository patiensRepository;

    @GetMapping(path = "/index")
    public String index (){
        return "index";
    }

    @GetMapping(path = "/patiens")
    public String list (Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword){
        //List<Patient> patients = patiensRepository.findAll();
        //Page<Patient> pagePatients = patiensRepository.findAll(PageRequest.of(page, size));

        Page<Patient> pagePatients = patiensRepository.findByNameContains(keyword, PageRequest.of(page, size));

        //model.addAttribute("patients", patients);
        model.addAttribute("patients", pagePatients);
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);

        return "patiens";
    }


    @GetMapping(path = "/deletePatient")
    public String deletePatient(Long id){
        patiensRepository.deleteById(id);
        return "redirect:/patiens";
    }


    @GetMapping(path = "/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient", new Patient());
        model.addAttribute("mode", "new");
        return "formPatient";
    }

    @PostMapping(path = "/savePatient")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formPatient";
        patiensRepository.save(patient);
        model.addAttribute("patient", patient);
        return "confirmation";
    }

    @GetMapping(path = "/editPatient")
    public String editPatient(Model model,Long id){
        Optional<Patient> p = patiensRepository.findById(id);
        if (!p.isPresent()) return "redirect:/patiens";
        model.addAttribute("patient", p.get());
        model.addAttribute("mode", "edit");
        return "formPatient";
    }





    @GetMapping("/listPatients")
    @ResponseBody
    public List<Patient> list(){
        return patiensRepository.findAll();
    }


}
