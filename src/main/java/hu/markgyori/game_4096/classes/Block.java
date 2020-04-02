package hu.markgyori.game_4096.classes;

import java.awt.Point;

import hu.markgyori.game_4096.Utils;
import hu.markgyori.game_4096.interfaces.IBlock;
import hu.markgyori.game_4096.interfaces.ITable;

import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class Block implements IBlock {
	private int point;
	private ITable table;
	private StackPane panel;
	private Label label;
	private boolean locked;
	private boolean isNew;
	
	public Block(ITable table, Point p) {
		this.table = table;
		this.locked = false;
		this.isNew = false;
		
		this.panel = new StackPane();
		this.panel.setPrefSize(153, 153);
		this.panel.applyCss();
		
		this.label = new Label();
		this.panel.getChildren().add(this.label);
		
		// TODO Register block to main panel.
		this.table.GetPanel().add(this.panel, p.x, p.y);
		
		this.SetPoint(0);
		this.Render();
	}
	
	public int GetPoint() {
		return this.point;
	}

	public void SetPoint(int num) {
		this.point = num;
	}
	
	public boolean IsLocked() {
		return this.locked && this.point > 0;
	}
	
	public void SetLocked(boolean state) {
		this.locked = state;
	}
	
	public boolean IsNew() {
		return this.isNew;
	}
	
	public void SetIsNew(boolean state) {
		this.isNew = state;
	}

	public void Render() {
		this.panel.setStyle("-fx-background-color: " + Utils.GetColorByBlock(this.point) + "; -fx-border-color: black");
		this.label.setStyle("-fx-font-size:32px; -fx-text-fill: " + Utils.GetTextColorByBlock(this.point));
		this.label.setText("" + this.GetPoint());
	}
}