package myapp.application.guessword.model;

public class GameGuessResponse extends GameResponse {

	private Long gameId;
	private GameState status;


	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public GameState getStatus() {
		return status;
	}

	public void setStatus(GameState status) {
		this.status = status;
	} 

}
