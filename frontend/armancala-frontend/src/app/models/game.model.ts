export class Game {
  id: number;
  pits: number[];
  currentPlayer: number;
  status: GameStatus;
  winner: number | null;

  constructor() {
    this.id = 0;
    this.pits = new Array(14).fill(4);
    this.pits[6] = 0;
    this.pits[13] = 0
    this.currentPlayer = 1;
    this.status = GameStatus.IN_PROGRESS;
    this.winner = null;
  }
}

export enum GameStatus {
  IN_PROGRESS = 'IN_PROGRESS',
  FINISHED = 'FINISHED'
}
