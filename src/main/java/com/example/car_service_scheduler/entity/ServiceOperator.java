package com.example.car_service_scheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceOperatorId;

    private String name;

    @OneToMany(mappedBy = "serviceOperator",cascade = CascadeType.ALL)
    private List<AppointmentSlot> appointmentSlotList=new ArrayList<>();
}
