package hu.markgyori.game_4096.interfaces;

import javafx.scene.layout.StackPane;

/**
 * Block interface for 4096 game blocks.
 * @author marko
 *
 */
public interface IBlock {
	
	/**
	 * Returns the block points. This is can be set by SetPoint function. 
	 * @return Returns the block points.
	 */
	public abstract int getPoint();
	
	/**
	 * Set block point. This will appear in the block.
	 * @param num
	 */
	public abstract void setPoint(int num);
	
	public abstract StackPane getPanel();
	
	/**
	 * Returns the block lock state.
	 * @return Returns that block is locked.
	 */
	public abstract boolean isLocked();
	
	/**
	 * Set block to lock or unlock. If lock the block is not movable.
	 * @param state
	 */
	public abstract void setLocked(boolean state);
	
	/**
	 * Set the block is new value.
	 * @param state
	 */
	public abstract void setIsNew(boolean state);

	/**
	 * Return the block new state. If true the block is was not opened yet. 
	 * @return Return the block state.
	 */
	public abstract boolean isNew();
	
	/**
	 * Function for render the block. This function update the block design.
	 */
	public abstract void render();
}
