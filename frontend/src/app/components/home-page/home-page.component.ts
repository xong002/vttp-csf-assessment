import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  minuteOptions = [5, 15, 30, 45, 60, 1000];
  selectedMin = 5;

  @ViewChild('dropdown')
  private eRef! : ElementRef;

  ngOnInit(){
    let m = localStorage.getItem("minutes");
    if(m != null){
      this.selectedMin = +m;
    }
  }

  changeMinutes(){
    this.selectedMin = this.eRef.nativeElement.value;
  }
}
