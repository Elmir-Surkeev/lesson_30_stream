package kg.attractor.java;

import kg.attractor.java.lesson.MovieCollection;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");
        // это для занятия
        var movieCollection = MovieCollection.readFromJson();

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.

        //var orders = RestaurantOrders.read("orders_100.json").getOrders();
        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)
    }
}
