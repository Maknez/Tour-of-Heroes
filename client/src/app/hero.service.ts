import { Injectable } from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {Hero} from "./hero";
import {of} from "rxjs/internal/observable/of";
import {MessageService} from "./message.service";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, tap} from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  private basicUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private messageService: MessageService) { }

  getHeroes(): Observable<Hero[]> {
    this.messageService.add('HeroService: fetched heroes');

    return this.http.get<Hero[]>(`${this.basicUrl}/heroes`)
      .pipe(
        tap(heroes => this.log(`Fetched heroes`)),
        catchError(this.handleError(`getHeroes`, []))
      );
  }

  getHero(id: number): Observable<Hero> {
    this.messageService.add(`HeroService: fetched hero id=${id}`);

    return this.http.get<Hero>(`${this.basicUrl}/heroes/details/${id}`)
      .pipe(
        tap(_ => this.log(`Fetched heroes id=${id}`)),
        catchError(this.handleError<Hero>(`getHero id=${id}`))
      );
  }

  addHero(hero: Hero): Observable<Hero> {
    return this.http.post<Hero>(`${this.basicUrl}/heroes`, hero, httpOptions)
      .pipe(
        tap((hero: Hero) => this.log(`Added hero w/ id=${hero.id}`)),
        catchError(this.handleError<Hero>('addHero'))
      );
  }

  deleteHero(hero: Hero | number): Observable<Hero> {
    const id = typeof hero === 'number' ? hero : hero.id;

    return this.http.delete<Hero>(`${this.basicUrl}/heroes/${id}`, httpOptions)
      .pipe(
        tap(_ => this.log(`Deleted hero=${id}`)),
        catchError(this.handleError<Hero>('deleteHero'))
      );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    }
  }

  private log(message: string) {
    this.messageService.add(`MessageService: ${message}`);
  }
}
