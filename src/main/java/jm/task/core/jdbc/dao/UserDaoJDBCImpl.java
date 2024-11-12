package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private Connection conn;

    public UserDaoJDBCImpl() {
        this.conn = getConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS mydbtest.users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS mydbtest.users";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO mydbtest.users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();

            System.out.println("User with name"  + name + ' ' +
                    "added to the database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM mydbtest.users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM mydbtest.users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                users.add(new User(name, lastName, age));
            }
            users.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE mydbtest.users";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

