package org.example;
import java.util.List;
import java.util.Objects;

public class Route {
    private String id;
    private double distance;
    private int popularity;
    private boolean isFavorite;
    private List<String> locationPoints;

    public void increasePopularity() {
        this.popularity++;
    }

    public boolean hasLocationPoints(String startPoint, String endPoint) {
        if (locationPoints.size() < 2) {
            return false;
        }

        return locationPoints.get(0).equals(startPoint) && locationPoints.get(locationPoints.size() - 1).equals(endPoint);
    }

    public int getFavoritePriority() {
        return isFavorite ? 1 : 0;
    }

    public String getFirstLocationPoint() {
        if (!locationPoints.isEmpty()) {
            return locationPoints.get(0);
        }
        return null;
    }

    //Конструктор
    public Route(String id, double distance, int popularity, boolean isFavorite, List<String> locationPoints) {
        this.id = id;
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Double.compare(route.distance, distance) == 0 &&
                popularity == route.popularity &&
                isFavorite == route.isFavorite &&
                Objects.equals(id, route.id) &&
                Objects.equals(locationPoints, route.locationPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, distance, popularity, isFavorite, locationPoints);
    }

    //Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<String> getLocationPoints() {
        return locationPoints;
    }

    public void setLocationPoints(List<String> locationPoints) {
        this.locationPoints = locationPoints;
    }
}