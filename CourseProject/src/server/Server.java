package server;


import connection.Connection;
import message.Message;
import message.MessageType;
import session.*;
import user.Log;
import user.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static Map<User, Connection> connectionMap = new ConcurrentHashMap<>();
    public static Map<String, User> users = new ConcurrentHashMap<>();
    public static Set<Ticket> boughtTickets = new TreeSet<>();
    public static List<Session> allSessions = new LinkedList<>();
    public static Set<Ticket> bookedTickets = new TreeSet<>();

    static {
        Film[] films = {new Film("Мстители","Фантастика",145,"бла бла бла")};
        users.put("a", new User("a", "a", false));
        users.put("ad", new User("ad", "ad", true));
        Calendar session1 = Calendar.getInstance();
        session1.set(2016,10,15,18,45);
        Calendar session2 = Calendar.getInstance();
        session2.set(2016,10,15,14,10);
        ArrayList<Place> places = new ArrayList<>();
        places.add(new Place(20.5, StatePlace.AVAILABLE, 1));
        places.add(new Place(10.5, StatePlace.AVAILABLE, 2));
        places.add(new Place(10.5, StatePlace.AVAILABLE, 3));
        places.add(new Place(10.5, StatePlace.AVAILABLE, 4));
        places.add(new Place(15.5, StatePlace.AVAILABLE, 5));
        places.add(new Place(10.5, StatePlace.AVAILABLE, 6));
        places.add(new Place(10.5, StatePlace.AVAILABLE, 7));
        places.add(new Place(12.5, StatePlace.AVAILABLE, 8));
        places.add(new Place(10.5, StatePlace.AVAILABLE, 9));
        ArrayList<Place> places1 = new ArrayList<>();
        places1.add(new Place(22.7, StatePlace.AVAILABLE, 1));
        allSessions.add(new Session(session1, places, 160, films[0]));
        allSessions.add(new Session(session2, places1, 170, films[0]));
    }

    public static void sendBroadcastMessage(Message message) {
        connectionMap.entrySet()
                .forEach(pair -> {
                    try {
                        pair.getValue().send(message);
                    } catch (IOException e) {
                        System.out.println("Ошибка. Сообщение не было доставлено.");
                    }
                });
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            User user = null;

            SocketAddress address = null;
            try (Connection connection = Connection.build(socket)) {
                while (user == null) {
                    user = authorizationOrRegistration(connection);
                    if (user == null) continue;
                    ConsoleHelper.writeMessage("New User = " + user.getLogin());
                    System.out.println(connection);
                    connection.serverMainLoop(user);
                }

            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e);
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом ");
            }
            if (user != null) {
                connectionMap.remove(user);
                ConsoleHelper.writeMessage("Соединение с удаленным адресом закрыто");
            }

        }

        private User authorizationOrRegistration(Connection connection) throws IOException, ClassNotFoundException {
            do {
                Message answer = connection.receive();
                if (answer.getType() == MessageType.USER_AUTHORIZATION) {
                    User user = (User) answer.getData();
                    if (Log.authorization(user)) {
                        connectionMap.put(user, connection);
                        connection.send(new Message(MessageType.USER_ACCEPTED, answer.getData()));
                        return user;
                    } else {
                        connection.send(new Message(MessageType.USER_NOT_FOUND));
                        return null;
                    }
                } else if (answer.getType() == MessageType.USER_REGISTRATION) {
                    User user = (User) answer.getData();
                    if (Log.registration(user)) {
                        users.put(user.getLogin(), user);
                        connectionMap.put(user, connection);
                        connection.send(new Message(MessageType.USER_REGISTERED, answer.getData()));
                        return user;
                    } else {
                        connection.send(new Message(MessageType.USER_ALREADY_EXIST));
                        return null;
                    }
                }
            } while (true);
        }


        public static void sendBroadcastMessage(Message message) {
            try {
                for (Map.Entry<User, Connection> pair : connectionMap.entrySet()) {
                    pair.getValue().send(message);
                }
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Ошибка. Сообщение не было доставлено.");
            }
        }
    }

    public static void main(String... args) {
        ConsoleHelper.writeMessage("Enter port:");
        int port = ConsoleHelper.readInt();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            ConsoleHelper.writeMessage("Сервер успешно запущен на порту " + port);
            do {
                Socket newSocket = serverSocket.accept();
                new Handler(newSocket).start();
            } while (true);
        } catch (Exception e) {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException ignored) {
            }
            ConsoleHelper.writeMessage("Что-то пошло не так: " + e);
        }
    }

    public static Map<String, User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.put(user.getLogin(), user);
    }
}
