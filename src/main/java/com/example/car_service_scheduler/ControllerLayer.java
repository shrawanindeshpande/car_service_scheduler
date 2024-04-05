package com.example.car_service_scheduler;

import com.example.car_service_scheduler.entity.AppointmentSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ControllerLayer {

    @Autowired
    private  ServiceLayer serviceLayer;
    @PostMapping("addServiceOperator")
    public ResponseEntity addServiceOperator(@RequestParam String name){
        String msg= serviceLayer.addServiceOperator(name);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    @PostMapping("createAppointmentSlot")
    public ResponseEntity createAppointmentSlot(@RequestParam int serviceOperatorId){
        String msg= serviceLayer.createAppointmentSlot(serviceOperatorId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    @PostMapping("/bookAppointment")
    public ResponseEntity bookAppointment(@RequestParam int serviceOperatorId,@RequestParam int startTime){
        AppointmentSlot appointmentSlot = serviceLayer.bookAppointment(serviceOperatorId,startTime);
        return new ResponseEntity<>(appointmentSlot,HttpStatus.OK);
    }
//
    @DeleteMapping("/cancelAppointment")
    public ResponseEntity cancelAppointment(@RequestParam int appointementId){
        String msg= serviceLayer.cancelAppointment(appointementId);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
//
    @PostMapping("/rescheduleAppointment")
    public ResponseEntity rescheduleAppointment(@RequestParam int appointmentId,@RequestParam int startTime){
        AppointmentSlot appointmentSlot=serviceLayer.rescheduleAppointment(appointmentId,startTime);
        return new ResponseEntity<>(appointmentSlot,HttpStatus.OK);
    }
//
    @GetMapping("/showBookedAppointment")
    public ResponseEntity showBookedAppointment(@RequestParam int serviceOperatorId){
        List<String> bookedSlots=serviceLayer.getBookedAppointments(serviceOperatorId);
        return new ResponseEntity<>(bookedSlots,HttpStatus.OK);
    }
//
    @GetMapping("/showAvailableAppointment")
    public ResponseEntity showAvailableAppointment(@RequestParam int serviceOperatorId){
        List<String> availableSlotList=serviceLayer.getAvailableSlots(serviceOperatorId);
        return new ResponseEntity<>(availableSlotList,HttpStatus.OK);
    }

}
