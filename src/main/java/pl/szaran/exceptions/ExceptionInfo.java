package pl.szaran.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionInfo {
    private ExceptionCode exceptionCode;
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public ExceptionInfo(ExceptionCode exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ExceptionInfo: \n" +
                "\t Code: " + exceptionCode + '\n' +
                "\t Message: " + exceptionMessage + '\n' +
                "\t Time: " + exceptionDateTime;
    }
}