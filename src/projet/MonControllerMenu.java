package projet;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MonControllerMenu {
	@FXML
	Button myButtonOption, myButtonNewParty, myButtonLoadGame, myButtonQuickGame;

	public static Save save;
	public static String path;
	
	public void initialize() {
		System.out.println("Scene Menu");
	}

	public void sceneOption(ActionEvent event) throws IOException {
		Parent home_page_parent =FXMLLoader.load(getClass().getResource("option.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) myButtonOption.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}

	public void sceneNouvellePartie(ActionEvent event) throws IOException {
		Parent home_page_parent =FXMLLoader.load(getClass().getResource("nouvellePartie.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) myButtonOption.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	
	public void sceneLoadGame(ActionEvent event) throws IOException {
		System.out.println("a");
		Parent home_page_parent =FXMLLoader.load(getClass().getResource("loadGame.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) myButtonOption.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	
	public void launchQuickGame() throws IOException {
		path = "." + File.separator + "compil"  + Main.PATHSAVE.substring(1) + "QuickGame.txt" ;
		
		String config = "." + File.separator + "compil"  + Main.PATHCONFIG.substring(1) + "quickGame.txt";
		save = Save.quickGame(config);
		
		for(Player p: save.players) {
			System.out.println(p.getName());
		}
		
		
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("board.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) myButtonOption.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
		
		
	}
}