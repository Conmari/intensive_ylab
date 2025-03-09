package scari.corp.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import scari.corp.model.Transaction;
import scari.corp.model.TransactionType;
import scari.corp.service.TransactionService;

public class TransactionConsoleUI {

    private final TransactionService transactionService;
    private final DateTimeFormatter dateFormatter;
    private final Scanner scanner;

    public TransactionConsoleUI(TransactionService transactionService, DateTimeFormatter dateFormatter, Scanner scanner) {
        this.transactionService = transactionService;
        this.dateFormatter = dateFormatter;
        this.scanner = scanner;
    }

    public void addTransaction(String userEmail) {
        try {
            System.out.println("Введите сумму:");
            double amount = scanner.nextDouble();
            scanner.nextLine(); 

            System.out.println("Введите категорию:");
            String category = scanner.nextLine();

            System.out.println("Введите дату (yyyy-MM-dd):");
            LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter);

            System.out.println("Введите описание:");
            String description = scanner.nextLine();

            System.out.println("Введите тип (INCOME/EXPENSE):");
            TransactionType type = TransactionType.valueOf(scanner.nextLine().toUpperCase());

            transactionService.addTransaction(amount, category, date, description, type, userEmail);
            System.out.println("Транзакция добавлена.");

        } catch (java.util.InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат суммы.");
            scanner.nextLine(); // Consume остаток строки
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: Неверный формат даты. Используйте yyyy-MM-dd.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Неверный тип транзакции. Используйте INCOME или EXPENSE.");
        }
    }

    public void editTransaction(String userEmail) {
        try {
            System.out.println("Введите ID транзакции для редактирования:");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("Введите новую сумму:");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.println("Введите новую категорию:");
            String category = scanner.nextLine();

            System.out.println("Введите новое описание:");
            String description = scanner.nextLine();

            transactionService.editTransaction(id, amount, category, description, userEmail);
            System.out.println("Транзакция отредактирована.");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ввода.");
            scanner.nextLine(); // Consume остаток строки
        }
    }

    public void deleteTransaction(String userEmail) {
        try {
            System.out.println("Введите ID транзакции для удаления:");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            transactionService.deleteTransaction(id, userEmail);
            System.out.println("Транзакция удалена.");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ID.");
            scanner.nextLine(); // Consume остаток строки
        }
    }

    public void viewTransactions(String userEmail) {
        System.out.println("Выберите способ фильтрации:");
        System.out.println("1. Все транзакции");
        System.out.println("2. По дате");
        System.out.println("3. По категории");
        System.out.println("4. По типу");

        int filterChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Transaction> transactions = null;

        switch (filterChoice) {
            case 1:
                transactions = transactionService.getAllTransactions(userEmail);
                break;
            case 2:
                try {
                    System.out.println("Введите дату (yyyy-MM-dd):");
                    LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter);
                    transactions = transactionService.getTransactionsByDate(date, userEmail);
                } catch (DateTimeParseException e) {
                    System.out.println("Ошибка: Неверный формат даты. Используйте yyyy-MM-dd.");
                    return;
                }
                break;
            case 3:
                System.out.println("Введите категорию:");
                String category = scanner.nextLine();
                transactions = transactionService.getTransactionsByCategory(category, userEmail);
                break;
            case 4:
                try {
                    System.out.println("Введите тип (INCOME/EXPENSE):");
                    TransactionType type = TransactionType.valueOf(scanner.nextLine().toUpperCase());
                    transactions = transactionService.getTransactionsByType(type, userEmail);
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: Неверный тип транзакции. Используйте INCOME или EXPENSE.");
                    return;
                }
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("\nТранзакции:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("Транзакции не найдены.");
        }
    }
}