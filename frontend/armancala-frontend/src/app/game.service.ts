import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from './models/game.model'; // You need to define this model

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiUrl = '/api/games';

  constructor(private http: HttpClient) {}

  createGame(): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}`, {});
  }

  getGame(gameId: number): Observable<Game> {
    return this.http.get<Game>(`${this.apiUrl}/${gameId}`);
  }

  makeMove(gameId: number, pitIndex: number): Observable<Game> {
    return this.http.post<Game>(`${this.apiUrl}/${gameId}/move/${pitIndex}`, {});
  }
}
