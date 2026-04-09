package backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import backend.model.Expense;
import backend.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public Expense saveExpense(Expense expense) {

        // 🔥 Call Python ML API
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = Map.of(
                "amount", expense.getAmount()
        );

        Map response = restTemplate.postForObject(
                "http://localhost:5000/predict",
                request,
                Map.class
        );

        // Set result from ML
        expense.setStatus((String) response.get("status"));

// 👇 NEW
Double score = Double.valueOf(response.get("score").toString());
expense.setScore(score);

        return repo.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }
}