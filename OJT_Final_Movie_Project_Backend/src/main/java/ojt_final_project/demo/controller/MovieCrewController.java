package ojt_final_project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ojt_final_project.demo.dto.CrewDTO;
import ojt_final_project.demo.entity.MovieCrew;
import ojt_final_project.demo.service.MovieCrewService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/movie_crew")
public class MovieCrewController {

	@Autowired
	MovieCrewService movieCrewService;
	
	 @GetMapping("/crew/{movieId}")  // not use
	    public List<CrewDTO> getCrewWithRoles(@PathVariable int movieId) {
	        return movieCrewService.getCrewDTOsByMovieId(movieId);
	    }

	 @GetMapping("/role/{movieId}/{crewId}")
	 public ResponseEntity<MovieCrew> getCrewRole(
	     @PathVariable int movieId,
	     @PathVariable int crewId) {

	     MovieCrew movieCrew = movieCrewService.getCrewRole(movieId, crewId);
	     if (movieCrew == null) {
	         return ResponseEntity.notFound().build();
	     }
	     return ResponseEntity.ok(movieCrew);
	 }
	 
	 @PutMapping(value = "/crew/role-update", consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<?> updateStatus(@RequestBody MovieCrew info) {
	     try {
	         System.out.println("Received MovieCrew: " + info);
	         MovieCrew updated = movieCrewService.updateCrewRole(info);
	         return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	     } catch (IllegalStateException e) {
	         // Client-side validation failed (e.g., duplicate Director)
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	     } catch (Exception e) {
	         // Fallback for unexpected errors
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
	     }
	 }

}
