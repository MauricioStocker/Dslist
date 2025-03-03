package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
}