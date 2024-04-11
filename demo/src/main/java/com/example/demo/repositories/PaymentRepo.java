package com.example.demo.repositories;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentDetail,Integer> {
    @Query("SELECT  app" +
            " FROM PaymentDetail app " +
            " order by app.paymentDate DESC ")
    List<PaymentDetail> getPaymentDetails();


}
