import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-share-news',
  templateUrl: './share-news.component.html',
  styleUrls: ['./share-news.component.css'],
})
export class ShareNewsComponent {
  formGroup!: FormGroup;
  fb = inject(FormBuilder);
  tags: string[] = [];

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
    let tagsArray = tagsInput.split(/\s+/);
    tagsArray.forEach((tag) => {
      if (!this.tags.find((t) => t === tag)) this.tags.push(tag);
    });
    this.formGroup.controls['tags'].reset();
  }

  deleteTag(tag: string) {
    this.tags.splice(
      this.tags.findIndex((t) => t === tag),
      1
    );
  }

  processForm() {}
}
