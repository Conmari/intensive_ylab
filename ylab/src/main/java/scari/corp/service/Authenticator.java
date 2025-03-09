package scari.corp.service;

import java.util.Scanner;

import scari.corp.model.User;

public class Authenticator {

    private RegistrationService registrationService;

    public Authenticator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public User login(Scanner scanner) {
        System.out.println("Введите email:");
        String email = scanner.nextLine();

        User user = registrationService.findUserByEmail(email);

        if (user == null) {
            System.out.println("Пользователь с таким email не найден.");
            return null;
        }

        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (user.getPassword().equals(password)) {
            System.out.println("Авторизация прошла успешно!");
            return user;
        } else {
            System.out.println("Неверный пароль.");
            return null;
        }
    }

}
