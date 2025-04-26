package myapp.application.guessword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import myapp.application.guessword.model.GameCreateResponse;
import myapp.application.guessword.model.GameGetResponse;
import myapp.application.guessword.model.GameGuessResponse;
import myapp.application.guessword.model.GameRequest;
import myapp.application.guessword.service.GameService;

@RestController
public class GameController {
	
   @Autowired
   private GameService gameService;
   
   @PostMapping("/game")
   public GameCreateResponse newGame() {
	  System.out.println("Enter: GameController.newGame()");
	  return gameService.createNewGame();
   } 
   
   @PostMapping("/game/{gameId}/guess")
   public GameGuessResponse guess(@PathVariable Long gameId, @RequestBody GameRequest request) {
	   System.out.println("Enter: GameController.guess()");
	   if (request == null || request.getGuess() == null)
		   return null;
	   return gameService.guess(gameId, request.getGuess().toLowerCase());
   }
   
   @PostMapping("/game/{gameId}/end")
   public GameGetResponse end(@PathVariable Long gameId) {
	   System.out.println("Enter: GameController.end()"); 
	   return gameService.endGame(gameId);
   }   
   
   @GetMapping("/game/{gameId}")
   public GameGetResponse getGame(@PathVariable Long gameId) {
	   System.out.println("Enter: GameController.getGame()");
	   return gameService.getGame(gameId);
   }
}
