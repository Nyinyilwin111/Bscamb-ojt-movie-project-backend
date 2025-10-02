package ojt_final_project.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import ojt_final_project.demo.dto.CrewDTO;
import ojt_final_project.demo.entity.Crew;
import ojt_final_project.demo.entity.MovieCrew;
import ojt_final_project.demo.repo.MovieCrewRepo;

@Service
public class MovieCrewServiceImplement implements MovieCrewService {

	@Autowired
	MovieCrewRepo movieCrewRepo;

	 @Override
	    public List<CrewDTO> getCrewDTOsByMovieId(int movieId) {
		 List<MovieCrew> movieCrews = movieCrewRepo.findByMovie_Id(movieId);
	        List<CrewDTO> crewDTOList = new ArrayList<>();

	        for (MovieCrew mc : movieCrews) {
	            Crew crew = mc.getCrew();
	            CrewDTO dto = new CrewDTO();
	            dto.setName(crew.getName());
	            dto.setRole(mc.getRole()); // from MovieCrew
	            dto.setDate_of_birth(crew.getDate_of_birth().toString());
	            dto.setDescription(crew.getDescription());
	            dto.setLink(crew.getLink());
	            dto.setImage(crew.getImage());
	            crewDTOList.add(dto);
	        }

	        return crewDTOList;
	    }

	@Override
	public MovieCrew getCrewRole(int movieId, int crewId) {

		return movieCrewRepo.findByMovieIdAndCrewId(movieId, crewId).orElse(null);
	}

	@Override
	public MovieCrew updateCrewRole(MovieCrew role) {
	    return movieCrewRepo.findById(role.getId())
	        .map(existing -> {
	            if ("Director".equalsIgnoreCase(role.getRole())) {
	                boolean existsDirector = movieCrewRepo.existsByMovieIdAndRoleIgnoreCase(
	                        existing.getMovie().getId(),
	                        "Director"
	                );

	                if (existsDirector && !"Director".equalsIgnoreCase(existing.getRole())) {
	                    throw new IllegalStateException("This movie already has a Director assigned!");
	                }
	            }

	            // update role
	            existing.setRole(role.getRole());
	            return movieCrewRepo.save(existing);
	        })
	        .orElseThrow(() -> new EntityNotFoundException("Crew role not found with id " + role.getId()));
	}

	


}
