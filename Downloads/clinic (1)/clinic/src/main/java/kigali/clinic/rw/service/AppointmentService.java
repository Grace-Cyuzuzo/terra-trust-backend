package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import kigali.clinic.rw.domain.AppointmentStatus;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String saveAppointment(Appointment appointment) {
        boolean alreadyBooked = appointmentRepo.existsByDoctorIdAndAppointmentDateAndStatusNot(
                appointment.getDoctor().getId(),
                appointment.getAppointmentDate(),
                AppointmentStatus.CANCELLED);
        if (alreadyBooked) {
            return "Doctor is already booked on that date";
        }
        appointmentRepo.save(appointment);
        return "saved successfully";
    }

    public Appointment getAppointmentById(UUID id) {
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        if (appointment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointment with id " + id);
        }
        return appointment.get();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public String updateAppointment(UUID id, Appointment updated) {
        Optional<Appointment> existing = appointmentRepo.findById(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointment with id " + id);
        }
        Appointment appointment = existing.get();
        appointment.setAppointmentDate(updated.getAppointmentDate());
        appointment.setReason(updated.getReason());
        appointment.setStatus(updated.getStatus());
        appointment.setPatient(updated.getPatient());
        appointment.setDoctor(updated.getDoctor());
        appointmentRepo.save(appointment);
        return "updated successfully";
    }

    public String deleteAppointment(UUID id) {
        if (appointmentRepo.existsById(id)) {
            appointmentRepo.deleteById(id);
            return "Appointment with id " + id + " deleted successfully";
        }
        return "we don't have appointment with that id " + id;
    }

    public List<Appointment> getPendingAppointmentsForDoctor(UUID doctorId) {
    return appointmentRepo.findByDoctorId(doctorId, Pageable.unpaged()).stream()
            .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
            .sorted(Comparator.comparing(Appointment::getAppointmentDate))
            .collect(Collectors.toList());
}

public List<Appointment> getOverdueAppointments() {
    return appointmentRepo.findOverdueAppointments(AppointmentStatus.SCHEDULED);
}

public List<Object[]> getAppointmentCountPerDoctor() {
    return appointmentRepo.countAppointmentsPerDoctor();
}

public List<Appointment> getAppointmentsBySpecialization(String specializationName) {
    return appointmentRepo.findAppointmentsByDoctorSpecialization(specializationName);
}

public Page<Appointment> getDoctorAppointmentsPaginated(UUID doctorId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").ascending());
    return appointmentRepo.findByDoctorId(doctorId, pageable);
}

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepo.findByStatusOrderByAppointmentDateAsc(status);
    }

           public List<Appointment> getAppointmentsBetween(LocalDate start, LocalDate end) {
           return appointmentRepo.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start, end);
       }

           public List<Object[]> getAppointmentCountByStatus() {
        return appointmentRepo.countAppointmentsByStatus();
    }
}
