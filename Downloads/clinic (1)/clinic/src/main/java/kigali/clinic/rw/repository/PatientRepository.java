package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import kigali.clinic.rw.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);

    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
           @Query("SELECT DISTINCT p FROM Appointment a JOIN a.patient p WHERE a.doctor.id = :doctorId")
       List<Patient> findPatientsByDoctorId(@Param("doctorId") UUID doctorId);

}

