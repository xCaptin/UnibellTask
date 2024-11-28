package org.example.unibelltask.repository;

import org.example.unibelltask.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с клиентами.
 *
 * @author Kirill Shinkarev.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Page<Client> findAll(Pageable pageable);
}
