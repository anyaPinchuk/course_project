package session;

import java.io.Serializable;

/**
 * Created by ANYA on 11.10.2016.
 */

public class Film implements Serializable {
    private String filmName;
    private String genre;
    private double duration;
    private String description;

    public Film(String filmName, String genre, double duration, String description) {
        this.filmName = filmName;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
    }

    public Film(Film film) {
        this.filmName = film.filmName;
        this.genre = film.genre;
        this.duration = film.duration;
        this.description = film.description;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
