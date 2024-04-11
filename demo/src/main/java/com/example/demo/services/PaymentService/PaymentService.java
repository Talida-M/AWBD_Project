package com.example.demo.services.PaymentService;

import com.example.demo.dtos.PaymentDTO;
import com.example.demo.entity.*;
import com.example.demo.repositories.AppointmentsRepo;
import com.example.demo.repositories.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PaymentService implements IPaymentService{
    @Autowired
      PaymentRepo paymentRepo;
    @Autowired
    AppointmentsRepo appointmentsRepo;

    public PaymentService(PaymentRepo paymentRepo, AppointmentsRepo appointmentsRepo) {
        this.paymentRepo = paymentRepo;
        this.appointmentsRepo = appointmentsRepo;
    }

    @Transactional
    @Override
    public void createPayment(PaymentDTO pay) {
        Appointment app = appointmentsRepo.getAppointmentById(pay.getAppointment_id());
        PaymentDetail newapp = new PaymentDetail();
        newapp.setAppointment_id(app);
        newapp.setPaymentDate(pay.getPaymentDate());
        newapp.setAmount(pay.getAmount());
        newapp.setCurrency(pay.getCurrency());
        newapp.setStatus("Success");

         paymentRepo.save(newapp);
    }

    @Override
    public List<PaymentDetail> getPayments() {
        return paymentRepo.getPaymentDetails();
    }
}
