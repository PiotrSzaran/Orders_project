package pl.szaran.service;

import lombok.experimental.UtilityClass;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.EGuarantee;
import pl.szaran.model.EPayment;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

// UserDataService.getString("....")
// @UtilityClass
public final class UserDataService {

    private static Scanner scanner = new Scanner(System.in);

    public static String getString(String message, String regex) {
        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches(regex)) {
            throw new MyException(ExceptionCode.SERVICE, "String value is not correct");
        }

        return text;
    }

    public static int getInt(String message) {
        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.SERVICE, "Int value is not correct");
        }

        return Integer.parseInt(text);
    }

    public static BigDecimal getBigDecimal(String message) {
        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.SERVICE, "BigDecimal value is not correct");
        }

        return new BigDecimal(text);
    }

    public static EPayment getPayment() {
        EPayment[] payments = EPayment.values();
        AtomicInteger counter = new AtomicInteger(1);

        Arrays.stream(payments).forEach(payment -> System.out.println(counter + ". " + payment));
        System.out.println("Wybierz numer płatności");
        String text = scanner.nextLine();

        if (!text.matches("[1-" + payments.length + "]")) {
            throw new MyException(ExceptionCode.SERVICE, "Payment option value is not correct");
        }

        return payments[Integer.parseInt(text) - 1];
    }

    public static EGuarantee getGuarantee() {
        EGuarantee[] guarantees = EGuarantee.values();
        AtomicInteger counter = new AtomicInteger(1);

        Arrays.stream(guarantees).forEach(guarantee -> System.out.println(counter + ". " + guarantee));
        System.out.println("Wybierz numer usługi gwarancyjnej");
        String text = scanner.nextLine();
        if (!text.matches("[1-" + guarantees.length + "]")) {
            throw new MyException(ExceptionCode.SERVICE, "Guarantee option value is not correct");
        }

        return guarantees[Integer.parseInt(text) - 1];
    }

    public static void close() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}