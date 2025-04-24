package kr.hhplus.be.server.infrastructure.payment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.hhplus.be.server.domain.payment.Payment;
import kr.hhplus.be.server.domain.payment.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Payment save(Payment payment) {
        em.persist(payment);
        return payment;
    }

    @Override
    public List<Payment> findByUserId(Long userId) {
        return em.createQuery("SELECT p FROM Payment p WHERE p.userId = :userId", Payment.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
