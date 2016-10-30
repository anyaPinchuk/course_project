package client;


import message.Message;
import message.MessageType;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by ANYA on 20.09.2016.
 */

public class Connection implements Closeable{
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket) throws IOException
    {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    public void send(Message message) throws IOException{
        synchronized (out){
            out.writeObject(message);
            out.flush();
        }
    }

    public void connect(boolean isAdmin) throws Exception {
        System.out.println("1");
        if (((Message) in.readObject()).getType() == MessageType.CONNECTION_REQUEST) {
            System.out.println("2");
            if (isAdmin){
                out.writeObject(new Message(MessageType.USER_ADMIN));
                out.flush();
                System.out.println("3");
            }
            else {
                out.writeObject(new Message(MessageType.COMMON_USER));
                out.flush();
            }

        } else throw new Exception("Не удалось подключиться к серверу");
    }

    public Message receive() throws IOException, ClassNotFoundException{
        synchronized (in){
            return (Message)in.readObject();
        }
    }


    public void close() throws IOException{
        socket.close();
        out.close();
        in.close();
    }
}
