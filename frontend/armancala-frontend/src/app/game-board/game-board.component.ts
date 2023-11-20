import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Game} from "../models/game.model";

@Component({
  selector: 'app-game-board',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-board.component.html',
  styleUrl: './game-board.component.scss'
})
export class GameBoardComponent implements OnChanges {
  @Input() game: Game;
  @Output() pitClicked = new EventEmitter<number>();

  constructor() {
    this.game = new Game()
  }

  getStonesArray(pitCount: number): any[] {
    return new Array(pitCount);
  }

  ngOnChanges(changes: SimpleChanges): void {
    // @ts-ignore
    if (changes.game) {

    }
  }

  onPitClick(pitIndex: number): void {
    this.pitClicked.emit(pitIndex);
  }
}
