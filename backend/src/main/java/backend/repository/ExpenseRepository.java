package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}