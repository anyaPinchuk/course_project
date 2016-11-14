package connection;

import message.Message;
import message.MessageType;
import user.User;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.SQLException;

/**
 * Created by ANYA on 20.09.2016.
 */
public abstract class Connection implements Closeable{
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException
    {
        this.socket = socket;
        out = objectOutputStream;
        in = objectInputStream;
    }
    public void send(Message message) throws IOException{
        synchronized (out){
            out.writeObject(message);
            out.flush();
        }
    }
    public Message receive() throws IOException, ClassNotFoundException{
        synchronized (in){
            return (Message)in.readObject();
        }
    }

    public static Connection build(Socket socket) throws IOException, ClassNotFoundException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        objectOutputStream.writeObject(new Message(MessageType.CONNECTION_REQUEST));
        objectOutputStream.flush();
        Message message = (Message)objectInputStream.readObject();
        switch (message.getType()){
            case COMMON_USER:
                return new ConnectionCommonUser(socket, objectOutputStream, objectInputStream);
            case USER_ADMIN:
                return new ConnectionAdmin(socket, objectOutputStream, objectInputStream);
        }
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
        throw new IOException();
    }
    public void close() throws IOException{
        socket.close();
        out.close();
        in.close();
    }

    public abstract void serverMainLoop(User user) throws IOException, ClassNotFoundException, SQLException;
}
