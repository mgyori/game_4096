package hu.markgyori.game_4096.view;

import java.awt.Point;

import hu.markgyori.game_4096.App;
import hu.markgyori.game_4096.classes.Block;
import hu.markgyori.game_4096.classes.Table;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Class for game view. This class register the key press event and the table view grid.
 * @author marko
 *
 */
public class GameView extends Table {
	private GridPane panel;
	private StackPane view;
	private Label score;
	
	public GameView(int rows, int cols) {
		super(rows, cols);
		
		this.view = new StackPane();
		this.view.setAlignment(Pos.TOP_CENTER);
		
		this.score = new Label();
		this.score.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 4, 0.0, 2, 1)");
		
		this.panel = new GridPane();
		
		this.panel.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				App.getLogger().debug("Key pressed {}", event.getCharacter());
				switch(event.getCode()) {
					case DOWN:
					case S:
						moveDown();
						break;
						
					case UP:
					case W:
						moveUp();
						break;
					
					case LEFT:
					case A:
						moveLeft();
						break;
						
					case RIGHT:
					case D:
						moveRight();
						break;
						
					default:
						break;
				}
			}
			
		});
		
		this.initBlocks();
		
		this.view.getChildren().addAll(this.panel, this.score);
	}
	
	public void initBlocks() {
		for(Block b : this.blocks.values())
			this.panel.getChildren().remove(b.getPanel());
		this.blocks.clear();
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getColumns(); j++) {
				Point p = new Point(i, j);
				this.blocks.put(p, new Block(this, p));
			}
		}
	}
	
	/**
	 * Returns the JavaFX GridPane where the board is drawn.
	 * @return JavaFX GridPane for table
	 */
	public GridPane getPanel() {
		return this.panel;
	}
	
	/**
	 * Returns the JavaFX StackPane. Use to draw stacked game panels.
	 * @return JavaFX StackPane for game stack view.
	 */
	public StackPane getView() {
		return this.view;
	}
	
	/**
	 * Set score text in game view.
	 * @param score
	 */
	public void setScore(int score) {
		this.score.setText(String.format("Your points: %d", score));
	}
}
