package session;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ANYA on 07.10.2016.
 */
public class Ticket {
    private String loginOfUser;
    private int placeNumber;
    private Session session;

    public Ticket() {
    }

    public Ticket(String loginOfUser, int placeNumber, Session session) {
        this.loginOfUser = loginOfUser;
        this.placeNumber = placeNumber;
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getLoginOfUser() {
        return loginOfUser;
    }

    public void setLoginOfUser(String loginOfUser) {
        this.loginOfUser = loginOfUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return loginOfUser != null ? loginOfUser.equals(ticket.loginOfUser) : ticket.loginOfUser == null;

    }

    @Override
    public int hashCode() {
        int result = loginOfUser != null ? loginOfUser.hashCode() : 0;
        result = 31 * result + placeNumber;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        return result;
    }
}
