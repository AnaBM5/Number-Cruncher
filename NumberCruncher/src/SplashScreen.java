import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SplashScreen extends JDialog {

	public SplashScreen() {
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setBounds(100, 100, 755, 475);
		getContentPane().setLayout(null);

		JLabel lblLogo = new JLabel("Number Cruncher");
		lblLogo.setVerticalAlignment(SwingConstants.TOP);
		lblLogo.setBackground(Color.WHITE);
		lblLogo.setForeground(Color.ORANGE);
		lblLogo.setFont(new Font("Source Code Pro", Font.BOLD | Font.ITALIC, 26));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(0, 112, 741, 167);
		getContentPane().add(lblLogo);

		JPanel panel = new JPanel();
		panel.setBounds(0, 278, 741, 160);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Please enter your initials:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(294, 10, 206, 35);
		panel.add(lblNewLabel);

		JTextField textField = new JTextField();
		textField.setBounds(294, 55, 147, 35);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			//If confirm button is pressed
				String initials;
				initials = textField.getText();						//Takes the string from the textfield
				CheckInitials(initials);							//And calls method to process it accordingly.
			}
		});
		btnNewButton.setBounds(323, 113, 85, 21);
		panel.add(btnNewButton);
		
		JLabel lblWelcome = new JLabel("Welcome to");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Source Code Pro", Font.ITALIC, 17));
		lblWelcome.setForeground(Color.ORANGE);
		lblWelcome.setBounds(0, 67, 741, 56);
		getContentPane().add(lblWelcome);

	}

	private void CheckInitials(String initials) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void CheckInitials(String initials)
		//
		// Method parameters	:	initials -	used to define the initials of the newly generated player
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called when the user confirms the initials they want to have.
		//							It checks that the string given in the input text is neither empty nor
		//							just made up of blank spaces, asking for a new input if it doesn't meet the
		//							conditions. If the string is valid, it creates a new player whose
		//							"initials" data member takes the value of the string given. Then it checks if
		//							the initials have already been used in a saved game, and if they have, asks the
		//							player whether they want to used this saved game or not. Finally, it opens the 
		//							Game window and closes the Splash Screen window.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-28		A. Mojica				Initial setup
		//
		//							2023-06-02		A. Mojica				Checks if the player already has a 
		//																	saved game
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		boolean validInitials;									//Defines boolean to determine whether the input was valid
		ArrayList<String> savedData;							//Defines List of Strings to store all the lines of the save file
		String[] playerInfo;									//Defines list of Strings to store information of a singular player
		int savedPlayer;										//Defines int to store the position the saved player has in the save file
		int highScore;
		
		initials = initials.trim();								//Trims the string given to take out the spaces at the sides of it
		validInitials = (!initials.equals(""));					//If after being trimmed the string is empty, the input is not valid

		if(validInitials) {																//If the initials are valid
			Player player = new Player(initials);										// Creates a new player with the initials
			NumberCruncherApp numberCruncherApp = new NumberCruncherApp();				// Creates the window with the difficulty and game
			numberCruncherApp.setPlayer(player);										// passes it the player created to use in the game
			
			savedData = numberCruncherApp.LoadSaveFile();								// Then, loads the save file
			savedPlayer = numberCruncherApp.FindPlayer(savedData, initials);			// And calls a method to check if the given initials
																						// have been used before
			
			if(savedPlayer!=-1) {														//If the initials have already been used
				
				playerInfo = savedData.get(savedPlayer).split(",");							// Stores string list with all the saved information
																							//of the player
				
				if(playerInfo[2].equals("0")) {												//If the player didn't go past the first level
					highScore = Integer.valueOf(playerInfo[10]);							// Loads the score they got to compare with future scores
					numberCruncherApp.getPlayer().setHighScore(highScore);					// And then proceeds as a new game
				
				}else {																		//Else, recognizes that a level was completed before

					int dialogResult = JOptionPane.showConfirmDialog 						//Asks the user if they want to use the saved game
							(getContentPane(), "There's already a saved game with those initials\n Do you want to load the previous game?");

					if(dialogResult == JOptionPane.YES_OPTION){															//If the user selects yes
						numberCruncherApp.LoadSaveState(playerInfo);							//Calls method to Setup the game with the saved data of the player

					}else if(dialogResult == JOptionPane.CANCEL_OPTION || dialogResult == JOptionPane.CLOSED_OPTION){	// Else if the dialogue box is closed or "cancel" is selected
						return;																							// Ends the method so the player can enter new initials

					}else if(dialogResult == JOptionPane.NO_OPTION) {													// If the player selects "No"
						highScore = Integer.valueOf(playerInfo[10]);												// The highScore is loaded anyway
						numberCruncherApp.getPlayer().setHighScore(highScore);											// to compare with future scores
					}

				}
				
			}
			
			numberCruncherApp.setVisible(true);													// Makes the game window visible
			dispose();																			// And disposes of the Splash Screen
		
		}else {																					//Else,
			JOptionPane.showMessageDialog(getContentPane(), "Error. Please enter initials");	//asks for new initials
		}
	}
}
