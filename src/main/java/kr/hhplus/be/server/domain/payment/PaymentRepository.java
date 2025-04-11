package kr.hhplus.be.server.domain.payment;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository {

    void save(Payment payment);

    List<Payment> findByUserId(Long userId);
}
