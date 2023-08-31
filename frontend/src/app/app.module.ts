import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { ShareNewsComponent } from './components/share-news/share-news.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { TopTagsComponent } from './components/home-page/top-tags/top-tags.component';
import { NewsListComponent } from './components/news-list/news-list.component';

@NgModule({
  declarations: [
    AppComponent,
    ShareNewsComponent,
    HomePageComponent,
    TopTagsComponent,
    NewsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
