import { Component, Input, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Tag } from 'src/app/models';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-top-tags',
  templateUrl: './top-tags.component.html',
  styleUrls: ['./top-tags.component.css']
})
export class TopTagsComponent {
  @Input() selectedMin! : number;
  newsService = inject(NewsService);
  topTagsList : Tag[] = []
  noResults = false;
  router = inject(Router);

  ngOnInit(){
    this.topTagsList = [];
    this.newsService.getTagsByTime(this.selectedMin).then( response => {
      for(let t of (response as any)){
        let newTag = new Tag();
        newTag.tag = t.tag;
        newTag.count = t.count;
        this.topTagsList.push(newTag);
      }
    }).catch(()=>{
      this.noResults = true;
      console.log("no result")
    }
    )
  }

  ngOnChanges(){
    this.topTagsList = [];
    this.newsService.getTagsByTime(this.selectedMin).then( response => {
      for(let t of (response as any)){
        console.log(t)
        let newTag = new Tag();
        newTag.tag = t.tag;
        newTag.count = t.count;
        this.topTagsList.push(newTag);
      }
    })
  }

  toNewsDetails(tag : string){
    this.router.navigate(['/news-list'], {queryParams: {minutes: this.selectedMin, tag: tag}})
  }
}
