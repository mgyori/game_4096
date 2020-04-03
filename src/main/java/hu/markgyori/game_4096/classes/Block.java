package hu.markgyori.game_4096.classes;

import java.awt.Point;

import hu.markgyori.game_4096.Config;
import hu.markgyori.game_4096.Utils;
import hu.markgyori.game_4096.interfaces.IBlock;
import hu.markgyori.game_4096.interfaces.ITable;
import hu.markgyori.game_4096.view.GameView;
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
		this.panel.setPrefSize(Config.WIDTH.getValue() / Config.SIZE.getValue(), Config.HEIGHT.getValue() / Config.SIZE.getValue());

		this.label = new Label();
		this.panel.getChildren().add(this.label);
		
		// TODO Register block to main panel.
		if (this.table instanceof GameView)
			((GameView)this.table).getPanel().add(this.panel, p.x, p.y);
		
		this.setPoint(0);
		this.render();
	}
	
	public int getPoint() {
		return this.point;
	}

	public void setPoint(int num) {
		this.point = num;
	}
	
	public boolean isLocked() {
		return this.locked && this.point > 0;
	}
	
	public void setLocked(boolean state) {
		this.locked = state;
	}
	
	public boolean isNew() {
		return this.isNew;
	}
	
	public void setIsNew(boolean state) {
		this.isNew = state;
	}
	
	public StackPane getPanel() {
		return this.panel;
	}

	public void render() {
		this.panel.setStyle("-fx-background-color: " + Utils.getColorByBlock(this.point) + "; -fx-border-color: black");
		this.label.setStyle("-fx-font-size:32px; -fx-text-fill: " + Utils.getTextColorByBlock(this.point) + "; -fx-effect: dropshadow(one-pass-box, black, 4, 0.0, 0, 0)");
		if (this.getPoint() == 0)
			this.label.setText("");
		else
			this.label.setText(String.format("%d", this.getPoint()));
	}
}