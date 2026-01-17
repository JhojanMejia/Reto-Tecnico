package com.reto.tasks.repository;

import com.reto.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Aquí Spring Data JPA ya nos da métodos como save(), findById(), delete(), etc.
}