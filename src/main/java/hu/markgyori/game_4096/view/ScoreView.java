package hu.markgyori.game_4096.view;

import hu.markgyori.game_4096.App;
import hu.markgyori.game_4096.Config;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {
	private Button btn;
	private Label score;
	private Label title;
	private TableView table;
	
	public ScoreView() {
		this.btn = new Button();
		this.score = new Label();
		this.title = new Label();
		
		this.btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				App.Instance().ShowGame();
			}
		});
		
		this.table = new TableView();
		this.table.setEditable(false);
		this.table.setMaxWidth(Config.WIDTH.GetValue() * 0.8);
		this.table.setMaxHeight(Config.HEIGHT.GetValue() * 0.4);
		TableColumn place = new TableColumn("Placement");
		TableColumn name = new TableColumn("Name");
		TableColumn time = new TableColumn("Time");
		TableColumn point = new TableColumn("Point");
		
		this.table.getColumns().addAll(place, name, time, point);
		
		this.title.setText("Game over!");
		this.title.setStyle("-fx-font-size: 30px; -fx-padding: 20px");
		this.btn.setText("New game");
		this.score.setText("Your points: 0!");
		this.score.setStyle("-fx-font-size: 18px; -fx-padding: 20px");
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(title, table, score, btn);
	}
	
	public void SetScore(int score) {
		this.score.setText(String.format("Your points: %d!", score));
	}
}
