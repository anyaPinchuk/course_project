package session;

import java.io.Serializable;

/**
 * Created by ANYA on 07.10.2016.
 */
public class Place implements Serializable{
    private double price;
    private StatePlace statePlace = StatePlace.AVAILABLE;
    private int placeNumber;

    public Place(double price, StatePlace statePlace, int placeNumber) {
        this.price = price;
        this.statePlace = statePlace;
        this.placeNumber = placeNumber;
    }

    public Place(Place place) {
        this.price = place.price;
        this.statePlace = place.statePlace;
        this.placeNumber = place.placeNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public StatePlace getStatePlace() {
        return statePlace;
    }

    public void setStatePlace(StatePlace statePlace) {
        this.statePlace = statePlace;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    @Override
    public String toString() {
        return "Place{" +
                "price=" + price +
                ", statePlace=" + statePlace +
                ", placeNumber=" + placeNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (Double.compare(place.price, price) != 0) return false;
        if (placeNumber != place.placeNumber) return false;
        return statePlace == place.statePlace;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (statePlace != null ? statePlace.hashCode() : 0);
        result = 31 * result + placeNumber;
        return result;
    }
}
