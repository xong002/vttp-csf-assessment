package vttp2023.batch3.csf.assessment.cnserver.services;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {
	
	@Autowired
	private NewsRepository newsRepo;

	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String saveNews(String jsonString) {
		JsonReader r = Json.createReader(new StringReader(jsonString));
        JsonObject o = r.readObject();

		News n = new News();
		n.setTitle(o.getString("title"));
		n.setDescription(o.getString("description"));
		n.setImage("set image url from s3");
		List<String> stringList = new LinkedList<String>();
		JsonArray jsonArray = o.getJsonArray("tags");
		jsonArray.forEach(obj -> {
			stringList.add(obj.asJsonObject().getString("tag"));
		});
		n.setTags(stringList);
		
		Document doc = newsRepo.insertNews(n);
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
