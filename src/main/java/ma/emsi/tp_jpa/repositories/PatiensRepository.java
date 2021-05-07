package ma.emsi.tp_jpa.repositories;

import ma.emsi.tp_jpa.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatiensRepository extends JpaRepository<Patient, Long>{

    Page<Patient> findByNameContains(String keyword, Pageable Pageable);
}
