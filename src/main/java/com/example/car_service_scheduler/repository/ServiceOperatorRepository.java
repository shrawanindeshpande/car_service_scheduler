package com.example.car_service_scheduler.repository;

import com.example.car_service_scheduler.entity.ServiceOperator;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOperatorRepository extends JpaRepository<ServiceOperator, Integer> {
}
