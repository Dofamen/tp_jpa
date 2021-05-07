package ma.emsi.tp_jpa;

import ma.emsi.tp_jpa.entities.Patient;
import ma.emsi.tp_jpa.repositories.PatiensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class TpJpaApplication implements CommandLineRunner {

    @Autowired
    private PatiensRepository patiensRepository;
    public static void main(String[] args) {
        SpringApplication.run(TpJpaApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {
        patiensRepository.save(new Patient(null, "Mehdi", new Date(), 23959, true));
        patiensRepository.save(new Patient(null, "Farah", new Date(), 23949, false));
        patiensRepository.save(new Patient(null, "name14", new Date(), 4523, false));
        patiensRepository.save(new Patient(null, "name13", new Date(), 523523, true));
        patiensRepository.save(new Patient(null, "name12", new Date(), 23259, false));
        patiensRepository.save(new Patient(null, "name11", new Date(), 2353399, false));
        patiensRepository.save(new Patient(null, "name10", new Date(), 499, true));
        patiensRepository.save(new Patient(null, "name9", new Date(), 234399, false));
        patiensRepository.save(new Patient(null, "name8", new Date(), 563499, false));
        patiensRepository.save(new Patient(null, "name7", new Date(), 908399, false));
        patiensRepository.save(new Patient(null, "name6", new Date(), 89799, true));
        patiensRepository.save(new Patient(null, "name5", new Date(), 9099, false));
        patiensRepository.save(new Patient(null, "name4", new Date(), 4567, true));
        patiensRepository.save(new Patient(null, "name3", new Date(), 789939, false));
        patiensRepository.save(new Patient(null, "name2", new Date(), 7659, true));
        patiensRepository.save(new Patient(null, "name1", new Date(), 3499, false));
    }
}
