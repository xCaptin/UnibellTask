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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue
    @Column(name = "phone_id")
    private UUID phoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;


    @NotBlank(message = "Номер телефона не должен быть пустым")
    @Pattern(regexp = "\\+7\\d{10}|\\+375\\d{9}",
             message = "Некорректный формат номера телефона. Ожидается формат +7XXXXXXXXXX или +375XXXXXXXXX")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
}
