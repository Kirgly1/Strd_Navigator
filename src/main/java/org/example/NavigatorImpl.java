package org.example;
import java.util.*;

public class NavigatorImpl implements Navigator {
    private final LinkedList<Route> routes = new LinkedList<>();

    @Override
    public void addRoute(Route route) {
        if (!routes.contains(route)) {
            routes.add(route);
        }
    }

    @Override
    public void removeRoute(String routeId) {
        routes.removeIf(route -> route.getId().equals(routeId));
    }

    @Override
    public boolean contains(Route route) {
        return routes.contains(route);
    }

    @Override
    public int size() {
        return routes.size();
    }

    @Override
    public Route getRoute(String routeId) {
        for (Route route : routes) {
            if (route.getId().equals(routeId)) {
                return route;
            }
        }
        return null;
    }

    @Override
    public void chooseRoute(String routeId) {
        Route selectedRoute = getRoute(routeId);
        if (selectedRoute != null) {
            selectedRoute.increasePopularity();
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> result = new ArrayList<>();

        for (Route route : routes) {
            List<String> locationPoints = route.getLocationPoints();

            int startIndex = -1;
            int endIndex = -1;

            for (int i = 0; i < locationPoints.size(); i++) {
                if (locationPoints.get(i).trim().equalsIgnoreCase(startPoint)) {
                    startIndex = i;
                }
                if (locationPoints.get(i).trim().equalsIgnoreCase(endPoint)) {
                    endIndex = i;
                }
            }

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                result.add(route);
            }
        }

        result.sort(Comparator
                .comparingInt(Route::getFavoritePriority)
                .reversed()
                .thenComparingInt(route -> getDistanceBetweenPoints(route, startPoint, endPoint))
                .thenComparingInt(Route::getPopularity)
        );

        return result;
    }

    // Вспомогательный метод для получения расстояния между точками
    private int getDistanceBetweenPoints(Route route, String startPoint, String endPoint) {
        List<String> locationPoints = route.getLocationPoints();

        int startIndex = -1;
        int endIndex = -1;

        for (int i = 0; i < locationPoints.size(); i++) {
            if (locationPoints.get(i).trim().equalsIgnoreCase(startPoint)) {
                startIndex = i;
            }
            if (locationPoints.get(i).trim().equalsIgnoreCase(endPoint)) {
                endIndex = i;
            }
        }

        if (startIndex != -1 && endIndex != -1) {
            return Math.abs(endIndex - startIndex);
        } else {
            // Возвращаем максимальное значение, если точки не найдены в маршруте
            return Integer.MAX_VALUE;
        }
    }



    @Override
    public void updatePopularity(String routeId, int newPopularity) {
        Route selectedRoute = getRoute(routeId);
        if (selectedRoute != null) {
            selectedRoute.setPopularity(newPopularity);
        }
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> result = new ArrayList<>();

        for (Route route : routes) {
            if (route.isFavorite() && !route.getLocationPoints().get(0).equals(destinationPoint)) {
                result.add(route);
            }
        }

        result.sort(Comparator
                .comparingDouble(Route::getDistance)
                .thenComparingInt(Route::getPopularity)
                .reversed()
        );

        return result;
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        List<Route> result = new ArrayList<>(routes);
        result.sort(Comparator
                .comparingInt(Route::getPopularity)
                .reversed()
                .thenComparingDouble(Route::getDistance)
                .thenComparingInt(route -> route.getLocationPoints().size())
                .thenComparingLong(route -> routes.indexOf(route))
        );

        return result.subList(0, Math.min(result.size(), 3));
    }

}
