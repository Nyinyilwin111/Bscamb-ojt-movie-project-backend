package ojt_final_project.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ojt_final_project.demo.dto.MovieLikeCountDTO;
import ojt_final_project.demo.entity.Movie;
import ojt_final_project.demo.entity.Trend_movie;
import ojt_final_project.demo.entity.User;
import ojt_final_project.demo.service.MovieService;
import ojt_final_project.demo.service.TrendMovieService;
import ojt_final_project.demo.service.UserService;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/trendMovie")
public class TrendMovieController {
		
	@Autowired
	TrendMovieService trendMovieService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MovieService movieService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveTrendMovie(@RequestBody Map<String, Object> payload) {
	    Integer number = (Integer) payload.get("number");
	    Integer movieId = (Integer) payload.get("movie_id");
	    Integer userId = (Integer) payload.get("user_id");

	    if (movieId == null || userId == null) {
	        return ResponseEntity.badRequest().body("movie_id and user_id are required");
	    }

	    User user = userService.get(userId);
	    Movie movie = movieService.get(movieId);

	    if (user == null || movie == null) {
	        return ResponseEntity.badRequest().body("Invalid user_id or movie_id");
	    }

	    Trend_movie trendMovie = new Trend_movie();
	    trendMovie.setUser(user);         
	    trendMovie.setMovie(movie);       
	    trendMovie.setLike(number != null ? number : 0);

	    trendMovieService.save(trendMovie); 

	    Map<String, Object> response = new HashMap<>();
	    response.put("success", true);
	    return ResponseEntity.ok(response);
	}
	
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTrendMovie(@PathVariable int id, @RequestBody Trend_movie trendMovie) {
        Trend_movie updated = trendMovieService.updateTrendMovie(id, trendMovie);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.badRequest().body("Trend_movie not found for update");
        }
    }
    
    @GetMapping("/check/{movieId}/{userId}")
    public ResponseEntity<Map<String, Object>> checkTrendMovie(
            @PathVariable int movieId,
            @PathVariable int userId) {

        Map<String, Object> response = new HashMap<>();

        if (movieId <= 0 || userId <= 0) {
            response.put("success", false);
            response.put("id", null);
            response.put("like", 0);
            response.put("message", "Invalid movieId or userId");
            return ResponseEntity.badRequest().body(response);
        }

        Trend_movie trendMovie = trendMovieService.checkTrendMove(userId, movieId);

        if (trendMovie != null) {
            response.put("success", true);
            response.put("id", trendMovie.getId());
            response.put("like", trendMovie.getLike());
        } else {
            response.put("success", false);
            response.put("id", null);
            response.put("like", 0);
        }

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/topLiked")
    public List<MovieLikeCountDTO> getTopLikedMovies() {
        return trendMovieService.getTopLikedMovies();
    }
    
    @GetMapping("/getTrendMovie")
    public ResponseEntity<List<Trend_movie>> getArr() {
		List<Trend_movie> mov = trendMovieService.getTrendMovieAll();
		return new ResponseEntity<List<Trend_movie>>(mov, HttpStatus.ACCEPTED);

	}

}
