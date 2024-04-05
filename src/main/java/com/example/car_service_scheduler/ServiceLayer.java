package com.example.car_service_scheduler;

import com.example.car_service_scheduler.entity.AppointmentSlot;
import com.example.car_service_scheduler.entity.ServiceOperator;
import com.example.car_service_scheduler.repository.AppointmentRepository;
import com.example.car_service_scheduler.repository.ServiceOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceOperatorRepository serviceOperatorRepository;


    //add service operator create appointment slot for same;
    String addServiceOperator(String name){
        ServiceOperator operator=new ServiceOperator();
        operator.setName(name);
        serviceOperatorRepository.save(operator);
        return "new Service operator has been added to the database";
    }

    String createAppointmentSlot(int serviceOperatorId){
        ServiceOperator serviceOperator=serviceOperatorRepository.findById(serviceOperatorId).get();
        for(int i=0;i<24;i++){
            AppointmentSlot appointmentSlot=new AppointmentSlot();
            appointmentSlot.setStartTime(i);
            if(i==23){
                appointmentSlot.setEndTime(0);
            }
            else{
                appointmentSlot.setEndTime(i+1);
            }
            appointmentSlot.setAvailable(true);
            appointmentSlot.setServiceOperator(serviceOperator);
            serviceOperator.getAppointmentSlotList().add(appointmentSlot);
        }
        serviceOperatorRepository.save(serviceOperator);
        return "appointments for service operator "+serviceOperator.getServiceOperatorId()+" has been added";
    }

    //book appointment function

    AppointmentSlot bookAppointment(int serviceOperatorId,int startTime){
        ServiceOperator serviceOperator=serviceOperatorRepository.findById(serviceOperatorId).get();
        List<AppointmentSlot> appointmentSlotList=serviceOperator.getAppointmentSlotList();
        AppointmentSlot bookedSlot=null;
        for(AppointmentSlot appointmentSlot:appointmentSlotList){
            if(appointmentSlot.getStartTime()==startTime){
                appointmentSlot.setAvailable(false);
                bookedSlot=appointmentSlot;
                break;
            }
        }
        serviceOperatorRepository.save(serviceOperator);
        return bookedSlot;

    }

    String cancelAppointment(int appointmentSlotId){
        AppointmentSlot appointmentSlot=appointmentRepository.findById(appointmentSlotId).get();
        if(appointmentSlot.isAvailable()==true){
            return "appointment is not scheduled";
        }
        appointmentSlot.setAvailable(true);
        appointmentRepository.save(appointmentSlot);
        return "appointment has been canceled";
    }
    AppointmentSlot rescheduleAppointment(int appointmentSlotId,int startTime){
        AppointmentSlot appointmentSlot=appointmentRepository.findById(appointmentSlotId).get();
        ServiceOperator serviceOperator=appointmentSlot.getServiceOperator();
        cancelAppointment(appointmentSlotId);
        appointmentSlot=bookAppointment(serviceOperator.getServiceOperatorId(),startTime);
        serviceOperatorRepository.save(serviceOperator);
        return appointmentSlot;
    }

    List<String> getBookedAppointments(int serviceOperatorId){
        ServiceOperator serviceOperator=serviceOperatorRepository.findById(serviceOperatorId).get();
        List<String> bookedSlots=new ArrayList<>();
        for(AppointmentSlot slot:serviceOperator.getAppointmentSlotList()){
            if(!slot.isAvailable()){
                String slotTime=slot.getStartTime()+"-"+ slot.getEndTime();
                bookedSlots.add(slotTime);
            }
        }
        return bookedSlots;
    }

    List<String> getAvailableSlots(int serviceOperatorId){
        ServiceOperator serviceOperator=serviceOperatorRepository.findById(serviceOperatorId).get();
        int startTime=-1;
        int endTime=-1;
        int flag=0;
        List<String> availableSlotList=new ArrayList<>();
        for(AppointmentSlot appointmentSlot:serviceOperator.getAppointmentSlotList()){
            if(appointmentSlot.isAvailable()) {
//                startTime=appointmentSlot.getStartTime();
//                endTime=appointmentSlot.getEndTime();
//                availableSlotList.add(startTime+"-"+endTime);

                if (appointmentSlot.getStartTime() == endTime) {
                    endTime = appointmentSlot.getEndTime();
                } else {
                    //System.out.println(startTime+"-"+endTime);
                    if(startTime!=-1 && endTime!=-1){
                        availableSlotList.add(startTime + "-" + endTime);
                    }
                    startTime = appointmentSlot.getStartTime();
                    endTime = appointmentSlot.getEndTime();
                }
            }
        }
        availableSlotList.add(startTime+"-"+endTime);
        return availableSlotList;
    }

}
