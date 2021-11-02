package projet;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class myControllerBoard {
	@FXML
	VBox vboxBoard;
	@FXML
	ToggleButton buttonPutACell, buttonMoveCell, buttonSetTrap, buttonBlockLink;
	@FXML
	Label labelAction;
	@FXML
	ToggleGroup choice;
	@FXML
	Button buttonHome;
	
	static int lastIdBlock = -1;
	static int lastIdMove = -1;
	static ToggleButton action;
	BoardFX boardFx = new BoardFX(MonControllerMenu.save.board.getNbLayer(), MonControllerMenu.save.board.getType().getElement()/2 ,MonControllerMenu.save.board);
	static int turn = 0;

	public void initialize() {
		//Le board charge ses parametre comme le nombre de couche mais pas le tableau en lui meme car on ne peut pas l'afficher apparamment		
		System.out.println("Initialisation...");
		
		if (boardFx.getBoard().nbCell() >= 3*MonControllerMenu.save.players.size()) {
			buttonPutACell.setVisible(false);
			buttonMoveCell.setVisible(true);
			buttonSetTrap.setVisible(true);
			buttonBlockLink.setVisible(true);
		}else {
			buttonPutACell.setVisible(true);
			buttonMoveCell.setVisible(false);
			buttonSetTrap.setVisible(false);
			buttonBlockLink.setVisible(false);
		}
		
		
		
		
		Group plateau = new Group();
		plateau.setTranslateX(100);
		plateau.setTranslateY(100);

		for(int i = 0; i<boardFx.getLayer(); i++) {
			plateau.getChildren().add(boardFx.getListPoly().get(i));
		}
		for(int i = 0; i<boardFx.getNbSommets();i++) {
			plateau.getChildren().add(boardFx.getListLine().get(i));
		}
		for(int i = 0; i<boardFx.getNbSommets()*2*boardFx.getLayer(); i++ ) {
			plateau.getChildren().add(boardFx.getListButtons().get(i));
		}

		vboxBoard.getChildren().add(plateau);
	}

	public void putCell() {

		action = (ToggleButton) choice.getSelectedToggle();

		System.out.println("Je pose un cell");
		labelAction.setText(MonControllerMenu.save.players.get(turn).getName() + ": Place your cell ");
	}

	public void moveCell() {
		action = (ToggleButton) choice.getSelectedToggle();
		System.out.println("Je déplace un cell");
		labelAction.setText( MonControllerMenu.save.players.get(turn).getName() + ": which cell do\n you want to move?");

	}

	public void setTrap() {
		action = (ToggleButton) choice.getSelectedToggle();

		System.out.println("je pose un piege");
		labelAction.setText( MonControllerMenu.save.players.get(turn).getName() + ": Place your trap");

	}

	public void blockLink() {
		action = (ToggleButton) choice.getSelectedToggle();

		System.out.println("Bloque Link");
		labelAction.setText( MonControllerMenu.save.players.get(turn).getName() +": Press the cell you want to block");

	}
	
	public void screenHome() throws IOException {
		System.out.println("menu");
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) buttonHome.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
} 


