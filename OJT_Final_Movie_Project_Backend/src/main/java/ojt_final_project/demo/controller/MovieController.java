package ojt_final_project.demo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ojt_final_project.demo.dto.CrewDTO;
import ojt_final_project.demo.dto.MovieDTO;
import ojt_final_project.demo.entity.Category;
import ojt_final_project.demo.entity.Crew;
import ojt_final_project.demo.entity.Movie;
import ojt_final_project.demo.entity.MovieCrew;
import ojt_final_project.demo.service.CategoryService;
import ojt_final_project.demo.service.CrewService;
import ojt_final_project.demo.service.MovieService;
import ojt_final_project.demo.service.StorageService;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@Autowired
	CrewService crewService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	StorageService storageService;
		
	@GetMapping("/getAll")
	public ResponseEntity<List<Movie>> getArr() {
		List<Movie> stds = movieService.getAll();
		return new ResponseEntity<List<Movie>>(stds, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/getMovie/withId/{id}")
	public ResponseEntity<Movie> getMovieWithId(@PathVariable int id) {
		Movie movie = movieService.get(id);
		return new ResponseEntity<Movie>(movie, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/category/{category_id}")
	public ResponseEntity<?> getMoviesByCategory(
			@PathVariable("category_id") int categoryID
	) {
		Category category = categoryService.get(categoryID);
		if (category == null) {
			return ResponseEntity.badRequest().body("Category ID is invalid");
		}
		List<Movie> movieList = movieService.getAllByCategory(category);
		return ResponseEntity.ok().body(movieList);
	}
	
	@GetMapping("/media/{fileType}/{fileName}")
	public ResponseEntity<?> getPoster(
			@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName
	) throws IOException {
		MediaType contentType = MediaType.IMAGE_PNG;
		switch (fileType) {
			case "mp4" :
				contentType = MediaType.APPLICATION_OCTET_STREAM;
				break;
			case "jpg" :
				contentType = MediaType.IMAGE_JPEG;
				break;
			case "png" :
				contentType = MediaType.IMAGE_PNG;
				break;
			default :
				return ResponseEntity.badRequest()
						.body("Unsupported File Type");
		}
		byte[] fileBytes = storageService.load(fileName);
		if (fileBytes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}
	
	@GetMapping("/movie/title/{title}")
	public ResponseEntity<Boolean> findMovieByTitle(
			@PathVariable("title") String title
	) {
		Movie movie = movieService.getByTitle(title);
		if (movie == null) {
			return ResponseEntity.ok().body(false);
		}
		return ResponseEntity.ok().body(true);
	}
	@PostMapping("/save")
	public ResponseEntity<?> saveMovieWithCrews(@RequestBody MovieDTO movieDto) {
	    try {
	        Movie movie = new Movie();
	        movie.setName(movieDto.getName());
	        movie.setDiscuss(movieDto.getDescription());
	        movie.setMovie_poster(movieDto.getMovie_poster());
	        movie.setTrailer(movieDto.getTrailer());
	        movie.setMovies(movieDto.getMovies());

	        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(movieDto.getMovie_created_date());
	        movie.setMovie_created_date(date);

	        Category category = categoryService.findByCategoryName(movieDto.getCategory().getCategoryName());
	        if (category == null) {
	            return ResponseEntity.badRequest().body("Invalid category");
	        }
	        movie.setCategory(category);

	        // First save movie to generate ID
	        movie = movieService.create(movie);

	        List<MovieCrew> movieCrewList = new ArrayList<>();

	        for (CrewDTO c : movieDto.getCrews()) {
	            Crew crew = crewService.findByName(c.getName());
	            if (crew == null) {
	                crew = new Crew();
	                crew.setName(c.getName());
	                crew.setDescription(c.getDescription());
	                crew.setDate_of_birth(LocalDate.parse(c.getDate_of_birth()));
	                crew.setLink(c.getLink());
	                crew.setImage(c.getImage());
	                crew = crewService.save(crew);
	            }

	            MovieCrew movieCrew = new MovieCrew();
	            movieCrew.setCrew(crew);
	            movieCrew.setMovie(movie);
	            movieCrew.setRole(c.getRole());
	            movieCrewList.add(movieCrew);
	        }

	        movie.setMovieCrews(movieCrewList);
	        movieService.create(movie); // Save the movie again with movieCrews

	        return ResponseEntity.ok("Movie saved successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save movie");
	    }
	}

	@GetMapping("/movie/{id}/crews")
	public List<Crew> getCrewsByMovieId(@PathVariable int id) {
	    return crewService.getCrewsByMovieId(id);
	}

	@GetMapping("/crew/{name}/movies")
	public List<Movie> getMoviesByCrewName(@PathVariable String name) {
	    return movieService.getMoviesByCrewName(name);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable int id) {
	    Movie movie = movieService.get(id);
	    if (movie == null) {
	        return ResponseEntity.notFound().build();
	    }

	    storageService.delete(movie.getMovie_poster());
	    storageService.delete(movie.getTrailer());
	    storageService.delete(movie.getMovies());

	    Set<Crew> crewsToCheck = movie.getMovieCrews().stream()
	    	    .map(MovieCrew::getCrew)
	    	    .collect(Collectors.toSet());

	    	for (Crew crew : crewsToCheck) {
	    	    // Check if this crew is only used in this movie
	    	    if (crew.getMovieCrews().size() <= 1) {
	    	        String imagePath = crew.getImage();
	    	        if (imagePath != null && !imagePath.isBlank()) {
	    	            storageService.delete(imagePath);
	    	        }
	    	    }
	    	}

	    boolean isDeleted = movieService.delete(id);
	    if (!isDeleted) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete movie");
	    }

	    return ResponseEntity.ok("Movie and associated files deleted successfully");
	}

	@CrossOrigin(origins = "http://localhost:8085", allowedHeaders = "*", methods = {RequestMethod.PUT, RequestMethod.OPTIONS})
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateMovie(
	        @PathVariable int id,
	        @RequestParam("name") String name,
	        @RequestParam("category") String categoryName,
	        @RequestParam("movie_created_date") String movieCreatedDate,
	        @RequestParam("discuss") String discuss,
	        @RequestParam(value = "trailer", required = false) MultipartFile trailerFile,
	        @RequestParam(value = "movies", required = false) MultipartFile fullMovieFile,
	        @RequestParam(value = "movie_poster", required = false) MultipartFile posterFile
	) {
	    try {
	        Movie movie = movieService.get(id);
	        if (movie == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
	        }
	        movie.setName(name);
	        movie.setDiscuss(discuss);
	        
	        java.sql.Date sqlDate;
	        try {
	            LocalDate localDate = LocalDate.parse(movieCreatedDate, DateTimeFormatter.ISO_LOCAL_DATE);
	            sqlDate = java.sql.Date.valueOf(localDate);
	        } catch (DateTimeParseException e) {
	            long timestamp = Long.parseLong(movieCreatedDate);
	            LocalDate localDate = Instant.ofEpochMilli(timestamp)
	                                        .atZone(ZoneId.systemDefault())
	                                        .toLocalDate();
	            sqlDate = java.sql.Date.valueOf(localDate);
	        }
	        movie.setMovie_created_date(sqlDate);


	        Category category = categoryService.findByCategoryName(categoryName);
	        if (category == null) {
	            return ResponseEntity.badRequest().body("Invalid category");
	        }
	        movie.setCategory(category);

	        // Handle optional file uploads and save paths only
	        if (trailerFile != null && !trailerFile.isEmpty()) {
	            storageService.delete(movie.getTrailer());
	            String trailerPath = storageService.create(trailerFile, "mp4");
	            movie.setTrailer(trailerPath);
	        }

	        if (fullMovieFile != null && !fullMovieFile.isEmpty()) {
	            storageService.delete(movie.getMovies());
	            String fullMoviePath = storageService.create(fullMovieFile, "mp4");  // or the correct type
	            movie.setMovies(fullMoviePath);
	        }

	        if (posterFile != null && !posterFile.isEmpty()) {
	            storageService.delete(movie.getMovie_poster());
	            String posterPath = storageService.create(posterFile, "png");  // or "jpg" based on your file
	            movie.setMovie_poster(posterPath);
	        }

	        movieService.create(movie); // Save updated movie

	        return ResponseEntity.ok("Movie updated successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update movie");
	    }
	}

}
