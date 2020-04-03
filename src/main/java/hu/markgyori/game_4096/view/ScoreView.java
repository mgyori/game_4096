package hu.markgyori.game_4096.view;

import hu.markgyori.game_4096.App;
import hu.markgyori.game_4096.Config;
import hu.markgyori.game_4096.model.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {
	private Button btn;
	private Label score;
	private Label title;
	private TableView<UserData> table;
	
	@SuppressWarnings("unchecked")
	public ScoreView() {
		this.btn = new Button();
		this.score = new Label();
		this.title = new Label();
		
		this.btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				App.getInstance().showMainMenu();
			}
		});
		
		this.table = new TableView<UserData>();
		this.table.setEditable(false);
		this.table.setMaxWidth(Config.WIDTH.getValue() * 0.8);
		this.table.setMaxHeight(Config.HEIGHT.getValue() * 0.4);
		//this.table.setMouseTransparent(true);
		//this.table.setFocusTraversable(true);
		this.table.getStylesheets().add("style.css");
		
		TableColumn<UserData, String> place = new TableColumn<UserData, String>();
		place.setText("Placement");
		place.setCellValueFactory(new PropertyValueFactory<UserData, String>("index"));
		setPercentSize(this.table, place, 0.1);
		
		TableColumn<UserData, String> name = new TableColumn<UserData, String>();
		name.setText("Name");
		name.setCellValueFactory(new PropertyValueFactory<UserData, String>("name"));
		setPercentSize(this.table, name, 0.5);
		
		TableColumn<UserData, String> time = new TableColumn<UserData, String>();
		time.setText("Time");
		time.setCellValueFactory(new PropertyValueFactory<UserData, String>("time"));
		setPercentSize(this.table, time, 0.2);
		
		TableColumn<UserData, String> point = new TableColumn<UserData, String>();
		point.setText("Point");
		point.setCellValueFactory(new PropertyValueFactory<UserData, String>("score"));
		setPercentSize(this.table, point, 0.2);
		
		this.table.getColumns().addAll(place, name, time, point);
		
		this.title.setText("Game over!");
		this.title.setStyle("-fx-font-size: 30px; -fx-padding: 20px");
		this.btn.setText("New game");
		this.score.setText("Your points: 0!");
		this.score.setStyle("-fx-font-size: 18px; -fx-padding: 20px");
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(title, table, score, btn);
	}
	
	private void setPercentSize(TableView<?> parent, final TableColumn<?, ?> column, final double percent)
	{
		parent.widthProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> item, Number old, Number current) {
				column.setPrefWidth(current.doubleValue() * percent);
			}
		});
	}
	
	public void setScore(int score) {
		this.score.setText(String.format("Your points: %d!", score));
		this.table.setItems(FXCollections.observableArrayList(App.getUserFactory().getUsers()));
	}
}
