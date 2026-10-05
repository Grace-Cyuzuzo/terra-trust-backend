package kigali.clinic.rw.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import kigali.clinic.rw.domain.AppointmentStatus;
//import java.time.LocalDate;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

       @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAppointment(@RequestBody Appointment appointment) {
        String result = appointmentService.saveAppointment(appointment);
        if (result.equals("Doctor is already booked on that date")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable UUID id, @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }

    @GetMapping("/doctor/{doctorId}/pending")
public ResponseEntity<List<Appointment>> getPendingAppointments(@PathVariable UUID doctorId) {
    return ResponseEntity.ok(appointmentService.getPendingAppointmentsForDoctor(doctorId));
}

@GetMapping("/overdue")
public ResponseEntity<List<Appointment>> getOverdueAppointments() {
    return ResponseEntity.ok(appointmentService.getOverdueAppointments());
}

@GetMapping("/count-per-doctor")
public ResponseEntity<List<Object[]>> getAppointmentCountPerDoctor() {
    return ResponseEntity.ok(appointmentService.getAppointmentCountPerDoctor());
}

@GetMapping("/by-specialization")
public ResponseEntity<List<Appointment>> getAppointmentsBySpecialization(@RequestParam String name) {
    return ResponseEntity.ok(appointmentService.getAppointmentsBySpecialization(name));
}

@GetMapping("/doctor/{doctorId}/page")
public ResponseEntity<Page<Appointment>> getDoctorAppointmentsPaginated(
        @PathVariable UUID doctorId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(appointmentService.getDoctorAppointmentsPaginated(doctorId, page, size));
}

   @GetMapping("/by-status")
   public ResponseEntity<List<Appointment>> getByStatus(@RequestParam AppointmentStatus status) {
       return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
   }

   @GetMapping("/between")
public ResponseEntity<List<Appointment>> getBetween(@RequestParam String start, @RequestParam String end) {
    return ResponseEntity.ok(appointmentService.getAppointmentsBetween(
            LocalDate.parse(start), LocalDate.parse(end)));
}

@GetMapping("/stats/by-status")
public ResponseEntity<List<Object[]>> getStatsByStatus() {
    return ResponseEntity.ok(appointmentService.getAppointmentCountByStatus());
}

}