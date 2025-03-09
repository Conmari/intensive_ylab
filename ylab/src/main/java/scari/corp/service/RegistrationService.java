package scari.corp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import scari.corp.model.User;

public class RegistrationService {

    private Map<String, User> users = new HashMap<>();

    public void register(Scanner scanner) {
        System.out.println("Введите email:");
        String email = scanner.nextLine();

        if (users.containsKey(email)) {
            System.out.println("Пользователь с таким email уже существует.");
            return;
        }

        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        User newUser = new User(email, password, name);
        users.put(email, newUser);
        System.out.println("Регистрация прошла успешно!");
    }

    public User findUserByEmail(String email) {
        return users.get(email);
    }

    public void editProfile(Scanner scanner, String email) {
        User user = users.get(email);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        System.out.println("Редактирование профиля для " + email);
        System.out.println("Введите новое имя (или оставьте пустым, чтобы оставить прежним):");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            user.setName(newName);
        }

        System.out.println("Введите новый пароль (или оставьте пустым, чтобы оставить прежним):");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }

        System.out.println("Профиль обновлен: ");
    }

    public boolean deleteAccount(String email) {
        if (users.containsKey(email)) {
            users.remove(email);
            return true; // Удаление прошло успешно
        }
        return false; // Пользователь с таким email не найден
    }
}
