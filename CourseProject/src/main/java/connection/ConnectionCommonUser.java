package connection;

import message.Message;
import message.MessageType;
import server.Server;

import static server.Server.*;

import session.FilmSession;
import session.Place;
import session.StatePlace;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

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
    public void serverMainLoop(User user) throws IOException, ClassNotFoundException {
        synchronized (Server.allFilmSessions) {
            System.out.println("sessionlist");
            send(new Message(MessageType.SESSION_LIST, new LinkedList<>(allFilmSessions)));
            send(new Message(MessageType.FILM_LIST, new LinkedList<>(Server.films)));
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
                    selectedFilmSession = allFilmSessions.get(allFilmSessions.indexOf(message.getData()));
                    break;
                }
            }
        } while (true);
    }

    public void buyPlaces(ArrayList<Place> places) {
        synchronized (allFilmSessions) {
            FilmSession filmSession = allFilmSessions.stream().filter(s -> s.equals(selectedFilmSession)).findFirst().orElse(null);
            if (filmSession != null)
                filmSession.getAllPlaces().stream().filter(places::contains).forEach(place -> place.setStatePlace(StatePlace.BOUGHT));
            sendBroadcastMessage(new Message(MessageType.SESSION_LIST,  new LinkedList<>(allFilmSessions)));
        }

    }

    public void bookPlaces(ArrayList<Place> places) {
        synchronized (allFilmSessions) {
            FilmSession filmSession = allFilmSessions.stream().filter(s -> s.equals(selectedFilmSession)).findFirst().orElse(null);
            if (filmSession != null) {
                filmSession.getAllPlaces().stream().filter(places::contains).forEach(place -> place.setStatePlace(StatePlace.BOOKED));
            }
            LinkedList list = new LinkedList();
            allFilmSessions.forEach(session2 -> list.add(new FilmSession(session2)));
            sendBroadcastMessage(new Message(MessageType.SESSION_LIST, list));
        }
    }

}
