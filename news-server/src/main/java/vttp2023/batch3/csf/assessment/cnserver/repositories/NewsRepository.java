package vttp2023.batch3.csf.assessment.cnserver.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;

@Repository
public class NewsRepository {
	
	@Autowired
	private MongoTemplate template;

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method
	// 	db.news.insertOne(
	// {
	//     postDate: NumberInt(160000000),
	//     title: "title",
	//     description: "This is just a test description",
	//     image: {imageString:"imageString", url:"url"},
	//     tags: [
	//         { tag : "tag1"},
	//         { tag : "tag2"}
	//     ]
	// }
	// )
	public Document insertNews(News news){
		try{
		Document doc = new Document();
		doc.append("postDate", System.currentTimeMillis());
		doc.append("title", news.getTitle());
		doc.append("description", news.getDescription());
		doc.append("image", news.getImage());
		doc.append("tags", news.getTags());
		Document insertedDoc = template.insert(doc, "news");
		return insertedDoc;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}
	

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method


	// TODO: Task 3
	// Write the native Mongo query in the comment above the method


}
