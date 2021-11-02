package projet;

import java.io.File;
import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ControllerLoadGame {
	@FXML
	Button validate, myButtonMenu;
	@FXML
	HBox hbListView;

	String chemin = "." + File.separator + "compil" + File.separator + "Saves";
	String choice = "";

	public void initialize() {
		ListView<String> l = new ListView<String>();

		hbListView.getChildren().add(l);

		System.out.println("Scene Load Game");



		File path = new File(chemin);


		String[] filelist = path.list();

		l.getItems().addAll(filelist);
		l.getSelectionModel().getSelectedItems().addListener(new MonListChangeListener());
	}

	class MonListChangeListener implements ListChangeListener<String> {
		public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c){

			choice = chemin + "\\" +c.getList().toString().substring(1, c.getList().toString().length()-1) + "\\";
		}
	}

	public void screenHome(ActionEvent event) throws IOException {
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) validate.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}

	public void validateChoice(ActionEvent event) throws IOException {
		MonControllerMenu.save = new Save(choice);
		
		MonControllerMenu.path = choice;
		
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("board.fxml"));
		Scene home_page_scene = new Scene(home_page_parent);
		Stage app_stage = (Stage) validate.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
		
	}

}
