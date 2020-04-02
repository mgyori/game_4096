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
	
	public Block GetBlock(int x, int y) {
		return this.blocks.get(new Point(x, y));
	}

	public int GetRows() {
		return this.rows;
	}

	public int GetColumns() {
		return this.cols;
	}
	
	public void Render() {
		for(Block b : this.blocks.values())
			b.Render();
	}
	
	private boolean CheckFreeSpace() {
		for(Block b : this.blocks.values())
			if (b.GetPoint() == 0)
				return true;
		return false;
	}
	
	private void AddRandomBlock() {
		if (!CheckFreeSpace())
			return;
		
		int x = 0, y = 0;
		
		do {
			x = this.rand.nextInt(this.cols);
			y = this.rand.nextInt(this.rows);
		} while (this.GetBlock(x, y).GetPoint() != 0);
		
		App.GetLogger().debug("Add new block to x: {} y: {}!", x, y);
		
		this.GetBlock(x, y).SetPoint(this.rand.nextInt(10) < 4 ? 4 : 2);
		this.GetBlock(x, y).SetIsNew(true);
	}
	
	public void StartGame() {
		App.GetLogger().info("Start game!");
		
		this.canAddNew = true;
		this.score = 0;
		
		for(Block b : this.blocks.values()) {
			b.SetPoint(0);
			b.SetIsNew(false);
			b.SetLocked(false);
		}
		
		this.NextRound();
	}
	
	private void NextRound() {
		App.GetLogger().debug("Start next round!");
		
		this.UnlockAllBlock();
		
		if (!this.CheckFreeSpace()) {
			App.GetLogger().warn("No free space!");
		} else {
			if (canAddNew) {
				this.AddRandomBlock();
				this.AddRandomBlock();
			}
		}
		
		if(this instanceof GameView)
			((GameView)this).SetScore(this.score);
		
		this.Render();
		
		if (!CheckAnyMoveable())
			App.Instance().ShowScore(this.score);
	}
	
	private boolean CheckAnyMoveable() {
		for (int i = 0; i < this.rows - 1; i++) {
			for (int j = 0; j < this.cols - 1; j++) {
				Block thisBlock = this.GetBlock(i, j);
				Block downerBlock = this.GetBlock(i + 1, j);
				Block rightBlock = this.GetBlock(i, j + 1);
				
				if (thisBlock.GetPoint() == 0 || downerBlock.GetPoint() == 0 || rightBlock.GetPoint() == 0)
					return true;
				else if (thisBlock.GetPoint() == downerBlock.GetPoint() || thisBlock.GetPoint() == rightBlock.GetPoint())
					return true;
			}
		}
		
		return false;
	}
	
	private void UnlockAllBlock() {
		App.GetLogger().debug("Unlocked all block");
		for(Block b : this.blocks.values()) {
			b.SetLocked(false);
			b.SetIsNew(false);
		}
	}
	
	private void ProcessBlocks(Block selected, Block target) {
		if (selected.GetPoint() != 0 && target.GetPoint() == 0) {
			target.SetPoint(selected.GetPoint());
			selected.SetPoint(0);
			selected.SetLocked(false);
			target.SetLocked(false);
			this.canAddNew = true;
		} else if (selected.GetPoint() != 0 && selected.GetPoint() == target.GetPoint()) {
			this.score+=selected.GetPoint();
			this.canAddNew = true;
			target.SetPoint(selected.GetPoint() * 2);
			selected.SetPoint(0);
			selected.SetLocked(false);
			target.SetLocked(true);
			
			if (target.GetPoint() == Config.MAX_POINT.GetValue())
				App.Instance().ShowScore(this.score);
		}
	}
	
	public void MoveDown() {
		App.GetLogger().debug("Move down");
		this.canAddNew = false;
		for (int i = 0; i < this.rows; i++) {
			for (int j = this.cols - 2; j >= 0; j--) {
				int thisX = i,
					thisY = j - 1;
				while (true) {
					thisY++;
					
					if (thisY + 1 >= this.cols)
						break;
					
					Block thisBlock = this.GetBlock(thisX, thisY);
					if (thisBlock.IsLocked())
						continue;
					
					Block downerBlock = this.GetBlock(thisX, thisY + 1);
					if (downerBlock.IsLocked())
						continue;
					
					this.ProcessBlocks(thisBlock, downerBlock);
				}
			}
		}
		
		this.NextRound();
	}
	
	public void MoveUp() {
		App.GetLogger().debug("Move up");
		this.canAddNew = false;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 1; j < this.cols; j++) {
				int thisX = i,
					thisY = j + 1;
				while (true) {
					thisY--;
					
					if (thisY - 1 < 0)
						break;
					
					Block thisBlock = this.GetBlock(thisX, thisY);
					if (thisBlock.IsLocked())
						continue;
					
					Block upperBlock = this.GetBlock(thisX, thisY - 1);
					if (upperBlock.IsLocked())
						continue;
					
					this.ProcessBlocks(thisBlock, upperBlock);
				}
			}
		}
		
		this.NextRound();
	}

	public void MoveLeft() {
		App.GetLogger().debug("Move Left");
		this.canAddNew = false;
		for (int i = 1; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				int thisX = i + 1,
					thisY = j;
				while (true) {
					thisX--;
					
					if (thisX - 1 < 0)
						break;

					Block thisBlock = this.GetBlock(thisX, thisY);
					if (thisBlock.IsLocked())
						continue;
					
					Block leftBlock = this.GetBlock(thisX - 1, thisY);
					if (leftBlock.IsLocked())
						continue;
					
					this.ProcessBlocks(thisBlock, leftBlock);
				}
			}
		}
		
		this.NextRound();
	}

	public void MoveRight() {
		App.GetLogger().debug("Move Right");
		this.canAddNew = false;
		for (int i = this.rows - 2; i >= 0; i--) {
			for (int j = 0; j < this.cols; j++) {
				int thisX = i - 1,
					thisY = j;
				while (true) {
					thisX++;
					
					if (thisX + 1 >= this.rows)
						break;
					
					Block thisBlock = this.GetBlock(thisX, thisY);
					if (thisBlock.IsLocked())
						continue;
					
					Block rightBlock = this.GetBlock(thisX + 1, thisY);
					if (rightBlock.IsLocked())
						continue;
					
					this.ProcessBlocks(thisBlock, rightBlock);
				}
			}
		}
		
		this.NextRound();
	}
}