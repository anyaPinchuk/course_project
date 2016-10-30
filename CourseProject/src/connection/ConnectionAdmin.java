package connection;

import message.Message;
import message.MessageType;
import server.Server;
import session.Session;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ANYA on 21.09.2016.
 */
public class ConnectionAdmin extends Connection {
    private User user;

    @Override
    public void serverMainLoop(User user) throws IOException, ClassNotFoundException{
        synchronized (Server.allSessions) {
            System.out.println("sessionlist");
            send(new Message(MessageType.SESSION_LIST, Server.allSessions));
        }
        this.user = user;
        do{
            Message message = receive();
            switch (message.getType()){
                case ADD_SESSION: {
                    Session session = (Session) message.getData();
                    session.setId(Session.getAutoId());
                    synchronized (Server.allSessions){
                        Server.allSessions.add(session);
                        send(new Message(MessageType.SESSION_ADDED));
                        Server.sendBroadcastMessage(new Message(MessageType.SESSION_LIST, Server.allSessions));
                    }
                    break;
                }
            }
        } while (true);
    }


    public ConnectionAdmin(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException {
        super(socket, objectOutputStream, objectInputStream);


    }
}
