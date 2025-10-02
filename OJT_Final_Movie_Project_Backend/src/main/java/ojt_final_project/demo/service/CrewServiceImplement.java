package ojt_final_project.demo.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import ojt_final_project.demo.entity.Crew;
import ojt_final_project.demo.repo.CrewReposity;

@Service
public class CrewServiceImplement implements CrewService {

    @Autowired
    CrewReposity crewRepo;

    List<Crew> crews = new ArrayList<>();

    @Override
    public Crew save(Crew crew) {
        return crewRepo.save(crew);
    }

    @Override
    public boolean saveUserInfo(Crew info) {
        return crewRepo.save(info) != null;
    }

    @Override
    public List<Crew> getCrewsByMovieId(int movieId) {
        return crewRepo.findByMovieId(movieId);
    }

    @Override
    public boolean delete(int id) {
        Crew crew = this.get(id);
        if (crew == null) return false;

        crewRepo.deleteById(crew.getId());
        return true;
    }

    @Override
    public Crew get(int id) {
        return crewRepo.findById(id).orElse(null);
    }

    @Override
    public Crew findByName(String name) {
        return crewRepo.findByName(name);
    }

	@Override
	public List<Crew> getAll() {
		// TODO Auto-generated method stub
		return crewRepo.findAll();
	}
	
	@Override
	public Crew update(Crew crew) {
	    return crewRepo.findById(crew.getId())
	        .map(existing -> {
	            existing.setName(crew.getName());
	            existing.setDate_of_birth(crew.getDate_of_birth());
	            existing.setLink(crew.getLink());
	            existing.setDescription(crew.getDescription());
	            existing.setImage(crew.getImage());
	            return crewRepo.save(existing);
	        })
	        .orElseThrow(() -> new EntityNotFoundException("Crew not found with id " + crew.getId()));
	}


}
