import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShareNewsComponent } from './components/share-news/share-news.component';

const routes: Routes = [
  { path: '', redirectTo: '/share-news', pathMatch: 'full'},
  { path: 'share-news', component: ShareNewsComponent},
  // { path: 'news-details'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash : true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
