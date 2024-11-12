package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // Укажите драйвер вашей БД
    private static final String DB_URL = "jdbc:mysql:///mydbtest"; // Укажите URL вашей БД
    private static final String DB_USER = "root"; // Укажите имя пользователя
    private static final String DB_PASSWORD = "1234"; // Укажите пароль

    private static final Logger logger = Logger.getLogger(Util.class.getName());
    // Метод для получения соединения
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Загрузка драйвера
            Class.forName(DB_DRIVER);
            // Установка соединения
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Соединение установлено");
        } catch (ClassNotFoundException e) {
            logger.severe("Ошибка: драйвер не найден.");
        } catch (SQLException e) {
            logger.severe("Ошибка при установлении соединения.");
        }
        return connection;
    }

}
