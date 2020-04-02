package hu.markgyori.game_4096.view;

import hu.markgyori.game_4096.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {
	public ScoreView() {
		Button btn = new Button();
		Label score = new Label();
		Label title = new Label();
		
		TableView table = new TableView();
		table.setEditable(false);
		table.setMaxWidth(Config.WIDTH.GetValue() * 0.8);
		table.setMaxHeight(Config.HEIGHT.GetValue() * 0.4);
		TableColumn place = new TableColumn("Placement");
		TableColumn name = new TableColumn("Name");
		TableColumn time = new TableColumn("Time");
		TableColumn point = new TableColumn("Point");
		
		table.getColumns().addAll(place, name, time, point);
		
		title.setText("Game over!");
		title.setStyle("-fx-font-size: 30px; -fx-padding: 20px");
		btn.setText("New game");
		score.setText("Your points: 1234!");
		score.setStyle("-fx-font-size: 18px; -fx-padding: 20px");
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(title, table, score, btn);
	}
}
