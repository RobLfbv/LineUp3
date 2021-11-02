package projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Save {
	protected Board board; 
	protected ArrayList<Player> players;
	private static Scanner scan = new Scanner(System.in);

	public Save(String path) throws FileNotFoundException {
		players=restorePlayers(path);
		board=restoreBoard(path,players);
	}
	public Save() {
		players=new ArrayList<Player>();
		board=new Board();
	}
	public Save(ArrayList<Player> players,Board board) {
		this.players=players;
		this.board=board;
	}
	/* ================================ */
	protected static void createSave(String path,Board board, ArrayList<Player> players) {
		AskAndDisplay.clearNLines(1);
		String fileName;
		
		try {
			AskAndDisplay.clearScreen();
			
			String[] pathSaves = Save.listAllFiles(Main.PATHSAVE);
			for (int i = 0; i < pathSaves.length; i++) {
				System.out.println(i+". "+pathSaves[i]);
			}
			System.out.println("\n-------------------\n");
			System.out.println("Choose a new name for the save\n");
			
			fileName = AskAndDisplay.askValidName(scan.nextLine());
			
			
			while (new File(path+fileName+".txt").exists()){
				AskAndDisplay.clearNLines(1);
				System.out.println(fileName+".txt already exist, do you want to overwrite it ?\n");
				if (AskAndDisplay.askYesOrNo()) {
					break;
				}else {
					AskAndDisplay.clearNLines(2);
					fileName = AskAndDisplay.askValidName(scan.nextLine());
				}
				
			}
			
			
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
		System.exit(0);
	}
	/* ================================ */
	
	protected static boolean createSave(String path,Board board, ArrayList<Player> players, String fileName) {		
		try {			

			FileWriter mySave = new FileWriter(path+fileName+".txt");
			
			for (Player player : players) {
				mySave.write(player.toSave()+"\n");
			}

			mySave.write("PLAYER_END\n");

			mySave.write(board.toSave());

			mySave.write("CELLS_END\n");

			mySave.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/* ------------------------------- */

	protected static boolean updateSave(String file,Board board, ArrayList<Player> players) {		
		try {			

			FileWriter mySave = new FileWriter(file);
			
			for (Player player : players) {
				mySave.write(player.toSave()+"\n");
			}

			mySave.write("PLAYER_END\n");

			mySave.write(board.toSave());

			mySave.write("CELLS_END\n");

			mySave.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/* ------------------------------- */

	private static Board restoreBoard(String path, ArrayList<Player> players) throws FileNotFoundException {

		File mySave = new File(path);
		Scanner myReader = new Scanner(mySave);
		String line="";

		/* Defile jusqu'à la ligne SETTINGS*/
		while(!line.equals("PLAYER_END")) {
			line=myReader.nextLine();
		}

		line=myReader.nextLine();

		String[] settingsData = line.split("#");

		Board newBoard = new Board(Integer.parseInt(settingsData[0]),Shape.valueOf(settingsData[1]),Integer.parseInt(settingsData[2]),convertStringToArrayList(settingsData[3],Integer.parseInt(settingsData[2])));

		while(!line.equals("SETTINGS_END")) {
			line=myReader.nextLine();
		}

		for(int pos = 0; pos<newBoard.board[0].length; pos++) {
			for(int layer = 0; layer<newBoard.board.length; layer++) {
				line=myReader.nextLine();
				newBoard.board[layer][pos] = restoreCell(line,players) ;
			}
		}

		myReader.close();
		return newBoard;

	}

	/* ------------------------------- */
	private static ArrayList<BlockedLink> convertStringToArrayList(String str, int duration){
		ArrayList<BlockedLink> arrayList = new ArrayList<BlockedLink>();

		if (str.equals("empty")) {
			return arrayList;
		}

		String[] splitStr = str.split("§");
		String[] tempSplit;

		for (int i = 0; i < splitStr.length; i++) {
			tempSplit = splitStr[i].split("°");
			arrayList.add(new BlockedLink (Integer.parseInt(tempSplit[0]),Integer.parseInt(tempSplit[1]), duration));
		}

		return arrayList;

	}
	/* ------------------------------- */

	private static Player restorePlayer(String playerLine) {

		if (playerLine.equals("PLAYER_END")) {
			return null;
		}

		String[] playerData = playerLine.split("#");
		Player player;
		if (playerData[0].equals("HUMAN")) {
			player = new Player(playerData[1],Color.valueOf(playerData[2]),Integer.parseInt(playerData[3]),Integer.parseInt(playerData[4]),Integer.parseInt(playerData[5]),Integer.parseInt(playerData[6]));
		}else {
			player = new DumbAI();
		}

		return player;

	}

	/* ------------------------------- */

	private static ArrayList<Player> restorePlayers(String path) throws FileNotFoundException {
		ArrayList<Player> players = new ArrayList<Player>();
		File mySave = new File(path);
		System.out.println(path);
		Scanner myReader = new Scanner(mySave);
		String line="";
		while(!line.equals("PLAYER_END")) {
			System.out.println("a");
			line=myReader.nextLine();
			if (!line.equals("PLAYER_END")) {
				players.add(restorePlayer(line));
			}
		}
		myReader.close();
		return players;

	}

	/* ------------------------------- */

	private static Cell restoreCell(String cellLine,ArrayList<Player> players) {
		String[] cellData = cellLine.split("#");
		return new Cell(associateNameWithPlayer(cellData[3],players), Integer.parseInt(cellData[1]), Integer.parseInt(cellData[2]), Boolean.parseBoolean(cellData[4]), associateNameWithPlayer(cellData[5],players));

	}

	/* ------------------------------- */

	private static Player associateNameWithPlayer(String name,ArrayList<Player> players) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	/* ------------------------------- */
	protected static String[] listAllFiles(String path) {
		return new File(path).list();
	}
	/* ------------------------------- */

	protected static Save dispalyAndChooseSaves(String path) throws FileNotFoundException {
		AskAndDisplay.clearNLines(1);
		String[] pathSaves = listAllFiles(path);

		if (pathSaves.length==0) {
			System.out.println("\nIl n'y a aucune savegarde disponible");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {}
			return null;
		}else {
			System.out.println("\n\nChoisissez votre sauvegarde en ecrivant son numéro\n-------------------");
			for (int i = 0; i < pathSaves.length; i++) {
				System.out.println(i+". "+pathSaves[i]);
			}
		}
		System.out.println("-------------------\n");

		return new Save(path+"/"+pathSaves[AskAndDisplay.askValidNumber(0,pathSaves.length-1)]);
	}

	/* ------------------------------- */
	protected static Save quickGame(String path) {
		File file = new File(path);
		int nbLayer, timeBlocked;
		Shape shape;
		String[] split;
		Save save = new Save();
		try {
			Scanner myReader = new Scanner(file);
			
			split = myReader.nextLine().split(" ");
			nbLayer = Integer.parseInt(split[1]);

			split = myReader.nextLine().split(" ");
			shape = Shape.valueOf(split[1]);	
			
			split = myReader.nextLine().split(" ");
			timeBlocked = Integer.parseInt(split[1]);

			save.board = new Board(nbLayer, shape, timeBlocked, new ArrayList<BlockedLink>());
			
			split = myReader.nextLine().split(" ");
			
			for (int i = 0; i < Integer.parseInt(split[1]); i++) {
				save.players.add(new Player("Player_"+Player.getnPlayers(),Color.getUnusedColor()));
				for (int j = 0; j < 3; j++) {
					save.board.putCell(AskAndDisplay.getRandomUnusedCellId(save.board), save.players.get(save.players.size()-1));
				}
			}
			split = myReader.nextLine().split(" ");
			for (int i = 0; i < Integer.parseInt(split[1]); i++) {
				save.players.add(new DumbAI());
				for (int j = 0; j < 3; j++) {
					save.board.putCell(AskAndDisplay.getRandomUnusedCellId(save.board), save.players.get(save.players.size()-1));
				}
			}
			
			myReader.close();
			
			return save;
			
		} catch (Exception e) {
			System.out.println("Default config error");
			e.printStackTrace();
		}
		
		return null;
	}
	/* ------------------------------- */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(AskAndDisplay.askValidNumber(1, 5));
	}
}
