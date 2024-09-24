import java.awt.EventQueue;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: clear text field when entering new number //
//		NextLevel /?/
//		Game Over //
//		New Game //
//		Make more tracks visible when changing difficulty//
//		Score //
//		Add arrow besides previous guesses//
//		Check for existing initials //
//		Load Game	//
//		Save Game	//? /Save without deleting previous saved games//
//		Change Help Text //



public class NumberCruncherApp extends JDialog {

	private TrackManager trackManager;					// Declares data member to store information of the tracks
	private Player player;								// Declares data member to store player information
	private static ImageIcon correctImage;				// Declares data members to store the different icons that
	private static ImageIcon incorrectUpImage;			// can be shown depending on the input of the player
	private static ImageIcon incorrectDownImage;		

	private JLabel levelLbl;							//Visual components that are needed as global to perform different methods
	private JLabel difficultyLbl;
	private JLabel lblRange;
	private JLabel lblScore;
	private JButton btnNewGame;
	
	private JPanel gamePanel;
	private JPanel difficultyPanel;
	private JPanel tracksPanel;

	private JTextField livesText;
	private JTextField userGuessTextField;

	
	public NumberCruncherApp() {
		setResizable(false);

		correctImage = new ImageIcon("Images/Right.png");					//Defines the ImageIcons necessary to give the user feedback
		incorrectUpImage= new ImageIcon("Images/WrongUp.png");		
		incorrectDownImage= new ImageIcon("Images/WrongDown.png");	
		trackManager = new TrackManager();									//Instantiates a TrackManager for the game
		
		setTitle("Number Cruncher");
		setBounds(100, 100, 1148, 741);
		getContentPane().setLayout(null);

		difficultyPanel = new JPanel();
		difficultyPanel.setBackground(Color.DARK_GRAY);
		difficultyPanel.setBounds(0, 0, 1146, 704);
		getContentPane().add(difficultyPanel);
		difficultyPanel.setLayout(null);

		gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 1134, 704);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		gamePanel.setVisible(false);

		//////--------------------------------------------------------////////

		JPanel difficultyButtonPanel = new JPanel();
		difficultyButtonPanel.setBackground(Color.ORANGE);
		difficultyButtonPanel.setBounds(447, 302, 254, 273);
		difficultyPanel.add(difficultyButtonPanel);
		difficultyButtonPanel.setLayout(null);

