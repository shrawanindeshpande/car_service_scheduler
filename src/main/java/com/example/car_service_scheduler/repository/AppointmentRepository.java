package com.example.car_service_scheduler.repository;

import com.example.car_service_scheduler.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentSlot,Integer> {
}
