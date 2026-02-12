package com.nexusbus.tracker.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="f_name")
    private String fName;

    @Column(name="l_name")
    private String lName;

    @Column(name="password")
    private String password;

    @Column(name="email_id", unique=true)
    private String emailId;

    @Column(name="role")
    private String role;

    @OneToMany(mappedBy="users", cascade=CascadeType.ALL)
    @JsonManagedReference("user-trip")
    private List<BusTrip> busTrips=new ArrayList<>();
}
