import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GameService } from './game.service';
import {Game} from "./models/game.model";

describe('GameService', () => {
  let service: GameService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Import HttpClientTestingModule
      providers: [GameService]
    });

    service = TestBed.inject(GameService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create a new game', () => {
    const mockGame: Game = new Game();

    service.createGame().subscribe(game => {
      expect(game).toBeTruthy();
    });

    const req = httpTestingController.expectOne('/api/games');
    expect(req.request.method).toEqual('POST');
    req.flush(mockGame);
  });

  it('should retrieve a game by ID', () => {
    const mockGame: Game = new Game();
    const gameId = 0;

    service.getGame(gameId).subscribe(game => {
      expect(game).toBeTruthy();
      expect(game.id).toEqual(gameId);
    });

    const req = httpTestingController.expectOne(`/api/games/${gameId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockGame);
  });

  it('should make a move in the game', () => {
    const mockGame: Game = new Game();
    const gameId = 1;
    const pitIndex = 2;

    service.makeMove(gameId, pitIndex).subscribe(game => {
      expect(game).toBeTruthy();
    });

    const req = httpTestingController.expectOne(`/api/games/${gameId}/move/${pitIndex}`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockGame);
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});
