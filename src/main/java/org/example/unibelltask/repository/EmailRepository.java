package org.example.unibelltask.repository;

import org.example.unibelltask.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {

    List<Email> findByClientClientId(UUID clientId);
}
