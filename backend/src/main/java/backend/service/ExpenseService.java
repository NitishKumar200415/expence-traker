package backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.model.Expense;
import backend.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public Expense saveExpense(Expense expense) {

        // 🔥 anomaly logic
        if (expense.getAmount() > 10000) {
            expense.setStatus("ANOMALY");
        } else {
            expense.setStatus("NORMAL");
        }

        return repo.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }
}