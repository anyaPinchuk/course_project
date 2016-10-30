package connection;

import message.Message;
import message.MessageType;
import server.Server;

import static server.Server.*;

import session.Film;
import session.Place;
import session.Session;
import session.StatePlace;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.jar.Pack200;

/**
 * Created by ANYA on 21.09.2016.
 */
public class ConnectionCommonUser extends Connection {

    private User user;
    private Session selectedSession;

    public ConnectionCommonUser(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException {
        super(socket, objectOutputStream, objectInputStream);
    }

    @Override
    public void serverMainLoop(User user) throws IOException, ClassNotFoundException {
        synchronized (Server.allSessions) {
            System.out.println("sessionlist");
            send(new Message(MessageType.SESSION_LIST, new LinkedList<>(allSessions)));
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
                    selectedSession = allSessions.get(allSessions.indexOf(message.getData()));
                    break;
                }
            }
        } while (true);
    }

    public void buyPlaces(ArrayList<Place> places) {
        synchronized (allSessions) {
            Session session = allSessions.stream().filter(s -> s.equals(selectedSession)).findFirst().orElse(null);
            if (session != null)
                session.getAllPlaces().stream().filter(places::contains).forEach(place -> place.setStatePlace(StatePlace.BOUGHT));
            sendBroadcastMessage(new Message(MessageType.SESSION_LIST,  new LinkedList<>(allSessions)));
        }
    }

    public void bookPlaces(ArrayList<Place> places) {
        synchronized (allSessions) {
            Session session = allSessions.stream().filter(s -> s.equals(selectedSession)).findFirst().orElse(null);
            if (session != null) {
                session.getAllPlaces().stream().filter(places::contains).forEach(place -> place.setStatePlace(StatePlace.BOOKED));
            }
            LinkedList list = new LinkedList();
            allSessions.forEach(session2 -> list.add(new Session(session2)));
            sendBroadcastMessage(new Message(MessageType.SESSION_LIST, list));
        }
    }

}
