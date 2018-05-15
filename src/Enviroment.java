import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Author: Cole Polyak
 * 15 May 2018
 * 
 * This class creates the game grid and handles the moves.
 */

/*
 * To Be Done
 * -----------
 * Create GUI
 * Add button image icon
 * Create score keeping methods.
 * Create input panel.
 */


public class Enviroment extends JFrame
{
	private CookieButton gameBoard[][];
	private int width;
	private int height;
	
	// To determine which players turn it is.
	boolean player;
	
	// Our specialized JButtons.
	private class CookieButton extends JButton
	{
		private int row, col;
		
		private boolean clicked;
		
		public CookieButton(int row, int col)
		{
			this.row = row;
			this.col = col;
			clicked = false;
		}
		
		// Getters
		public int getRow() {return row;}
		public int getCol() {return col;}
		public boolean getClicked() { return clicked;}
		
		// Setter
		public void setClicked(boolean b) {clicked = b;}
		
	}
	
	// Initialize board.
	public Enviroment(int x, int y)
	{
		setTitle("Chomp!");
		setSize(2000,2000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Player 1 starts by default.
		player = true;
		
		width = x;
		height = y;
		
		setLayout(new GridLayout(width, height));
		
		gameBoard = new CookieButton[width][height];
		
		// Populating the newly created gameboard array.
		populate();
	}
	
	// Fills the array with buttons.
	public void populate()
	{
		for(int i = 0; i < gameBoard.length; ++i)
		{
			for(int j = 0; j < gameBoard[i].length; ++j)
			{
				
				// Creates the poisoned cookie.
				if(i == 0 && j == 0) { createPoisonCookie(i,j); continue;}
				
				// Creates the button and adds it to the frame.
				gameBoard[i][j] = new CookieButton(i,j);
				add(gameBoard[i][j]);
				
				// Adds the action listener to the button.
				gameBoard[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// Takes the appropriate measures to alter the gameBoard.
						changeStatus(((CookieButton) e.getSource()).getRow(),
									 ((CookieButton) e.getSource()).getCol());
					}
					
				});
			}
		}
	}
	
	// Edits the board after a move.
	public void changeStatus(int row, int col)
	{
		// Checks if the cookie has already been eaten.
		if(gameBoard[row][col].getClicked())
		{
			throw new IllegalStateException("That cookie has already been eaten!");
		}
		
		// iterates over every button below and to the right of the clicked on cookie.
		for(int i = row; i < gameBoard.length; ++i)
		{
			for(int j = col; j < gameBoard[i].length; ++j)
			{
				// If it already as been clicked.
				if(gameBoard[i][j].getClicked()) {continue;}
				
				gameBoard[i][j].setClicked(true);
				
				// Player 1
				if(getPlayer()) 
				{
					gameBoard[i][j].setBackground(Color.WHITE);
				}
				
				// Player 2
				else
				{
					gameBoard[i][j].setBackground(Color.BLACK);
				}
			}
		}
		togglePlayer();
	}
	
	// Creates the game ending poisonous cookie.
	public void createPoisonCookie(int i, int j)
	{
		// Creating and adding.
		gameBoard[i][j] = new CookieButton(i,j);
		add(gameBoard[i][j]);
		
		gameBoard[i][j].setBackground(Color.RED);
		
		// Action listener updates the final cookie before ending game.
		gameBoard[i][j].addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updatePoison();
				endGame();
			}
			
		});
	}
	
	// Used for the poison cookie, this determines the winner of the game.
	public void updatePoison()
	{
		if(getPlayer())
		{
			gameBoard[0][0].setBackground(Color.WHITE);
			return;
		}
		gameBoard[0][0].setBackground(Color.BLACK);
	}
	
	// Ends the game with a popup message.
	public void endGame()
	{
		if(getPlayer()) 
		{
			JOptionPane.showMessageDialog(this, "Game over \n Player 2 wins!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Game over \n Player 1 wins!");
		}
		
	}
	
	// Player logic.
	public boolean getPlayer() {return player;}
	public void togglePlayer() {player = !(player);}
}
