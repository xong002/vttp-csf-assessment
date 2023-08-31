package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {
	
	@Autowired
	private AmazonS3 s3;
	// TODO: Task 1

	public String saveImage(MultipartFile file) throws Exception {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());
		Map<String, String> userData = new HashMap<>();
		userData.put("filename", file.getOriginalFilename());
		metadata.setUserMetadata(userData);

		String id = UUID.randomUUID().toString().substring(0, 8);

		try{
			PutObjectRequest putReq = new PutObjectRequest("csf-xz", "images/%s".formatted(id), file.getInputStream(), metadata);
			putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putReq);
		} catch (IOException ioe){
			throw new Exception("Error uploading image");
		}

		return id;
	}

}
