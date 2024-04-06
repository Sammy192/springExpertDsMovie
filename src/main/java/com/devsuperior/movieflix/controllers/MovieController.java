package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findByGenre(
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "genreId", defaultValue = "0") Long genreId,
			Pageable pageable) {
		Page<MovieCardDTO> list = movieService.searchMoviesByGenre(title, genreId, pageable);
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping(value = "/detail")
	public ResponseEntity<Page<MovieDetailsDTO>> findMoviesDetailByGenre(
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "genreId", defaultValue = "0") Long genreId,
			Pageable pageable) {
		Page<MovieDetailsDTO> list = movieService.findMoviesDetailByGenre(title, genreId, pageable);
		return ResponseEntity.ok().body(list);
	}


	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_VISITOR')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
		MovieDetailsDTO dto = movieService.findById(id);
		return ResponseEntity.ok(dto);
	}
}
