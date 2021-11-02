package projet;


import java.util.Random;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class interactButton implements EventHandler<Event>{
	static BoardFX bf;
	public interactButton(BoardFX bf){
		interactButton.bf = bf;
	}




	@Override
	public void handle(Event e) {
		Button b = (Button) e.getSource();
		Group gp = (Group) b.getParent();
		VBox vbBoard = (VBox) gp.getParent();
		HBox all = (HBox) vbBoard.getParent();
		VBox inAll = (VBox) all.getChildren().get(0);
		HBox hbLabel = (HBox) inAll.getChildren().get(0);
		Label lb = (Label) hbLabel.getChildren().get(0);

		VBox choice = (VBox) inAll.getChildren().get(1);
		ToggleButton putcell = (ToggleButton) choice.getChildren().get(0);
		ToggleButton movecell = (ToggleButton) choice.getChildren().get(1);
		ToggleButton setTrap = (ToggleButton) choice.getChildren().get(2);
		ToggleButton blockLink = (ToggleButton) choice.getChildren().get(3);

		try {
			//------------------------------Poser-Un-pion------------------------------------------------//
			if (myControllerBoard.action.getId().equals("buttonPutACell")) {

				int id =  Integer.parseInt(b.getText());

				for(int i = 0; i<bf.getBoard().getSize();i++) {

					if(bf.getBoard().searchCell(Integer.parseInt(bf.getListButtons().get(i).getText())).getId() == id && bf.getBoard().searchCell(id).getState() == null) {
						bf.getBoard().putCell(id, MonControllerMenu.save.players.get(myControllerBoard.turn));
						myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	
						lb.setText(MonControllerMenu.save.players.get(myControllerBoard.turn).getName()  +  " Put a cell");

					}
				}

				while (MonControllerMenu.save.players.get(myControllerBoard.turn).getName().substring(0,2).equals("AI")) {
					int idRandom = AskAndDisplay.getRandomUnusedCellId(bf.getBoard());
					System.out.println("Tour de l'Ia/ Pos :" + idRandom);

					for(int i = 0; i<bf.getBoard().getSize();i++) {

						if(bf.getBoard().searchCell(Integer.parseInt(bf.getListButtons().get(i).getText())).getId() == idRandom && bf.getBoard().searchCell(idRandom).getState() == null) {

							bf.getBoard().putCell(idRandom, MonControllerMenu.save.players.get(myControllerBoard.turn));
							myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	
							lb.setText(MonControllerMenu.save.players.get(myControllerBoard.turn).getName()  +  " Put a cell");
						}
					}
				}

				if (bf.getBoard().nbCell() >= MonControllerMenu.save.players.size() * 3) {
					myControllerBoard.action = null;
					putcell.setVisible(false);
					movecell.setVisible(true);
					setTrap.setVisible(true);
					blockLink.setVisible(true);
				}

				if (bf.getBoard().isFinish(id)) {
					lb.setText("GG You win");
				}

				//------------------------------Deplacer-Un-pion------------------------------------------------//
			}else if (myControllerBoard.action.getId().equals("buttonMoveCell")) {
				int idChoose = Integer.parseInt(b.getText());
				if (myControllerBoard.lastIdMove < 1) {
					if (bf.board.searchCell(idChoose).getState().equals(MonControllerMenu.save.players.get(myControllerBoard.turn)) ) {
						myControllerBoard.lastIdMove = idChoose;
						lb.setText("You move the cell " + myControllerBoard.lastIdMove + "\n Choose where you want to move it");	
					}else {
						lb.setText("It's not your cell");	
					}

				}else {
					int move = Integer.parseInt(b.getText());

					if ( bf.getBoard().searchCell(myControllerBoard.lastIdMove).isNextTo(bf.getBoard(), bf.getBoard().searchCell(move)) && bf.getBoard().searchCell(move).getState() == null && 
							bf.getBoard().searchCell(myControllerBoard.lastIdMove).getState().equals(MonControllerMenu.save.players.get(myControllerBoard.turn)) && !bf.board.isBlocked(myControllerBoard.lastIdMove, move)) {
						bf.getBoard().moveCell(myControllerBoard.lastIdMove, move);

						for(int i = 0; i<bf.getBoard().getSize();i++) {

							if(bf.getBoard().searchCell(Integer.parseInt(bf.getListButtons().get(i).getText())).getId() == myControllerBoard.lastIdMove) {
								bf.getListButtons().get(i).setStyle(null);
								lb.setText(MonControllerMenu.save.players.get(myControllerBoard.turn).getName()  +  " Put a cell");
							}
						}

						if (bf.getBoard().isFinish(move)) {
							lb.setText("GG, You win");
						};

						myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	
						lb.setText(MonControllerMenu.save.players.get(myControllerBoard.turn).getName()  +  ": which cell do\n you want to move?");


					}else {
						lb.setText("You cant move it, choose an other cell");
					}

					myControllerBoard.lastIdMove = -1;

					while (MonControllerMenu.save.players.get(myControllerBoard.turn).getName().substring(0,2).equals("AI")) {
						System.out.println("Tour de l'ia");
						Random random = new Random();
						int nbRand, nb;
						int idx = 0;
						MonControllerMenu.save.players.get(myControllerBoard.turn)  ;
						
						do {
							nbRand = random.nextInt(MonControllerMenu.save.board.getSize())+1;
							idx++;
						} while (MonControllerMenu.save.board.searchCell(nbRand).getState() != MonControllerMenu.save.players.get(myControllerBoard.turn) && idx < 100);

						do {
							nb=AskAndDisplay.getACellNextToAnother(MonControllerMenu.save.board,nbRand);
							idx++;
						} while (MonControllerMenu.save.board.searchCell(nb).getState()!=null && idx < 10);

						bf.getBoard().moveCell( nbRand ,  nb);
						
						
						myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	
						if (bf.getBoard().isFinish(nb)) {
							lb.setText("GG You win");
						}
					}
					
					if (bf.getBoard().isFinish(move)) {
						lb.setText("GG You win");
					}
				}
				
				

				//------------------------------Poser-Un-Trap------------------------------------------------//
			}else if (myControllerBoard.action.getId().equals("buttonSetTrap")) {
				myControllerBoard.lastIdBlock = -1;
				int id =  Integer.parseInt(b.getText());
				String msg =  "Trap not set";
				for(int i = 0; i<bf.getBoard().getSize();i++) {
					if ( MonControllerMenu.save.players.get(myControllerBoard.turn).getTrap() ==  0) {
						lb.setText(   "You have 0 trap");

					}else if(bf.getBoard().searchCell(Integer.parseInt(bf.getListButtons().get(i).getText())).getId() == id && bf.getBoard().searchCell(id).getState() == null ) {

						MonControllerMenu.save.players.get(myControllerBoard.turn).setTrap(MonControllerMenu.save.players.get(myControllerBoard.turn).getTrap() - 1);
						bf.getBoard().putTrap(id, MonControllerMenu.save.players.get(myControllerBoard.turn));
						myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	
						msg = "Trap set";
					}
				}
				
				
				lb.setText(msg);

				while (MonControllerMenu.save.players.get(myControllerBoard.turn).getName().substring(0,2).equals("AI")) {
					System.out.println("Tour de l'ia");
					Random random = new Random();
					int nbRand, nb;
					int idx = 0;
					MonControllerMenu.save.players.get(myControllerBoard.turn)  ;
					
					do {
						nbRand = random.nextInt(MonControllerMenu.save.board.getSize())+1;
						idx++;
					} while (MonControllerMenu.save.board.searchCell(nbRand).getState() != MonControllerMenu.save.players.get(myControllerBoard.turn) && idx < 100);

					do {
						nb=AskAndDisplay.getACellNextToAnother(MonControllerMenu.save.board,nbRand);
						idx++;
					} while (MonControllerMenu.save.board.searchCell(nb).getState()!=null && idx < 10);

					bf.getBoard().moveCell( nbRand ,  nb);
					
					
					myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	

				}
				
				//------------------------------Bloquer-Un-Arc------------------------------------------------//
			}else if (myControllerBoard.action.getId().equals("buttonBlockLink")) {
				myControllerBoard.lastIdMove = -1;

				if (myControllerBoard.lastIdBlock < 1) {
					myControllerBoard.lastIdBlock = Integer.parseInt(b.getText());
					//Mise en couleur des choix possibles


				}else {
					int move = Integer.parseInt(b.getText());

					if ( MonControllerMenu.save.players.get(myControllerBoard.turn).block ==  0) {
						lb.setText(   "You have 0 block");

					}else if ( bf.getBoard().searchCell(myControllerBoard.lastIdBlock).isNextTo(bf.getBoard(), bf.getBoard().searchCell(move)) ) {
						bf.getBoard().putBlock(myControllerBoard.lastIdBlock, move);
						myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	
						lb.setText(MonControllerMenu.save.players.get(myControllerBoard.turn).getName()  +  ": which cell do\n you want to block?");
						MonControllerMenu.save.players.get(myControllerBoard.turn).block -= 1 ;

					}else {
						lb.setText(   "Impossible to block");
					}
					myControllerBoard.lastIdBlock = -1;
				}
				
				while (MonControllerMenu.save.players.get(myControllerBoard.turn).getName().substring(0,2).equals("AI")) {
					System.out.println("Tour de l'ia");
					Random random = new Random();
					int nbRand, nb;
					int idx = 0;
					MonControllerMenu.save.players.get(myControllerBoard.turn)  ;
					
					do {
						nbRand = random.nextInt(MonControllerMenu.save.board.getSize())+1;
						idx++;
					} while (MonControllerMenu.save.board.searchCell(nbRand).getState() != MonControllerMenu.save.players.get(myControllerBoard.turn) && idx < 100);

					do {
						nb=AskAndDisplay.getACellNextToAnother(MonControllerMenu.save.board,nbRand);
						idx++;
					} while (MonControllerMenu.save.board.searchCell(nb).getState()!=null && idx < 10);

					bf.getBoard().moveCell( nbRand ,  nb);
					
					
					myControllerBoard.turn= (myControllerBoard.turn +1) % MonControllerMenu.save.players.size();	

				}
			}

			Save.updateSave(MonControllerMenu.path, bf.getBoard(), MonControllerMenu.save.players);
			bf.updateBoardFx();


		} catch (Exception e2) {
			lb.setText( MonControllerMenu.save.players.get(myControllerBoard.turn).getName() +  "Choose an action");
		}
	}

}
