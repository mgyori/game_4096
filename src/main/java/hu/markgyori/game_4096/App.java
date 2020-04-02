package hu.markgyori.game_4096;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.markgyori.game_4096.view.GameView;
import hu.markgyori.game_4096.view.ScoreView;

/**
 * Main class for 4096 game.
 * @author marko
 *
 */
public class App extends Application
{
	private static Logger logger;
	private static App instance;
	
	private Stage primaryStage;
	private Scene gameScane;
	private Scene scoreScane;
	
	/**
	 * Main class main function. This launch the JavaFX application window.
	 * @param args
	 */
    public static void main( String[] args )
    {
    	logger = LoggerFactory.getLogger(App.class);
        launch(args);
    }

	@Override
	/**
	 * Main function for JavaFX application
	 * @throws Exception if any error occurs 
	 */
	public void start(Stage stage) throws Exception {
		instance = this;
		
		primaryStage = stage;
		primaryStage.setTitle("4096 Game");

		GameView gameView = new GameView(5, 5);
		gameScane = new Scene(gameView.GetView(), 600, 600);
		//TODO Capture key press event and passes it to the Table.
		gameScane.setOnKeyPressed(gameView.GetPanel().getOnKeyPressed());
				
		ScoreView scoreView = new ScoreView();
		scoreScane = new Scene(scoreView, 600, 600);
		
		this.showGame();
		
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	
	public void showGame() {
		primaryStage.setScene(gameScane);
		GetLogger().info("Show game scane");
	}
	
	public void showScore() {
		primaryStage.setScene(scoreScane);
		GetLogger().info("Show score scane");
	}
	
	public static App Instance() {
		return instance;
	}
	
	/**
	 * Returns the SLF5J logger instance.
	 * @return Returns SLF4J logger.
	 */
	public static Logger GetLogger() {
		return logger;
	}
}