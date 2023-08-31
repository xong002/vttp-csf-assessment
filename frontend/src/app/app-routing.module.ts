import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShareNewsComponent } from './components/share-news/share-news.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { NewsListComponent } from './components/news-list/news-list.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'share-news', component: ShareNewsComponent },
  { path: 'news-list', component: NewsListComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
