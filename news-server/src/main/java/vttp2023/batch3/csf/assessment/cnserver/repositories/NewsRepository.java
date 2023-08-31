package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate template;

	// TODO: Task 1
	// Write the native Mongo query in the comment above the method
	// db.news.insertOne(
	// {
	// postDate: NumberInt(160000000),
	// title: "title",
	// description: "This is just a test description",
	// image: {imageString:"imageString", url:"url"},
	// tags: [
	// { tag : "tag1"},
	// { tag : "tag2"}
	// ]
	// }
	// )
	public Document insertNews(News news) {
		try {
			Document doc = new Document();
			doc.append("postDate", System.currentTimeMillis());
			doc.append("title", news.getTitle());
			doc.append("description", news.getDescription());
			doc.append("image", news.getImage());
			doc.append("tags", news.getTags());
			Document insertedDoc = template.insert(doc, "news");
			return insertedDoc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// TODO: Task 2
	// db.news.aggregate(
	// {
	// $match: { "postDate" : { $gte: 1693455438073}}
	// },
	// {
	// $unwind: "$tags"
	// },
	// {
	// $group: {
	// _id : "$tags",
	// count: { $sum : 1}
	// }
	// },
	// {
	// $match: {
	// $nor : [{_id: ''},{_id: 'null'}]
	// }
	// },
	// {
	// $sort : { count : -1 }
	// },
	// {
	// $limit: 10
	// },
	// {
	// $project : {
	// _id : 0,
	// tag: "$_id",
	// count: 1
	// }
	// }
	// )
	// Write the native Mongo query in the comment above the method
	public List<Document> findTopTagsByMinutes(Integer minutes) {
		long dateNowMs = System.currentTimeMillis();
		long selectedMs = minutes * 60000;
		long earliestMs = dateNowMs - selectedMs;

		MatchOperation match1 = Aggregation.match(Criteria.where("postDate").gte(earliestMs));
		UnwindOperation unwind = Aggregation.unwind("tags");
		GroupOperation group = Aggregation.group("tags").count().as("count");
		Criteria criteria = new Criteria();
		MatchOperation match2 = Aggregation.match(criteria.norOperator(
				Criteria.where("_id").is(""), Criteria.where("_id").is(null)));
		SortOperation sort = Aggregation.sort(Sort.by(Direction.DESC, "count"));
		LimitOperation limit = Aggregation.limit(10);
		ProjectionOperation project = Aggregation.project("count").and("_id").as("tag");

		Aggregation pipeline = Aggregation.newAggregation(match1, unwind, group, match2, sort, limit, project);

		AggregationResults<Document> result = template.aggregate(pipeline, "news", Document.class);

		if (result.getMappedResults().isEmpty()) {
			return null;
		}
		return result.getMappedResults();

	}

	// TODO: Task 3
	// Write the native Mongo query in the comment above the method
	// db.news.aggregate(
	// {
	// $match: { "postDate" : { $gte: 1693455438073}}
	// },
	// {
	// $unwind: "$tags"
	// },
	// {
	// $match: { "tags" : "apple"}
	// },
	// )
	public List<Document> getNewsByTag(Integer minutes, String tag) {
		long dateNowMs = System.currentTimeMillis();
		long selectedMs = minutes * 60000;
		long earliestMs = dateNowMs - selectedMs;

		MatchOperation match1 = Aggregation.match(Criteria.where("postDate").gte(earliestMs));
		UnwindOperation unwind = Aggregation.unwind("tags");
		MatchOperation match2 = Aggregation.match(Criteria.where("tags").gte(tag));

		Aggregation pipeline = Aggregation.newAggregation(match1, unwind, match2);

		AggregationResults<Document> result = template.aggregate(pipeline, "news", Document.class);

		if (result.getMappedResults().isEmpty()) {
			return null;
		}
		return result.getMappedResults();
	}

}
