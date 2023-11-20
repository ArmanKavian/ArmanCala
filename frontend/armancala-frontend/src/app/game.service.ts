import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from './models/game.model'; // You need to define this model

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiUrl = 'http://localhost:8080/api/games';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {

  }

  createGame(): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}`, {}, this.httpOptions);
  }

  getGame(gameId: number): Observable<Game> {
    return this.http.get<Game>(`${this.apiUrl}/${gameId}`);
  }

  makeMove(gameId: number, pitIndex: number): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}/${gameId}/move/${pitIndex}`, {});
  }
}
