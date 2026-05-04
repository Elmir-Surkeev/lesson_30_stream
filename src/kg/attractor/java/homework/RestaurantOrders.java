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

        Order mostExpensive = homeOrders.stream()
                .max(Comparator.comparingDouble(Order::getTotal))
                .get();

        Order cheapest = homeOrders.stream()
                .min(Comparator.comparingDouble(Order::getTotal))
                .get();

        System.out.println("Самый прибыльный: " + mostExpensive.getCustomer().getFullName()
                + " " + mostExpensive.getTotal());
        System.out.println("Наименее прибыльный: " + cheapest.getCustomer().getFullName()
                + " " + cheapest.getTotal());
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

    public Map<String, List<Order>> getOrderGroupCollect() {
        return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getFullName()));
    }

    public Map<String, Double> getTotalByCustomer() {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> order.getCustomer().getFullName(),
                        Order::getTotal,
                        Double::sum
                ));
    }


    public String getCustomerWithMaxTotal() {
        Map<String, Double> totals = getTotalByCustomer();

        String maxCustomer = null;
        double maxTotal = Double.MIN_VALUE;

        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            if (entry.getValue() > maxTotal) {
                maxTotal = entry.getValue();
                maxCustomer = entry.getKey();
            }
        }
        return maxCustomer + " – $" + maxTotal;
    }

    public String getCustomerWithMinTotal() {
        return getTotalByCustomer().entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " – $" + e.getValue())
                .orElse("Нет данных");
    }

    public Map<String, Integer> getItemsSoldCount() {
        Map<String, Integer> result = new HashMap<>();

        orders.stream()
            .flatMap(order -> order.getItems().stream())
            .forEach(item -> result.put(
                    item.getName(),
                    result.getOrDefault(item.getName(), 0) + item.getAmount()
            ));

        return result;
    }

    public List<String> getEmailsByItemName(String itemName) {

        return orders.stream()
                .filter(order -> order.getItems().stream()
                        .anyMatch(item ->  item.getName().equalsIgnoreCase(itemName)))
                .map(order -> order.getCustomer().getEmail())
                .collect(Collectors.toList());
    }
}
