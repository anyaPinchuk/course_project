package session;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ANYA on 11.10.2016.
 */
@Entity
@Table(name = "film")
public class Film implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Integer film_id;
    @Column(name = "name")
    private String filmName;
    private String genre;
    private String description;

    public Film() {
    }

    public Integer getId() {
        return film_id;
    }

    public void setId(Integer id) {
        this.film_id = id;
    }

    public Film(String filmName, String genre, String description) {
        this.filmName = filmName;
        this.genre = genre;
        this.description = description;
    }

    public Film(Film film) {
        this.filmName = film.filmName;
        this.genre = film.genre;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
