package client;

import session.Film;
import session.FilmSession;
import session.Place;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ANYA on 04.10.2016.
 */
public class ClientGuiController {
    public void setAdmin(boolean isAdmin) {

        model.setAdmin(isAdmin);

    }

    public boolean getAdmin() {
        return model.isAdmin();
    }

    private final ClientGuiModel model = new ClientGuiModel(new ClientGuiView(this));

    public void tryConnection(String ip, int port) {
        model.connectionToServer(ip, port);
    }

    public void tryRegistration() {
        model.tryRegistration();
    }

    public void authorization(String name, String password) {
        model.authorization(name, password);
    }

    public void registration(String name, String password) {
        model.registration(name, password);
    }

    public void back() {
        model.back();
    }

    public void tryAddSession() {
        model.tryAddSession();
    }

    public void tryDeleteSession() {
        model.tryDeleteSession();
    }

    public List<Film> getFilms() {
        return model.getFilms();
    }

    public void addSession(Calendar date, ArrayList<Place> places, int duration, String filmName) {
        model.addSession(date,places,duration,filmName);
    }

    public void deleteSession(int id) {
        model.deleteSession(id);
    }

    public List<FilmSession> getSessionList() {
        return model.getFilmSessions();
    }

    public void buyOrBook(boolean flag, ArrayList<Place> selectedPlaces, FilmSession s) {
        model.buyOrBook(flag, selectedPlaces, s);
    }

    public static void main(String[] args) {
        new ClientGuiController();
    }
}
