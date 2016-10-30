package client;

import session.Place;
import session.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANYA on 04.10.2016.
 */
public class ClientGuiController {
    public void setAdmin(boolean isAdmin){

        model.setAdmin(isAdmin);

    }
    public boolean getAdmin(){
       return model.isAdmin();
    }

    private final ClientGuiModel model = new ClientGuiModel(new ClientGuiView(this));

    public void tryConnection(String ip, int port){
        model.connectionToServer(ip, port);
    }

    public void tryRegistration(){
        model.tryRegistration();
    }

    public void authorization(String name, String password){
        model.authorization(name, password);
    }

    public void registration(String name, String password){
        model.registration(name, password);
    }

    public void back(){
        model.back();
    }

    public void tryAddSession(){
        model.tryAddSession();
    }
    public void addSession(Session session){
        model.addSession(session);
    }
    public List<Session> getSessionList(){
        return model.getSessions();
    }
    public void buyOrBook(boolean flag, ArrayList<Place> selectedPlaces, Session s){
        model.buyOrBook(flag,selectedPlaces,s);
    }
    public static void main(String[] args) {
        new ClientGuiController();
    }
}
