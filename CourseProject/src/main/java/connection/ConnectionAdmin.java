package connection;

import dao.FilmDAO;
import dao.FilmSessionDAO;
import message.Message;
import message.MessageType;
import server.Server;
import session.Film;
import session.FilmSession;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ANYA on 21.09.2016.
 */
public class ConnectionAdmin extends Connection {
    private User user;

    @Override
    public void serverMainLoop(User user) throws IOException, ClassNotFoundException, SQLException {
        FilmDAO films = new FilmDAO();
        FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
        List<FilmSession> allFilms = filmSessionDAO.findAll();
        synchronized (allFilms) {
            List<Film> filmList = films.findAll();
            System.out.println("sessionlist");
            send(new Message(MessageType.SESSION_LIST, allFilms));
            if (!filmList.isEmpty())
                send(new Message(MessageType.FILM_LIST, filmList));
        }
        this.user = user;
        do {
            Message message = receive();
            switch (message.getType()) {
                case ADD_SESSION: {
                    List<Film> filmList = films.findAll();
                    FilmSession filmSession = (FilmSession) message.getData();
                    Film film = filmSession.getFilm();
                    if (!filmList.isEmpty()) {
                        for (Film f : filmList) {
                            if (f.getFilmName().equals(film.getFilmName())) {
                                film.setDescription(f.getDescription());
                                film.setGenre(f.getGenre());
                                break;
                            }
                        }
                    }
                    synchronized (allFilms) {
                        filmSessionDAO.insert(filmSession);
                        allFilms = filmSessionDAO.findAll();
                        send(new Message(MessageType.SESSION_ADDED));
                        Server.sendBroadcastMessage(new Message(MessageType.SESSION_LIST, allFilms));
                    }
                    break;
                }
                case DELETE_SESSION: {
                    int id = (Integer) message.getData();
                    int flag = 0;
                    allFilms = filmSessionDAO.findAll();
                    synchronized (allFilms) {
                        for (FilmSession s : allFilms) {
                            if (s.getId() == id) {
                                filmSessionDAO.deleteById(id);
                                flag++;
                            }
                        }
                    }
                    if (flag == 0) {
                        send(new Message(MessageType.ID_NOT_FOUND));
                    } else send(new Message(MessageType.SESSION_DELETED));
                    allFilms = filmSessionDAO.findAll();
                    Server.sendBroadcastMessage(new Message(MessageType.SESSION_LIST, allFilms));
                    break;
                }
            }
        } while (true);
    }


    public ConnectionAdmin(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException {
        super(socket, objectOutputStream, objectInputStream);


    }
}
