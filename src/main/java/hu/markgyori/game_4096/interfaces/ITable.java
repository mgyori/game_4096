package hu.markgyori.game_4096.interfaces;

/**
 * Interface for game table. This table use for 4096 game board generation.
 * @author marko
 * 
 */
public interface ITable {
	/**
	 * Get the game block in specified location.
	 * @param x
	 * @param y
	 * @return The block in x-y coordinate.
	 */
	public abstract IBlock getBlock(int x, int y);
	
	/**
	 * Returns the table rows count.
	 * @return number of rows
	 */
	public abstract int getRows();
	
	/**
	 * Returns the table columns count.
	 * @return number of columns
	 */
	public abstract int getColumns();
		
	/**
	 * Function for move down the blocks.
	 */
	public abstract void moveDown();
	
	/**
	 * Function for move up the blocks.
	 */
	public abstract void moveUp();
	
	/**
	 * Function for move left the blocks.
	 */
	public abstract void moveLeft();
	
	/**
	 * Function for move right the blocks.
	 */
	public abstract void moveRight();
	
	/**
	 * Function for render and update the block and the table design.
	 */
	public abstract void render();
	
	/**
	 * Function for start game and reset score (and viewed blocks).
	 */
	public abstract void startGame();
}
