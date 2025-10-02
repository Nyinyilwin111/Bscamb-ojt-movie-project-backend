package ojt_final_project.demo.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ojt_final_project.demo.entity.Crew;
import ojt_final_project.demo.service.CrewService;
import ojt_final_project.demo.service.StorageService;

@RestController
@RequestMapping("/crew")
public class CrewController {

    @Autowired
    CrewService crewService;
    
	@Autowired
	StorageService storageService;

    @PostMapping("/crewSave")  // not use
    public ResponseEntity<Crew> saveUser(@RequestBody Crew crew) {
        System.out.println("testing : " + crew.getName() + " " + crew.getId() + " " + crew.getDescription() + " " + crew.getDate_of_birth());
        boolean result = crewService.saveUserInfo(crew);
        if (!result) {
            return new ResponseEntity<>(crew, HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(crew, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")  // not use
    public ResponseEntity<Boolean> deleteCrewWithId(@PathVariable int id) {
        Boolean crew = crewService.delete(id);
        if (!crew) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/getallcrew")
    public ResponseEntity<List<Crew>> getAll() {
        List<Crew> crew = crewService.getAll();
        
        if (crew == null || crew.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }

        crew.forEach(category -> System.out.println("Category: " + category));

        return new ResponseEntity<>(crew, HttpStatus.OK);
    }

    @GetMapping("/getcrew/{name}")
    public ResponseEntity<Crew> getCrewWithName(@RequestParam String name) {
    	Crew crew = crewService.findByName(name);
    	if(crew != null) return new ResponseEntity<Crew>(crew,HttpStatus.ACCEPTED);
		return null;
      
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
    		@PathVariable int id,
	        @RequestParam("name") String name,
	        @RequestParam("link") String link,
	        @RequestParam("date_of_birth") String dob,
	        @RequestParam("description") String discuss,
	        @RequestParam(value = "image", required = false) MultipartFile image
    		){
    	try {
        	Crew crew = crewService.get(id);
        	if( crew == null) {
        		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("crew not found");
        	}
        	crew.setName(name);
        	crew.setLink(link);
        	crew.setDate_of_birth(LocalDate.parse(dob));
        	crew.setDescription(discuss);
            if (image != null && !image.isEmpty()) {
                storageService.delete(crew.getImage());
                String imagePath = storageService.create(image, "jpg"); 
                crew.setImage(imagePath);
            }
            crewService.update(crew);
    		return ResponseEntity.ok("Crew updated successfully");
    	}catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update movie");
	    }
    }
}
