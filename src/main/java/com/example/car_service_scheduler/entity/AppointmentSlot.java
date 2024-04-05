package com.example.car_service_scheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AppointmentId;
    private int startTime;
    private int endTime;
    private boolean isAvailable;
    @JoinColumn
    @ManyToOne
    private  ServiceOperator serviceOperator;
}
