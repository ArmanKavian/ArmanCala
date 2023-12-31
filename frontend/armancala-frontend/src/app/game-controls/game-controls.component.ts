import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-game-controls',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-controls.component.html',
  styleUrl: './game-controls.component.scss'
})
export class GameControlsComponent {
  @Output() newGame = new EventEmitter<void>();
  @Output() makeMove = new EventEmitter<number>();

  startNewGame() {
    this.newGame.emit();
  }
}
