package projet;

import java.util.ArrayList;
import java.util.Random;

public class DumbAI extends Player{
	private static Random random = new Random();
	private static int nbAI=0;

	public DumbAI() {
		super("AI "+nbAI,Color.getUnusedColor());
		nbAI++;
	}


	public void doPlayer(Board board, ArrayList<Player> players) {
		if (this.nbCells<3) {
			this.askPut(board);
		}else {
			this.doRandom(board);
		}
	}


	private void doRandom(Board board) {
		boolean end=false;
		while(!end) {
			switch (random.nextInt(10)) {
			case 0:
				if (this.block>0) {
					end=this.askBlock(board);
					break;
				}else {
					break;
				}
			case 9:
				if (this.trap>0) {
					end=this.askTrap(board);
					break;
				}else {
					break;
				}

			default:
				end=this.askMove(board);
			}
		}
	}
	
	/* =========================================== */

	@SuppressWarnings("unused")
	private void doRandomFx(Board board) {
		boolean end=false;
		while(!end) {
			switch (random.nextInt(10)) {
			case 0:
				if (this.block>0) {
					end=this.askBlock(board);
					break;
				}else {
					break;
				}
			case 9:
				if (this.trap>0) {
					end=this.askTrap(board);
					break;
				}else {
					break;
				}

			default:
				end=this.askMove(board);
			}
		}
	}
	
	
	/* =========================================== */

	private void askPut(Board board) {
		int pos = AskAndDisplay.getRandomUnusedCellId(board);
		board.putCell(pos, this);
		this.lastAction=pos;
		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" put a piece in "+pos+"\n";
	}

	/* ------------------------------------------- */

	private boolean askBlock(Board board) {
		int nb;
		int nbRand;
		do {
			nbRand = random.nextInt(board.getSize())+1;
			nb=AskAndDisplay.getACellNextToAnother(board,nbRand);	
		}while(!board.putBlock(nbRand, nb));

		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" block the link between "+nbRand+" et "+nb+"\n";
		this.block--;
		return true;
	}

	/* ------------------------------------------- */

	private boolean askTrap(Board board) {
		int nbRand = random.nextInt(board.getSize())+1;
		if (board.searchCell(nbRand).getState()==null) {
			board.putTrap(nbRand, this);
			
			Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" put a piece in "+nbRand+"\n";
			this.trap--;
			return true;
		}else {
			return false;
		}
	}

	/* ------------------------------------------- */

	private boolean askMove(Board board) {
		int nbRand,nb,idx=0;
		
		do {
			nbRand = random.nextInt(board.getSize())+1;
			idx++;
		} while (board.searchCell(nbRand).getState()!=this && idx < 100);
		
		idx=0;
		
		do {
			nb=AskAndDisplay.getACellNextToAnother(board,nbRand);
			idx++;
		} while (board.searchCell(nb).getState()!=null && idx < 10);
		
		if (idx>10) {
			return false;
		}
		
		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" bouge "+nbRand+" vers "+nb+"\n";
		this.lastAction=nb;
		board.moveCell(nbRand, nb);
		
		return true;
	}

	/* ------------------------------------------- */

	public String toString() {
		return "BOT#" + name + "#" + color + "#" + trap + "#" + nbCells+ "#" + block;
	}

	/* =========================================== */

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		DumbAI p3 = new DumbAI();
		DumbAI p2 = new DumbAI();
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p3);
		players.add(p2);
		Board board = new Board();
		int idx=0;
		
		while (true) {
			p3.doPlayer(board,players);
			p2.doPlayer(board,players);
		System.out.println(AskAndDisplay.displayBoard(board));
			idx++;
			
		}
	}



}
