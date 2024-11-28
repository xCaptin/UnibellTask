package org.example.unibelltask.repository;

import org.example.unibelltask.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {

    List<Phone> findByClientClientId(UUID clientId);
}
