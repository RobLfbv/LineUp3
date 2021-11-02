package projet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MonControllerNewGame {
	@FXML
	Button home, buttonAddLayern, buttonMinusLayer, buttonAddTimeBlocked, buttonMinusTimeBlocked, buttonAddPlayer, buttonMinusPlayer, buttonAddAi, buttonMinusAi, validate;
	@FXML
	Label labelLayer, labelTimeBlocked, labelPlayer, labelAi;
	@FXML
	ChoiceBox<Shape> choiceBoxShape;
	@FXML
	VBox vboxPlayer, vboxAi;
	@FXML
	TextField textFieldFileName;

	String[] myShape = {"Square", "Triangle"};

	public void initialize() {
		System.out.println("Scene New game");
		choiceBoxShape.getItems().addAll(Shape.values());
		choiceBoxShape.setAccessibleText("Shape");;
		choiceBoxShape.setValue(Shape.values()[0]);


		HBox hbPlayer = new HBox();
		Label lbPlayer = new Label("Player 1");
		TextField tfPlayer = new TextField();
		ChoiceBox<Color> choiceBoxColor = new ChoiceBox<Color>();
		choiceBoxColor.setValue(Color.RED);
		for(Color c : Color.values()) {
			choiceBoxColor.getItems().add(c);
		}

		tfPlayer.setPromptText("Nickname");
		hbPlayer.getChildren().add(lbPlayer);
		hbPlayer.getChildren().add(tfPlayer);
		hbPlayer.getChildren().add(choiceBoxColor);
		vboxPlayer.getChildren().add(hbPlayer);

		HBox hbAi = new HBox();
		Label lbAi = new Label("Ai 1");
		lbAi.setFont(new Font("Arial", 25));
		hbAi.getChildren().add(lbAi);


		vboxAi.getChildren().add(hbAi);
	}

	public void screenHome(ActionEvent event) throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) home.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}

	public void addLayer(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelLayer.getText()) < 5) {
			labelLayer.setText("" + (Integer.parseInt(labelLayer.getText()) + 1)  );
		}
	}

	public void minusLayer(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelLayer.getText()) > 3) {
			labelLayer.setText("" + (Integer.parseInt(labelLayer.getText()) - 1)  );
			if (Integer.parseInt(labelLayer.getText()) + Integer.parseInt(labelAi.getText()) > Integer.parseInt(labelLayer.getText()) - 1 ) {
				if (Integer.parseInt(labelAi.getText()) > 0) {
					minusAi(event);			
				}else {
					minusPlayer(event);				
				}
			}
		}
	}

	public void addTimeBlocked(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelTimeBlocked.getText()) < 9) {
			labelTimeBlocked.setText("" + (Integer.parseInt(labelTimeBlocked.getText()) + 1)  );
		}
	}

	public void minusTimeBlocked(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelTimeBlocked.getText()) > 0) {
			labelTimeBlocked.setText("" + (Integer.parseInt(labelTimeBlocked.getText()) - 1)  );
		}
	}

	public void addPlayer(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelPlayer.getText()) <  (Integer.parseInt(labelLayer.getText()) - Integer.parseInt(labelAi.getText()) - 1 )) {
			labelPlayer.setText("" + (Integer.parseInt(labelPlayer.getText()) + 1)  );


			HBox hb = new HBox();
			Label lb = new Label("Player " + labelPlayer.getText());
			TextField tf = new TextField();
			tf.setPromptText("Nickname");
			hb.getChildren().add(lb);
			hb.getChildren().add(tf);
			ChoiceBox<Color> choiceBoxColor = new ChoiceBox<Color>();
			choiceBoxColor.setValue(Color.values()[Integer.parseInt(labelPlayer.getText()) - 1] );
			for(Color c : Color.values()) {
				choiceBoxColor.getItems().add(c);
			}
			hb.getChildren().add(choiceBoxColor);

			vboxPlayer.getChildren().add(hb);
		}
	}

	public void minusPlayer(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelPlayer.getText()) > 1) {
			labelPlayer.setText("" + (Integer.parseInt(labelPlayer.getText()) - 1)  );
			vboxPlayer.getChildren().remove( vboxPlayer.getChildren().size() - 1);
		}
	}

	public void addAi(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelAi.getText()) < (Integer.parseInt(labelLayer.getText()) - Integer.parseInt(labelPlayer.getText()) - 1 )) {
			labelAi.setText("" + (Integer.parseInt(labelAi.getText()) + 1)  );

			HBox hbAi = new HBox();
			Label lbAi = new Label("Ai " + labelAi.getText());
		
			hbAi.getChildren().add(lbAi);
			lbAi.setFont(new Font("Arial", 25));

			vboxAi.getChildren().add(hbAi);
		}
	}

	public void minusAi(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelAi.getText()) > 0) {
			labelAi.setText("" + (Integer.parseInt(labelAi.getText()) - 1)  );
			vboxAi.getChildren().remove( vboxAi.getChildren().size() - 1);
		}
	}

	public void validateChoice(ActionEvent event) throws IOException {
		int layer = Integer.parseInt(labelLayer.getText());
		Shape shape = choiceBoxShape.getValue();
		int timeBlocked = Integer.parseInt(labelTimeBlocked.getText());

		ArrayList<Player> player = new ArrayList<Player>();

		for (int i = 0; i < vboxPlayer.getChildren().size(); i++) {
			HBox hb = (HBox) vboxPlayer.getChildren().get(i);
			TextField tf = (TextField) hb.getChildren().get(1);
			ChoiceBox<Color> c = (ChoiceBox<Color>) hb.getChildren().get(2);
			System.out.println(tf.getText());
			player.add(new Player( tf.getText(), c.getValue()));
		}
		
		try {
			for (int i = 0; i < vboxPlayer.getChildren().size(); i++) {
				HBox hb = (HBox) vboxAi.getChildren().get(i);
				player.add(new DumbAI());
			}
		}catch(Exception e) {
			System.out.println("Pas d'Ia");
		}
		
		String path = "." + File.separator + "compil"  + Main.PATHSAVE.substring(1) ;
		
		Save.createSave(path, new Board(layer, shape, timeBlocked, new ArrayList<BlockedLink>()), player, textFieldFileName.getText());
		
		player.clear();
		
		MonControllerMenu.save = new Save(path + textFieldFileName.getText() + ".txt");
		
		MonControllerMenu.path =path + textFieldFileName.getText() + ".txt" ;
		
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("board.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) home.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();

		System.out.println("Ai " + labelAi.getText());
		System.out.println("Player " + labelPlayer.getText());
		System.out.println("Layer " + labelLayer.getText());
		System.out.println("Time blocked " + labelTimeBlocked.getText());


	}
}
