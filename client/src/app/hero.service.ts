import { Injectable } from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {Hero} from "./hero";
import {HEROES} from "./mock-heroes";
import {of} from "rxjs/internal/observable/of";

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  constructor() { }

  getHeroes(): Observable<Hero[]> {
    return of(HEROES);
  }
}
