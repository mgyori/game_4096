package hu.markgyori.game_4096.interfaces;

import javafx.scene.layout.GridPane;

/**
 * Interface for game table. This table use for 4096 game board generation.
 * @author marko
 * 
 */
public interface ITable {
	public abstract IBlock GetBlock(int x, int y);
	
	/**
	 * Returns the table rows count.
	 * @return number of rows
	 */
	public abstract int GetRows();
	
	/**
	 * Returns the table columns count.
	 * @return number of columns
	 */
	public abstract int GetColumns();
	
	/**
	 * Returns the JavaFX GridPane in the board is drawn.
	 * @return JavaFX GridPane for table
	 */
	public abstract GridPane GetPanel();
	
	/**
	 * Function for move down the blocks.
	 */
	public abstract void MoveDown();
	
	/**
	 * Function for move up the blocks.
	 */
	public abstract void MoveUp();
	
	/**
	 * Function for move left the blocks.
	 */
	public abstract void MoveLeft();
	
	/**
	 * Function for move right the blocks.
	 */
	public abstract void MoveRight();
	
	/**
	 * Function for render and update the block and the table design.
	 */
	public abstract void Render();
}
