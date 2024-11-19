package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;


import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl extends Util implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT)";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            logger.info("Таблица пользователей успешно создана.");
        } catch (HibernateException e) {
            logger.severe("Ошибка при создании таблицы пользователей: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS mydbtest.users").executeUpdate();
            session.getTransaction().commit();
            logger.info("Таблица пользователей успешно удалена.");
        } catch (HibernateException e) {
            logger.severe("Ошибка при удалении таблицы пользователей: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            logger.info("Пользователь " + name + " " + lastName + " успешно сохранен.");
        } catch (HibernateException e) {
            logger.severe("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                logger.info("Пользователь с id " + id + " успешно удален.");
            } else {
                logger.warning("Пользователь с id " + id + " не найден.");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.severe("Ошибка при удалении пользователя по id: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
            session.getTransaction().commit();
            logger.info("Получено " + users.size() + " пользователей.");
        } catch (HibernateException e) {
            logger.severe("Ошибка при получении всех пользователей: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            logger.info("Таблица пользователей очищена.");
        } catch (HibernateException e) {
            logger.severe("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }
}
