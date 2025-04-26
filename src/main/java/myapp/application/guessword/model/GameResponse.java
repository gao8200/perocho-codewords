package myapp.application.guessword.model;

public class GameResponse {
	private String maskedWord;
	private int remainingAttempts;
	
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
