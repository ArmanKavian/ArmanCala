import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { Game } from './models/game.model';
import { GameService } from './game.service';
import {GameControlsComponent} from "./game-controls/game-controls.component";
import {GameBoardComponent} from "./game-board/game-board.component";
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, GameControlsComponent, GameBoardComponent, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ArmanCala';

  game: Game;

  constructor(private gameService: GameService) {
    this.game = new Game()
  }

  startNewGame() {
    this.gameService.createGame().subscribe(game => {
      this.game = game;
    });
  }

  makeMove(pitIndex: number) {
    if (this.game) {
      this.gameService.makeMove(this.game.id, pitIndex).subscribe(updatedGame => {
        this.game = updatedGame;
      }, error => {
        console.error('Error making move:', error);
      });
    }
  }

  handlePitClick(pitIndex: number): void {
    this.makeMove(pitIndex)
  }
}
