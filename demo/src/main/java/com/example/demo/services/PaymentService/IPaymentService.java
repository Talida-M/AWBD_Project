package com.example.demo.services.PaymentService;


import com.example.demo.dtos.PaymentDTO;
import com.example.demo.entity.PaymentDetail;

import javax.transaction.Transactional;
import java.util.List;


public interface IPaymentService {
    void createPayment(PaymentDTO pay);


    List<PaymentDetail> getPayments();
}
