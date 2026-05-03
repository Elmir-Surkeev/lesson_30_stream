package kg.attractor.java;

import kg.attractor.java.homework.RestaurantOrders;
import kg.attractor.java.homework.domain.Item;
import kg.attractor.java.homework.domain.Order;
import kg.attractor.java.lesson.MovieCollection;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("hello world");
        // это для занятия
        var movieCollection = MovieCollection.readFromJson();
        // это для домашки
        // выберите любое количество заказов, какое вам нравится.

        var orders = RestaurantOrders.read("orders_100.json").getOrders();

        while (true) {
            try {
                System.out.println("Введите выборку" +
                        "\n 1 для просмотра общую стоимость заказа" +
                        "\n 2 вывода всех заказов" +
                        "\n 3 для вывода самых дорогих заказов");
                int choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("Общая стоимость заказа");
                        orders.forEach(order -> order.calculateTotal());
                        break;
                    case 2:
                        System.out.println("Вывести все заказы");
                        orders.forEach(order -> order.printAllOrders());
                        break;
                    case 3:
                        System.out.println("Вывод всех самых дорогих заказов" +
                                "\n введите ограничение");
                        int countOrder = sc.nextInt();
                        List<Item> mostExpensiveItem = orders.stream()
                                .flatMap(order -> order.getItems().stream()
                                        .sorted(Comparator.comparingDouble(Item::getPrice).reversed()))
                                .limit(countOrder).collect(Collectors.toList());

                        mostExpensiveItem.forEach(i -> System.out.println(i.getName() + " – $" + i.getPrice()));

                    default:
                        System.out.println("В выборке нету таких чисел");
                }
            }catch (InputMismatchException e){
                e.printStackTrace();
            }
        }
        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)
    }
}
