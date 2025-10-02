package ojt_final_project.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImplementation implements StorageService {

    private final Path storagePath;

    @Autowired
    public StorageServiceImplementation() throws IOException {
    	Path storePath = Paths.get("").resolve("src").resolve("main")
    			.resolve("resources").resolve("static").resolve("File");
    	if(!Files.exists(storePath)) {
    		Files.createDirectories(storePath);
    	}
    	this.storagePath = storePath;
    		
	}
    
    @Override
    public String create(MultipartFile file, String fileType) {
        String filePath = null;

        try {
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }

            String fileName = Instant.now().getEpochSecond() + "_" + 
                    StringUtils.cleanPath(file.getOriginalFilename());
            Files.copy(file.getInputStream(), storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            if (fileType.contains("mp4")) {
                filePath = "/media/mp4/" + fileName;
            } else if (fileType.contains("jpg") || fileType.contains("jpeg")) {
                filePath = "/media/jpg/" + fileName;
            } else {
                filePath = "/media/png/" + fileName;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    @Override
    public byte[] load(String filePaths) {
        try {
            String cleanPath = filePaths.replace("/media/", "");
            Path filePath = Path.of("media", cleanPath.split("/", 2)[1]); // e.g. "png/123_image.png"
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean delete(String filePath) {
        if (filePath != null && !filePath.isBlank()) {
            try {
                String cleanPath = filePath.replace("/media/", "");
                Path fileToDelete = Path.of("media", cleanPath.split("/", 2)[1]);
                Files.deleteIfExists(fileToDelete);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


//    ---------------
    
	@Override
	public String upload(MultipartFile file, String type) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Files.copy(file.getInputStream(), this.storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}

	@Override
	public byte[] getFile(String fileName) throws IOException {
		byte[] fileByte = null;
		Path path = this.storagePath.resolve(fileName);
		Resource resource = new UrlResource(path.toUri());
		if(resource.exists() && resource.isReadable()) {
			fileByte = StreamUtils.copyToByteArray(resource.getInputStream());
		}
		return fileByte;
	}
	
}
