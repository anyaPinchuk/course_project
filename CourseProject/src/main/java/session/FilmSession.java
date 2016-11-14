package session;

import org.junit.Test;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ANYA on 07.10.2016.
 */
@Entity
@Table(name = "film_session")
public class FilmSession implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_session_id")
    private Integer id;
    private Calendar date;
    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "film_session", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Place> allPlaces;
    private double duration;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public FilmSession(Calendar date, ArrayList<Place> allPlaces, double duration, Film film) {
        this.date = date;
        this.allPlaces = allPlaces;
        this.duration = duration;
        this.film = film;
    }

    public FilmSession() {
    }

    public FilmSession(FilmSession filmSession){
        this.date = Calendar.getInstance();
        this.date.setTime(filmSession.date.getTime());
        this.id = filmSession.id;
        this.allPlaces = new ArrayList<>();
        filmSession.allPlaces.forEach(place -> allPlaces.add(new Place(place)));
        this.duration = filmSession.duration;
        this.film = new Film(filmSession.film);
    }

    public double getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Place> getAllPlaces() {
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

        FilmSession filmSession = (FilmSession) o;

        if (Double.compare(filmSession.id, id) != 0) return false;
        return date != null ? date.equals(filmSession.date) : filmSession.date == null;

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
        return "FilmSession{" +
                "date=" + date.get(Calendar.DAY_OF_MONTH) + "."+date.get(Calendar.MONTH) + " " + date.get(Calendar.HOUR)
                + ":"+date.get(Calendar.MINUTE) +
                ", id=" + id +
                ", allPlaces=" + allPlaces +
                ", duration=" + duration +
                '}';
    }
}
