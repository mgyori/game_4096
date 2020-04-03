package hu.markgyori.game_4096.classes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

import hu.markgyori.game_4096.App;
import hu.markgyori.game_4096.Config;
import hu.markgyori.game_4096.interfaces.ITable;
import hu.markgyori.game_4096.view.GameView;

public class Table implements ITable {
	protected HashMap<Point, Block> blocks;
	private int rows;
	private int cols;
	private Random rand;
	private int score;
	private boolean canAddNew;
	
	public Table(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.rand = new Random();
		this.blocks = new HashMap<Point, Block>();
	}
	
	public Block getBlock(int x, int y) {
		return this.blocks.get(new Point(x, y));
	}

	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.cols;
	}
	
	public void render() {
		for(Block b : this.blocks.values())
			b.render();
	}
	
	private boolean checkFreeSpace() {
		for(Block b : this.blocks.values())
			if (b.getPoint() == 0)
				return true;
		return false;
	}
	
	private void addRandomBlock() {
		if (!checkFreeSpace())
			return;
		
		int x = 0, y = 0;
		
		do {
			x = this.rand.nextInt(this.cols);
			y = this.rand.nextInt(this.rows);
		} while (this.getBlock(x, y).getPoint() != 0);
		
		App.getLogger().debug("Add new block to x: {} y: {}!", x, y);
		
		this.getBlock(x, y).setPoint(this.rand.nextInt(10) < 4 ? 4 : 2);
		this.getBlock(x, y).setIsNew(true);
	}
	
	public void startGame() {
		App.getLogger().info("Start game!");
		
		this.canAddNew = true;
		this.score = 0;
		
		for(Block b : this.blocks.values()) {
			b.setPoint(0);
			b.setIsNew(false);
			b.setLocked(false);
		}
		
		this.nextRound();
	}
	
	private void nextRound() {
		App.getLogger().debug("Start next round!");
		
		this.unlockAllBlock();
		
		if (!this.checkFreeSpace()) {
			App.getLogger().warn("No free space!");
		} else {
			if (canAddNew) {
				for (int i = 0; i < Config.ADDED_BLOCKS.getValue(); i++)
					this.addRandomBlock();
			}
		}
		
		if(this instanceof GameView)
			((GameView)this).setScore(this.score);
		
		this.render();
		
		if (!checkAnyMoveable())
			App.getInstance().showScore(this.score);
	}
	
	private boolean checkAnyMoveable() {
		for (int i = 0; i < this.rows - 1; i++) {
			for (int j = 0; j < this.cols - 1; j++) {
				Block thisBlock = this.getBlock(i, j);
				Block downerBlock = this.getBlock(i + 1, j);
				Block rightBlock = this.getBlock(i, j + 1);
				
				if (thisBlock.getPoint() == 0 || downerBlock.getPoint() == 0 || rightBlock.getPoint() == 0)
					return true;
				else if (thisBlock.getPoint() == downerBlock.getPoint() || thisBlock.getPoint() == rightBlock.getPoint())
					return true;
			}
		}
		
		return false;
	}
	
	private void unlockAllBlock() {
		App.getLogger().debug("Unlocked all block");
		for(Block b : this.blocks.values()) {
			b.setLocked(false);
			b.setIsNew(false);
		}
	}
	
	private void processBlocks(Block selected, Block target) {
		if (selected.getPoint() != 0 && target.getPoint() == 0) {
			target.setPoint(selected.getPoint());
			selected.setPoint(0);
			selected.setLocked(false);
			target.setLocked(false);
			this.canAddNew = true;
		} else if (selected.getPoint() != 0 && selected.getPoint() == target.getPoint()) {
			this.score+=selected.getPoint();
			this.canAddNew = true;
			target.setPoint(selected.getPoint() * 2);
			selected.setPoint(0);
			selected.setLocked(false);
			target.setLocked(true);
			
			if (target.getPoint() == Config.MAX_POINT.getValue())
				App.getInstance().showScore(this.score);
		}
	}
	
	public void moveDown() {
		App.getLogger().debug("Move down");
		this.canAddNew = false;
		for (int i = 0; i < this.rows; i++) {
			for (int j = this.cols - 2; j >= 0; j--) {
				int thisX = i,
					thisY = j - 1;
				while (true) {
					thisY++;
					
					if (thisY + 1 >= this.cols)
						break;
					
					Block thisBlock = this.getBlock(thisX, thisY);
					if (thisBlock.isLocked())
						continue;
					
					Block downerBlock = this.getBlock(thisX, thisY + 1);
					if (downerBlock.isLocked())
						continue;
					
					this.processBlocks(thisBlock, downerBlock);
				}
			}
		}
		
		this.nextRound();
	}
	
	public void moveUp() {
		App.getLogger().debug("Move up");
		this.canAddNew = false;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 1; j < this.cols; j++) {
				int thisX = i,
					thisY = j + 1;
				while (true) {
					thisY--;
					
					if (thisY - 1 < 0)
						break;
					
					Block thisBlock = this.getBlock(thisX, thisY);
					if (thisBlock.isLocked())
						continue;
					
					Block upperBlock = this.getBlock(thisX, thisY - 1);
					if (upperBlock.isLocked())
						continue;
					
					this.processBlocks(thisBlock, upperBlock);
				}
			}
		}
		
		this.nextRound();
	}

	public void moveLeft() {
		App.getLogger().debug("Move Left");
		this.canAddNew = false;
		for (int i = 1; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				int thisX = i + 1,
					thisY = j;
				while (true) {
					thisX--;
					
					if (thisX - 1 < 0)
						break;

					Block thisBlock = this.getBlock(thisX, thisY);
					if (thisBlock.isLocked())
						continue;
					
					Block leftBlock = this.getBlock(thisX - 1, thisY);
					if (leftBlock.isLocked())
						continue;
					
					this.processBlocks(thisBlock, leftBlock);
				}
			}
		}
		
		this.nextRound();
	}

	public void moveRight() {
		App.getLogger().debug("Move Right");
		this.canAddNew = false;
		for (int i = this.rows - 2; i >= 0; i--) {
			for (int j = 0; j < this.cols; j++) {
				int thisX = i - 1,
					thisY = j;
				while (true) {
					thisX++;
					
					if (thisX + 1 >= this.rows)
						break;
					
					Block thisBlock = this.getBlock(thisX, thisY);
					if (thisBlock.isLocked())
						continue;
					
					Block rightBlock = this.getBlock(thisX + 1, thisY);
					if (rightBlock.isLocked())
						continue;
					
					this.processBlocks(thisBlock, rightBlock);
				}
			}
		}
		
		this.nextRound();
	}
}