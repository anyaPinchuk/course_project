package connection;

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
import java.util.LinkedList;

/**
 * Created by ANYA on 21.09.2016.
 */
public class ConnectionAdmin extends Connection {
    private User user;

    @Override
    public void serverMainLoop(User user) throws IOException, ClassNotFoundException{
        synchronized (Server.allFilmSessions) {
            System.out.println("sessionlist");
            send(new Message(MessageType.SESSION_LIST, new LinkedList<>(Server.allFilmSessions)));
            send(new Message(MessageType.FILM_LIST, new LinkedList<>(Server.films)));
        }
        this.user = user;
        do{
            Message message = receive();
            switch (message.getType()){
                case ADD_SESSION: {
                    FilmSession filmSession = (FilmSession) message.getData();
                    filmSession.setId(FilmSession.getAutoId());
                    Film film = filmSession.getFilm();
                    for (Film f: Server.films) {
                        if (f.getFilmName().equals(film.getFilmName())){
                            film.setDuration(f.getDuration());
                            film.setDescription(f.getDescription());
                            film.setGenre(f.getGenre());
                        }
                    }
                    synchronized (Server.allFilmSessions){
                        Server.allFilmSessions.add(filmSession);
                        send(new Message(MessageType.SESSION_ADDED));
                        Server.sendBroadcastMessage(new Message(MessageType.SESSION_LIST, new LinkedList<>(Server.allFilmSessions)));
                    }
                    break;
                }
                case DELETE_SESSION:{
                    int id = (Integer) message.getData() - 1;
                    int flag = 0;
                    synchronized (Server.allFilmSessions){
                        for (FilmSession s:Server.allFilmSessions) {
                            if(s.getId() == id){
                                Server.allFilmSessions.remove(id);
                                flag++;
                            }
                        }
                    }
                    if (flag == 0){
                        send(new Message(MessageType.ID_NOT_FOUND));
                    }
                    else send(new Message(MessageType.SESSION_DELETED));
                    Server.sendBroadcastMessage(new Message(MessageType.SESSION_LIST, new LinkedList<>(Server.allFilmSessions)));
                    break;
                }
            }
        } while (true);
    }


    public ConnectionAdmin(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException {
        super(socket, objectOutputStream, objectInputStream);


    }
}
