import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NewsDetails } from 'src/app/models';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.css']
})
export class NewsListComponent {
  svc = inject(NewsService);
  route = inject(ActivatedRoute);
  newsList : NewsDetails[] = [];
  
  ngOnInit(){
    this.svc.getNewsByTag(this.route.snapshot.queryParams['minutes'],this.route.snapshot.queryParams['tag']).then( response => {
      for(let n of (response as any)){
        let newsDetails = new NewsDetails();
        newsDetails.title = n.title;
        newsDetails.description = n.description
        newsDetails.postDate = n.postDate
        newsDetails.image = n.image
        newsDetails.tags = n.tags
        this.newsList.push(newsDetails);
      }
    })
    localStorage.setItem("minutes", this.route.snapshot.queryParams['minutes']);
  }
}
