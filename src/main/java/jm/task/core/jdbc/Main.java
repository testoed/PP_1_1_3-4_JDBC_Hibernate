package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Элон", "Маск", (byte)50);
        userService.saveUser("Гарри", "Поттер", (byte)30);
        userService.saveUser("Леонардо", "ДиКаприо", (byte)45);
        userService.saveUser("Тони", "Старк", (byte)35);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
