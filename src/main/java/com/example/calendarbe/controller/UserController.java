package com.example.calendarbe.controller;

import com.example.calendarbe.dto.AppointmentWithUsersDTO;
import com.example.calendarbe.model.Appointment;
import com.example.calendarbe.model.User;
import com.example.calendarbe.service.IAppointmentService;
import com.example.calendarbe.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping("/api/login")
    public ResponseEntity<Long> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.findUserByEmailAndPassword(email, password);
        if(user != null) {
            return ResponseEntity.ok(user.getId());
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }
    @PostMapping("/api/appointments")
    public ResponseEntity<Appointment> findAppointment(@RequestBody Appointment appointment) {
        Appointment sameAppointment = appointmentService.findByTitleAndStartAndAndEnd(appointment.getTitle(),appointment.getStart(),appointment.getEnd());
        if(sameAppointment != null) {
            return ResponseEntity.ok(appointment);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping("/api/appointments")
//    public ResponseEntity<Appointment> findAppointment(@RequestParam String title, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
//        Appointment sameAppointment = appointmentService.findByTitleAndStartAndAndEnd(title, start, end);
//        if (sameAppointment != null) {
//            return ResponseEntity.ok(sameAppointment);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("/api/user/{id}/appointment")
    public ResponseEntity<String> joinAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        // Kiểm tra người dùng tồn tại
        Optional<User> user = userService.findUserById(id);
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra xem cuộc hẹn đã tồn tại hay chưa
        Appointment existingAppointment = appointmentService.findByTitleAndStartAndAndEnd(appointment.getTitle(), appointment.getStart(), appointment.getEnd());
        if(existingAppointment == null) {
            return ResponseEntity.notFound().build();
        }

        // Thêm người dùng vào danh sách người dùng của cuộc hẹn
        Set<User> users = existingAppointment.getUsers();
        users.add(user.get());
        existingAppointment.setUsers(users);
        appointmentService.save(existingAppointment);

        // Cập nhật danh sách cuộc hẹn của người dùng
        Set<Appointment> userAppointments = user.get().getAppointments();
        userAppointments.add(existingAppointment);
        user.get().setAppointments(userAppointments);
        userService.save(user.get());

        return ResponseEntity.ok().body("{\"message\": \"Joint appointment successfully\"}");
    }


    @PostMapping("/api/users/{id}/appointments")
    public ResponseEntity<String> createOrUpdateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        try {
            Optional<User> user = userService.findUserById(id);
            if(!user.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Set<Appointment> savedAppointments = user.get().getAppointments();
            for (Appointment existingAppointment : savedAppointments) {
                if (existingAppointment.getStart().isBefore(appointment.getEnd()) && existingAppointment.getEnd().isAfter(appointment.getStart())) {
                    user.get().getAppointments().remove(existingAppointment);
                    appointmentService.deleteById(existingAppointment.getId());
                    break;
                }
            }

            Set<User> userSet = new HashSet<>();
            userSet.add(user.get());
            appointment.setUsers(userSet);
            appointmentService.save(appointment);
            Set<Appointment> appointmentSet;

            if(user.get().getAppointments().isEmpty()) {
                appointmentSet = new HashSet<>();
            } else {
                appointmentSet = user.get().getAppointments();
            }
            appointmentSet.add(appointment);
            user.get().setAppointments(appointmentSet);
            userService.save(user.get());

            return ResponseEntity.ok().body("{\"message\": \"Appointment created successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create or update appointment");
        }
    }

//    @GetMapping("/api/users/{id}/appointments")
//    public ResponseEntity<List<Appointment>> getAppointmentsByUserId(@PathVariable Long id) {
//        Optional<User> user = userService.findUserById(id);
//        if (!user.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        Set<Appointment> appointments = user.get().getAppointments();
//        return ResponseEntity.ok(new ArrayList<>(appointments));
//    }
@GetMapping("/api/users/{id}/appointments")
public ResponseEntity<List<AppointmentWithUsersDTO>> getAppointmentsByUserId(@PathVariable Long id) {
    Optional<User> user = userService.findUserById(id);
    if (!user.isPresent()) {
        return ResponseEntity.notFound().build();
    }
    Set<Appointment> appointments = user.get().getAppointments();

    List<AppointmentWithUsersDTO> appointmentDTOs = new ArrayList<>();
    for (Appointment appointment : appointments) {
        AppointmentWithUsersDTO appointmentDTO = new AppointmentWithUsersDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setTitle(appointment.getTitle());
        appointmentDTO.setLocation(appointment.getLocation());
        appointmentDTO.setStart(appointment.getStart());
        appointmentDTO.setEnd(appointment.getEnd());
        appointmentDTO.setUsers(appointment.getUsers());

        appointmentDTOs.add(appointmentDTO);
    }

    return ResponseEntity.ok(appointmentDTOs);
}


}
