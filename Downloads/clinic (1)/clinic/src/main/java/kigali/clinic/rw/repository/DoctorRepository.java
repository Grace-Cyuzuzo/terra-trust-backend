package kigali.clinic.rw.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;


@Repository 
public interface DoctorRepository extends JpaRepository<Doctor,UUID> {
    Boolean existsByOffice(Office office);
    Optional<Doctor> findByFirstNameAndLastName(String firstName, String lastName);
           @Query("SELECT d FROM Doctor d JOIN d.specializations s WHERE LOWER(s.name) = LOWER(:name)")
       List<Doctor> findDoctorsBySpecializationName(@Param("name") String name);
               @Query("SELECT d FROM Doctor d WHERE d.office IS NULL ORDER BY d.lastName")
    List<Doctor> findDoctorsWithoutOffice();
}
