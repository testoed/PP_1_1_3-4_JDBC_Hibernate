package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class Util {

    private static SessionFactory factory;
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

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

    // Метод для создания сессии
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DB_DRIVER);
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, DB_USER);
                settings.put(Environment.PASS, DB_PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
