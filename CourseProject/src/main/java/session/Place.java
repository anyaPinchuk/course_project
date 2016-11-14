package session;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ANYA on 07.10.2016.
 */
@Entity
public class Place implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer place_id;
    private double price;
    @Column(name = "state")
    //@Enumerated(EnumType.STRING)
    private String statePlace = StatePlace.AVAILABLE.getS();
    @ManyToOne
    @JoinColumn(name = "film_session_id")
    private FilmSession film_session;

    public Place(double price, String statePlace, int placeNumber) {
        this.price = price;
        this.statePlace = statePlace;
        this.place_id = placeNumber;
    }

    public Place(Place place) {
        this.price = place.price;
        this.statePlace = place.statePlace;
        this.place_id = place.place_id;
    }

    public Place() {
    }


    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatePlace() {
        return statePlace;
    }

    public void setStatePlace(String statePlace) {
        this.statePlace = statePlace;
    }

    public FilmSession getFilm_session() {
        return film_session;
    }

    public void setFilm_session(FilmSession film_session) {
        this.film_session = film_session;
    }

    @Override
    public String toString() {
        return "Place{" +
                "price=" + price +
                ", statePlace=" + statePlace +
                ", placeNumber=" + place_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (Double.compare(place.price, price) != 0) return false;
        if (place_id != null ? !place_id.equals(place.place_id) : place.place_id != null) return false;
        return statePlace != null ? statePlace.equals(place.statePlace) : place.statePlace == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = place_id != null ? place_id.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (statePlace != null ? statePlace.hashCode() : 0);
        return result;
    }
}
