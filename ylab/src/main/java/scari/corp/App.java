package scari.corp;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import scari.corp.model.User;
import scari.corp.service.Authenticator;
import scari.corp.service.RegistrationService;
import scari.corp.service.TransactionService;
import scari.corp.ui.TransactionConsoleUI;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);
        RegistrationService registrationService = new RegistrationService();
        Authenticator authenticator = new Authenticator(registrationService); 
        TransactionService transactionService = new TransactionService();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TransactionConsoleUI transactionUI = new TransactionConsoleUI(transactionService, dateFormatter, scanner); 

        User loggedInUser = null;

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Регистрация");
            System.out.println("2. Авторизация");
            if (loggedInUser != null) {
                System.out.println("3. Редактировать профиль");
                System.out.println("4. Удалить аккаунт");
                System.out.println("5. Добавить транзакцию");
                System.out.println("6. Редактировать транзакцию");
                System.out.println("7. Удалить транзакцию");
                System.out.println("8. Просмотреть транзакции");
                System.out.println("9. Выйти из аккаунта");
                System.out.println("10. Выход");
            } else {
                System.out.println("3. Выход");
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (loggedInUser == null) {
                switch (choice) {
                    case 1:
                        registrationService.register(scanner);
                        break;
                    case 2:
                        loggedInUser = authenticator.login(scanner);
                        if (loggedInUser != null) {
                            System.out.println("Добро пожаловать, " + loggedInUser.getName() + "!");
                        }
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный выбор.");
                }
            } else {
                switch (choice) {
                    case 3:
                        registrationService.editProfile(scanner, loggedInUser.getEmail());
                        break;
                    case 4:
                        if (registrationService.deleteAccount(loggedInUser.getEmail())) {
                            System.out.println("Аккаунт удален.");
                            loggedInUser = null;
                        } else {
                            System.out.println("Ошибка при удалении аккаунта.");
                        }
                        break;
                    case 5:
                        transactionUI.addTransaction(loggedInUser.getEmail()); // Вызываем метод из TransactionConsoleUI
                        break;
                    case 6:
                        transactionUI.editTransaction(loggedInUser.getEmail()); // Вызываем метод из TransactionConsoleUI
                        break;
                    case 7:
                        transactionUI.deleteTransaction(loggedInUser.getEmail()); // Вызываем метод из TransactionConsoleUI
                        break;
                    case 8:
                        transactionUI.viewTransactions(loggedInUser.getEmail()); // Вызываем метод из TransactionConsoleUI
                        break;
                    case 9:
                        loggedInUser = null;
                        System.out.println("Вы вышли из аккаунта.");
                        break;
                    case 10:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный выбор.");
                }
            }
        }
    }
}