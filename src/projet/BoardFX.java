package projet;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class BoardFX {
	private List<Polygon> listPoly = new ArrayList<Polygon>();
	private List<Button> listButtons = new ArrayList<Button>();
	private List<Line> listLine = new ArrayList<Line>();
	private int layer;
	private int nbTop;
	protected Board board;
	private final double R=20;
	public int idLastCell;
	public int turn = 0;

	public BoardFX(int layer, int nbSommets, Board board) {
		this.layer = layer;
		this.nbTop = nbSommets;
		this.board = board;
		this.init();
	}
	/*click sur 6 changement de couleur, bordure plus grosse, puis différentes actions possiblent qui apparaissent (visibility from true to false)
    Quelles options possibles ? Faire apparaître ceux possiblent,set trap place un piège et passe le tours, 
    si move les déplacements possibles vont changer de couleur, same pour block juste la selection du pion qui change*/

	/*save:
      entré path, board, player
            FileWriter mySave = new FileWriter(path+fileName+".txt");

            for (Player player : players) {
                mySave.write(player.toSave()+"\n");
            }

            mySave.write("PLAYER_END\n");

            mySave.write(board.toSave());

            mySave.write("CELLS_END\n");

            mySave.close();

            System.out.println("Successfully saved");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        System.exit(0);*/
	public void init() {
		int sommet = 0;
		int idx = 0;
		double x = 0 ,y = 0;
		List<Double> l = new ArrayList<Double>();
		for(int a = 0; a<layer; a++) {
			Polygon polygon = new Polygon();
			if(this.board.getType()==Shape.SQUARE) {
				polygon.getPoints().addAll(new Double[]{ 
						250.0+50*a, 50.0+50*a, 
						750.0-50*a, 50.0+50*a, 
						750.0-50*a, 550.0-50*a, 
						250.0+50*a, 550.0-50*a, 
				}); 
			}else {
				int deValueY = 120;
				int deValueX = 90;

				for(int d = 3; d<this.layer;d++) {
					deValueY -= 20;
					deValueX -= 20;
				}
				polygon.getPoints().addAll(new Double[]{ 

						450.0,      25.0+deValueX*a, 
						850.0-deValueY*a, 550.0-deValueX*a, 
						50.0+deValueY*a, 550.0-deValueX*a, 
				}); 
			}
			l = polygon.getPoints();
			polygon.setFill(Color.web("0xffffff00"));
			polygon.setStroke(Color.BLACK);
			polygon.setStrokeWidth(5);
			this.listPoly.add(polygon);
			for(int i = 0; i<l.size(); i++) {
				int id = this.board.getBoard()[this.layer-1-a][i].getId();
				this.listButtons.add(new Button(""+id));
				this.listButtons.get(idx).setShape(new Circle(R));
				this.listButtons.get(idx).setMinSize(2*R, 2*R);
				this.listButtons.get(idx).setMaxSize(2*R, 2*R);
				this.listButtons.get(idx).addEventHandler(ActionEvent.ACTION,new interactButton(this));
				if((i+1)%2==1) {
					this.listButtons.get(idx).setTranslateX(l.get(sommet)-17);
					x = l.get(sommet)-17;
					sommet++;
					this.listButtons.get(idx).setTranslateY(l.get(sommet)-17);
					y = l.get(sommet)-17;
					sommet++;
				}else {
					this.listButtons.get(idx).setTranslateX((x+(l.get(sommet%l.size())-17))/2);
					this.listButtons.get(idx).setTranslateY((y+(l.get((sommet+1)%l.size())-17))/2);
					if(a==0) {
						this.listLine.add(new Line());
						this.listLine.get(this.listLine.size()-1).setStartX((x+25+(l.get(sommet%l.size())))/2);
						this.listLine.get(this.listLine.size()-1).setStartY((y+25+(l.get((sommet+1)%l.size())))/2);
						this.listLine.get(this.listLine.size()-1).setStrokeWidth(5);
					}else if(a==layer-1) {
						this.listLine.get((i+1)/2-1).setEndX((x+25+(l.get(sommet%l.size())))/2);
						this.listLine.get((i+1)/2-1).setEndY((y+25+(l.get((sommet+1)%l.size())))/2);
					}
				}

				idx++;
			}
			sommet = 0;
		}
		this.updateBoardFx();
	}

	public List<Polygon> getListPoly() {
		return listPoly;
	}
	public List<Button> getListButtons() {
		return listButtons;
	}
	public List<Line> getListLine() {
		return listLine;
	}
	public int getLayer() {
		return layer;
	}
	public int getNbSommets() {
		return nbTop;
	}
	public double getR() {
		return R;
	}
	public Board getBoard() {
		return this.board;
	}

	public void updateBoardFx() {

		for (int i=0; i < listButtons.size(); i++) {
			for (int j = 0; j < board.getSize()+1; j++) {

				int idButton = Integer.parseInt(listButtons.get(i).getText());
				int idBoard = j;

				if (idBoard == idButton ) {
					if (board.searchCell(j).getState() != null  ) {
						Cell c = board.searchCell(j);
						String color = Color.valueOf(c.getState().getColor().name()).toString().substring(2,8);
						listButtons.get(i).setStyle("-fx-background-color: #" + color + ";"); 

						
					}else {
						listButtons.get(i).setStyle("-fx-border-color: #000000;"); 

					}
					
					for (int k = 0; k < board.getSize(); k++) {
						if ( board.isBlocked(j, k )) {
							Cell c = board.searchCell(j);
							String color = Color.valueOf(c.getState().getColor().name()).toString().substring(2,8);
							listButtons.get(i).setStyle("-fx-background-color: #" + color + ";" 
									+   "-fx-border-color: #000000; -fx-border-width: 5px;"); 
						}
					}
					
				}

			}
		}
		System.out.println(AskAndDisplay.displayBoard(board));

	}
}
