package com.example.calendarbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "appointment")
public class Appointment {
    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;
    @ManyToMany(mappedBy = "appointments")
    @JsonIgnore
    private Set<User> users;
    public Appointment() {}
    public Appointment(Long id, String title, String location, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.start = start;
        this.end = end;
    }

    public Appointment(Long id, String title, String location, LocalDateTime start, LocalDateTime end, Set<User> users) {
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
