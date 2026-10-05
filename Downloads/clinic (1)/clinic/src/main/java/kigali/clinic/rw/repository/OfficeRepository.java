package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;

@Repository 
public interface OfficeRepository  extends JpaRepository<Office,UUID>{
    
    List<Office> findByName(String name);

    Optional<Office> findByNameAndOfficeNumber(String name, String officeNumber);

    Optional<Office> findByDoctor(Doctor doctor);

    Optional<Office>  findByOfficeNumber(int officeNumber);

    // @Query ("""
    //         SELECT FROM Office O WHERE O.doctor.name : = name
    //         """)
    // List<Office> findByDoctorName(@Param("name") String name);

           @Query("SELECT o.name, o.officeNumber, COUNT(a) FROM Appointment a JOIN a.doctor d JOIN d.office o "
            + "GROUP BY o.id, o.name, o.officeNumber ORDER BY COUNT(a) DESC, o.officeNumber ASC")
       List<Object[]> findBusiestOffice(Pageable pageable);


}
