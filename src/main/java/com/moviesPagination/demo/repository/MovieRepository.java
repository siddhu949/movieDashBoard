package com.moviesPagination.demo.repository;
import com.moviesPagination.demo.model.*;
import org.springframework.data.jpa.repository.*;

public interface MovieRepository extends JpaRepository<Movie,Integer>{

}
