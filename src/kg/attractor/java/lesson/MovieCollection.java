package kg.attractor.java.lesson;

import com.google.gson.Gson;

import kg.attractor.java.lesson.domain.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MovieCollection {

    private final List<Movie> movies = new ArrayList<>();

    public static MovieCollection readFromJson() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(Files.readString(Path.of("data", "movies.json")), MovieCollection.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Movie> getMovies() {
        return movies;
    }
}
