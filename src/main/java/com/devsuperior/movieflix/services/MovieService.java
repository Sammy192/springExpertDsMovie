package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.Utils.Utils;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> searchMoviesByGenre(String title, Long genreId, Pageable pageable) {

        genreId = genreId == 0 ? null : genreId;

        Page<Movie> page = movieRepository.searchMoviesByGenre(title, genreId, pageable);
        Page<MovieCardDTO> dto = page.map(x -> new MovieCardDTO(x));

        return dto;
    }

    @Transactional(readOnly = true)
    public Page<MovieDetailsDTO> findMoviesDetailByGenre(String title, Long genreId, Pageable pageable) {
        genreId = genreId == 0 ? null : genreId;
        Page<Movie> page = movieRepository.searchMoviesDetailByGenre(title, genreId, pageable);
        List<Long> movieIds = page.map(Movie::getId).toList();

        List<Movie> entities = movieRepository.searchMoviesWithGenre(movieIds);
        entities =  Utils.orderListMoviesByGenre(page.getContent(), entities);

        List<MovieDetailsDTO> dtos = entities.stream().map(MovieDetailsDTO::new).toList();

        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Movie entity = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return new MovieDetailsDTO(entity);
    }

}
