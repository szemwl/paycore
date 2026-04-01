package org.paycore.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * Идентификатор заказа.
     * Генерируется на стороне сервера, клиент не должен передавать его в запросе.
     */
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    /**
     * Счёт отправителя.
     * Обязательное поле, не должно быть пустым.
     */
    @NotBlank(message = "Поле sourceAccount обязательно и не должно быть пустым")
    @Size(max = 32, message = "Поле sourceAccount не должно превышать 32 символа")
    @Column(name = "source_account", nullable = false, length = 32)
    private String sourceAccount;

    /**
     * Счёт получателя.
     * Обязательное поле, не должно быть пустым.
     */
    @NotBlank(message = "Поле destinationAccount обязательно и не должно быть пустым")
    @Size(max = 32, message = "Поле destinationAccount не должно превышать 32 символа")
    @Column(name = "destination_account", nullable = false, length = 32)
    private String destinationAccount;

    /**
     * Сумма перевода.
     * Должна быть больше 0.
     */
    @DecimalMin(value = "0.01", inclusive = true, message = "Поле amount должно быть больше 0")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    /**
     * Валюта операции.
     * Ожидается код в формате ISO 4217, например USD, EUR, BYN.
     */
    @NotBlank(message = "Поле currency обязательно и не должно быть пустым")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Поле currency должно содержать 3 заглавные буквы, например USD")
    @Column(nullable = false, length = 3)
    private String currency;

    /**
     * Статус заказа.
     * На создании заказа выставляется сервером.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;

    /**
     * Описание заказа.
     * Необязательное поле.
     */
    @Size(max = 255, message = "Поле description не должно превышать 255 символов")
    @Column(length = 255)
    private String description;

    /**
     * Дата и время создания заказа.
     * Устанавливается сервером автоматически.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления заказа.
     * Устанавливается сервером автоматически.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Заполняем технические поля перед первым сохранением в БД.
     */
    @PrePersist
    public void beforeCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Обновляем время изменения записи перед апдейтом в БД.
     */
    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
