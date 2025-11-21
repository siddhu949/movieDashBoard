package com.moviesPagination.demo.service.impl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moviesPagination.demo.model.Movie;
import com.moviesPagination.demo.repository.MovieRepository;
import com.moviesPagination.demo.service.MovieService;

import jakarta.annotation.PostConstruct;
@Service
public class MovieServiceImpl implements MovieService{
	@Autowired
	MovieRepository mr;
	
//	 @PostConstruct
//	    public void initDB() {
//
//	        if (mr.count() == 0) {   // optional: only load once
//
//	            List<Movie> movies = IntStream.rangeClosed(1, 50)
//	                    .mapToObj(i -> new Movie(
//	                            "Movie " + i,
//	                            randomLanguage(),
//	                            randomGenre(),
//	                            randomDuration(),
//	                            "Description for Movie " + i,
//	                            null  // or use random poster
//	                    ))
//	                    .collect(Collectors.toList());
//
//	            mr.saveAll(movies);
//
//	            System.out.println("📌 Dummy Movie Data Loaded Successfully!");
//	        }
//	    }
//
//
//	    // Generate random languages
//	    private String randomLanguage() {
//	        String[] languages = { "English", "Hindi", "Tamil", "Telugu", "Kannada", "Spanish" };
//	        return languages[new Random().nextInt(languages.length)];
//	    }
//
//	    // Generate random genres
//	    private String randomGenre() {
//	        String[] genres = { "Action", "Comedy", "Drama", "Horror", "Sci-Fi", "Thriller" };
//	        return genres[new Random().nextInt(genres.length)];
//	    }
//
//	    // Generate a random movie duration
//	    private int randomDuration() {
//	        return new Random().nextInt(60) + 90;   // 90–150 minutes
//	    }

	@Override
	public List<Movie> findAllMovies() {
		return mr.findAll();
	}

	@Override
	public Movie findById(Integer id) {
	    return mr.findById(id).orElse(null);
	}

	@Override
	public Movie save(Movie movie) {
		return mr.save(movie);
	}

	@Override
	public void delete(Integer id) {
		 mr.deleteById(id);
		
	}
	public List<Movie> findMovieWithSorting(String field){
        return  mr.findAll(Sort.by(Sort.Direction.ASC,field));
    }
	public Page<Movie> findMovieWithPagination(int offset,int pageSize){
        Page<Movie> movie = mr.findAll(PageRequest.of(offset, pageSize));
        return  movie;
    }

//    public Page<Movie> findMovieWithPaginationAndSorting(int offset,int pageSize,String field){
//        Page<Movie> products = mr.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
//        return  movie;
//    }

}
