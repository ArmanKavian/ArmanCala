import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { Game } from './models/game.model';
import { GameService } from './game.service';
import {GameControlsComponent} from "./game-controls/game-controls.component";
import {GameBoardComponent} from "./game-board/game-board.component";
import { HttpClientModule } from '@angular/common/http';
import {Recommendation} from "./models/recommendation.model";
import { MatDialog } from '@angular/material/dialog';
import { RecommendationDialogComponent } from './recommendation-dialog/recommendation-dialog.component';

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

  constructor(private gameService: GameService, public dialog: MatDialog) {
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

  onRecommendMove() {
    if (this.game) {
      this.gameService.recommendMove(this.game.id).subscribe(
        (recommendation: Recommendation) => {
          this.showRecommendationDialog(recommendation);
        },
        error => {
          console.error('Error fetching recommendation:', error);
        }
      );
    }
  }

  private showRecommendationDialog(recommendation: Recommendation) {
    console.log("MOVE:" + recommendation.move)
    this.dialog.open(RecommendationDialogComponent, {
      width: '250px',
      data: { move: recommendation.move }
    });
  }
}
