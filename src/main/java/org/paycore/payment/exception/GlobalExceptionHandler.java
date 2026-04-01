package org.paycore.payment.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    /**
     * Обработка ошибок валидации полей тела запроса.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        errorLogger.error(
                "Ошибка валидации. Метод: {}, URI: {}, Ошибки полей: {}",
                request.getMethod(),
                request.getRequestURI(),
                fields
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Некорректный запрос");
        response.put("message", "Ошибка валидации входных данных");
        response.put("fields", fields);

        return response;
    }

    /**
     * Обработка случая, когда тело запроса отсутствует
     * или передан битый / некорректный JSON.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEmptyOrInvalidBody(HttpMessageNotReadableException ex,
                                                        HttpServletRequest request) {
        errorLogger.error(
                "Ошибка чтения тела запроса. Метод: {}, URI: {}, Причина: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage(),
                ex
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Некорректный запрос");
        response.put("message", "Тело запроса отсутствует или имеет неверный формат");

        return response;
    }

    /**
     * Обработка ошибок типов параметров.
     * Например: кривой UUID в path или невалидное значение enum в query param.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                  HttpServletRequest request) {
        errorLogger.error(
                "Ошибка типа параметра. Метод: {}, URI: {}, Параметр: {}, Значение: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getName(),
                ex.getValue(),
                ex
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Некорректный запрос");
        response.put("message", "Передан параметр в неверном формате: " + ex.getName());

        return response;
    }

    /**
     * Обработка случая, когда запрашиваемый заказ не найден.
     * Например: передан несуществующий UUID заказа.
     */
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleOrderNotFound(OrderNotFoundException ex,
                                                   HttpServletRequest request) {
        errorLogger.error(
                "Заказ не найден. Метод: {}, URI: {}, Причина: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 404);
        response.put("error", "Ресурс не найден");
        response.put("message", ex.getMessage());

        return response;
    }

    /**
     * Общий обработчик прочих неожиданных ошибок.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGenericException(Exception ex,
                                                      HttpServletRequest request) {
        errorLogger.error(
                "Непредвиденная ошибка. Метод: {}, URI: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 500);
        response.put("error", "Внутренняя ошибка сервера");
        response.put("message", "На сервере произошла непредвиденная ошибка");

        return response;
    }
}
