package session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ANYA on 07.10.2016.
 */
public class Session implements Serializable{
    private Calendar date;
    private double id;
    private static int autoId = 0;
    private ArrayList<Place> allPlaces;
    private double duration;
    private Film film;

    public static int getAutoId() {
        return ++autoId;
    }

    public Session(Calendar date, ArrayList<Place> allPlaces, double duration, Film film) {
        this.date = date;
        this.id = ++autoId;
        this.allPlaces = allPlaces;
        this.duration = duration;
        this.film = film;
    }

    public Session(Session session){
        this.date = Calendar.getInstance();
        this.date.setTime(session.date.getTime());
        this.id = session.id;
        this.allPlaces = new ArrayList<>();
        session.allPlaces.forEach(place -> allPlaces.add(new Place(place)));
        this.duration = session.duration;
        this.film = new Film(session.film);
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Place> getAllPlaces() {
        return allPlaces;
    }

    public void setAllPlaces(ArrayList<Place> allPlaces) {
        this.allPlaces = allPlaces;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (Double.compare(session.id, id) != 0) return false;
        return date != null ? date.equals(session.date) : session.date == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = date != null ? date.hashCode() : 0;
        temp = Double.doubleToLongBits(id);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Session{" +
                "date=" + date.get(Calendar.DAY_OF_MONTH) + "."+date.get(Calendar.MONTH) + " " + date.get(Calendar.HOUR)
                + ":"+date.get(Calendar.MINUTE) +
                ", id=" + id +
                ", allPlaces=" + allPlaces +
                ", duration=" + duration +
                '}';
    }
}
