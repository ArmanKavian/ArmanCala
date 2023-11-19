export class Game {
  id: number;
  pits: number[];
  currentPlayer: number;
  status: GameStatus;
  winner: number | null;

  constructor() {
    this.id = 0;
    this.pits = new Array(14).fill(4); // Assuming 6 pits per player plus 2 scoring pits
    this.currentPlayer = 1; // Player 1 starts
    this.status = GameStatus.IN_PROGRESS;
    this.winner = null;
  }
}

export enum GameStatus {
  IN_PROGRESS = 'IN_PROGRESS',
  FINISHED = 'FINISHED'
}
