package pl.szaran.repository;

import pl.szaran.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends GenericRepository<Payment> {
    Optional<Payment> findByName (String name);
}
