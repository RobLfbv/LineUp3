package projet;

import java.io.IOException;

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
import javafx.stage.Stage;

public class MonControllerOption {
	@FXML
	Button home, buttonAddLayern, buttonMinusLayer, buttonAddTimeBlocked, buttonMinusTimeBlocked, buttonAddPlayer, buttonMinusPlayer, buttonAddAi, buttonMinusAi, validate;
	@FXML
	Label labelLayer, labelTimeBlocked, labelPlayer, labelAi;
	@FXML
	ChoiceBox<String> choiceBoxShape;
	
	String[] myShape = {"Square", "Triangle"};

	public void initialize() {
		System.out.println("Scene New game");
		choiceBoxShape.getItems().addAll(myShape);
		choiceBoxShape.setAccessibleText("Shape");;
		choiceBoxShape.setValue(myShape[0]);
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
		}
	}

	public void minusPlayer(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelPlayer.getText()) > 1) {
			labelPlayer.setText("" + (Integer.parseInt(labelPlayer.getText()) - 1)  );
		}
	}

	public void addAi(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelAi.getText()) < (Integer.parseInt(labelLayer.getText()) - Integer.parseInt(labelPlayer.getText()) - 1 )) {
			labelAi.setText("" + (Integer.parseInt(labelAi.getText()) + 1)  );
			
		}
	}

	public void minusAi(ActionEvent event) throws IOException {
		if (Integer.parseInt(  labelAi.getText()) > 0) {
			labelAi.setText("" + (Integer.parseInt(labelAi.getText()) - 1)  );
		}
	}

	public void validateChoice(ActionEvent event) throws IOException {
		System.out.println("Ai " + labelAi.getText());
		System.out.println("Player " + labelPlayer.getText());
		System.out.println("Layer " + labelLayer.getText());
		System.out.println("Time blocked " + labelTimeBlocked.getText());
	}
}
