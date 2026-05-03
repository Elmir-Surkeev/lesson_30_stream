package kg.attractor.java.lesson.domain;

import java.util.*;

public class Movie {
    private final List<Actor> cast = new ArrayList<>();
    private final Set<String> genres = new HashSet<>();
    private final List<Integer> length = new ArrayList<>();
    private String name;
    private int year;
    private String type;
    private Person director;

    public double getBudget() {
        return budget;
    }

    private double budget = 0.0;

    public Set<String> getGenres() {
        return genres;
    }

    public List<Integer> getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                Objects.equals(name, movie.name) &&
                Objects.equals(type, movie.type) &&
                Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, type, director);
    }

    public Person getDirector() {
        return director;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<Actor> getCast() {
        return cast;
    }

    @Override
    public String toString() {
        return String.format("%s | %-15.15s | %s ", year, director, name);
    }
}
