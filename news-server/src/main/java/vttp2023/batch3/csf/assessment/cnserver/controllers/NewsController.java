package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.Response;

import jakarta.json.Json;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

@RestController
@RequestMapping("/api")
public class NewsController {

	@Autowired
	private NewsService newsService;

	// TODO: Task 1
	@PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> saveNews(@RequestPart String title, @RequestPart String description,
			@RequestPart Optional<String> tags, @RequestPart Optional<MultipartFile> image) {
		System.out.println(tags);
		String insertedId = newsService.saveNews(title, description, tags, image);
		if (insertedId == null) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
		return ResponseEntity.status(200).body(Json.createObjectBuilder().add("newsId", insertedId).build().toString());
	}

	// TODO: Task 2
	@GetMapping(path = "/toptags")
	public ResponseEntity<List<TagCount>> getTopTags(@RequestParam Integer minutes) {
		List<TagCount> tags = newsService.getTags(minutes);
		if (tags.isEmpty()) {
			return ResponseEntity.status(500).body(null);
		}
		return ResponseEntity.status(200).body(tags);
	}

	// TODO: Task 3
	@GetMapping(path = "/tags")
	public ResponseEntity<List<News>> getTopTags(@RequestParam String tag, @RequestParam Integer minutes) {
		List<News> newsList = newsService.getNewsByTag(minutes, tag);
		if (newsList.isEmpty()) {
			return ResponseEntity.status(500).body(null);
		}
		return ResponseEntity.status(200).body(newsList);
	}

}
