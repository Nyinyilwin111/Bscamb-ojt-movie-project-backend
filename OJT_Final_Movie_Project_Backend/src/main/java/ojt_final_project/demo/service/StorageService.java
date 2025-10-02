package ojt_final_project.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	public String create(MultipartFile file, String fileType);

	public byte[] load(String filePath);

	public boolean delete(String filePath);
	
//	public boolean check(String filePath);
	
//	------------
	
	public String upload(MultipartFile file,String type) throws IOException;

	public byte[] getFile(String fileName) throws MalformedURLException, IOException;

}
