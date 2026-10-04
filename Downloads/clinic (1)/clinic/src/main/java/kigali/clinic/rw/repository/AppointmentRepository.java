package kigali.clinic.rw.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Appointment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import kigali.clinic.rw.domain.AppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByReasonContainingIgnoreCaseOrderByAppointmentDateAsc(String keyword);

    @Query("SELECT a FROM Appointment a WHERE a.status = :status AND a.appointmentDate < CURRENT_DATE")
    List<Appointment> findOverdueAppointments(@Param("status") AppointmentStatus status);

    @Query("SELECT a.doctor.id, COUNT(a) FROM Appointment a GROUP BY a.doctor.id ORDER BY COUNT(a) DESC")
    List<Object[]> countAppointmentsPerDoctor();

    @Query("SELECT a FROM Appointment a JOIN a.doctor d JOIN d.specializations s WHERE s.name = :specializationName")
    List<Appointment> findAppointmentsByDoctorSpecialization(@Param("specializationName") String specializationName);

    Page<Appointment> findByDoctorId(UUID doctorId, Pageable pageable);
        List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);

}

