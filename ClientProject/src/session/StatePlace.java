package session;

import java.io.Serializable;

/**
 * Created by ANYA on 07.10.2016.
 */
public enum StatePlace implements Serializable{
    AVAILABLE("Свободно"),
    BOOKED("Забронировано"),
    BOUGHT("Куплено");

    public String s;
    StatePlace() {
    }
    StatePlace(String s){
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }
}
