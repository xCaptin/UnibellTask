package org.example.unibelltask.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Сущность для представления email клиента.
 *
 * @author Kirill Shinkarev.
 */
@Entity
@Table(name = "emails", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email_address", name = "uq_email_address")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    /**
     * Уникальный идентификатор email.
     */
    @Id
    @GeneratedValue
    @Column(name = "email_id")
    private UUID emailId;

    /**
     * Клиент, к которому принадлежит email.
     * Ссылка на сущность клиента.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;

    /**
     * Адрес электронной почты клиента.
     * Должен быть уникальным и соответствовать корректному формату email.
     */
    @NotBlank(message = "Email не должен быть пустым")
    @jakarta.validation.constraints.Email(message = "Некорректный формат email")
    @Column(name = "email_address", unique = true)
    private String emailAddress;
}
