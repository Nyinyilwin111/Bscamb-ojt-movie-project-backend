package ojt_final_project.demo.service;

import java.util.List;

import ojt_final_project.demo.dto.CrewDTO;
import ojt_final_project.demo.entity.MovieCrew;

public interface MovieCrewService {

	 List<CrewDTO> getCrewDTOsByMovieId(int movieId);
	 
	 public MovieCrew getCrewRole(int movieId, int crewId);
	 
	 public MovieCrew updateCrewRole(MovieCrew role);

}
