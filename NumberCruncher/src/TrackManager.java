import java.util.Set;

public class TrackManager {

	private Track[] tracks;							// Declares data member to store all of the tracks in the game
	private int activeTracks;						// Declares data member to store how many tracks are currently active
	private int rangeIncrease;						// Declares data member to store how much the range increases per level
	private int level;								// Declares data member to store the current level
	private int livesPerLevel;						// Declares data member to store the amount of lives the player gains per level


	public TrackManager() { 
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	TrackManager()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called as a constructor when an object of type TrackManager is instantiated.
		//							It defines the integer data members of the object as 0 and creates a list with 7 empty tracks. 
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-27		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		tracks = new Track[7];									// Instantiates the data member "tracks" as an empty list with 7 spaces
		activeTracks = 0;										// Instantiates the data member "activeTracks" as 0
		rangeIncrease = 0;										// Instantiates the data member "rangeIncrease" as 0
		level = 0;												// Instantiates the data member "level" as 0
		livesPerLevel=0;										// Instantiates the data member "livesPerLevel" as 0

		Track tempTrack;										// Defines variable to manage tracks in the loop
		int counter;											// Defines int to control the iterations of the loop

		for (counter = 0; counter<7; counter++) {				// For all spaces in the "tracks" list
			tempTrack = new Track();							// Create a new empty track
			tracks[counter] = tempTrack;						// And add it to a position in the list
		}
	}

