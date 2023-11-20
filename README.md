# ArmanCala

ArmanCala is a web application that allows two players to play the classic game of Mancala. 
The application features a backend built with Kotlin and Spring Boot for the game's logic and a frontend created using 
Angular for the user interface.

## Prerequisites

Before running the application, ensure you have the following prerequisites installed:

- Java 11 or higher
- Node.js and npm (for Angular)
- Angular CLI (for Angular development)

## Backend Setup


   ```bash
   git clone https://github.com/ArmanKavian/ArmanCala.git
   cd armancala
   ./gradlew bootRun
   ```

The backend will run on http://localhost:8080.

## Frontend Setup

 ```bash
   cd frontend
   npm install
   ng serve
   ```

The frontend will run on http://localhost:4200.

## Usage
Access the application by opening a web browser and navigating to http://localhost:4200.

Create a new game by clicking the "New Game" button.

Play the game by taking turns to select a pit and distribute the stones according to the Mancala rules.

The game will determine the winner and display the results when it's over.

## API Endpoints
The backend provides the following API endpoints:

- POST /api/games: Create a new game.
- GET /api/games/{gameId}: Retrieve the game state by ID.
- POST /api/games/{gameId}/move/{pitIndex}: Make a move in the game by specifying the game ID and the selected pit index.
- GET /api/games/{gameId}/recommend: Recommend the best move for a game by ID.

## Contributing
Contributions are welcome! Feel free to submit issues and pull requests to help improve the game.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
Special thanks to BOL.com human resource and technical teams for putting their 
valuable inspirations and professionalism into this project.