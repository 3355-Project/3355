package com.example.project3355.card.repository;

import com.example.project3355.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Long countByColumnsId(Long columnId);
}
