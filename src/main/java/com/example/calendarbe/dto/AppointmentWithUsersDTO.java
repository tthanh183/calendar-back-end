package com.example.calendarbe.dto;

import com.example.calendarbe.model.User;

import java.time.LocalDateTime;
import java.util.Set;

public class AppointmentWithUsersDTO {
    private Long id;
    private String title;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;
    private Set<User> users;
    public AppointmentWithUsersDTO() {}
    public AppointmentWithUsersDTO(Long id, String title, String location, LocalDateTime start, LocalDateTime end, Set<User> users) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.start = start;
        this.end = end;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
