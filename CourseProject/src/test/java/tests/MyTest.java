package tests;

import dao.*;
import org.junit.Test;
import session.Film;
import session.FilmSession;
import session.Place;
import session.Ticket;
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
            User user = new User("vov", "vova", false);
            userDAO.insert(user);
            //testFindAllUsers();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testDeleteUser() {
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.deleteById(10);
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
            userDAO.updateFieldById(12, "ann");
            testFindAllUsers();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testFindAllFilmSession(){
        FilmSessionDAO sessionDAO = new FilmSessionDAO();
        try {
            List<FilmSession> sessions = sessionDAO.findAll();
            for (FilmSession u : sessions) {
                System.out.println(u.getId() + " " + u.getDuration() + " " + u.getAllPlaces().get(0).getStatePlace());
            }
        } catch (SQLException e) {

        }
    }
    @Test
    public void testFindAllFilm(){
        FilmDAO sessionDAO = new FilmDAO();
        try {
            List<Film> films = sessionDAO.findAll();
            for (Film u : films) {
                System.out.println(u.getId() + " " + u.getFilmName());
            }
        } catch (SQLException e) {

        }
    }
    @Test
    public void testFindAllPlace(){
        PlaceDAO sessionDAO = new PlaceDAO();
        try {
            List<Place> places = sessionDAO.findAll();
            if (places!=null)
            for (Place u : places) {
                System.out.println(u.getPlace_id() + " " );
            }
        } catch (SQLException e) {

        }
    }

    @Test
    public void testFindAllTicket(){
        TicketDAO sessionDAO = new TicketDAO();
        try {
            List<Ticket> tickets = sessionDAO.findAll();
            for (Ticket u : tickets) {
                System.out.println(u.getTicket_id() + " " + u.getLoginOfUser());
            }
        } catch (SQLException e) {

        }
    }

    @Test
    public void testFindFilmById() {
        FilmDAO userDAO = new FilmDAO();
        try {
            Film u = userDAO.findById(1);
            System.out.println(u.getFilmName());
        } catch (SQLException e) {

        }
    }

    @Test
    public void testFindFilmByField() {
        FilmDAO userDAO = new FilmDAO();
        try {
            Film u = userDAO.findByField("Revengers");
            if (u != null)
                System.out.println(u.getDescription());
        } catch (SQLException e) {

        }
    }


}
