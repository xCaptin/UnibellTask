package org.example.unibelltask.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Сущность для представления клиента.
 * Клиент может иметь несколько телефонов и email.
 *
 * @author Kirill Shinkarev.
 */
@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    /**
     * Уникальный идентификатор клиента.
     */
    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private UUID clientId;

    /**
     * Имя клиента. Не должно быть пустым.
     */
    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    /**
     * Фамилия клиента. Не должна быть пустой.
     */
    @NotBlank(message = "Фамилия не должна быть пустым")
    private String lastName;

    /**
     * Список телефонов клиента.
     * Управляется каскадно и поддерживается связь с клиентом.
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Phone> phones = new HashSet<>();

    /**
     * Список email клиента.
     * Управляется каскадно и поддерживается связь с клиентом.
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Email> emails = new HashSet<>();
}
