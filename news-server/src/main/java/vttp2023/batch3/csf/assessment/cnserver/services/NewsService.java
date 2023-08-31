package vttp2023.batch3.csf.assessment.cnserver.services;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepo;

	@Autowired
	private ImageRepository imageRepo;

	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	@Transactional
	public String saveNews(String title, String description, Optional<String> tags, Optional<MultipartFile> file) {
		String imageUrl = "";

		try {
			if (!file.isEmpty())
				imageUrl = "https://csf-xz.sgp1.digitaloceanspaces.com/images/" + imageRepo.saveImage(file.get());
		} catch (Exception e) {
			return null; // change to custom Exception
		}

		News n = new News();
		n.setTitle(title);
		n.setDescription(description);
		n.setImage(imageUrl);

		List<String> stringList = new LinkedList<String>();
		if (!tags.isEmpty()) {
			JsonReader r = Json.createReader(new StringReader(tags.get()));
			JsonArray arr = r.readArray();
			System.out.println(arr.toString());
			arr.forEach(tag -> {
				stringList.add(tag.toString().replaceAll("\"", ""));
			});
		}
		n.setTags(stringList);

		Document doc = newsRepo.insertNews(n);
		if (doc == null) {
			return null; // change to custom Exception
		}

		return doc.get("_id").toString();
	}

	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(/* Any number of parameters */) {
		return new LinkedList<>();
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(/* Any number of parameters */) {
		return new LinkedList<>();
	}

}
