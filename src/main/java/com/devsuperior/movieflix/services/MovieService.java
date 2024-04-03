package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

//    @Transactional(readOnly = true)
//    public Page<MovieCardDTO> findAll(Pageable pageable) {
//        Page<Movie> list = movieRepository.findAll(pageable);
//        Page<MovieCardDTO> listDTO = list.map(x -> new MovieCardDTO(x));
//        return listDTO;
//    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> searchMoviesByGenre(String title, String genreId, Pageable pageable) {

        List<Long> genreIds = Arrays.asList();
        if (!"0".equals(genreId)) {
            String[] vet = genreId.split(",");
            List<String> list = Arrays.asList(vet);
            genreIds = list.stream().map(x -> Long.parseLong(x)).toList();

            //categoryIds = Arrays.asList(categoryId.split(",")).stream().map(Long::parseLong).toList();
        } else {
            genreIds = null;;
        }

        Page<Movie> page = movieRepository.searchMoviesByGenre(title, genreIds, pageable);
        //Page<Movie> page =  movieRepository.findAll(pageable);
        Page<MovieCardDTO> dto = page.map(x -> new MovieCardDTO(x));


//        List<Long> productIds = page.map(x -> x.getId()).toList();
//
//        List<Product> entities = movieRepository.searchProductsWithCategories(productIds);
//        //fazendo ordenar a lista de acordo com paretro de ordenacao - aproveitando o page acima
//
//        entities = (List<Product>) Utils.replace(page.getContent(), entities);
//
//        List<ProductDTO> dtos = entities.stream().map(p -> new ProductDTO(p, p.getCategories())).toList();
//
//        Page<ProductDTO> pageDto = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());

        return dto;
    }
}
