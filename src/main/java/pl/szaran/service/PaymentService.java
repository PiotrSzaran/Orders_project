package pl.szaran.service;

import pl.szaran.dto.PaymentDTO;
import pl.szaran.model.EPayment;
import pl.szaran.model.Payment;
import pl.szaran.repository.PaymentRepository;
import pl.szaran.repository.PaymentRepositoryImpl;

import java.util.*;
import java.util.stream.Collectors;

public class PaymentService implements ModelMapper {

    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();

    public void addPaymentsToDB() {

        /* pomijam walidację gdyż dodaję je bezpośrednio z typu wyliczeniowego EPayment
        Upewniam się za to, czy podany typ jest już w bazie danych.
         */

        EnumSet<EPayment> paymentMethods = EnumSet.allOf(EPayment.class);
        var currentPayments = getEPayments();

        for (EPayment p : paymentMethods
        ) {
            Payment payment = null;
            if (currentPayments.contains(p)) {
                System.out.println("PAYMENT ALREADY IN DB");
            } else {
                PaymentDTO paymentDTO = PaymentDTO.builder().payment(p).build();
                payment = ModelMapper.fromPaymentDTOToPayment(paymentDTO);
                paymentRepository.saveOrUpdate(payment);
                System.out.println("PAYMENT " + p.name() + " ADDED TO DB");
            }
        }
    }

    public List<PaymentDTO> getPayments() {
        return paymentRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromPaymentToPaymentDTO)
                .collect(Collectors.toList());
    }

    public List<EPayment> getEPayments() {
        return paymentRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromPaymentToPaymentDTO)
                .map(p -> p.getPayment())
                .collect(Collectors.toList());
    }

    public Map<Integer, Payment> getMapOfPayments() {
        List<Payment> list = new ArrayList<>(paymentRepository.findAll());
        int i = 1;
        Map<Integer, Payment> paymentsMap = new HashMap<>();
        for (Payment p : list) {
            paymentsMap.put(i, p);
            i++;
        }
        return paymentsMap;
    }

    public Map<Integer, Payment> showPayments() {
        Map<Integer, Payment> map = getMapOfPayments();
        for (Map.Entry<Integer, Payment> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getPayment());
        }
        return map;
    }

    public void deletePayments() {
        //metoda nie zadziała ze względu na klucz obcy w SQL. Najpierw trzeba pozbyć się z bazy zamówień
        paymentRepository.deleteAll();
    }

}
