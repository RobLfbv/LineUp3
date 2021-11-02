package projet;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListeSimple extends Application {
	Label label;
	String chemin = ".\\src\\";
	String currentPath = "C:\\Users\\rem59\\eclipse-workspace\\JavaFX\\src";
	ListView<String> list_deux = new ListView<String>();
	File path = new File(chemin);




	class MonListChangeListener implements ListChangeListener<String> {
		public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c){
			label.setText("Selection de " + c.getList());
			list_deux.getItems().clear();
			
			currentPath = chemin + "\\" +c.getList().toString().substring(1, c.getList().toString().length()-1) + "\\";
			File choix = new File(currentPath);

			System.out.println(currentPath);
			if(choix.isDirectory()) {
				label.setText("");
				String[] filelist = choix.list();
				list_deux.getItems().clear();
				list_deux.getItems().addAll(filelist);  
				label.setText("Aucune s�lection");
			}
		}
	}

	class OuvertureFichier implements ListChangeListener<String> {
		public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c){
			try {
				Runtime.getRuntime().exec("xdg-open " + currentPath +c.getList().toString().substring(1, c.getList().toString().length()-1) );
			} catch (IOException e) {
				System.out.println("lala");
				e.printStackTrace();
			}
		}
	}

	class MonRenduDeCellule extends ListCell<String> {
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			Canvas c = new Canvas(400, 40);
			GraphicsContext gc = c.getGraphicsContext2D();
			
			
			String dest =   currentPath +"\\" +item +"\\" ;
			File f = new File(dest);
			
			Image file;
			/*
			if (!f.isDirectory()) {
				file = new Image(".\\file.png");
			}else {
				file = new Image(".\\folder.png");
			}
						
			gc.drawImage(file, 1,1);

		    gc.fillText(item, 40, 15);
			setGraphic(c);*/
		}
	}

	public void start(Stage stage) {
		label = new Label("Aucune selection");


		ListView<String> list = new ListView<String>();
		String[] filelist = path.list();
		list.getItems().addAll(filelist);
		
		list.getSelectionModel().getSelectedItems().addListener(new MonListChangeListener());
		list_deux.getSelectionModel().getSelectedItems().addListener(new OuvertureFichier());

		list.setCellFactory(new Callback<ListView<String>,
				ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new MonRenduDeCellule();
			}
		});

		list_deux.setCellFactory(new Callback<ListView<String>,
				ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new MonRenduDeCellule();
			}
		});
		
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER_LEFT);
		root.setSpacing(10.0);
		root.setPadding(new Insets(3, 3, 3, 3));

		root.getChildren().addAll(list, list_deux,label);

		Scene scene = new Scene(root, 800, 300);
		stage.setTitle("Simple liste");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
