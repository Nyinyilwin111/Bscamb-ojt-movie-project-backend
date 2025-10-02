package ojt_final_project.demo.service;

import java.util.List;
import ojt_final_project.demo.entity.Crew;

public interface CrewService {

    Crew save(Crew crew);

    boolean saveUserInfo(Crew crew);

    List<Crew> getCrewsByMovieId(int movieId);

    boolean delete(int id);

    Crew get(int id);

    Crew findByName(String name);
    
    public List<Crew> getAll();
    
    public Crew update(Crew crew);
}
