package hu.markgyori.game_4096;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import hu.markgyori.game_4096.classes.UserFactory;
import hu.markgyori.game_4096.model.UserData;
import hu.markgyori.game_4096.view.GameView;
import hu.markgyori.game_4096.view.MainMenuView;
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
	
	private static UserFactory users;
	
	public Stage primaryStage;
	
	private GameView gameView;
	private Scene gameScane;
	
	private ScoreView scoreView;
	private Scene scoreScane;
	
	private MainMenuView mainMenuView;
	private Scene mainMenuScane;
	
	/**
	 * Main class main function. This launch the JavaFX application window.
	 * @param args
	 */
    public static void main(String[] args)
    {
    	logger = LoggerFactory.getLogger(App.class);
    	users = UserFactory.getFactory();
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

		gameView = new GameView(Config.SIZE.getValue(), Config.SIZE.getValue());
		gameScane = new Scene(gameView.getView(), Config.WIDTH.getValue(), Config.HEIGHT.getValue());
		//TODO Capture key press event and passes it to the Table.
		gameScane.setOnKeyPressed(gameView.getPanel().getOnKeyPressed());
				
		scoreView = new ScoreView();
		scoreScane = new Scene(scoreView, Config.WIDTH.getValue(), Config.HEIGHT.getValue());
		
		mainMenuView = new MainMenuView();
		mainMenuScane = new Scene(mainMenuView, Config.WIDTH.getValue(), Config.HEIGHT.getValue());
		
		this.showMainMenu();
		
        primaryStage.setResizable(false);
        primaryStage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				App.instance.onClose(event);
			}
        });
	}
	
	public void onClose(WindowEvent event) {
		getLogger().info("Close application...");
		users.close();

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				getLogger().info("De-registering JDBC driver: {}", driver.getClass().getName());
				DriverManager.deregisterDriver(driver);
			} catch (Exception e) {
				getLogger().error("Error deregistering driver: {}", driver.getClass().getName());
				getLogger().error(e.getMessage(), e);
			}
		}
		
		AbandonedConnectionCleanupThread.checkedShutdown();
	}
	
	public void showGame() {
		primaryStage.setScene(gameScane);
		gameView.resize(Config.SIZE.getValue(), Config.SIZE.getValue());
		gameView.startGame();
		getLogger().info("Show game scane");
	}
	
	public void showScore(int score) {
		if (score != 0) {
			UserData data = new UserData();
			data.setName(Config.USER_NAME);
			data.setScore(score);
			data.setTime((int)((System.currentTimeMillis() - Config.GAME_START) / 1000));
			users.addUserData(data);
		}
		primaryStage.setScene(scoreScane);
		scoreView.setScore(score);
		getLogger().info("Show score scane");
	}
	
	public void showMainMenu() {
		primaryStage.setScene(mainMenuScane);
		getLogger().info("Show main menu scane");
	}
	
	public static App getInstance() {
		return instance;
	}
	
	/**
	 * Returns the SLF4J logger instance.
	 * @return Returns SLF4J logger.
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	public static UserFactory getUserFactory() {
		return users;
	}
}