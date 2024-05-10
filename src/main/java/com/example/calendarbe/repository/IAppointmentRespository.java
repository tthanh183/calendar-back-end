package com.example.calendarbe.repository;

import com.example.calendarbe.model.Appointment;
import com.example.calendarbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentRespository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByUsers(User user);
    Appointment findByTitleAndStartAndAndEnd(String title, LocalDateTime start, LocalDateTime end);
}
