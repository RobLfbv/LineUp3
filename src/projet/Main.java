package projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {	
	public final static String PATHSAVE =  "." + File.separator +  "Saves" + File.separator;
	public final static String PATHCONFIG = "." + File.separator + "Config" + File.separator;
	//public final static String PATHCONFIG = System.getProperty("user.dir") + File.separator + "compil" +File.separator +"Config" + File.separator;
	public final static String PATH = System.getProperty("user.dir");
	public static String lastActions = "";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		Save game = AskAndDisplay.startGame();
		Board board = game.board;
		ArrayList<Player> players = game.players;
		String[] split;
		boolean end=false;
		int turn = 0;
		do {
			for (Player player : players) {
				System.out.println("?");
				System.out.println(player.getName()+player.lastAction);
				player.doPlayer(board, players);
				
				end=board.isFinish(player.lastAction);
				if (end) {break;}
			}
			split = lastActions.split("\\n");
			lastActions="";
			for (int i = split.length-1; i > split.length-1-players.size(); i--) {
				lastActions=split[i]+"\n"+lastActions;
			}
			for(int i = 0; i<board.getBlocked().size(); i++) {
                System.out.println("There is a block from " + board.getBlocked().get(i).start + " to " +board.getBlocked().get(i).end + " for "+board.getBlocked().get(i).duration+" turn");
            }
			turn++;
		} while (!end);
		
		System.out.println("GG! you've win in " + (turn/players.size()+1) + " actions!");
	}
}
