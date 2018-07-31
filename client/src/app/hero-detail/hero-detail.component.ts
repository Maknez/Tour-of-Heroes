import {Component, Input, OnInit} from '@angular/core';
import {Hero} from "../hero";

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.css']
})
export class HeroDetailComponent implements OnInit {

  // external HeroesComponent will bind to this property, that's why we use @Input decorator
  @Input() hero: Hero;

  constructor() { }

  ngOnInit() {
  }

}
