package myapp.application.guessword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import myapp.application.guessword.helper.GameHelper;
import myapp.application.guessword.model.GameCreateResponse;
import myapp.application.guessword.model.GameGetResponse;
import myapp.application.guessword.model.GameGuessResponse;
import myapp.application.guessword.model.GameRecord;
import myapp.application.guessword.model.GameState;
import myapp.application.guessword.repository.GameRecordRepository;

@Service
public class GameService {

	@Autowired
	private GameHelper gameHelper;

	@Autowired
	private GameRecordRepository repository;

	public GameCreateResponse createNewGame() {
		System.out.println("Enter: GameService.createNewGame()");
		String word = gameHelper.generateRandomWord();
		GameRecord game = gameHelper.createNewGameRecord(word);
		repository.save(game);

		return gameHelper.generateCreateGameResponse(game);
	}

	public GameGuessResponse guess(Long id, String guessChar) {
		System.out.println("Enter: GameService.guess() " + id + "-" + guessChar);

		GameRecord gameRecord = getGameRecord(id);
		if (gameRecord != null && gameHelper.checkIfContinueGame(gameRecord) && guessCharIsValid(guessChar)) {
			if (isGuessWord(guessChar)) { // if it's more than 1 character, it's considered guessWord
				continueGuessWordGame(gameRecord, guessChar);
			} else {
				continueGuessGame(gameRecord, guessChar);
			}
			repository.save(gameRecord);
		} else
			System.out.println(
					"gameRecord != null && gameHelper.checkIfContinueGame(gameRecord) && guessCharIsValid(guessChar) "
							+ "is false, will ignore the request");
		return gameHelper.generateGuessGameResponse(gameRecord);
	}

	public GameGetResponse getGame(Long id) {
		System.out.println("Enter: GameService.getGame() " + id);
		GameRecord gameRecord = getGameRecord(id);
		return gameHelper.generateGetGameResponse(gameRecord);
	}

	public GameGetResponse endGame(Long id) {
		System.out.println("Enter: GameService.getGame() " + id);
		GameRecord gameRecord = getGameRecord(id);
		if (gameRecord != null) {
			gameRecord.setStatus(GameState.FORFEIT);
			repository.save(gameRecord);
		}
		return gameHelper.generateGetGameResponse(gameRecord);
	}

	private GameRecord getGameRecord(Long id) {
		GameRecord gameRecord = null;
		try {
			gameRecord = repository.getReferenceById(id);
		} catch (Exception e) {
			System.err.println(e);
		}
		return gameRecord;
	}

	private boolean guessCharIsValid(String guessChar) {
		return guessChar != null && guessChar.length() > 0;
	}

	private boolean isGuessWord(String guessWord) {
		return guessWord.length() > 1;
	}

	private void continueGuessGame(GameRecord gameRecord, String guessChar) {
		System.out.println("Enter: GameService.continueGame()");
		boolean match = gameHelper.makeAGuess(gameRecord, guessChar);
		if (!match) {
			reduceAttemptCount(gameRecord);
		} else {
			System.out.println("updateWordResponse()");
			gameHelper.updateWordResponse(gameRecord, guessChar);
			if (gameHelper.checkIfWon(gameRecord))
				gameRecord.setStatus(GameState.WON);
		}

	}

	private void continueGuessWordGame(GameRecord gameRecord, String guessChar) {
		System.out.println("Enter: GameService.continueGuessWordGame()");
		boolean match = gameHelper.guessWord(gameRecord, guessChar);
		if (!match) {
			reduceAttemptCount(gameRecord);
		} else {
			System.out.println("updateWordResponse()");
			gameHelper.updateGuessWordResponse(gameRecord, guessChar);
			gameRecord.setStatus(GameState.WON);
		}
	}

	private void reduceAttemptCount(GameRecord gameRecord) {
		System.out.println("reduceAttemptCount()");
		gameHelper.reduceAttemptCount(gameRecord);
		if (gameHelper.checkIfLost(gameRecord))
			gameRecord.setStatus(GameState.LOST);
	}
}
