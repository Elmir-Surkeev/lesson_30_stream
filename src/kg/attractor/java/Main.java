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
        var movieCollection = MovieCollection.readFromJson();
        var restaurantOrders = RestaurantOrders.read("orders_100.json");
        var orders = restaurantOrders.getOrders();

        while (true) {
            try {
                System.out.println(
                        "\n Меню " +
                                "\n 0 Выход" +
                                "\n 1 Общая стоимость каждого заказа" +
                                "\n 2 Все заказы" +
                                "\n 3 Топ x самых дорогих товаров" +
                                "\n 4 Топ x самых дорогих заказов" +
                                "\n 5 Топ x самых дешёвых заказов" +
                                "\n 6 Заказы с доставкой на дом" +
                                "\n 7 Самый прибыльный и наименее прибыльный заказ на дом" +
                                "\n 8 Заказы в диапазоне стоимости" +
                                "\n 9 Общая стоимость всех заказов" +
                                "\n 9  Общая стоимость всех заказов" +
                                "\n 10 Уникальные email всех клиентов" +
                                "\n 11 Заказы сгруппированные по клиентам" +
                                "\n 12 Сумма заказов по каждому клиенту" +
                                "\n 13 Клиент с максимальной суммой" +
                                "\n 14 Клиент с минимальной суммой" +
                                "\n 15 Продажи товаров по количеству" +
                                "\n 16 Email клиентов заказавших товар"
                );

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 0:
                        System.out.println("До свидания!");
                        return;

                    case 1:
                        System.out.println(" Стоимость каждого заказа ");
                        orders.forEach(Order::calculateTotal);
                        break;

                    case 2:
                        System.out.println(" Все заказы ");
                        orders.forEach(Order::printAllOrders);
                        break;

                    case 3:
                        System.out.println("Введите количество:");
                        int countItems = sc.nextInt();
                        List<Item> expensive = orders.stream()
                                .flatMap(o -> o.getItems().stream())
                                .sorted(Comparator.comparingDouble(Item::getPrice).reversed())
                                .limit(countItems)
                                .collect(Collectors.toList());
                        expensive.forEach(i ->
                                System.out.println(i.getName() + " $" + i.getPrice()));
                        break;

                    case 4:
                        System.out.println("Введите количество:");
                        int x = sc.nextInt();
                        restaurantOrders.getMoreExpensiveOrders(x)
                                .forEach(o -> System.out.println(
                                        o.getCustomer().getFullName() + " $" + o.getTotal()));
                        break;

                    case 5:
                        System.out.println("Введите количество:");
                        int y = sc.nextInt();
                        restaurantOrders.getTopMore(y)
                                .forEach(o -> System.out.println(
                                        o.getCustomer().getFullName() + " $" + o.getTotal()));
                        break;

                    case 6:
                        System.out.println(" Заказы с доставкой на дом ");
                        restaurantOrders.getHomeDeliveryOrders()
                                .forEach(o -> System.out.println(
                                        o.getCustomer().getFullName() + " $" + o.getTotal()));
                        break;

                    case 7:
                        System.out.println(" Прибыльность заказов на дом ");
                        restaurantOrders.getMostAndLeastProfitableHomeDelivery();
                        break;

                    case 8:
                        System.out.println("Введите минимальную сумму:");
                        double min = sc.nextDouble();
                        System.out.println("Введите максимальную сумму:");
                        double max = sc.nextDouble();
                        restaurantOrders.getOrdersBetween(min, max)
                                .forEach(o -> System.out.println(
                                        o.getCustomer().getFullName() + " $" + o.getTotal()));
                        break;

                    case 9:
                        System.out.println("Общая стоимость всех заказов: $"
                                + restaurantOrders.getTotalAllOrders());
                        break;

                    case 10:
                        System.out.println(" Уникальные email ");
                        restaurantOrders.getUniqueEmailsSorted()
                                .forEach(System.out::println);
                        break;
                    case 11:
                        System.out.println(" Заказы по клиентам ");
                        restaurantOrders.getOrderGroupCollect()
                                .forEach((name, orderList) -> {
                                    System.out.println(name + ":");
                                    orderList.forEach(o -> System.out.println("  – $" + o.getTotal()));
                                });
                        break;

                    case 12:
                        System.out.println(" Сумма по клиентам ");
                        restaurantOrders.getTotalByCustomer()
                                .forEach((name, total) ->
                                        System.out.println(name + " – $" + total));
                        break;

                    case 13:
                        System.out.println("Клиент с максимальной суммой: "
                                + restaurantOrders.getCustomerWithMaxTotal());
                        break;

                    case 14:
                        System.out.println("Клиент с минимальной суммой: "
                                + restaurantOrders.getCustomerWithMinTotal());
                        break;

                    case 15:
                        System.out.println(" Продажи товаров ");
                        restaurantOrders.getItemsSoldCount()
                                .forEach((name, count) ->
                                        System.out.println(name + " " + count + " шт."));
                        break;
                    case 16:
                        System.out.println("Введите название товара:");
                        String itemName = sc.nextLine().toLowerCase();
                        restaurantOrders.getEmailsByItemName(itemName)
                                .forEach(System.out::println);
                        break;
                    default:
                        System.out.println("Такого пункта нет. Попробуйте снова");
                }

            } catch (InputMismatchException e) {
                System.out.println("Ошибка введите число!");
                sc.nextLine();
            }
        }
    }
}