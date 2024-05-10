package com.example.calendarbe.service;

import com.example.calendarbe.model.Appointment;

import java.time.LocalDateTime;

public interface IAppointmentService {

    void save(Appointment appointment);

    void deleteById(Long id);
    Appointment findByTitleAndStartAndAndEnd(String title, LocalDateTime start, LocalDateTime end);
}
