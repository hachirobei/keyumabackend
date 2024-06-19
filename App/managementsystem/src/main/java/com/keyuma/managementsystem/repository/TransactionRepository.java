package com.keyuma.managementsystem.repository;

import com.keyuma.managementsystem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
