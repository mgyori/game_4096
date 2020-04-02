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
		this.panel.setPrefSize(600, 600);
		
		this.panel.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				App.GetLogger().debug("Key pressed {}", event.getCharacter());
				switch(event.getCode()) {
					case DOWN:
					case S:
						MoveDown();
						break;
						
					case UP:
					case W:
						MoveUp();
						break;
					
					case LEFT:
					case A:
						MoveLeft();
						break;
						
					case RIGHT:
					case D:
						MoveRight();
						break;
						
					default:
						break;
				}
			}
			
		});
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Point p = new Point(i, j);
				this.blocks.put(p, new Block(this, p));
			}
		}
		
		this.view.getChildren().addAll(this.panel, this.score);
		
		this.StartGame();
	}
	
	/**
	 * Returns the JavaFX GridPane where the board is drawn.
	 * @return JavaFX GridPane for table
	 */
	public GridPane GetPanel() {
		return this.panel;
	}
	
	/**
	 * Returns the JavaFX StackPane. Use to draw stacked game panels.
	 * @return JavaFX StackPane for game stack view.
	 */
	public StackPane GetView() {
		return this.view;
	}
	
	/**
	 * Set score text in game view.
	 * @param score
	 */
	public void SetScore(int score) {
		this.score.setText(String.format("Your points: %d", score));
	}
}
