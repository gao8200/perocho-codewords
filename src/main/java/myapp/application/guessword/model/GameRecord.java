package myapp.application.guessword.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GameRecord {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
	private GameState status;
	private String word;
	private String maskedWord;
	private int remainingAttempts;
	
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMaskedWord() {
		return maskedWord;
	}
	public void setMaskedWord(String maskedWord) {
		this.maskedWord = maskedWord;
	}
	public int getRemainingAttempts() {
		return remainingAttempts;
	}
	public void setRemainingAttempts(int remainingAttempts) {
		this.remainingAttempts = remainingAttempts;
	}
	
	 
	
	
}
