package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Navigator navigator = new NavigatorImpl();
        Scanner scanner = new Scanner(System.in);


        navigator.addRoute(new Route("1", 700.0, 1, true, new LinkedList<>(Arrays.asList("Москва", "Тверь", "Великий Новгород", "Санкт-Петербург"))));
        navigator.addRoute(new Route("2", 1664.0, 2, true, new LinkedList<>(Arrays.asList("Москва", "Тула",
                "Воронеж", "Ростов-на-Дону", "Славянск-на-Кубани", "Керчь", "Феодосия"))));
        navigator.addRoute(new Route("3", 424.0, 1, true, new LinkedList<>(Arrays.asList("Москва", "Петушки", "Владимир", "Дзержинск", "Нижний Новгород"))));




        while (true) {
            System.out.println("\nВыберите действие ");
            System.out.println("1. Добавить маршрут");
            System.out.println("2. Удалить маршрут");
            System.out.println("3. Обновить любимые маршруты");
            System.out.println("4. Показать доступные маршруты");
            System.out.println("5. Показать Топ-3 маршрутов");
            System.out.println("6. Выйти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Введите подробности маршрута");
                    System.out.print("Номер: ");
                    String id = scanner.next();
                    System.out.print("Расстояние: ");
                    double distance = scanner.nextDouble();
                    System.out.print("Популярность: ");
                    int popularity = scanner.nextInt();
                    // Очистка буфера
                    scanner.nextLine();
                    System.out.print("Вам нравится этот маршрут? Введите true или false ");
                    boolean isFavorite = scanner.nextBoolean();
                    // Очистка буфера
                    scanner.nextLine();
                    System.out.print("Введите города и остановочные пункты ");
                    String[] pointsArray = scanner.nextLine().split(",");
                    navigator.addRoute(new Route(id, distance, popularity, isFavorite, new LinkedList<>(Arrays.asList(pointsArray))));
                    System.out.println("Маршрут успешно добавлен!");
                    break;

                case 2:
                    System.out.print("Введите номер маршрута который необходимо удалить");
                    String routeIdToRemove = scanner.next();
                    navigator.removeRoute(routeIdToRemove);
                    System.out.println("Удалено успешно!");
                    break;

                case 3:
                    System.out.print("Введите номер маршрута для изменения его популярности ");
                    String routeIdToUpdate = scanner.next();
                    System.out.print("Введите новое значение популярности");
                    int newPopularity = scanner.nextInt();
                    navigator.updatePopularity(routeIdToUpdate, newPopularity);
                    System.out.println("Обновлено успешно!");
                    break;

                case 4:
                    System.out.println("Введите начальную точку ");
                    String startPoint = scanner.next();
                    scanner.nextLine(); // Добавим эту строку для считывания символа новой строки
                    System.out.println("Введите конечную точку ");
                    String endPoint = scanner.nextLine();

                    System.out.println("Соответствующие маршруты:");
                    for (Route route : navigator.searchRoutes(startPoint, endPoint)) {
                        System.out.println(route.getId() + " - Расстояние: " + route.getDistance() +
                                ", Популярность: " + route.getPopularity() +
                                ", Избранные: " + route.isFavorite() +
                                ", Точки маршрута: " + String.join(", ", route.getLocationPoints()));
                    }
                    break;


                case 5:
                    System.out.println("Топ 3 маршрутов: ");
                    for (Route route : navigator.getTop3Routes()) {
                        System.out.println(route.getId() + " - Расстояние: " + route.getDistance() +
                                ", Популярность: " + route.getPopularity() +
                                ", Избранный: " + route.isFavorite());
                    }
                    break;

                case 6:
                    System.out.println("Закрытие приложения...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Пожалуйста выберите одну из вышеперечисленных опций");
            }
        }
    }
}
