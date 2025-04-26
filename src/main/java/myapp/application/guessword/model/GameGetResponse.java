package myapp.application.guessword.model;

public class GameGetResponse extends GameResponse {
	
    private GameState status;

	public GameState getStatus() {
		return status;
	}

	public void setStatus(GameState status) {
		this.status = status;
	} 
}
