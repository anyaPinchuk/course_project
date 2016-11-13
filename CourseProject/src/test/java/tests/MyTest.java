package tests;

import dao.UserDAO;
import org.junit.Test;
import user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANYA on 12.11.2016.
 */

public class MyTest {
    @Test
    public void testFindAllUsers() {
        UserDAO userDAO = new UserDAO();
        try {
            List<User> users = userDAO.findAll();
            for (User u : users) {
                System.out.println(u.getLogin() + " " + u.getPassword());
            }
        } catch (SQLException e) {

        }
    }

    @Test
    public void testInsertUser() {
        UserDAO userDAO = new UserDAO();
        try {
            User user = new User("vova", "vova", false);
            userDAO.insert(user);
            testFindAllUsers();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testDeleteUser() {
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.deleteById(3);
            testFindAllUsers();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testFindUserById() {
        UserDAO userDAO = new UserDAO();
        try {
            User u = userDAO.findById(2);
            System.out.println(u.getLogin());
        } catch (SQLException e) {

        }
    }

    @Test
    public void testFindUserByField() {
        UserDAO userDAO = new UserDAO();
        try {
            User u = userDAO.findByField("gg");
            if (u != null)
                System.out.println(u.getId());
        } catch (SQLException e) {

        }
    }

    @Test
    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO();
        try {
            User u = new User("a", "ann", false);
            userDAO.updateById(2, u);
            testFindAllUsers();
        } catch (SQLException e) {

        }
    }

}
