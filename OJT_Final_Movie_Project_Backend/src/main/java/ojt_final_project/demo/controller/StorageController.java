package ojt_final_project.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ojt_final_project.demo.service.StorageService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileType") String fileType) throws IOException {

        String result = storageService.create(file, fileType);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    
    @GetMapping("/media/{fileType}/{fileName}")  // not use
    public ResponseEntity<?>download(
    		@PathVariable("fileType") String fileType,
    		@PathVariable("fileName") String fileName
    		) throws IOException{
    	MediaType type = MediaType.IMAGE_PNG;
    	switch(fileType) {
    	case "mp4":
    		type = MediaType.APPLICATION_OCTET_STREAM;
    		break;
    	case "jpg":
    	case "jpeg":
    		type = MediaType.IMAGE_JPEG;
    		break;
    	case "txt":
    		type = MediaType.TEXT_PLAIN;
    		break;
    	case "png":
    		type = MediaType.IMAGE_PNG;
    		break;
    	default:
    		return ResponseEntity.badRequest().body("Unsupported File Type");
    	}
    	byte[] fileBytes = storageService.getFile(fileName);
    	if(fileBytes == null) {
    		return ResponseEntity.notFound().build();
    		
    	}
		return ResponseEntity.ok().contentType(type).body(fileBytes);
    	
    }
    

}
