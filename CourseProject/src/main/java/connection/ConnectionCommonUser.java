package connection;

import dao.FilmDAO;
import dao.FilmSessionDAO;
import message.Message;
import message.MessageType;
import server.Server;

import static server.Server.*;

import session.Film;
import session.FilmSession;
import session.Place;
import session.StatePlace;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ANYA on 21.09.2016.
 */
public class ConnectionCommonUser extends Connection {

    private User user;
    private FilmSession selectedFilmSession;

    public ConnectionCommonUser(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException {
        super(socket, objectOutputStream, objectInputStream);
    }

    @Override
    public void serverMainLoop(User user) throws IOException, ClassNotFoundException, SQLException {
        FilmDAO films = new FilmDAO();
        FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
        List<FilmSession> allFilms = filmSessionDAO.findAll();
        synchronized (allFilms) {
            List<Film> filmList = films.findAll();
            System.out.println("sessionlist");
            if (!allFilms.isEmpty())
            send(new Message(MessageType.SESSION_LIST, allFilms));
            if (!filmList.isEmpty())
            send(new Message(MessageType.FILM_LIST, filmList));
        }
        this.user = user;
        do {
            Message message = receive();
            switch (message.getType()) {
                case BOOK_ALL: {
                    bookPlaces((ArrayList<Place>) message.getData());
                    break;
                }
                case BUY_ALL: {
                    buyPlaces((ArrayList<Place>) message.getData());
                    break;
                }
                case SELECTED_SESSION: {
                    selectedFilmSession = allFilms.get(allFilms.indexOf(message.getData()));
                    break;
                }
            }
        } while (true);
    }

    public void buyPlaces(ArrayList<Place> places) throws SQLException {
        FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
        List<FilmSession> allFilms = filmSessionDAO.findAll();

        synchronized (allFilms) {
            FilmSession filmSession = allFilms.stream().filter(s -> s.equals(selectedFilmSession)).findFirst().orElse(null);
            if (filmSession != null)
                //сделать update in db stateplace field
                filmSession.getAllPlaces().stream().filter(places::contains).forEach(place -> place.setStatePlace(StatePlace.BOUGHT.getS()));
            allFilms = filmSessionDAO.findAll();
            sendBroadcastMessage(new Message(MessageType.SESSION_LIST,  allFilms));
        }

    }

    public void bookPlaces(ArrayList<Place> places) throws SQLException{
        FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
        List<FilmSession> allFilms = filmSessionDAO.findAll();
        synchronized (allFilms) {
            FilmSession filmSession = allFilms.stream().filter(s -> s.equals(selectedFilmSession)).findFirst().orElse(null);
            if (filmSession != null) {
                //сделать update in db stateplace field
                filmSession.getAllPlaces().stream().filter(places::contains).forEach(place -> place.setStatePlace(StatePlace.BOOKED.getS()));
            }
            allFilms = filmSessionDAO.findAll();
            sendBroadcastMessage(new Message(MessageType.SESSION_LIST,  allFilms));
        }
    }

}
