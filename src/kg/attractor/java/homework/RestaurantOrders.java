package kg.attractor.java.homework;

import com.google.gson.Gson;

import kg.attractor.java.homework.domain.Item;
import kg.attractor.java.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    public List<Order> getMoreExpensiveOrders(int x) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(x)
                .collect(Collectors.toList());
    }


    public List<Order> getTopMore(int y) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .limit(y)
                .collect(Collectors.toList());
    }

    public List<Order> getHomeDeliveryOrders() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }

    public void getMostAndLeastProfitableHomeDelivery() {
        List<Order> homeOrders = getHomeDeliveryOrders();

        if (homeOrders.isEmpty()) {
            System.out.println("Нет заказов на дом");
            return;
        }

        Order mostExpensive = homeOrders.get(0);
        Order cheapest = homeOrders.get(0);

        for (Order order : homeOrders) {
            if (order.getTotal() > mostExpensive.getTotal()) {
                mostExpensive = order;
            }
            if (order.getTotal() < cheapest.getTotal()) {
                cheapest = order;
            }
        }

        System.out.println("Самый прибыльный: " + mostExpensive.getCustomer().getFullName()
                + " $ " + mostExpensive.getTotal());
        System.out.println("Наименее прибыльный: " + cheapest.getCustomer().getFullName()
                + " $ " + cheapest.getTotal());
    }

    public List<Order> getOrdersBetween(double minOrderTotal, double maxOrderTotal) {
        return orders.stream()
                .filter(o -> o.getTotal() > minOrderTotal && o.getTotal() < maxOrderTotal)
                .collect(Collectors.toList());
    }

    public double getTotalAllOrders() {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public List<String> getUniqueEmailsSorted() {
        Set<String> emails = new TreeSet<>();
        orders.forEach(order -> emails.add(order.getCustomer().getEmail()));
        return new ArrayList<>(emails);
    }
}
