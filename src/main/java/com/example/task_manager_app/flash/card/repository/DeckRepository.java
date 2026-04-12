package com.example.task_manager_app.flash.card.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.task_manager_app.flash.card.entity.DeckEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<DeckEntity, Long> {
}
