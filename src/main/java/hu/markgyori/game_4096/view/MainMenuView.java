package hu.markgyori.game_4096.view;

import hu.markgyori.game_4096.App;
import hu.markgyori.game_4096.Config;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuView extends VBox {
	private ComboBox<String> gridBox;
	private ComboBox<String> modeBox;
	private TextField textField;
	
	public MainMenuView() {
		Label title = new Label();
		title.setText("4096 best game ever");
		title.getStyleClass().add("title");
		
		GridPane grid = new GridPane();
		grid.setHgap(3);
		grid.setVgap(2);
		
		Label gridLabel = new Label("Grid size:");
		gridLabel.setTextAlignment(TextAlignment.RIGHT);
		gridLabel.setMaxWidth(Double.MAX_VALUE);
		gridLabel.getStyleClass().add("menuText");
		
		gridBox = new ComboBox<String>();
		gridBox.getItems().addAll(
            "3x3",
            "4x4",
            "5x5",
            "6x6",
            "8x8"
        );
		gridBox.setValue(Config.SIZE.getValue() + "x" + Config.SIZE.getValue());
		gridBox.setMinWidth(160);
		gridBox.setMaxWidth(Double.MAX_VALUE);
		gridBox.getStyleClass().add("comboBox");
		
		Label modeLabel = new Label("Max point:");
		modeLabel.setTextAlignment(TextAlignment.RIGHT);
		modeLabel.getStyleClass().add("menuText");
		modeBox = new ComboBox<String>();
		modeBox.getItems().addAll(
            "512",
			"1024",
            "2048",
            "4096",
            "8192"
        );
		modeBox.setValue(String.format("%d", Config.MAX_POINT.getValue()));
		modeBox.setMinWidth(160);
		modeBox.getStyleClass().add("comboBox");

		Label nameLabel = new Label("Name:");
		nameLabel.setTextAlignment(TextAlignment.RIGHT);
		nameLabel.setMaxWidth(Double.MAX_VALUE);
		nameLabel.getStyleClass().add("menuText");
		textField = new TextField();
		textField.setMinWidth(160);
		
		grid.add(gridLabel, 0, 0);
		grid.add(gridBox, 1, 0);
		grid.add(modeLabel, 0, 1);
		grid.add(modeBox, 1, 1);
		grid.add(nameLabel, 0, 2);
		grid.add(textField, 1, 2);
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.CENTER);

		Button btn = new Button();
		btn.setText("Start game");
		btn.getStyleClass().add("startBtn");

		this.setSpacing(20);
		this.setAlignment(Pos.TOP_CENTER);
		this.getChildren().addAll(title, grid, btn);
		this.getStylesheets().add("style.css");
		
		Image img = new Image("background.jpg", 1920, 1080, false, true);
		BackgroundImage myBI = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

		this.setBackground(new Background(myBI));

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (textField.getText().length() < 3) {
					final Stage dialog = new Stage();
					dialog.setTitle("Error!");
					dialog.setResizable(false);
					dialog.setAlwaysOnTop(true);
	                dialog.initModality(Modality.APPLICATION_MODAL);
	                dialog.initOwner(App.getInstance().primaryStage);
	                VBox dialogVbox = new VBox(20);
	                dialogVbox.getChildren().add(new Text("Please enter your name!"));
	                Button btn = new Button("Ok!");
	                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							dialog.close();
						}
	                });
	                dialogVbox.getChildren().add(btn);
	                dialogVbox.setAlignment(Pos.CENTER);
	                Scene dialogScene = new Scene(dialogVbox, 300, 80);
	                dialog.setScene(dialogScene);
	                dialog.show();
				} else {
					Config.SIZE.setValue(Integer.parseInt(gridBox.getValue().split("x")[0]));
					Config.MAX_POINT.setValue(Integer.parseInt(modeBox.getValue()));
					Config.USER_NAME = textField.getText();
					App.getInstance().showGame();
				}
			}
		});
	}
}
