package vttp2023.batch3.csf.assessment.cnserver.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
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
	public List<TagCount> getTags(Integer minutes) {
		List<Document> result = newsRepo.findTopTagsByMinutes(minutes);
		List<TagCount> tagList = new LinkedList<>();
		for (Document d : result) {
			TagCount t = new TagCount(d.getString("tag"), d.getInteger("count"));
			tagList.add(t);
		}
		return tagList;
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(Integer minutes, String tag) {
		List<Document> result = newsRepo.getNewsByTag(minutes, tag);
		List<News> newsList = new LinkedList<>();
		for (Document d : result) {
			News n = new News();
			n.setId(d.getObjectId("_id").toString());
			n.setPostDate(d.getLong("postDate"));
			n.setTitle(d.getString("title"));
			n.setDescription(d.getString("description"));
			n.setImage(d.getString("image"));
			n.setTags(Arrays.asList(d.getString("tags").split(" ")));
			newsList.add(n);
		}
		return newsList;
	}

}
