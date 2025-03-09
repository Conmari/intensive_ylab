package scari.corp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import scari.corp.model.Transaction;
import scari.corp.model.TransactionType;

public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    public void addTransaction(double amount, String category, LocalDate date, String description, TransactionType type, String userEmail) {
        Transaction transaction = new Transaction(nextId++, amount, category, date, description, type, userEmail);
        transactions.add(transaction);
    }

    public void editTransaction(int id, double amount, String category, String description, String userEmail) {
        Transaction transaction = findTransactionById(id, userEmail);
        if (transaction != null) {
            transaction.setAmount(amount);
            transaction.setCategory(category);
            transaction.setDescription(description);
        } else {
            System.out.println("Транзакция с id " + id + " не найдена.");
        }
    }

    public void deleteTransaction(int id, String userEmail) {
        transactions.removeIf(transaction -> transaction.getId() == id && transaction.getUserEmail().equals(userEmail));
    }

    public List<Transaction> getAllTransactions(String userEmail) {
        return transactions.stream()
                .filter(transaction -> transaction.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByDate(LocalDate date, String userEmail) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().equals(date) && transaction.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByCategory(String category, String userEmail) {
        return transactions.stream()
                .filter(transaction -> transaction.getCategory().equalsIgnoreCase(category) && transaction.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByType(TransactionType type, String userEmail) {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == type && transaction.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }


    private Transaction findTransactionById(int id, String userEmail) {
        return transactions.stream()
                .filter(transaction -> transaction.getId() == id && transaction.getUserEmail().equals(userEmail))
                .findFirst()
                .orElse(null);
    }

}
