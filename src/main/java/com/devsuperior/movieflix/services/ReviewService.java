package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.Role;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projections.UserDetailsProjection;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserService userService;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto){
		Review entity = new Review();
		copyDtoToEntity(dto, entity);
		entity = reviewRepository.save(entity);
		return new ReviewDTO(entity);
	}

	private void copyDtoToEntity(ReviewDTO dto, Review entity) {
		UserDTO userDto = userService.getProfile();
		User user = new User();
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());

		Movie movie = new Movie();
		movie.setId(dto.getMovieId());

		entity.setText(dto.getText());
		entity.setUser(user);
		entity.setMovie(movie);
	}

}
