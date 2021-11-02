package projet;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AskAndDisplay {
	private static Scanner scan = new Scanner(System.in);
	private static Random random = new Random();

	/* ------------------------------- */
	/**
	 * @return clear the console   
	 */
	protected static void clearScreen(){
		System.out.print("\033[H\033[2J");
	}

	/* ------------------------------- */
	/**
	 * @param the number of lines that need to be erased  
	 */
	protected static void clearNLines(int n){
		for (int i = 0; i < n; i++) {
			System.out.print("\033[A"); // Move up
			System.out.print("\033[2K"); // Erase line content
		}
	}
	/* ------------------------------- */
	/**wait for X ms  
	 * @param waiting time in ms   
	 */
	protected static void waitForXMs(int time){
		try {Thread.sleep(time);} catch (InterruptedException e) {}
	}

	/* ------------------------------- */
	/**
	 * @return the name the player has chosen 
	 */
	protected static String askName() {
		String name="";
		clearScreen();
		System.out.println("Player "+Player.getnPlayers()+", Choose your name => (8 letters max)");
		name=scan.nextLine();


		while(Player.getNames().contains(name)) {
			System.out.println("This name has already been chosen, you have to choose one that's not among => "+Player.getNames().toString());
			name=scan.nextLine();
			name=askValidName(name);
		}

		return name.trim();
	}

	/* ------------------------------- */
	/**
	 * @param String the first name the player has chosen
	 * @return String the name chosen by the player
	 */
	protected static String askValidName(String str) {

		String answer=str;

		while (answer.length()<1 || answer.length()>12) {

			System.out.println("Can can only type name with 1 to 12 characters");

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {}

			AskAndDisplay.clearNLines(2);
			answer = scan.nextLine();
		}

		return answer;
	}

	/* ------------------------------- */
	/**
	 * @return the color the player has choosen  
	 */
	protected static Color askColor() {
		String color="_";
		clearScreen();
		System.out.println("Now, Choose your color => "+Color.ToString());

		color=scan.nextLine();
		color=askValidColor(color);

		while (Color.valueOf(color.toUpperCase().trim()).isUsed()) {
			System.out.println("This color has already been chosen, you have to choose among => "+Color.ToString());
			color=scan.nextLine();
			color=askValidColor(color);
		}

		Color.valueOf(color.toUpperCase().trim()).use();
		AskAndDisplay.clearScreen();
		return Color.valueOf(color.toUpperCase().trim());

	}

	/* ------------------------------- */
	/**
	 * @param c A String that need to be tested
	 * @return a valid color
	 */
	protected static String askValidColor(String c) {
		String color=c;
		while (!isInEnum(color,"color")) {
			System.out.println("\""+color+"\""+" color does not exist in the list ! => "+Color.ToString());
			AskAndDisplay.waitForXMs(1500);
			AskAndDisplay.clearNLines(2);
			color=scan.nextLine();
		}
		clearScreen();
		return color;
	}

	/* ------------------------------- */
	/**
	 * @param c A String that need to be tested
	 * @return a valid shape
	 */
	protected static String askValidShape(String c) {
		String shape=c;
		while (!isInEnum(shape,"shape")) {
			System.out.println("\""+shape+"\""+" does not exist in the list ! => "+Shape.ToString());
			AskAndDisplay.waitForXMs(1500);
			AskAndDisplay.clearNLines(2);
			shape=scan.nextLine();
		}
		AskAndDisplay.clearScreen();
		return shape.toUpperCase();
	}

	/* ------------------------------- */
	/**
	 * @param str A String that need to be tested
	 * @return true if the name is in the enum 
	 */
	protected static boolean isInEnum(String str, String type) {
		if (type=="color") {
			for (Color c : Color.values()) {
				if (c.name().equals(str.toUpperCase())) {
					return true;
				}
			}
			return false;
		}else {
			for (Shape s : Shape.values()) {
				if (s.name().equals(str.toUpperCase())) {
					return true;
				}
			}
			return false;
		}



	}

	/* ------------------------------- */
	/**
	 * @param min/max => min/max value possible
	 * @return return a valid integer
	 */
	protected static int askValidNumber(int min,int max) {
		int nb=-999;
		boolean integer = false;

		while (!integer) {
			try {
				nb = Integer.parseInt(scan.nextLine());
				if (nb < min || nb > max) {
					throw new NumberFormatException();
				}
				integer = true;
			} catch (NumberFormatException e) {
				System.out.println("You can only type numbers between "+min+" and "+max);
				AskAndDisplay.waitForXMs(1500);
				clearNLines(2);
			}
		}
		return nb;
	}

	/* ------------------------------- */
	/**
	 * @param void
	 * @return return a String containing yes or no
	 */
	protected static boolean askYesOrNo() {

		System.out.println(" => YES/NO\n");
		String answer = scan.nextLine();

		while (!(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no"))) {

			System.out.println("You can only answer by YES or NO");

			AskAndDisplay.waitForXMs(1500);

			clearNLines(2);
			answer = scan.nextLine();
		}
		clearNLines(2);
		return answer.equalsIgnoreCase("yes");
	}

	/* ------------------------------- */
	/**
	 * @param void
	 * @return return the id of an unused cell choosen by the player
	 */
	protected static int askUnusedCellId(Board board) {

		int answer = askValidNumber(-1,board.getSize());
		if (answer==-1) {
			return -1;
		}
		while (board.searchCell(answer).getState()!=null) {

			System.out.println("This cell is already used, choose another one !");

			AskAndDisplay.waitForXMs(1500);

			clearNLines(2);
			answer = askValidNumber(0,board.getSize());
		}

		return answer;
	}
	/* ------------------------------- */
	/**
	 * @param void
	 * @return return the id of an unused cell choosen
	 */
	protected static int getRandomUnusedCellId(Board board) {

		int answer = random.nextInt(board.getSize())+1;

		while (board.searchCell(answer).getState()!=null) {

			answer = random.nextInt(board.getSize())+1;
		}

		return answer;
	}
	/* ------------------------------- */
	/**
	 * @param board and player
	 * @return return the id of one of the player's cells
	 */
	protected static int askForOneOfYourCells (Board board,Player player) {

		int answer = askValidNumber(-1,board.getSize());

		if (answer==-1) {
			return -1;
		}
		while (board.searchCell(answer).getState()!=player) {

			System.out.println("This is not one of your cells, choose another one !");

			AskAndDisplay.waitForXMs(1500);

			clearNLines(2);
			answer = askValidNumber(-1,board.getSize());
			if (answer==-1) {
				return -1;
			}
		}
		clearNLines(2);
		return answer;
	}
	/* ------------------------------- */
	/**
	 * @param board and idCell
	 * @return returns the id of one of the cells next to the one specified
	 */
	protected static int askForACellNextToAnother (Board board, int idCell) {

		int answer = askValidNumber(-1,board.getSize());

		if (answer==-1) {
			return -1;
		}

		while (!board.searchCell(idCell).isNextTo(board, board.searchCell(answer))) {

			System.out.println("This cell is not next to the other one !");

			AskAndDisplay.waitForXMs(1500);

			clearNLines(2);
			answer = askValidNumber(-1,board.getSize());
			if (answer==-1) {
				return -1;
			}
		}
		clearNLines(2);
		return answer;
	}
	/* ------------------------------- */
	/**
	 * @param board and idCell
	 * @return returns the id of one of the cells next to the one specified
	 */
	protected static int getACellNextToAnother (Board board, int idCell) {
		List<Integer> nextTo = new ArrayList<Integer>();

		for (int i = 1; i < board.getSize()+1; i++) {
			if (board.searchCell(idCell).isNextTo(board, board.searchCell(i))) {
				nextTo.add(i);
			}
		}

		Collections.shuffle(nextTo);

		return nextTo.get(0);
	}
	/* ------------------------------- */
	/**displays the start-up screen
	 * @param path to saves
	 * @return save created or chosen by the player 
	 * @throws FileNotFoundException 
	 */
	protected static Save startGame () throws FileNotFoundException {
		Save save=null;
		while(save==null) {
			clearScreen();
			System.out.println("\n\t\t\tWelcome to LineUp 3");
			System.out.println("\n\nType 0 to start a new game\t|\tType 1 to load a save\n\n");
			System.out.println("\t\t     Type 2 for a quick game\n\n");

			switch (askValidNumber(0,2)) {
			case 0:
				save = newGame();
				break;
			case 1:
				save = Save.dispalyAndChooseSaves(Main.PATHSAVE);
				break;
			case 2:
				save = Save.quickGame(Main.PATHCONFIG+"quickGame.txt");
				break;
			}
		}

		return save;
	}
	/* ------------------------------- */
	/**
	 * @param void
	 * @return a save created by the player
	 */
	protected static Save newGame () {
		clearScreen();
		Save save = new Save();
		int nbPlayer, nbLayer, timeBlocked;
		Shape shape;

		System.out.println("Which shape do you want for the board ? => "+ Shape.ToString()+"\n");

		shape = Shape.valueOf(askValidShape(scan.nextLine()));

		System.out.println("How many layer do you want for the board ? It will impact the maximal number of player => between 3 and 9\n");

		nbLayer = askValidNumber(3,9);
		clearNLines(3);

		System.out.println("For how long (in rounds) should the links remain blocked => between 0 and 5\n");

		timeBlocked = askValidNumber(0,5);
		clearNLines(3);

		save.board = new Board(nbLayer, shape, timeBlocked, new ArrayList<BlockedLink>());

		//	--------------------

		System.out.println("How many players do you want ? => between 0 and "+(nbLayer-1)+"\n");

		nbPlayer=askValidNumber(0,nbLayer-1);

		for (int i = 0; i < nbPlayer; i++) {
			save.players.add(new Player());
		}



		if (nbPlayer>0) {
			System.out.println("How many AI do you want ? => between 0 and "+(nbLayer-nbPlayer-1)+"\n");
			nbPlayer=askValidNumber(0,nbLayer-nbPlayer);
		}else {
			System.out.println("How many AI do you want ? => between 1 and "+(nbLayer-nbPlayer-1)+"\n");
			nbPlayer=askValidNumber(1,nbLayer-nbPlayer);
		}


		for (int i = 0; i < nbPlayer; i++) {
			clearScreen();
			save.players.add(new DumbAI());
		}

		//	--------------------

		return save;
	}

		/**@return A String of the player's colour if the cell belongs to him otherwise it's white
		 * @param layer the layer where the cell is
		 * @param pos the position where the cell is
		 * @param zero false if the number is over 10, false if it's not
		 * @param b The board
		 * 
		 **/
	static private String printColored(int layer, int pos, boolean zero, Board b) {
		if (b.board[layer][pos].getState()!=null) {
			if(b.getType() == Shape.TRIANGLE) {
				if(zero) {
					return ""+b.board[layer][pos].getState().getColor().ansiColor+"00"+b.board[layer][pos].getId()+(char)27+"[0m";
				}else if(b.board[layer][pos].getId()<100){
					return ""+b.board[layer][pos].getState().getColor().ansiColor+"0"+b.board[layer][pos].getId()+(char)27+"[0m";
				}else {
					return ""+b.board[layer][pos].getState().getColor().ansiColor+""+b.board[layer][pos].getId()+(char)27+"[0m";
				}
			}else if (zero) {
				return ""+b.board[layer][pos].getState().getColor().ansiColor+"0"+b.board[layer][pos].getId()+(char)27+"[0m";
			}else {
				return ""+b.board[layer][pos].getState().getColor().ansiColor+b.board[layer][pos].getId()+(char)27+"[0m";
			}

		}else {
			if(b.getType() == Shape.TRIANGLE) {
				if(zero) {
					return "00"+b.board[layer][pos].getId();			
				}else if(b.board[layer][pos].getId()<100){
					return "0"+b.board[layer][pos].getId();			
				}else {
					return ""+b.board[layer][pos].getId();			
				}
			}else if (zero) {
				return "0"+b.board[layer][pos].getId();
			}else {
				return ""+b.board[layer][pos].getId();
			}
		}
	}

	/**@return A String with the space and the pipe for the Square
	 * @param spaceE the number of space to put in the string 
	 **/
	static private String addSpaceEL(int spaceE) {
		String stock = "";
		for(int E = 0; E<spaceE; E++) {
			stock += "│";
			stock += "   ";
		}
		return stock;
	}
	static private String addSpaceER(int spaceE) {
		String stock = "";
		for(int E = 1; E<spaceE; E++) {
			stock += "   ";
			stock += "│";
		}
		return stock;
	}
	/**@return A String with the dashes required for the Square
	 * @param spaceI the number of dashes to put in the string 
	 **/
	static private String addSpaceI(int spaceI) {
		String stock = "";
		for(int I = 0; I<spaceI; I++) {
			stock += "────";
		}
		return stock;
	}
	/**@return A String with the number colored
	 * @param c the Cell that we want to color
	 * @param b the board
	 **/
	static private String addNumbers(Cell c, Board b) {
		String stock = "";
		if(c.getId()<10) {
			stock += printColored(c.getLayer(),c.getPos(),true,b);
		}else {
			stock += printColored(c.getLayer(),c.getPos(),false,b);
		}
		return stock;
	}
	
	/**@return A String with the character required to do the line spacing
	 * @param spaceE the number of spaces and pipe
	 * @param b the board
	 **/
	static private String interLines(int spaceE, boolean f, Board b) {
		String stock = "";
		stock += addSpaceEL(spaceE);
		stock+="│";
		for(int I = 0; I<b.getNbLayer()*4+1-(spaceE*4); I++) {
			stock += " ";
		}
		if(f) {
			stock+=" ";
		}else {
			stock+="│";
		}
		for(int I = 0; I<b.getNbLayer()*4+1-(spaceE*4); I++) {
			stock += " ";
		}
		stock+="│";
		for(int E = 0; E<spaceE; E++) {
			stock += "   ";
			stock += "│";
		}
		return stock;
	}
	/**@return A String with the number for the center of the triangle
	 * @param b the board
	 * @param p An int equal to 5 or 1
	 **/
	static private String numbersTriangle(Board b, int p) {
		String stock = "";
		for(int i = b.getNbLayer()-1; i>=0; i--) {
			stock += addNumbers(b.board[i][5],b);
			if(i!=0) {
				stock+="──";
			}
		}
		return stock;
	}
	
	/**@return A String with the spaces for the triangle
	 * @param spaceE The number of spaces
	 **/
	static private String spaceTriangle(int spaceE){
		String stock = "";
		for(int e = 0; e<spaceE; e++) {
			stock+=" ";
		}
		return stock;
	}
	
	static private String slashTriangle(char c1,char c2,int layer,int slash) {
		String stock = "";
		for(int i = 0; i<slash; i++) {
			if(layer!=0) {
				if(i!=slash-1) {
					stock+=c1+"   "+c2;
				}
			}
		}
		return stock;
	}
	
	static private String slashTriangleBis(char c,int slash) {
		String stock = "";
		for(int i = 0; i<slash; i++) {
			if(i==slash-1) {
				stock+=""+c;
			}else {
				stock+=c+"    ";
			}
		}
		return stock;
	}
	
	static private String slashTriangleT(char c1, char c2, int slash) {
		String stock = "";
		for(int i = 0; i<slash; i++) {
			stock+=c1+"   "+c2;
		}
		return stock;
	}
	


	/* ------------------------------- */
	/**Display the board
	 * @return The String 
	 */
	static public String displayBoard(Board b) {
		if(b.getType()==Shape.SQUARE) {
			return displaySquare(b);
		}else if(b.getType()==Shape.TRIANGLE) {
			return displayTriangle(b);
		}else {
			return "";
		}
	}
	
	static private String displayTriangle(Board b) {
		String stock = "";
		int spaceE = (b.getNbLayer()*5)-2+b.getNbLayer()*3+1;
		int spaceI = 3;
		int slash = 1;
		int spaceENb = (b.getNbLayer()*5)-1+b.getNbLayer()*3+1;
		for(int layer = 0; layer<b.getNbLayer(); layer++) {
			stock += spaceTriangle(spaceENb);
			stock += slashTriangle('/',' ',layer,slash);
			stock+=addNumbers(b.board[b.getNbLayer()-layer-1][0],b);
			stock += slashTriangle(' ','\\',layer,slash);
			stock+="\n";
			spaceENb-=5;
			for(int nbE = 0; nbE<4; nbE++) {
				if(!(layer==b.getNbLayer() && nbE==3)) {
					stock += spaceTriangle(spaceE);
					stock += slashTriangleBis('/',slash);
					stock += spaceTriangle(spaceI);
					stock += slashTriangleBis('\\',slash);
				}
				if(!(layer==b.getNbLayer() && nbE==3)) {
					stock+="\n";
				}
				spaceE--;
				spaceI+=2;
			}
			spaceE--;
			slash++;
			if(layer!=b.getNbLayer()-1) {
				spaceI=3;
			}
		}
		stock += spaceTriangle(spaceE);
		stock+=" ";
		stock += numbersTriangle(b,5);
		stock +="       ";
		stock += numbersTriangle(b,1);
		stock += "\n";
		slash=b.getNbLayer();
		spaceI+=2;
		int spaceBNb = 3;
		for(int layer=0; layer<b.getNbLayer(); layer++) {
			for(int nbE=0; nbE<2; nbE++) {
				stock+=spaceTriangle(spaceE);
				stock += slashTriangleBis('/',slash);
				for(int i = 0; i<spaceI; i++) {
					if(i==spaceI/2 && layer!=0) {
						stock+="|";
					}else {
						stock+=" ";
					}
				}
				stock += slashTriangleBis('\\',slash);
				stock+="\n";
				spaceE--;
				spaceI+=2;
			}
			slash--;
			stock+=spaceTriangle(spaceE);
			stock += slashTriangleT('/', ' ', slash);
			for(int pos = 4; pos>=2;pos--) {
				stock += addNumbers(b.board[layer][pos],b);
				if(pos!=2) {
					stock+="─";
					for(int sB = 0; sB<spaceBNb;sB++) {
						stock+="─";
						if(sB==spaceBNb-1) {
							stock+="─";
						}
					}
				}

			}
			stock += slashTriangleT(' ', '\\', slash);
			spaceE--;
			spaceI+=12;
			spaceBNb += 8;
			stock+="\n";
		}
		return stock;
	}
	static private String displaySquare(Board b) {
		String stock = "";
		int spaceE = 0;
		int spaceI = b.getNbLayer();
		for(int layer = b.getNbLayer()-1; layer>=0;layer--){
			stock += addSpaceEL(spaceE);
			for(int pos = 0; pos<3; pos++) {
				if(pos!=b.getNbLayer()) {
					stock += addNumbers(b.board[layer][pos],b);
					if(pos!=2) {
						stock += addSpaceI(spaceI);
					}
				}else{
					stock += addNumbers(b.board[layer][pos],b);
				}
			}
			if(layer!=b.getNbLayer()-1) {
				stock += "  ";
				stock += "│";
			}

			stock += addSpaceER(spaceE);
			if(layer!=0) {
				stock += "\n";
				stock += interLines(spaceE,false,b);
			}else {
				stock += "\n";
				stock += interLines(spaceE,true,b);
			}
			stock += "\n";
			spaceE++;
			spaceI--;
		}
		for(int i = b.getNbLayer()-1; i>=0; i--) {
			stock += addNumbers(b.board[i][7],b);
			if(i!=0) {
				stock+="──";
			}
		}
		stock +="          ";
		for(int i = 0; i<b.getNbLayer(); i++) {
			stock += addNumbers(b.board[i][3],b);
			if(i!=b.getNbLayer()-1) {
				stock+="──";
			}
		}
		stock += "\n";
		for(int layer = 0; layer<b.getNbLayer();layer++){
			spaceE--;
			spaceI++;
			if(layer!=0) {
				stock += interLines(spaceE,false,b);
				stock += "\n";
			}else {
				stock += interLines(spaceE,true,b);
				stock += "\n";
			}
			stock += addSpaceEL(spaceE);

			for(int pos = 6; pos>=4; pos--) {
				if(pos!=4) {
					stock += addNumbers(b.board[layer][pos],b);
					if(pos!=4) {
						stock += addSpaceI(spaceI);
					}	
				}else {
					stock += addNumbers(b.board[layer][pos],b);
				}
			}

			if(layer!=b.getNbLayer()-1) {
				stock += "  ";
				stock += "│";
			}
			stock += addSpaceER(spaceE);

			stock += "\n";
		}
		return stock;
	}


	public static void main(String[] args) {
		Save game = Save.quickGame(Main.PATHCONFIG+"quickGame.txt");
		Board board = game.board;
		ArrayList<Player> players = game.players;
		while (true) {
			for (Player player : players) {
				player.doPlayer(board, players);
			}
			
		}
	}


}
