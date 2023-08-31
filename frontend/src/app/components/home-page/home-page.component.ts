import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  minuteOptions = [5, 15, 30, 450, 600];
  selectedMin = 5;

  @ViewChild('dropdown')
  private eRef! : ElementRef;

  changeMinutes(){
    this.selectedMin = this.eRef.nativeElement.value;
  }
}
