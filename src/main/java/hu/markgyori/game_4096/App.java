package hu.markgyori.game_4096;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.markgyori.game_4096.view.GameView;

/**
 * Main class for 4096 game.
 * @author marko
 *
 */
public class App extends Application
{
	private static Logger logger;
	
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
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("4096 Game");

		GameView view = new GameView(4, 4);
		Scene scene = new Scene(view.GetPanel(), 600, 600);
		//TODO Capture key press event and passes it to the Table.
		scene.setOnKeyPressed(view.GetPanel().getOnKeyPressed());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	
	/**
	 * Returns the SLF5J logger instance.
	 * @return Returns SLF4J logger.
	 */
	public static Logger GetLogger() {
		return logger;
	}
}