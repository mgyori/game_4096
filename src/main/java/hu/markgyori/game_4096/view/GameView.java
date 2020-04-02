package hu.markgyori.game_4096.view;

import java.awt.Point;

import hu.markgyori.game_4096.App;
import hu.markgyori.game_4096.classes.Block;
import hu.markgyori.game_4096.classes.Table;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Class for game view. This class register the key press event and the table view grid.
 * @author marko
 *
 */
public class GameView extends Table {
	private GridPane panel;
	
	public GameView(int rows, int cols) {
		super(rows, cols);
		
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
		
		this.StartGame();
	}
	
	/**
	 * Returns the JavaFX GridPane in the board is drawn.
	 * @return JavaFX GridPane for table
	 */
	public GridPane GetPanel() {
		return this.panel;
	}
}
