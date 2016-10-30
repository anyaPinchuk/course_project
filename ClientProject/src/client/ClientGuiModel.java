package client;

import message.Message;
import message.MessageType;
import session.Place;
import session.Session;
import user.User;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ANYA on 04.10.2016.
 */
public class ClientGuiModel {
    private final ClientGuiView view;

    private List<Session> sessions = new LinkedList<>();

    public List<Session> getSessions() {
        return sessions;
    }

    private Connection connection = null;
    private User user = null;
    private boolean admin;

    public ClientGuiModel(ClientGuiView view) {
        this.view = view;
    }

    public enum ConnectionState {
        TRY_CONNECTION,
        AUTHORIZATION,
        REGISTRATION,
        CONNECTED,
        PERSON,
        CONNECTED_ADMIN,
        ADD_NEW_SESSION
    }

    public boolean isAdmin() {
        return admin;
    }

    public void connectError() {
        synchronized (lock) {
            if (nowConnectionState != ConnectionState.TRY_CONNECTION) {
                nowConnectionState = ConnectionState.TRY_CONNECTION;
                try {
                    if (connection != null)
                        connection.close();
                } catch (IOException e) {
                }
                connection = null;
                user = null;
                view.updateWindow(ConnectionState.TRY_CONNECTION);
            }
        }
    }

    private final Object lock = new Object();
    private ConnectionState nowConnectionState = ConnectionState.TRY_CONNECTION;

    public void back() {
        switch (nowConnectionState) {
            case AUTHORIZATION: {
                nowConnectionState = ConnectionState.PERSON;
                view.updateWindow(nowConnectionState);
                break;
            }
            case REGISTRATION: {
                nowConnectionState = ConnectionState.AUTHORIZATION;
                view.updateWindow(nowConnectionState);
                break;
            }
        }
    }

    public void connectAuthorization() {
        nowConnectionState = ConnectionState.AUTHORIZATION;
    }

    public void connectRegistration() {
        nowConnectionState = ConnectionState.REGISTRATION;
    }

    public void connectPerson() {
        nowConnectionState = ConnectionState.PERSON;
    }

    public void connectSuccess() {
        nowConnectionState = ConnectionState.CONNECTED;
    }

    public void connectSuccessAdmin() {
        nowConnectionState = ConnectionState.CONNECTED_ADMIN;
    }

    public void connectAddSession(){ nowConnectionState = ConnectionState.ADD_NEW_SESSION;}

    private ExecutorService executor = Executors.newFixedThreadPool(1);


    public void tryRegistration() {
        connectRegistration();
        view.updateWindow(nowConnectionState);
        System.out.println("connectRegist");
    }

    public void tryAddSession(){
        connectAddSession();
        view.updateWindow(nowConnectionState);
    }

    public void connectionToServer(String ip, int port) {
        // Try connect
        Future<Connection> future = executor.submit(() -> {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(ip), port), 5000);
            Connection connection = new Connection(socket);
            return connection;
        });
        // Wait
        while (!future.isDone()) {
            Thread.yield();
        }
        try {
            connection = future.get();

            connectPerson();
            view.updateWindow(nowConnectionState);


        } catch (Exception exception) {
            view.showMessageError("Не удалось установить соединение");
            close();
        }
    }

    public void setAdmin(boolean isAdmin) {
        admin = isAdmin;
        try {
            connection.connect(isAdmin);
            executor.submit(() -> {
                try {
                    loop();
                } catch (Exception exception) {
                    System.out.println(exception);
                    System.out.println("Бай-бай");
                    //connectError();
                }
            });
            connectAuthorization();
            view.updateWindow(nowConnectionState);
        } catch (Exception e) {
            connectError();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Loop
    public void loop() throws IOException, ClassNotFoundException {
        while (!Thread.currentThread().isInterrupted() && connection != null) {
            Message message = connection.receive();
            if (nowConnectionState == ConnectionState.AUTHORIZATION) {
                communicationAuthorization(message);
            } else if (nowConnectionState == ConnectionState.REGISTRATION) {
                communicationRegistration(message);
            } else if (nowConnectionState == ConnectionState.CONNECTED) {
                System.out.println("connected");
                communicationConnectUser(message);
            } else if (nowConnectionState == ConnectionState.CONNECTED_ADMIN) {
                System.out.println("connectedadm");
                communicationConnectAdmin(message);
            }
            else if (nowConnectionState == ConnectionState.ADD_NEW_SESSION){
                communicationAddSession(message);
            }
        }
    }

    public void communicationRegistration(Message message) {
        switch (message.getType()) {
            case USER_REGISTERED: {
                user = (User) message.getData();
                connectSuccess();
                view.updateWindow(nowConnectionState);
                view.showMessageInfo("Вы вошли как " + user.getLogin());
                break;
            }
            case USER_ALREADY_EXIST: {
                view.showMessageInfo("Пользователь с таким логином уже существует");
            }
        }
    }

    public void communicationAuthorization(Message message) {
        switch (message.getType()) {
            case USER_ACCEPTED: {
                user = (User) message.getData();
                if (user.isAdmin()) {
                    connectSuccessAdmin();
                } else connectSuccess();
                view.updateWindow(nowConnectionState);
                view.showMessageInfo("Вы вошли, как " + user.getLogin());
                break;
            }
            case USER_NOT_FOUND: {
                view.showMessageError("Неверные логин либо пароль");
            }
        }
    }

    public void communicationAddSession(Message message){
        switch (message.getType()){
            case SESSION_ADDED: {
                view.showMessageInfo("Сеанс добавлен");
                connectSuccessAdmin();
                view.updateWindow(nowConnectionState);
                break;
            }
        }
    }

    public void addSession(Session session){
        try {
            connection.send(new Message(MessageType.ADD_SESSION, session));
        } catch (Exception exception) {
            connectError();
        }
    }

    public void communicationConnectUser(Message message) {
        System.out.println(message.getType());
        switch (message.getType()) {
            case SESSION_LIST: {
                sessions = (LinkedList<Session>) message.getData();
                System.out.println(sessions);
                view.showInfoToUser(message.getType(), sessions);
                break;
            }

        }
    }

    public void communicationConnectAdmin(Message message) {
        System.out.println(message.getType());
        switch (message.getType()) {
            case SESSION_LIST: {
                sessions = (LinkedList<Session>) message.getData();
                view.showInfoToUser(message.getType(), sessions);
                break;
            }
            case FILM_LIST:{
                break;
            }
        }
    }



    public void buyOrBook(boolean flag, ArrayList<Place> selectedPlaces, Session s) {
        try {
            if (flag) {
                connection.send(new Message(MessageType.SELECTED_SESSION, s));
                connection.send(new Message(MessageType.BOOK_ALL, selectedPlaces));
            } else {
                connection.send(new Message(MessageType.SELECTED_SESSION, s));
                connection.send(new Message(MessageType.BUY_ALL, selectedPlaces));
            }
        } catch (Exception e) {
            connectError();
        }
    }

    public void authorization(String name, String password) {
        try {
            User user = new User(name, password, admin);
            connection.send(new Message(MessageType.USER_AUTHORIZATION, user));
        } catch (Exception exception) {
            connectError();
        }
    }

    public void registration(String name, String password) {
        try {
            User user = new User(name, password, admin);
            connection.send(new Message(MessageType.USER_REGISTRATION, user));
        } catch (Exception exception) {
            connectError();
        }
    }

    protected void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (IOException ignored) {
            System.out.println("Finish " + ignored);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }
}