	public void SetupDifficulty(int difficulty) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void SetupDifficulty(int difficulty)
		//
		// Method parameters	:	difficulty -	used to determine the values that will be given to various data members
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called when the difficulty of the game changes.
		//							Depending on the value of the parameter received, it will define a certain amount of
		//							active tracks, range increase and lives given per level, data that will be necessary 
		//							to setup the progression of the levels.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-27		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		switch(difficulty) {									//Depending on the difficulty passed
		case 1:													
			activeTracks = 3;									// Defines the amount of active tracks,
			rangeIncrease = 10;									// by how much will the range increase per level
			livesPerLevel = 5;									// and the additional lives given when entering a new level
			break;
		case 2:
			activeTracks = 5;
			rangeIncrease = 100;
			livesPerLevel =7;
			break;
		case 3:
			activeTracks = 7;
			rangeIncrease = 1000;
			livesPerLevel = 11;
			break;
		}
		

	}

	public int NewLevel() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void NewLevel()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to advance to the next level. 
		//							It first determines whether or not the difficulty should be raised and does so if that's
		//							the case, otherwise, it setups all active tracks so that they generate new numbers to 
		//							be guessed, more guesses are added and all the numbers that where previously guessed are
		//							cleared from the list.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-27		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int counter;																	//Instantiates int to control the iterations of the loop
		Track tempTrack;																// Defines variable to manage tracks in the loop
		int difficultyChanged =  0;														// Defines vaaribale to store wheter the difficulty was changed
																						// when progressing to the next level
		
		level++;																		// Increases the value of the data member "level" by 1

		if(level==10) {																	// If the level is 10 and not all tracks are active yet
			difficultyChanged = activeTracks/2+1;										// Changes difficulty to the next one
			level = 0;																	// And set the level back to 0
		
		}else {																			//Else,
			for (counter = 0; counter<activeTracks; counter++) {						// Goes through all the tracks,
				tempTrack = tracks[counter];
				tempTrack.getNumbersGuessed().clear();									// Clears the list of guesses made
				tempTrack.setGuessesLeft(tempTrack.getGuessesLeft()+livesPerLevel);		// Adds lives depending on the difficulty
				tempTrack.DetermineNumberToGuess(level*rangeIncrease);					// Determines a new number to guess based on the new range
				tempTrack.setAnswered(false);											// And sets the track as not being answered yet
			}
		}

		return difficultyChanged;														//Returns a value different to 0 if the difficulty was changed

	}
	
	public void LoadLevel(int level, String[] tracksGuesses) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void LoadLevel(int level, String[] tracksGuesses)
		//
		// Method parameters	:	level	-		Used to define the data member level as the last level the player completed
		//
		//							tracksGuesses	- Used to define the guesses left from each track based on the saved Data
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to setup the level based on data previously saved.
		//							It first defines the data member level as the value passed and then setups how many guesses
		//							are left in each track when the level saved was completed.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-31		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int counter;																		//Instantiates int to control the iterations of the loop
		this.level = level;																	// Defines data member level as the last level completed
		
		for(counter = 0; counter<7; counter++) {											//Goes through all the tracks and assign to them a number
			tracks[counter].setGuessesLeft(Integer.parseInt(tracksGuesses[counter]));		// of guesses left according to the saved data.
		}
		
	}

	public int CheckGuessedNumberOnTrack(int number, int track) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	CheckGuessedNumberOnTrack(int number, int track) 
		//
		// Method parameters	:	number	-	Stores the number guessed by the player to compare with the number
		//										generated for the track.
		//
		//							track	- 	Used to define which track the player input should be compared to.
		//
		// Method return		:	int
		//
		// Synopsis				:   This method is called to determine wheter the number guessed by the player was higher,
		//							lower or equal to the number generated for the track.
		//							It first reduces the guesses left on the track by 1, then it compares the number given by 
		//							the user to the number that was generated for the given track. If both numbers are the
		//							same, the guess is correct and the track is marked as answered and the number is added
		//							to the list of numbers guessed; else it adds the given number to this list but, in this case, 
		//							alongside an arrow that represents whether the guess was too high or too low.
		//							Then it returns a number representing how the guessed number compares to the generated number.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-01		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Track currentTrack =  tracks[track];												//Stores the track that is being guessed in a variable
		int trackNumber = currentTrack.getNumberToGuess();									//Stores the number that was generated for said track
		
		currentTrack.setGuessesLeft(currentTrack.getGuessesLeft()-1);						//Reduce the number of guesses left in the track by 1
		
		if(number == trackNumber) {															//If the guess made by the user is the same as the number generated
			currentTrack.setAnswered(true);													// Sets the track as answered so no more guesses can be made
			currentTrack.getNumbersGuessed().addElement(number+"");							// Adds the number to the displayed list
			return 1;																		// And returns 1, meaning the guess was correct.
		}		
		else if (number < trackNumber) {													//Else, if the guess was lower than the number of the track
			currentTrack.getNumbersGuessed().addElement(number+" ↓");						// Adds the number to the displayed list with a down arrow
			return 2;																		// And returns 2, meaning the guess was too low
		}
		
		else if (number > trackNumber) {													//Else, if the guess was higher than the number of the track
			currentTrack.getNumbersGuessed().addElement(number+" ↑");						// Adds the number to the displayed list with an up arrow
			return 3;																		// And returns 3, meaning the guess was too high
		}
			
		
		return 0;																			//Else, returns 0 to show an error				
	}
	
	public boolean CheckAllAnswered() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	boolean CheckAllAnswered()
		//
		// Method parameters	:	none
		//
		// Method return		:	boolean
		//
		// Synopsis				:   This method is called to check is all the tracks have been guessed correctly.
		//							It goes through the active tracks and if all of them have "answered" defined
		//							as true, returns true, otherwise returns false;
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-31		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		int counter;													//Instantiates int to control the iterations of the loop
		int answeredTracks = 0;											// Defines int to count the number of tracks that have been answered
		
		for(counter=0; counter<activeTracks; counter++) {				//Goes through all the active tracks 
			if(tracks[counter].isAnswered())							// And counts how many have been answered
				answeredTracks++;
		}
		
		return answeredTracks==activeTracks;							// If all tracks have been answered return true, else returns false
	}
	

	public Track[] getTracks() {											//Getter for the data member "tracks"
		return tracks;														//returns the object's current tracks value
	}

	public void setTracks(Track[] tracks) {									// Setter for the data member "tracks"
		this.tracks = tracks;												// stores in the object's data member "tracks"
	}																		// the number given

	public int getActiveTracks() {											//Getter for the data member "activeTracks"
		return activeTracks;												//returns the object's current activeTracks value
	}

	public void setActiveTracks(int activeTracks) {							// Setter for the data member "activeTracks"
		this.activeTracks = activeTracks;									// stores in the object's data member "activeTracks"
	}																		// the number given

	public int getRangeIncrease() {											//Getter for the data member "rangeIncrease"
		return rangeIncrease;												//returns the object's current rangeIncrease value
	}

	public void setRangeIncrease(int rangeIncrease) {						// Setter for the data member "rangeIncrease"
		this.rangeIncrease = rangeIncrease;									// stores in the object's data member "rangeIncrease"
	}																		// the number given

	public int getLevel() {													//Getter for the data member "level"
		return level;														//returns the object's current level value
	}

	public void setLevel(int level) {										// Setter for the data member "level"
		this.level = level;													// stores in the object's data member "level"
	}																		// the number given

	public int getLivesPerLevel() {											//Getter for the data member "livesPerLevel"
		return livesPerLevel;												//returns the object's current livesPerLevel value
	}

	public void setLivesPerLevel(int livesPerRound) {						// Setter for the data member "livesPerRound"
		this.livesPerLevel = livesPerRound;									// stores in the object's data member "livesPerRound"
	}																		// the number given
}
