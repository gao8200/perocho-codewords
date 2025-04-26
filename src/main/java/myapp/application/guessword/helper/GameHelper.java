package myapp.application.guessword.helper;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.application.guessword.client.RandomWordClient;
import myapp.application.guessword.config.GameConfig;
import myapp.application.guessword.model.GameCreateResponse;
import myapp.application.guessword.model.GameGetResponse;
import myapp.application.guessword.model.GameGuessResponse;
import myapp.application.guessword.model.GameRecord;
import myapp.application.guessword.model.GameState;

@Service
public class GameHelper {

	public static final String BLANK = "_";
	public static final String SPACE = " ";
	public static final int NO_MORE_ATTEMPT_LEFT = 0;

	@Autowired
	private RandomWordClient randomWordClient;

	@Autowired
	private GameConfig gameConfig;

	public String generateRandomWord() {
		System.out.println("Enter: GameHelper.generateRandomWord");
		String word = null;		
		try {
			word = randomWordClient.getWord(gameConfig.getRandomApiEndpoint());
		} catch (Exception e) {
			System.err.println("Exception occurred: " + e.getMessage());
		}
		//if there's some unforseen error in the api call, let's just pick from property file
		if (word == null) {
			System.out.println("Pulling up random word from property.game.randomWords");
			Random random = new Random();
			word = gameConfig.getRandomWords()[random.nextInt(gameConfig.getRandomWords().length)];
		}
		return word.toLowerCase();
	}

	public GameRecord createNewGameRecord(String wordToGuess) {
		System.out.println("Enter: GameHelper.createNewGameRecord");
		GameRecord newGame = new GameRecord();
		newGame.setRemainingAttempts(gameConfig.getMaxAttempt());
		newGame.setWord(wordToGuess);
		newGame.setMaskedWord(generateInititalGuessString(wordToGuess));
		newGame.setStatus(GameState.IN_PROGRESS);
		return newGame;
	}
	
	public GameCreateResponse generateCreateGameResponse(GameRecord game) {
		System.out.println("Enter: GameHelper.generateGameResponse");
		GameCreateResponse response = new GameCreateResponse();
		if (game==null)
			return response;
		
		response.setGameId(game.getGameId());
		response.setRemainingAttempts(game.getRemainingAttempts());  
		response.setMaskedWord(game.getMaskedWord());
		return response;
	}	

	public GameGuessResponse generateGuessGameResponse(GameRecord game) {
		System.out.println("Enter: GameHelper.generateGameResponse");
		GameGuessResponse response = new GameGuessResponse();
		if (game==null)
			return response;

		response.setRemainingAttempts(game.getRemainingAttempts());
		response.setGameId(game.getGameId());
		response.setStatus(game.getStatus());
		response.setMaskedWord(game.getMaskedWord());
		return response;
	}
	
	public GameGetResponse generateGetGameResponse(GameRecord game) {
		System.out.println("Enter: GameHelper.generateGameResponse");
		GameGetResponse response = new GameGetResponse();
		if (game==null)
			return response;

		response.setRemainingAttempts(game.getRemainingAttempts()); 
		response.setStatus(game.getStatus());
		response.setMaskedWord(game.getMaskedWord());
		return response;
	}	

	private String generateInititalGuessString(String wordToGuess) {
		StringBuilder sb = new StringBuilder();
		wordToGuess.chars().forEach( c -> {
			sb.append(BLANK);
			sb.append(SPACE);
		});
		return sb.toString().trim();
	}

	public boolean makeAGuess(GameRecord gameRecord, String guess) {
		System.out.println("Enter: GameHelper.makeAGuess");
		gameRecord.setStatus(GameState.IN_PROGRESS);
		System.out.println("word is "+gameRecord.getWord());//TODO
		return gameRecord.getWord().indexOf(guess) >= 0; 
	}
	
	public boolean guessWord(GameRecord gameRecord, String guess) {
		System.out.println("Enter: GameHelper.guessWord");
		gameRecord.setStatus(GameState.IN_PROGRESS);
		System.out.println("word is "+gameRecord.getWord());//TODO
		return gameRecord.getWord().equalsIgnoreCase(guess);
	}	

	public void updateWordResponse(GameRecord gameRecord, String guess) {
		System.out.println("Enter: GameHelper.updateWordResponse");
		StringBuilder sb = new StringBuilder();
		String word = gameRecord.getWord();
		String masked = gameRecord.getMaskedWord();
		for (int index=0, indexMask=0; index < word.length(); index++, indexMask+=2) {
			if (word.charAt(index) == guess.charAt(0)) 
				sb.append(guess.substring(0,1));
			else {
				if (masked.charAt(indexMask) != '_')
				  sb.append(String.valueOf(masked.charAt(indexMask)));
				else
				  sb.append(BLANK);
			}
			sb.append(SPACE);
		}
		gameRecord.setMaskedWord(sb.toString().trim());
	}
	
	public void updateGuessWordResponse(GameRecord gameRecord, String guess) {
		System.out.println("Enter: GameHelper.updateGuessWordResponse");
		StringBuilder sb = new StringBuilder();
		String word = gameRecord.getWord(); 
		for (int index=0 ; index < word.length(); index++) { 
			sb.append(String.valueOf(word.charAt(index)));
			sb.append(SPACE);
		}
		gameRecord.setMaskedWord(sb.toString().trim());
	}	

	public boolean checkIfWon(GameRecord gameRecord) {
		return gameRecord.getMaskedWord().indexOf(BLANK) < 0;
	}

	//returns true if not yet done, else false - WIN or LOST
	public boolean checkIfContinueGame(GameRecord gameRecord) {		  
		return (gameRecord != null && gameRecord.getStatus().equals(GameState.IN_PROGRESS));
	}

	public void reduceAttemptCount(GameRecord gameRecord) { 
		gameRecord.setRemainingAttempts(gameRecord.getRemainingAttempts()-1); 
	}

	public boolean checkIfLost(GameRecord gameRecord) {
		return gameRecord.getRemainingAttempts()==NO_MORE_ATTEMPT_LEFT;
	}
}
