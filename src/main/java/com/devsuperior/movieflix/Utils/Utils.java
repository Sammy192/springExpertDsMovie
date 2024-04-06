package com.devsuperior.movieflix.Utils;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.IdProjection;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static List<Movie> orderListMoviesByGenre(List<Movie> ordered, List<Movie> unordered) {
        Map<Long, Movie> map = new HashMap<>();
        for (Movie obj : unordered) {
            map.put(obj.getId(), obj);
        }
        List<Movie> result = new ArrayList<>();
        for (Movie obj : ordered) {
            result.add(map.get(obj.getId()));
        }

        return result;
    }
}