package com.example.calendarbe.service.impl;

import com.example.calendarbe.model.Appointment;
import com.example.calendarbe.model.User;
import com.example.calendarbe.repository.IAppointmentRespository;
import com.example.calendarbe.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private IAppointmentRespository appointmentRespository;
    @Override
    public void save(Appointment appointment) {
        appointmentRespository.save(appointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRespository.deleteById(id);
    }

    @Override
    public Appointment findByTitleAndStartAndAndEnd(String title, LocalDateTime start, LocalDateTime end) {
        return appointmentRespository.findByTitleAndStartAndAndEnd(title,start,end);
    }
}
