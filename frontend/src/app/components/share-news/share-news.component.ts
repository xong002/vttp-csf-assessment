import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { News } from 'src/app/models';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-share-news',
  templateUrl: './share-news.component.html',
  styleUrls: ['./share-news.component.css'],
})
export class ShareNewsComponent {
  formGroup!: FormGroup;
  fb = inject(FormBuilder);
  tags: string[] = [];
  svc = inject(NewsService);
  router = inject(Router);

  @ViewChild('uploadFile')
  private eRef!: ElementRef;

  ngOnInit() {
    this.formGroup = this.fb.group({
      title: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(5),
      ]),
      photo: this.fb.control('', Validators.required),
      description: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(5),
      ]),
      tags: this.fb.control<string>(''),
    });
  }

  addTag() {
    let tagsInput: string = this.formGroup.value['tags'];
    if (tagsInput != null) {
      let tagsArray = tagsInput.split(/\s+/);
      tagsArray.forEach((tag) => {
        if (tag.trim() != '' && !this.tags.find((t) => t === tag))
          this.tags.push(tag);
      });
    }
    this.formGroup.controls['tags'].reset();
  }

  deleteTag(tag: string) {
    this.tags.splice(
      this.tags.findIndex((t) => t === tag),
      1
    );
  }

  processForm() {
    let n = new News();
    n.title = this.formGroup.value['title'];
    n.description = this.formGroup.value['description'];
    n.tags = this.tags;
    this.svc.saveNews(n, this.eRef).then(response => {
      alert("News saved. News Id: " + (response as any).newsId);
      this.router.navigate(['/']);
    }).catch(error => {
      alert("There is an error. Please try again");
    });
  }
}
