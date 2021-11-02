package projet;

import java.util.ArrayList;

public class Player {

	protected String name;
	protected Color color;
	protected int trap;
	protected static int nPlayers = 0;
	private static ArrayList<String> names = new ArrayList<String>();
	protected int block;
	protected int nbCells;
	protected int lastAction;

	/* =============================== */
	protected Player(String name, Color color, int trap, int block, int nbCells, int lastAction) {
		nPlayers++;
		this.name=name;
		this.color=color;
		this.trap=trap;
		this.block=block;
		this.nbCells=nbCells;
		this.lastAction=lastAction;
		names.add(name);
		color.use();
	}

	protected Player(String name, Color color) {
		this(name,color,1,1,0,-1);
	}


	/* =============================== */

	public Player() {
		this(AskAndDisplay.askName(),AskAndDisplay.askColor());
	}

	/* =============================== */
	/**displays a menu allowing the player to choose an action
	 * @param Board and ArrayList of players
	 * @return void
	 */

	public void doPlayer(Board board, ArrayList<Player> players) {
		boolean end=false;
		int i;

		while(!end) {
			AskAndDisplay.clearScreen();

			System.out.println("Summary of the last round\n");
			System.out.println(Main.lastActions);
			System.out.println("\n===================");

			System.out.println(AskAndDisplay.displayBoard(board));
			System.out.println("\n=================== \n");
			System.out.println("Trap : "+trap+" | Block : "+block+" | Number of cells : "+nbCells+"/"+3+" \n");
			System.out.println(this.getColor().ansiColor + this.getName() +(char)27+"[0m"+ ", choose your action");

			if (this.getnbCells()<3) {
				System.out.println("1. Put a piece");
				System.out.println("\n0. Save");
				System.out.println("\n=================== \n");
				i = AskAndDisplay.askValidNumber(0,1);

				switch (i) {
				case 0:
					Save.createSave(Main.PATHSAVE, board, players);
					break;
				case 1:
					end=this.askPut(board);
					break;
				}
			}else {
				System.out.println("1. Move a piece");
				System.out.println("2. Put a trap");
				System.out.println("3. Block a link");
				System.out.println("\n0. Save");
				System.out.println("\n=================== \n");
				i = AskAndDisplay.askValidNumber(0,3);

				switch (i) {
				case 0:
					Save.createSave(Main.PATHSAVE, board, players);
					break;
				case 1:
					end=this.askMove(board);
					break;
				case 2:
					end=this.askTrap(board);
					break;
				case 3:
					end=this.askBlock(board);
					break;
				}
			}

		}
	}

	/* =============================== */
	/**
	 * @param Board 
	 * @return where the player wants to place his piece, if he wants to  
	 */
	private boolean askPut(Board board) {
		AskAndDisplay.clearNLines(1);

		int pos;

		System.out.println("In which cell do you want to set your piece ? -1 to go back");
		pos=AskAndDisplay.askUnusedCellId(board);

		if (pos==-1) {
			return false;
		}

		board.putCell(pos, this);
		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" place pion en "+pos+"\n";
		this.lastAction=pos;
		return true;
	}

	/* ------------------------------- */
	/**
	 * @param Board 
	 * @return Where the player wants to move his piece, if he wants to  
	 */
	private boolean askMove(Board board) {
		AskAndDisplay.clearNLines(1);

		int[] posCells = new int[2];


		System.out.println("Which cell do you want to move ? -1 to go back");

		posCells[0]=AskAndDisplay.askForOneOfYourCells(board,this);

		if (posCells[0]==-1) {
			return false;
		}

		do {
			System.out.println("Where do you want to move "+posCells[0]+" ? -1 to go back");
			posCells[1]=AskAndDisplay.askForACellNextToAnother(board,posCells[0]);

			if (posCells[1]==-1) {
				return false;
			}

			if (board.searchCell(posCells[1]).getState()!=null) {
				System.out.println("This cell is already taken !");
				AskAndDisplay.waitForXMs(1500);
				AskAndDisplay.clearNLines(1);
			}

		}while (board.searchCell(posCells[1]).getState()!=null);


		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" bouge "+posCells[0]+" vers "+posCells[1]+"\n";
		board.moveCell(posCells[0],posCells[1]);
		this.lastAction=posCells[1];
		return true;

	}

