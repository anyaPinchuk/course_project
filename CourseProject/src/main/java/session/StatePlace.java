package session;


/**
 * Created by ANYA on 07.10.2016.
 */
public enum StatePlace {
    AVAILABLE("Свободно"),
    BOOKED("Забронировано"),
    BOUGHT("Куплено");
    public String s;
    StatePlace() {
    }
    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    StatePlace(String s){
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }
}
