package pl.szaran.validators;

import pl.szaran.dto.PaymentDTO;
import pl.szaran.model.EPayment;

import java.util.HashMap;
import java.util.Map;

public class PaymentValidator {

    /*
    Ta klasa jest niepotrzebna. Dostępne płatności znajdują się w EPayment i są dodawane
    za pomocą metody .addPaymentsToDB() klasy PaymentService
     */

    Map<String, String> validatePaymentData(PaymentDTO paymentDTO) {

        Map<String, String> errors = new HashMap<>();

        if (paymentDTO == null) {
            errors.put("payment", "payment object is null");
            return errors;
        }
        if (paymentDTO.getPayment() == null) {
            errors.put("payment", "payment is null");
            return errors;
        }
        if (!(paymentDTO.getPayment() instanceof EPayment)) {
            errors.put("payment", " payment incorrect value: " + paymentDTO.getPayment() + " Must be ENUM");
            return errors;
        }

        return errors;
    }
}
