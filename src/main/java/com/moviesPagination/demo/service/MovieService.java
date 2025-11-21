package com.moviesPagination.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.moviesPagination.demo.model.Movie;

public interface MovieService {
	List<Movie> findAllMovies();
	Movie findById(Integer id);
	Movie save(Movie movie);
	void delete(Integer id);
	public List<Movie> findMovieWithSorting(String field);
	public Page<Movie> findMovieWithPagination(int offset,int pageSize);
}