	/* ------------------------------- */
	/**
	 * @param Board 
	 * @return Where the player wants to place his trap, if he wants to
	 */
	private boolean askTrap(Board board) {
		AskAndDisplay.clearNLines(1);
		int pos;

		if (this.trap==0) {
			System.out.println("You have no trap available !");
			AskAndDisplay.waitForXMs(1500);
			return false;
		}

		System.out.println("In which cell do you want to set your trap ?  Be careful, you only have one ! -1 to go back");

		pos = AskAndDisplay.askUnusedCellId(board);

		if (pos==-1) {
			return false;
		}

		board.putTrap(pos,this);
		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" put a trap in "+pos+"\n";
		return true;

	}

	/* ------------------------------- */
	/**
	 * @param Board 
	 * @return An array containing two cells locating a link
	 */						
	private boolean askBlock(Board board) { 
		AskAndDisplay.clearNLines(1);

		if (this.block==0) {
			System.out.println("You have no block available !");
			AskAndDisplay.waitForXMs(1500);
			return false;
		}

		int[] posCells = new int[2];
		boolean blocked;

		do {
			System.out.println("What is the number of one of the cells in the link ? -1 to go back");

			posCells[0]=AskAndDisplay.askValidNumber(0,board.getSize());

			if (posCells[0]==-1) {
				return false;
			}

			AskAndDisplay.clearNLines(2);
			System.out.println("What is the number of the other ? -1 to go back");

			posCells[1]=AskAndDisplay.askForACellNextToAnother(board,posCells[0]);

			if (posCells[1]==-1) {
				return false;
			}

			blocked = board.isBlocked(posCells[0],posCells[1]);
			if (blocked) {
				System.out.println("This link has already been blocked");
				AskAndDisplay.waitForXMs(1500);
			}
		} while (blocked);

		board.putBlock(posCells[0],posCells[1]);	
		this.block--;
		Main.lastActions+=this.getColor().ansiColor + this.getName() +(char)27+"[0m"+" block the link between "+posCells[0]+" and "+posCells[1]+"\n";
		return true;
	}

	/* ------------------------------- */
	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/* ------------------------------- */
	/**
	 * @return the color
	 */
	protected Color getColor() {
		return color;
	}

	/* ------------------------------- */
	/**
	 * @return the trap
	 */
	public int getTrap() {
		return trap;
	}

	/* ------------------------------- */
	/**
	 * @param set trap
	 */
	public void setTrap(int trap) {
		this.trap = trap;
	}

	/* ------------------------------- */
	/**
	 * @param set nbCells
	 */
	public void setnbCells(int nbCells) {
		this.nbCells = nbCells;
	}

	/* ------------------------------- */
	/**
	 * @return the nbCells
	 */
	public int getnbCells() {
		return nbCells;
	}

	/* ------------------------------- */
	/**
	 * @return the nPlayers
	 */
	public static int getnPlayers() {
		return nPlayers;
	}

	/* ------------------------------- */
	/**
	 * @return the names
	 */
	public static ArrayList<String> getNames() {
		return names;
	}

	/* ------------------------------- */
	/**
	 * @return a String that can be understood by the save class 
	 */
	public String toSave() {
		return "HUMAN#" + name + "#" + color + "#" + trap + "#" + block +  "#" +nbCells + "#" + lastAction;
	}

	/* =============================== */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* =============================== */

	public static void main(String[] args) {
		Player p3 = new Player("Baptiste",Color.ORANGE);
		DumbAI p2 = new DumbAI();
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p3);
		players.add(p2);
		Board board = new Board();

		System.out.println("toto");

		while (true) {
			//	p3.doPlayer(board,players);
			p2.doPlayer(board,players);

		}
	}




}