		JButton btnEasy = new JButton("Easy");
		btnEasy.setBackground(Color.WHITE);
		btnEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			//If the easy difficulty was selected
				difficultyPanel.setVisible(false);					// Makes difficulty panel invisible
				SetupDifficulty(1);									// Setups the difficulty to easy
				gamePanel.setVisible(true);							// and makes the game panel visible
			}
		});
		btnEasy.setBounds(61, 97, 128, 45);
		difficultyButtonPanel.add(btnEasy);

		JButton btnModerate = new JButton("Moderate");
		btnModerate.setBackground(Color.WHITE);
		btnModerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			//If the moderate difficulty was selected
				difficultyPanel.setVisible(false);					// Makes difficulty panel invisible
				SetupDifficulty(2);									// Setups the difficulty to moderate
				gamePanel.setVisible(true);							// and makes the game panel visible
			}
		});
		btnModerate.setBounds(61, 152, 128, 45);
		difficultyButtonPanel.add(btnModerate);

		JButton btnDifficult = new JButton("Difficult");
		btnDifficult.setBackground(Color.WHITE);
		btnDifficult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			//If the hard difficulty was selected
				difficultyPanel.setVisible(false);					// Makes difficulty panel invisible
				SetupDifficulty(3);									// Setups the difficulty to difficult
				gamePanel.setVisible(true);							// and makes the game panel visible
			}
		});
		btnDifficult.setBounds(61, 207, 128, 45);
		difficultyButtonPanel.add(btnDifficult);

		JLabel lblNewLabel = new JLabel("Select Difficultty:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(61, 44, 128, 24);
		difficultyButtonPanel.add(lblNewLabel);

		JLabel tittleLabel = new JLabel("Number Cruncher");
		tittleLabel.setForeground(Color.ORANGE);
		tittleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tittleLabel.setFont(new Font("Source Code Pro", Font.BOLD | Font.ITALIC, 22));
		tittleLabel.setBounds(0, 35, 1136, 236);
		difficultyPanel.add(tittleLabel);

		///------------------------------------------------------//////////


		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.ORANGE);
		infoPanel.setBounds(0, 0, 1134, 99);
		gamePanel.add(infoPanel);
		infoPanel.setLayout(null);

		levelLbl = new JLabel("Level 1\r\n");
		levelLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		levelLbl.setBounds(503, 15, 91, 27);
		infoPanel.add(levelLbl);

		lblRange = new JLabel("Range: 1-10");
		lblRange.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRange.setBounds(633, 10, 177, 36);
		infoPanel.add(lblRange);

		lblScore = new JLabel("Score: 0");
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblScore.setBounds(566, 56, 160, 22);
		infoPanel.add(lblScore);

		difficultyLbl = new JLabel("Easy -");
		difficultyLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		difficultyLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		difficultyLbl.setBounds(361, 15, 139, 27);
		infoPanel.add(difficultyLbl);

		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(Color.LIGHT_GRAY);
		sidePanel.setBounds(0, 98, 113, 607);
		gamePanel.add(sidePanel);
		sidePanel.setLayout(null);

		JLabel livesLbl = new JLabel("Lives:");
		livesLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		livesLbl.setBounds(10, 28, 93, 19);
		livesLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		sidePanel.add(livesLbl);

		JLabel lblNumbersGuessed = new JLabel("<html>Numbers<p> Guessed:\r\n");
		lblNumbersGuessed.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNumbersGuessed.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumbersGuessed.setBounds(0, 57, 103, 53);
		sidePanel.add(lblNumbersGuessed);

		JLabel lblguessNumber = new JLabel("<html>Guess<p> Number:\r\n");
		lblguessNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblguessNumber.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblguessNumber.setBounds(0, 419, 103, 53);
		sidePanel.add(lblguessNumber);

		tracksPanel = new JPanel();
		tracksPanel.setBounds(112, 98, 1022, 497);
		gamePanel.add(tracksPanel);
		tracksPanel.setLayout(null);

		JPanel trackPanel1 = new JPanel();
		trackPanel1.setBounds(20, 21, 130, 456);
		tracksPanel.add(trackPanel1);
		trackPanel1.setLayout(null);


		JList numbersGuessedList = new JList(trackManager.getTracks()[0].getNumbersGuessed());
		numbersGuessedList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer)numbersGuessedList.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);


		JScrollPane numbersScrollPane = new JScrollPane(numbersGuessedList);
		numbersScrollPane.setBounds(10, 35, 109, 353);
		numbersScrollPane.setViewportView(numbersGuessedList);
		trackPanel1.add(numbersScrollPane);

		livesText = new JTextField();
		livesText.setForeground(Color.BLACK);
		livesText.setHorizontalAlignment(SwingConstants.CENTER);
		livesText.setFont(new Font("Tahoma", Font.BOLD, 12));
		livesText.setText("5");
		livesText.setBackground(Color.LIGHT_GRAY);
		livesText.setEditable(false);
		livesText.setBounds(10, 0, 109, 29);
		trackPanel1.add(livesText);
		livesText.setColumns(10);

		userGuessTextField = new JTextField();
		userGuessTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!trackManager.getTracks()[0].isAnswered()) {
					userGuessTextField.setText("");
				}
				
			}
		});
		userGuessTextField.setHorizontalAlignment(SwingConstants.CENTER);
		userGuessTextField.setBounds(10, 398, 46, 46);
		trackPanel1.add(userGuessTextField);
		userGuessTextField.setColumns(10);

		JLabel imageLbl = new JLabel("");
		imageLbl.setIcon(null);
		imageLbl.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl.setBounds(58, 398, 61, 46);
		trackPanel1.add(imageLbl);

		
		JPanel trackPanel2 = new JPanel();
		trackPanel2.setLayout(null);
		trackPanel2.setBounds(160, 21, 130, 456);
		tracksPanel.add(trackPanel2);
		
		JList numbersGuessedList2 = new JList(trackManager.getTracks()[1].getNumbersGuessed());
		numbersGuessedList2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer2 = (DefaultListCellRenderer)numbersGuessedList2.getCellRenderer();
		renderer2.setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane numbersScrollPane_1 = new JScrollPane(numbersGuessedList2);
		numbersScrollPane_1.setBounds(10, 35, 109, 353);
		trackPanel2.add(numbersScrollPane_1);

		JTextField textField = new JTextField();
		textField.setText("5");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.BLACK);
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(10, 0, 109, 29);
		trackPanel2.add(textField);

		JTextField textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!trackManager.getTracks()[1].isAnswered()) {
					textField_1.setText("");
				}
			}
		});
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setColumns(10);
		textField_1.setBounds(10, 398, 46, 46);
		trackPanel2.add(textField_1);

		JLabel imageLbl_1 = new JLabel("");
		imageLbl_1.setIcon(null);
		imageLbl_1.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl_1.setBounds(58, 398, 61, 46);
		trackPanel2.add(imageLbl_1);

		JPanel trackPanel3 = new JPanel();
		trackPanel3.setLayout(null);
		trackPanel3.setBounds(300, 21, 130, 456);
		tracksPanel.add(trackPanel3);


		JList numbersGuessedList3 = new JList(trackManager.getTracks()[2].getNumbersGuessed());
		numbersGuessedList3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer3 = (DefaultListCellRenderer)numbersGuessedList3.getCellRenderer();
		renderer3.setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane numbersScrollPane_1_1 = new JScrollPane(numbersGuessedList3);
		numbersScrollPane_1_1.setBounds(10, 35, 109, 353);
		trackPanel3.add(numbersScrollPane_1_1);

		JTextField textField_2 = new JTextField();
		textField_2.setText("5");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setForeground(Color.BLACK);
		textField_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBackground(Color.LIGHT_GRAY);
		textField_2.setBounds(10, 0, 109, 29);
		trackPanel3.add(textField_2);

		JTextField textField_3 = new JTextField();
		textField_3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!trackManager.getTracks()[2].isAnswered()) {
					textField_3.setText("");
				}
			}
		});
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setColumns(10);
		textField_3.setBounds(10, 398, 46, 46);
		trackPanel3.add(textField_3);

		JLabel imageLbl_1_1 = new JLabel("");
		imageLbl_1_1.setIcon(null);
		imageLbl_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl_1_1.setBounds(58, 398, 61, 46);
		trackPanel3.add(imageLbl_1_1);

		JPanel trackPanel4 = new JPanel();
		trackPanel4.setLayout(null);
		trackPanel4.setBounds(440, 21, 130, 456);
		tracksPanel.add(trackPanel4);


		JList numbersGuessedList4 = new JList(trackManager.getTracks()[3].getNumbersGuessed());
		numbersGuessedList4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer4 = (DefaultListCellRenderer)numbersGuessedList4.getCellRenderer();
		renderer4.setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane numbersScrollPane_2 = new JScrollPane(numbersGuessedList4);
		numbersScrollPane_2.setBounds(10, 35, 109, 353);
		trackPanel4.add(numbersScrollPane_2);

		JTextField textField_4 = new JTextField();
		textField_4.setText("5");
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setForeground(Color.BLACK);
		textField_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBackground(Color.LIGHT_GRAY);
		textField_4.setBounds(10, 0, 109, 29);
		trackPanel4.add(textField_4);

		JTextField textField_5 = new JTextField();
		textField_5.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!trackManager.getTracks()[3].isAnswered()) {
					textField_5.setText("");
				}
				
			}
		});
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setColumns(10);
		textField_5.setBounds(10, 398, 46, 46);
		trackPanel4.add(textField_5);

		JLabel imageLbl_2 = new JLabel("");
		imageLbl_2.setIcon(null);
		imageLbl_2.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl_2.setBounds(58, 398, 61, 46);
		trackPanel4.add(imageLbl_2);

		JPanel trackPanel5 = new JPanel();
		trackPanel5.setLayout(null);
		trackPanel5.setBounds(580, 21, 130, 456);
		tracksPanel.add(trackPanel5);


		JList numbersGuessedList5 = new JList(trackManager.getTracks()[4].getNumbersGuessed());
		numbersGuessedList5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer5 = (DefaultListCellRenderer)numbersGuessedList5.getCellRenderer();
		renderer5.setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane numbersScrollPane_3 = new JScrollPane(numbersGuessedList5);
		numbersScrollPane_3.setBounds(10, 35, 109, 353);
		trackPanel5.add(numbersScrollPane_3);

		JTextField textField_6 = new JTextField();
		textField_6.setText("5");
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setForeground(Color.BLACK);
		textField_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBackground(Color.LIGHT_GRAY);
		textField_6.setBounds(10, 0, 109, 29);
		trackPanel5.add(textField_6);

		JTextField textField_7 = new JTextField();
		textField_7.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				if(!trackManager.getTracks()[4].isAnswered()) {
					textField_7.setText("");
				}
			}
		});
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setColumns(10);
		textField_7.setBounds(10, 398, 46, 46);
		trackPanel5.add(textField_7);

		JLabel imageLbl_3 = new JLabel("");
		imageLbl_3.setIcon(null);
		imageLbl_3.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl_3.setBounds(58, 398, 61, 46);
		trackPanel5.add(imageLbl_3);

		JPanel trackPanel6 = new JPanel();
		trackPanel6.setLayout(null);
		trackPanel6.setBounds(720, 21, 130, 456);
		tracksPanel.add(trackPanel6);

		JList numbersGuessedList6 = new JList(trackManager.getTracks()[5].getNumbersGuessed());
		numbersGuessedList6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer6 = (DefaultListCellRenderer)numbersGuessedList6.getCellRenderer();
		renderer6.setHorizontalAlignment(JLabel.CENTER);
		
		JScrollPane numbersScrollPane_4 = new JScrollPane(numbersGuessedList6);
		numbersScrollPane_4.setBounds(10, 35, 109, 353);
		trackPanel6.add(numbersScrollPane_4);

		JTextField textField_8 = new JTextField();
		textField_8.setText("5");
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setForeground(Color.BLACK);
		textField_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_8.setEditable(false);
		textField_8.setColumns(10);
		textField_8.setBackground(Color.LIGHT_GRAY);
		textField_8.setBounds(10, 0, 109, 29);
		trackPanel6.add(textField_8);

		JTextField textField_9 = new JTextField();
		textField_9.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!trackManager.getTracks()[5].isAnswered()) {
					textField_9.setText("");
				}
			}
		});
		textField_9.setHorizontalAlignment(SwingConstants.CENTER);
		textField_9.setColumns(10);
		textField_9.setBounds(10, 398, 46, 46);
		trackPanel6.add(textField_9);

		JLabel imageLbl_4 = new JLabel("");
		imageLbl_4.setIcon(null);
		imageLbl_4.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl_4.setBounds(58, 398, 61, 46);
		trackPanel6.add(imageLbl_4);

		JPanel trackPanel7 = new JPanel();
		trackPanel7.setLayout(null);
		trackPanel7.setBounds(865, 21, 130, 456);
		tracksPanel.add(trackPanel7);

		
		JList numbersGuessedList7 = new JList(trackManager.getTracks()[6].getNumbersGuessed());
		numbersGuessedList7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		numbersGuessedList.setBounds(10, 35, 109, 353);
		DefaultListCellRenderer renderer7 = (DefaultListCellRenderer)numbersGuessedList7.getCellRenderer();
		renderer7.setHorizontalAlignment(JLabel.CENTER);

		JScrollPane numbersScrollPane_5 = new JScrollPane(numbersGuessedList7);
		numbersScrollPane_5.setBounds(10, 35, 109, 353);
		trackPanel7.add(numbersScrollPane_5);

		JTextField textField_10 = new JTextField();
		textField_10.setText("5");
		textField_10.setHorizontalAlignment(SwingConstants.CENTER);
		textField_10.setForeground(Color.BLACK);
		textField_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_10.setEditable(false);
		textField_10.setColumns(10);
		textField_10.setBackground(Color.LIGHT_GRAY);
		textField_10.setBounds(10, 0, 109, 29);
		trackPanel7.add(textField_10);

		JTextField textField_11 = new JTextField();
		textField_11.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!trackManager.getTracks()[6].isAnswered()) {
					textField_11.setText("");
				}
			}
		});
		textField_11.setHorizontalAlignment(SwingConstants.CENTER);
		textField_11.setColumns(10);
		textField_11.setBounds(10, 398, 46, 46);
		trackPanel7.add(textField_11);

		JLabel imageLbl_5 = new JLabel("");
		imageLbl_5.setIcon(null);
		imageLbl_5.setHorizontalAlignment(SwingConstants.CENTER);
		imageLbl_5.setBounds(58, 398, 61, 46);
		trackPanel7.add(imageLbl_5);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewGame();
			}
		});
		
		btnNewGame.setBounds(550, 630, 106, 35);
		btnNewGame.setVisible(false);
		gamePanel.add(btnNewGame);
		
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(112, 594, 1022, 111);
		gamePanel.add(bottomPanel);
		bottomPanel.setLayout(null);
		
				JButton btnCheckButton = new JButton("Check");
				btnCheckButton.setBounds(456, 70, 98, 31);
				bottomPanel.add(btnCheckButton);

				JLabel lblInstructions = new JLabel("Means the guess was too low");
				lblInstructions.setBounds(431, 12, 232, 24);
				bottomPanel.add(lblInstructions);
				lblInstructions.setFont(new Font("Tahoma", Font.PLAIN, 13));

				JLabel lblInstructions2 = new JLabel("Means the guess was too high");
				lblInstructions2.setBounds(102, 3, 232, 42);
				bottomPanel.add(lblInstructions2);
				lblInstructions2.setFont(new Font("Tahoma", Font.PLAIN, 13));

				JLabel lblUpIcon = new JLabel("");
				lblUpIcon.setIcon(incorrectUpImage);
				lblUpIcon.setBounds(31, 5, 61, 46);
				bottomPanel.add(lblUpIcon);

				JLabel lblDownIcon = new JLabel("");
				lblDownIcon.setIcon(incorrectDownImage);
				lblDownIcon.setBounds(360, 5, 61, 46);
				bottomPanel.add(lblDownIcon);

				JLabel lblInstructions3 = new JLabel("Means the guess was correct\r\n");
				lblInstructions3.setFont(new Font("Tahoma", Font.PLAIN, 13));
				lblInstructions3.setBounds(755, 3, 232, 42);
				bottomPanel.add(lblInstructions3);

				JLabel lblCorrectIcon = new JLabel("");
				lblCorrectIcon.setIcon(correctImage);
				lblCorrectIcon.setBounds(684, 5, 61, 46);
				bottomPanel.add(lblCorrectIcon);
				btnCheckButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CheckInput();
					}
				});
		

		trackPanel1.setVisible(false);
		trackPanel2.setVisible(false);
		trackPanel3.setVisible(false);
		trackPanel4.setVisible(false);
		trackPanel5.setVisible(false);
		trackPanel6.setVisible(false);
		trackPanel7.setVisible(false);


	}

	private void SetupDifficulty(int difficulty) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void SetupDifficulty(int difficulty)
		//
		// Method parameters	:	difficulty -	used to determine the values that will be given to various data members
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called when the difficulty of the game changes. It calls the SetupDifficulty
		//							method of TrackManager to do the back-end setup and then change the difficulty label and
		//							the visibility of the tracks to match this information. Then, it calls for a new level.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-28		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int numberOfTracks;												//Defines int to store number of active tracks
		Component[] trackList;											//Defines component list to store the panels that contain the tracks
		int counter;													//Defines int to control iterations of the loop
		
		trackManager.SetupDifficulty(difficulty);						// calls method of trackManager for back-end changes
		numberOfTracks = trackManager.getActiveTracks();				// stores the number of active tracks
		trackList= tracksPanel.getComponents();							// stores list with all the panels that represent the tracks
		

		switch(difficulty){												//Changes the difficulty label according to the 
		case 1:															//difficulty passed
			difficultyLbl.setText("Easy -");
			break;
		case 2:
			difficultyLbl.setText("Moderate -");
			break;
		case 3:
			difficultyLbl.setText("Difficult -");
			break;
		}															

		
		for(counter = 0; counter < numberOfTracks; counter ++) {		// Goes trough the amount of active tracks
			trackList[counter].setVisible(true);						// and makes their respective UI component visible
		}
		

		NewLevel();														// Calls for a new level

	}

	private void NewLevel(){
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void NewLevel()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to  make the visual change necessary when advancing to the next level. 
		//							It calls the corresponding method from TrackManager to do the back-end setup, and depending 
		//							on the value return from this, it either changes the difficulty or proceeds to the next level
		//							by modifying the labels showing the level and the range of possible numbers, as well 
		//							as updating the tracks with the new amount of guesses that are left for each one.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-05-28		A. Mojica				Initial setup
		//							
		//							2023-06-01		A. Mojica				Checks for changes in the difficulty.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int numberOfTracks;												//Defines integer to store the number of visible tracks
		Component[] trackList;											//Defines component list to store the panels that contain the tracks
		int counter;													//Defines int to control iterations of the loop
		Track tempTrack;												//Defines tempTrack to reference the tracks inside the loop
		JPanel tempTrackPanel;											//Defines tempTrackPanel to reference the panel containing the visual
																		//component of the tracks
		int difficultyChanged;
		difficultyChanged =trackManager.NewLevel();						// calls method of trackManager for back-end changes
		
		if(difficultyChanged == 4) {									//If the hard difficulty was completed
			JOptionPane.showMessageDialog(getContentPane(), 			//Shows the player a win message
					"Congratulations!\n "
					+ "You completed Number Cruncher"); 
			GameOver();													//And ends the game
			
		}else if(difficultyChanged!=0)									// Else if the difficulty needs to be changed
			SetupDifficulty(difficultyChanged);							// calls method to change difficulty
		
		else {																// else, updates level as usual
			numberOfTracks = trackManager.getActiveTracks();				// stores the number of active tracks
			trackList= tracksPanel.getComponents();							// stores list with all the panels that represent the tracks
			
			levelLbl.setText("Level " + trackManager.getLevel()+"\r\n");															//Changes the level and range labels
			lblRange.setText("Range: 1-"+ (trackManager.getLevel()*trackManager.getRangeIncrease()));	// based on the new values

			for(counter = 0; counter<numberOfTracks; counter++) {										// For each active/visible track
				tempTrack = trackManager.getTracks()[counter];											// store both the track and its visual
				tempTrackPanel =((JPanel)trackList[counter]);											// component
				
				((JTextField)tempTrackPanel.getComponent(1)).setText(""+tempTrack.getGuessesLeft());	//Updates label showing the the guesses left
																										// for the track
				((JTextField)tempTrackPanel.getComponent(2)).setText("");								//Clears the textField where you input the guess
				((JTextField)tempTrackPanel.getComponent(2)).setEditable(true);							// and makes it editable again
				
				((JLabel)tempTrackPanel.getComponent(3)).setIcon(null);									// Stops showing the icon that represents whether
																										// the answer was correct or incorrect
				
			}
		}
		
	}
	
	public void CheckInput() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void CheckInput()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to check that input given by the user is a valid number.
		//							It goes through all the textFields of the tracks and verifies that the strings written can be 
		//							converted into a number and that these numbers are within the range of the level, if they are,
		//							it stores the numbers and proceeds to check if the guesses where correct, otherwise it informs 
		//							the user of the problem and ends the method so that changes can be made to the strings 
		//							inside the inputFields.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		int numberOfTracks;												//Defines integer to store the number of visible tracks
		Component[] trackList;											//Defines component list to store the panels that contain the tracks
		int[]  numberList;												//Defines int list to store the guesses made in each track
		String tempNumberString;										//Defines string to store the string inside theinputTextFields
		JPanel tempTrackPanel;											//Defines jPanel to store the tracks' panels
		int tempNumber;													//Define int to check if the inputText can be parsed to a valid number
		int counter;													//Defines int to control iterations of the loop
		
		numberOfTracks = trackManager.getActiveTracks();				// stores the number of active tracks
		trackList= tracksPanel.getComponents();							// Stores all the panels with the tracks
		numberList = new int[numberOfTracks];							// Instantiates a list that can store the guesses of all
																		// active tracks
		
		try {
			for(counter = 0; counter<numberOfTracks; counter++) {												//Goes through all active tracks		
				tempTrackPanel =((JPanel)trackList[counter]);
				tempNumberString = ((JTextField)tempTrackPanel.getComponent(2)).getText();						//Gets the string inputed as a guess
				tempNumber = Integer.parseInt(tempNumberString);												//Verifies that is an integer
				
				if(tempNumber<1 || tempNumber > trackManager.getLevel()*trackManager.getRangeIncrease()){		//If the number is outside the range of the level
					JOptionPane.showMessageDialog(getContentPane(),												// Shows error message
							"ERROR. Please enter numbers valid within the given range.");						// and ends method
					return;
				}
				
				numberList[counter] = tempNumber;																//Else, adds the number to the list of guesses
			}
		}catch (Exception e) {																	//If the input can't be converted to an integer
			JOptionPane.showMessageDialog(getContentPane(), "ERROR. Please enter numbers.");	//Shows error message
			return;																				// And ends method
		}	
		
		
		CheckGuessedNumber(numberList);								//If no error occurred, checks if the guesses where correct

		
	}
	
	public void CheckGuessedNumber(int[] numberList) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void CheckGuessedNumber(int[] numberList)
		//
		// Method parameters	:	numberList		-	used to check all the number inputed as guesses
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to provide visual feedback for whether the guesses where correct, higher
		//							or lower than the number generated for the tracks.
		//							It goes through all the guesses and calls a method to compare them to their respective track number, 
		//							changing the icon displayed depending on whether the guess was higher, lower, or correct;
		//							additionally, if the guess was correct, makes it so no more guesses can be made on the track
		//							and increases the score based on the number of guesses left on said track.
		//							After all guesses have been compared, checks if the conditions for either game over or going
		//							to the next level have been met to proceed accordingly.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int numberOfTracks;												//Defines integer to store the number of visible tracks
		Component[] trackList;											//Defines component list to store the panels that contain the tracks
		JPanel tempTrackPanel;											//Defines jPanel to store the tracks' panels
		int result;														//Defines int to store how the guess compares to the number generated
		int guessesLeft;												//Defines int to store the number of guesses left in a given track
		boolean nextLevel = false;										//Defines bool to confirm if the player proceeds to the next level
		boolean gameOver = false;										//Defines bool to confirm if the player lost the game
		int counter;													//Defines int to control iterations of the loop
		
		numberOfTracks = trackManager.getActiveTracks();				// stores the number of active tracks
		trackList= tracksPanel.getComponents();							// Stores all the panels with the tracks
		
		for(counter = 0; counter<numberOfTracks; counter++) {										//Goes through all the active track
			if(!trackManager.getTracks()[counter].isAnswered())
			{
				tempTrackPanel =((JPanel)trackList[counter]);
				
				result = trackManager.CheckGuessedNumberOnTrack(numberList[counter], counter);		//Calls method to compare the number guessed
																									// with the number generated
				
				guessesLeft = trackManager.getTracks()[counter].getGuessesLeft();					//Stores the guesses left in the track
				((JTextField)tempTrackPanel.getComponent(1)).setText(""+guessesLeft);				//And shows the number in the UI
				
				switch(result) {																	
				case 1:																				//If the guess was correct
					((JLabel)tempTrackPanel.getComponent(3)).setIcon(correctImage);					//Sets the icon as a checkmark
					((JTextField)tempTrackPanel.getComponent(2)).setEditable(false);				//Makes it so no more guesses can be inputed in the track
					player.setScore(player.getScore()+(guessesLeft*10));							//Adds to the player score based on the number of guesses left
					lblScore.setText("Score: "+player.getScore());									//Shows the new score in the UI
					
					if(trackManager.CheckAllAnswered()) 											//Checks if all tracks have been answered so
						nextLevel = true;															// the player proceeds to the next level
						
					break;
				case 2:																				//If the guess was lower
					((JLabel)tempTrackPanel.getComponent(3)).setIcon(incorrectDownImage);			//Changes icon to an X with a down arrow
					if(guessesLeft==0)																//And if there are no more guesses left
						gameOver = true;															//the game should end
					break;
				case 3:																				//If the guess was higher
					((JLabel)tempTrackPanel.getComponent(3)).setIcon(incorrectUpImage);				//Changes icon to an X with a up arrow
					if(guessesLeft==0)																//And if there are no more guesses left
						gameOver = true;															//the game should end
					break;
					
				default:																			//Shows error message if another value was returned
					JOptionPane.showMessageDialog(getContentPane(), "ERROR checking results");
					break;
				}
				
			}
		}
	
		if(gameOver) {																//If a track ran out of guesses
			JOptionPane.showMessageDialog(getContentPane(), "GAME OVER"); 			//Shows Game over message		
			GameOver();																//And ends the game
		}
			
		else if(nextLevel) {														//If all tracks have been answered
			GiveBonusScore();														//Calls method to add the bonus score
			SaveGameState(false);														//Saves the game state of the level that was completed
			JOptionPane.showMessageDialog(getContentPane(), "Next Level!");			//Informs the player they are advancing to the next level
			NewLevel();																//Calls method to generate the next level
		}
	}
	
	public void GiveBonusScore() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void GiveBonusScore()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to calculate the bonus score that the user gets after completing a level.
		//							or lower than the number generated for the tracks.
		//							It adds the guesses left on all the active tracks and for every 3 guesses left adds a bonus 50 
		//							points to the player's score. Then it updates the interface so it reflects this change.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int numberOfTracks;												//Defines integer to store the number of visible tracks
		int totalGuessesLeft = 0;										//Defines int to add the guesses left from the tracks
		int bonusScore;													//Defines int to store how many bonus points the player gets
		int counter;													//Defines int to control iterations of the loop
		
		numberOfTracks = trackManager.getActiveTracks();				// stores the number of active tracks
		
		for(counter = 0; counter<numberOfTracks; counter++) {							//goes through all the tracks
			totalGuessesLeft+= trackManager.getTracks()[counter].getGuessesLeft();		//adding its guesses left to the count
		}
		
		bonusScore = (totalGuessesLeft/3) * 50;											//For every 3 guesses, the player gets a bonus 50 points
																						
		player.setScore(player.getScore()+bonusScore);									//Adds the bonus to the player Score
		lblScore.setText("Score: "+player.getScore());									//Shows the new score in the interface
	}

	private void GameOver() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void GameOver()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to end the game after either losing or completing all the levels.
		//							It makes so the user can no longer interact with the tracks and changes the colour of the
		//							interface to reflect this. It also hides the submit button and changes it for a button
		//							to start a new game.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		Component[] trackList;													//Defines component list to store the panels that contain the tracks
		JPanel tempTrackPanel;											//Defines JPanel to store the panels that contain the tracks
		int numberOfTracks;												//Defines integer to store the number of visible tracks
		int counter;													//Defines int to control iterations of the loop
		
		numberOfTracks = trackManager.getActiveTracks();				// stores the number of active tracks
		trackList= tracksPanel.getComponents();	
		
		JOptionPane.showMessageDialog(getContentPane(), 						//Shows the player's score and highScore
				"You got a Score of: \n"+player.getScore()
				+"\n\nYou current HighScore is:\n"+player.GetNewHighScore());
		
		SaveGameState(true); 													//Saves player info with new HighScore
		
		
		for(counter = 0; counter<numberOfTracks; counter++) {					//goes through all the tracks
			tempTrackPanel =((JPanel)trackList[counter]);
			tempTrackPanel.setBackground(Color.LIGHT_GRAY);						//changes the colour of the panels to light gray
			((JLabel)tempTrackPanel.getComponent(3)).setEnabled(false);			//sets the imageIcons' enabled as false so they appear grey
			((JTextField)tempTrackPanel.getComponent(2)).setEditable(false);	//Makes the input field no longer interactable
			trackManager.getTracks()[counter].setAnswered(true);				//And the tracks as answered so the numbers can't be changed
		}
		
		gamePanel.getComponent(4).setVisible(false);							//Hides the panel with the explanation of the icons
																				// and the submit button
		btnNewGame.setVisible(true);											//Makes button for New Game visible
		tracksPanel.setBackground(new Color(220,220,220));						//Changes the background for the game a track panels
		gamePanel.setBackground(new Color(220,220,220));						// so they are darker
	}
	
	private void NewGame() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void NewGame()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to start a new game after the Game Over.
		//							It calls for a Splash Screen to start the application from the beginning and
		//							disposes of the current window.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		SplashScreen splashScreen = new SplashScreen();							//Instantiates a new Splash Screen window
		splashScreen.setVisible(true);											//makes it visible
		dispose();																// and disposes of the game window
	}
	
	public ArrayList<String>  LoadSaveFile() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	ArrayList<String>  LoadSaveFile()
		//
		// Method parameters	:	none
		//
		// Method return		:	ArrayList<String> 
		//
		// Synopsis				:   This method is called read the file with all the saved games.
		//							It goes through the lines and adds thems as strings to an ArrayList, then returning
		//							this list so the information can be further used.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		String t_String;																	// temporary local string variable	
		
		ArrayList<String> savedData = new ArrayList<>();
		
		try {
			FileReader inputFile = new FileReader("Data/SavedGames.txt");					// create object to physical file
			
			BufferedReader bufferedInputFile = new BufferedReader(inputFile);				// create buffered input reader to 
																							// file
			
			while ((t_String = bufferedInputFile.readLine()) != null){						// while the end-of-file is not located,
																							// read the next line from the file
																							// and store in the t_String variable
				savedData.add(t_String);
			}
			
			bufferedInputFile.close();														// close the buffered input reader
			inputFile.close();																// close the physical file
			
			
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return savedData;
	}
	
	public int FindPlayer(ArrayList<String> savedData, String initials) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	int FindPlayer(ArrayList<String> savedData, String initials)
		//
		// Method parameters	:	savedData		-	used to check the data stored in the save file
		//
		//							initials		- 	used to look if the inputed initials have already been used
		//
		// Method return		:	int
		//
		// Synopsis				:   This method is called to find if a player's initials have already been used in
		//							a previous game.
		//							It goes through the different lines of saved information and, if any of them have
		//							the initials entered, returns their position in the list. If the initials were not
		//							found returns -1.
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		String tempInitials;											//Defines string to store the initials in each line
		int dataListSize = savedData.size();							//Defines int to store the size of the dataList
		int counter;													//Defines int to control iterations of the loop
		String[] tempPlayerData;
		
		for(counter = 0; counter < dataListSize; counter++) {			//Goes through the lines of the file
			tempPlayerData =savedData.get(counter).split(",");			//Splits the line by the comas
			tempInitials = tempPlayerData[0];							//Takes the position corresponding to the initials
			
			if(tempInitials.equals(initials)) {							//If the saved initials equals those inputed
				return counter;											//returns the position of the line
			}
		}
		return -1;														//Returns -1 if no saved player has the initials
	}
	
	public void LoadSaveState(String[] playerInfo) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void LoadSaveState(String[] playerInfo) 
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to load a game based on the data stored on a save state.
		//							It loads a level number that goes from 11-19, 21-29 and 31-39 to represent a 
		//							difficulty and a level, loads the difficulty of last level completed based on
		//							this, calls the track manager method to setup the saved level and then proceeds
		//							to the following level to continue the game, it also gives the player the saved 
		//							score and hides the difficulty panel to go directly into the game.
		//							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int level;											//defines int to store the last level completed by the player
		String score;										//defines string to store text with the player's saved score
		
		level = Integer.parseInt(playerInfo[2]);			//Stores the data corresponding to the level
		
		SetupDifficulty(level/10);													//Takes the first number of the data to setup
																					//the difficulty
		trackManager.LoadLevel(level%10, Arrays.copyOfRange(playerInfo, 3, 10));	//Calls method to change the levels and 
																					// the guesses left for each track in the track manager
		
		NewLevel();																	//starts a new level
		
		score = playerInfo[1];														//Takes the position corresponding to the player's score
		player.setScore(Integer.parseInt(score));									//Changes the player's score to match this information
		lblScore.setText("Score: "+score);											//Updates UI to reflect changes
		
		player.setHighScore(Integer.parseInt(playerInfo[10]));						//Sets the player's highScore to compare to the new Score they get
		
		difficultyPanel.setVisible(false);											//Hides difficulty panel
		gamePanel.setVisible(true);													//Shows game panel
	}
	
	private String SetupSaveData() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	String SetupSaveData()
		//
		// Method parameters	:	none
		//
		// Method return		:	String
		//
		// Synopsis				:   This method is called to structure the string that will be used to save the
		//							player's game.
		//							First it defines the level as a two digit number marking the difficulty and the 
		//							individual level. Then it adds the player's initials, score and the level defined
		//							to a string to later add as well the guesses left in each track, everything being
		//							separated by commas.
		//							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		int level;														//Defines int to save the level and difficulty
		int counter;													//Defines int to control the loop
		String playerInfo;												//Defines String to structure the game data
		
		switch(trackManager.getActiveTracks()) {											//Uses the number of active tracks
		case 3:																				// to define the difficulty as either
			level = 10;																		// 10 = easy
			break;																			// 20 = moderate
		case 5: 																			// or 30 = difficult
			level = 20;
			break;
		case 7: 
			level = 30;
			break;
		default:
			level = 0;
			JOptionPane.showMessageDialog(getContentPane(), "ERROR SAVING DIFFICULTY");
			break;
		}
		
		level+=trackManager.getLevel();														//Adds the current level to the established difficulty
		playerInfo= player.getInitials()+','+player.getScore()+','+ level;					//Starts a string with the player's initials, scores and level
																							//separated by commas
		
		for(counter = 0; counter<7; counter ++) {														//goes through the tracks
			playerInfo += ','+ String.valueOf(trackManager.getTracks()[counter].getGuessesLeft());		//and adds the guesses left to the string
		}																								//with the data
		
		playerInfo += ','+ String.valueOf(player.getHighScore());							// and finally adds the player's highScore
		
		return playerInfo;															//returns the string with all the necessary information
	}
	
	public void SaveGameState(boolean gameOver) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void SaveGameState()
		//
		// Method parameters	:	gameOver	-	used to determine what player data should be saved
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to write the file with the saved games.
		//							It loads the information that's already saved and rewrites it with the exception
		//							of the player's previously saved file so it can be overwritten.
		//							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-05		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		String playerInfo;
		String[] playerInfoArray;
		ArrayList<String> savedData;
		int savedDataSize;
		int playerPos;
		int counter;
		
		savedData = LoadSaveFile();																//loads the saved data
		playerPos = FindPlayer(savedData, player.getInitials());								//checks if the player already had a saved game
		
		if(gameOver)																			//If the game is over
			playerInfo = SaveHighScore();														//Calls method to just change the saved HighScore
		
		else																					//Else, if we are progressing to a new level
			playerInfo = SetupSaveData();														//Calls method to build the string with the 
																								// information of the player and the game
	
		
		savedDataSize= savedData.size();														//Stores the amount of saved games to go through them
		
		
		try {
			FileWriter outputFile = new FileWriter("Data/SavedGames.txt");						// open the physical file
			
			BufferedWriter bufferedOutputFile = new BufferedWriter(outputFile);				// establish a buffered write object
																							// to the physical file
			
			for(counter = 0; counter < savedDataSize; counter ++) {							//Copies all the saved file and writes it again,
				if(counter != playerPos) {													// except for the line where the player's previous
					bufferedOutputFile.write(savedData.get(counter));						// game was saved
					bufferedOutputFile.newLine();
				}
			}
			
			bufferedOutputFile.write(playerInfo);											// At the end of the file saves the player's
			bufferedOutputFile.newLine();													// new saved game
			
			
			bufferedOutputFile.close();														// close the buffered write object
			outputFile.close();																// close the physical file
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String SaveHighScore() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void SaveHighScore()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method is called to rewrite the player's saved data with a new highScore.
		//							It loads the information that's already saved and passes it onto a new string,
		//							with the exception of the highScore, which is rewritten based on its new value.
		//							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2023-06-06		A. Mojica				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		String newPlayerInfo="";
		String[] playerInfoArray;
		ArrayList<String> savedData;
		int playerPos;
		int counter;
		
		savedData = LoadSaveFile();												//loads the saved data
		playerPos = FindPlayer(savedData, player.getInitials());				//checks if the player already had a saved game
		
		if(playerPos == -1) {																//If a new player didn't complete the first level
			newPlayerInfo = player.getInitials()+','+String.valueOf(player.getScore())+		// and thus, doesn't have a save file, generates a save
					",0,0,0,0,0,0,0,0,"+ String.valueOf(player.getHighScore());				// string with just the initials and score, the other values
																							// being 0
		}else {
			playerInfoArray = savedData.get(playerPos).split(",");					//Stores an array with the player's data
			newPlayerInfo = "";														//Instantiates the new string as empty

			for(counter = 0; counter <10; counter++) {								//Keeps most of the information the same
				newPlayerInfo+= playerInfoArray[counter]+',';
			}

			newPlayerInfo += String.valueOf(player.getHighScore());					//But changes the highScore at the end of the string

		}
		
		return newPlayerInfo;													//returns the string with the changed highScore
	}
	
	public TrackManager getTrackManager() {										//Getter for the data member "trackManager"
		return trackManager;													//returns the object's current trackManager value
	}

	public void setTrackManager(TrackManager trackManager) {					// Setter for the data member "trackManager"
		this.trackManager = trackManager;										// stores in the object's data member "trackManager"
	}																			// the object of type TrackManager given

	public Player getPlayer() {													//Getter for the data member "Player"
		return player;															//returns the object's current player value
	}

	public void setPlayer(Player player) {										// Setter for the data member "player"
		this.player = player;													// stores in the object's data member "player"
	}																			// the object of type Player given
}
