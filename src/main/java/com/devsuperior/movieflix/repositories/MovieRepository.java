package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.projections.UserDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(value = """   			
			SELECT obj FROM Movie obj JOIN FETCH obj.genre genre
			WHERE (:genreId IS NULL OR obj.genre.id IN :genreId)
			AND LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))
			ORDER BY obj.title ASC
			""",
			countQuery = """
     		SELECT COUNT(obj) FROM Movie obj JOIN obj.genre genre
			WHERE (:genreId IS NULL OR obj.genre.id IN :genreId)
			AND LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))
			ORDER BY obj.title ASC
			""")
	Page<Movie> searchMoviesByGenre(String title, Long genreId, Pageable pageable);

	@Query(value = """   			
			SELECT obj FROM Movie obj JOIN FETCH obj.genre genre
			WHERE (:genreId IS NULL OR obj.genre.id IN :genreId)
			AND LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))
			""",
			countQuery = """
     		SELECT COUNT(obj) FROM Movie obj JOIN obj.genre genre
			WHERE (:genreId IS NULL OR obj.genre.id IN :genreId)
			AND LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))
			""")
	Page<Movie> searchMoviesDetailByGenre(String title, Long genreId, Pageable pageable);

	@Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id IN :genreIds")
	List<Movie> searchMoviesWithGenre(List<Long> genreIds);
}
