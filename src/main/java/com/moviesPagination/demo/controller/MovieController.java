package com.moviesPagination.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.moviesPagination.demo.model.Movie;
import com.moviesPagination.demo.service.MovieService;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    // ===========================
    // GET ALL MOVIES
    // ===========================
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.findAllMovies();
    }


    // ===========================
    // GET MOVIE BY ID
    // ===========================
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id) {
        return movieService.findById(id);
    }


    // ===========================
    // CREATE MOVIE (POST)
    // ===========================
    @PostMapping
    public String createMovie(@RequestBody Movie movie) {
        movieService.save(movie);
        return "success";
    }


    // ===========================
    // UPDATE MOVIE (PUT)
    // ===========================
    @PutMapping("/{id}")
    public String updateMovie(@PathVariable Integer id, @RequestBody Movie movieDetails) {

        Movie movie = movieService.findById(id);

        if (movie == null) {
            return "movie not found";
        }

        // Update fields
        movie.setTitle(movieDetails.getTitle());
        movie.setLanguage(movieDetails.getLanguage());
        movie.setDescription(movieDetails.getDescription());
       

        movieService.save(movie);

        return "success";
    }


    // ===========================
    // DELETE MOVIE (DELETE)
    // ===========================
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Integer id) {

        movieService.delete(id);
        return "success";
    }
    
    // ===========================
    //  	Sorting
    // ===========================
    
    @GetMapping("/sort/{field}")
    private List<Movie> getMovieWithSort(@PathVariable String field){
    	List<Movie> allMovies=movieService.findMovieWithSorting(field);
    	return  allMovies;
    }
    @GetMapping("/pagination/{pageNumber}/{pageSize}")
    public Page<Movie> getMovieWithPagination(@PathVariable int pageNumber,
                                              @PathVariable int pageSize) {

        return movieService.findMovieWithPagination(pageNumber, pageSize);
    }

    
    
    
    
    
    
    
    
}
