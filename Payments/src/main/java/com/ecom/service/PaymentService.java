package com.ecom.service;

import com.ecom.model.Payment;
import com.ecom.repo.PaymentRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepository;

    public PaymentService(PaymentRepo paymentRepository1) {
        this.paymentRepository = paymentRepository1;
    }

    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for Order ID: " + orderId));
    }

    public Payment updatePaymentStatus(Long paymentId, String newStatus) {
        Payment payment = getPaymentById(paymentId);
        payment.setPaymentStatus(newStatus);
        return paymentRepository.save(payment);
    }
}
