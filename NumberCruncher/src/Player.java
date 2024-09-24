import java.util.ArrayList;

public class Player {
	private String initials;								// Declares data member to store the initials of the player
	private int score;										// Declares data member to store the current score of the player
	private int highScore;									// Declares data member to store the highest score the player got
															// after finishing the game

	public Player(String initials) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	Player(String initials)
		//
		// Method parameters	:	initials - used to give a value to the data member "initials"
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called as a constructor when an object of type Player is instantiated.
		//							It defines the data members of the object so that the player initials takes the value 
		//							of the string passed and the score is 0 so that points can be added later. It also
		//							defines highScore as 0 for new players
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-25		A. Mojica				Initial setup
		//
		//							2023-06-06		A. Mojica				Added HighScore
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		this.initials = initials;							// Instantiates the data member "initials" as the string passed
		score = 0;											// Instantiates the data member "score" as 0
		highScore = 0;										// Instantiates the data member "highScore" as 0
	}

	public int GetNewHighScore() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	int GetNewHighScore() 
		//
		// Method parameters	:	none
		//
		// Method return		:	int
		//
		// Synopsis				:   This method is called to check if the player's highScore needs to be changed at the end
		//							of the game depending if their current score if higher. Then it returns the highScore
		//							value no matter if it was changed or not.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-06		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		if(score>highScore)									//If the current Score is higher than the saved highscore
			highScore= score;								//changes the highscore to the current Score
		
		return highScore;									//and returns its value
	}
	
	public String getInitials() {							//Getter for the data member "Initials"
		return initials;									//returns the object's current initials value
	}

	public void setInitials(String initials) {				// Setter for the data member "initials"
		this.initials = initials;							// stores in the object's data member "initials"
	}														// the string given

	public  int getScore() {								//Getter for the data member "Score"
		return score;										//returns the object's current score value
	}

	public void setScore(int score) {						// Setter for the data member "score"
		this.score = score;									// stores in the object's data member "score"
	}														// the number given

	public int getHighScore() {								//Getter for the data member "highScore"
		return highScore;									//returns the object's current highScore value
	}

	public void setHighScore(int highScore) {				// Setter for the data member "highScore"
		this.highScore = highScore;							// stores in the object's data member "highScore"
	}														// the number given

}
