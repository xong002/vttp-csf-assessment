import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { News } from './models';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  http = inject(HttpClient);

  saveNews(news : News, eRef : ElementRef){
    let formData = new FormData();
    formData.set('title', news.title);
    formData.set('description', news.description);
    console.log(JSON.stringify(news.tags));
    formData.set('tags', JSON.stringify(news.tags));
    formData.set('image', eRef.nativeElement.files[0]);
    return firstValueFrom(this.http.post<string>('/api/post', formData));
  }

  getTagsByTime(minutes : number){
    return firstValueFrom(this.http.get('/api/toptags', {params : {minutes : minutes}}));
  }

  getNewsByTag(minutes: number, tag: string){
    return firstValueFrom(this.http.get('/api/tags', {params: {minutes: minutes, tag: tag}}));
  }
}
