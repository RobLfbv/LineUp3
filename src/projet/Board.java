package projet;

import java.util.ArrayList;

/**The class that create the board on which we will play
 */
public class Board {
	/**An array compose of cells that represent the board
	 */
	protected Cell[][] board;
	/**An int that represent the number of layer in the board
	 */
	private int nbLayer;
	/**A Shape type that represent the type of shape the board will have
	 */
	private Shape type; 

	protected ArrayList<BlockedLink> blocked;

	private final int TIME_BLOCKED;


	/* ------------------------------- */
	/**Create a new instance of board
	 * @param nbLayer The number of layers wanted
	 * @param s The shape wanted
	 */
	public Board(int nbLayer, Shape s,int timeBlocked, ArrayList<BlockedLink> blocked) {
		this.nbLayer = nbLayer;
		this.type = s;
		this.TIME_BLOCKED=timeBlocked;
		this.blocked=blocked;
		this.board = new Cell[this.nbLayer][s.getElement()];
		this.initBoard();
	}
	/* ------------------------------- */
	/**Create a new instance of Board with standard parameters, a square shape and 3 layers
	 */
	public Board() {
		this(4,Shape.SQUARE,3,new ArrayList<BlockedLink>());
	}
	public Board(Shape s) {
		this(3,s,3,new ArrayList<BlockedLink>());
	}

	/* ------------------------------- */
	/**Found a cell from the board with its id
	 * @param id The id of the cell that need to be found in the board
	 * @return The Cell that correspond to the id
	 */
	public Cell searchCell(int id) {
		for(int pos = 0; pos<this.board[0].length; pos++) {
			for(int layer = 0; layer<this.board.length; layer++) {
				if(this.board[layer][pos].getId()==id) {
					return this.board[layer][pos];
				}
			}
		}
		return null;
	}

	/* ------------------------------- */
	/**Remove a cell from the board with its id
	 * @param id The id of the cell that need to be remove
	 * @return A boolean, true if the removal is a success, false if it's not
	 */
	public void removeCell(int id) {
		this.searchCell(id).getState().setnbCells(this.searchCell(id).getState().getnbCells()-1);
		this.searchCell(id).setState(null);
	}

	/* ------------------------------- */
	/**Exchange the position of two cell in the board
	 * @param id1 The id of the first Cell
	 * @param id2 The id of the second Cell
	 * @return A boolean, true if the exchange is a success, false if it's not
	 */
	protected boolean moveCell(int id1, int id2) {
		Cell stock1 = this.searchCell(id1);
		Cell stock2 = this.searchCell(id2);
		Cell stock3 = new Cell(stock1.getPos(), stock1.getLayer());
		stock3.setState(stock1.getState());

		boolean b = true;
		for(int i = 0; i<this.getBlocked().size();i++) {
			if((this.getBlocked().get(i).start==id1 && this.getBlocked().get(i).end==id2) || (this.getBlocked().get(i).end==id1 && this.getBlocked().get(i).start==id2)) {
				b = false;
			}
		}

		if(stock1!=stock2 && stock1!=null && stock2!=null && b) {
			stock1.setState(stock2.getState());
			stock2.setState(stock3.getState());
			this.checkTrapped(id2);
			return true;
		}
		return false;
	}

	/* ------------------------------- */
	/**Place to the position and the layer the Cell associated to the id
	 * @param id The id of the Cell that need to be placed
	 * @return A boolean, true if the placement is a success, false if it's not
	 */
	protected boolean putCell(int id,Player p) {
		Cell stock = this.searchCell(id);
		if(stock !=null) {
			stock.setState(p);
			this.board[stock.getLayer()][stock.getPos()] = stock;
			p.nbCells++;
			return true;
		}
		return false;
	}

	/* ------------------------------- */
	/**Do the initialization of the boards by placing every cell at the right spot
	 */
	protected void initBoard() {
		for(int pos = 0; pos<this.board[0].length; pos++) {
			for(int layer = 0; layer<this.board.length; layer++) {
				this.board[layer][pos] = new Cell(pos,layer);
			}
		}
		Cell.idInit=1;
	}
	/* ------------------------------- */
	/**@return The number of layers of the board
	 */
	public int getNbLayer() {
		return nbLayer;
	}
	/* ------------------------------- */
	/**@return The board
	 */
	public Cell[][] getBoard() {
		return board;
	}
	/* ------------------------------- */
	/**
	 * @return the type
	 */
	public Shape getType() {
		return type;
	}
	/* ------------------------------- */
	/**
	 * @return the tIME_BLOCKED
	 */
	public int getTIME_BLOCKED() {
		return TIME_BLOCKED;
	}
	/* ------------------------------- */
	/**
	 * @return the blocked
	 */
	public ArrayList<BlockedLink> getBlocked() {
		return blocked;
	}
	/* ------------------------------- */

	/* ------------------------------- */
	/** @return the size of the board
	 */
	public int getSize() {
		return this.getNbLayer() * this.type.getElement();
	}

	/* ------------------------------- */

	public boolean putBlock(int cell1, int cell2) {
		return blocked.add(new BlockedLink(cell1, cell2, TIME_BLOCKED));
	}

	/* ------------------------------- */

	public boolean isBlocked(int cell1, int cell2) {
		for (BlockedLink block : blocked) {
			if ((block.start==cell1 && block.end==cell2)||(block.start==cell2 && block.end==cell1)) {
				return true;
			}
		}
		return false;
	}

	/* ------------------------------- */

	public void putTrap(int id, Player player) {
		this.searchCell(id).setTrapped(true);
		this.searchCell(id).setTrappedBy(player);
		player.setTrap(0);
	}

	/* ------------------------------- */

	public void checkTrapped(int id) {

		if (this.searchCell(id).getTrapped() && (this.searchCell(id).getState()!=null || this.searchCell(id).getState()!=this.searchCell(id).getTrappedBy())) {
			System.out.println(this.searchCell(id).getState().getName()+" a sauté sur un piege en "+this.searchCell(id).getId());
			this.searchCell(id).getState().setTrap(1);
			this.moveCell(this.searchCell(id).getId(),AskAndDisplay.getRandomUnusedCellId(this));//==============================================================================================================================================================
			this.searchCell(id).setTrapped(false);
			this.searchCell(id).setTrappedBy(null);
		}

	}

	/* ------------------------------- */

	public String toSave() {
		String board=""+this.getNbLayer()+"#"+this.getType()+"#"+this.getTIME_BLOCKED()+"#"+BlockedLink.getBlockedasString(blocked)+"\nSETTINGS_END\n";

		for(int pos = 0; pos<this.board[0].length; pos++) {
			for(int layer = 0; layer<this.board.length; layer++) {
				board += this.board[layer][pos].toString()+"\n";
			}
		}
		return board;
	}

	/* ------------------------------- */

	public boolean isFinish(int id) {
		Cell c = this.searchCell(id);
		int nb = this.getType().getElement();
		if((c.getPos()+1)%2==0 && c.getState()!=null) {
			if((c.getState()==this.board[c.getLayer()][(((c.getPos()-1)%nb)+nb)%nb].getState() && c.getState()==this.board[c.getLayer()][(((c.getPos()+1)%nb)+nb)%nb].getState())){
				return true;
			}
			if(this.getNbLayer()-(c.getLayer()+1)>1 && c.getState()==this.board[c.getLayer()+1][c.getPos()].getState() && c.getState()==this.board[c.getLayer()+2][c.getPos()].getState()){
				return true;
			}if((c.getLayer()+1)>2 && c.getState()==this.board[c.getLayer()-1][c.getPos()].getState() && c.getState()==this.board[c.getLayer()-2][c.getPos()].getState()) {
				return true;
			}				if(c.getState()==this.board[(((c.getPos()-1)%nb)+nb)%nb][c.getPos()].getState() && c.getState()==this.board[(((c.getPos()+1)%nb)+nb)%nb][c.getPos()].getState()) {
				return true;
			}
		}else if(c.getState()!=null){
			if((c.getState()==this.board[c.getLayer()][(((c.getPos()+1)%nb)+nb)%nb].getState() && c.getState()==this.board[c.getLayer()][(((c.getPos()+2)%nb)+nb)%nb].getState())
					|| (c.getState()==this.board[c.getLayer()][(((c.getPos()-1)%nb)+nb)%nb].getState() && c.getState()==this.board[c.getLayer()][(((c.getPos()-2)%nb)+nb)%nb].getState())) {
				return true;
			}
		}
		return false;
	}



	/* =============================== */
	
	public int nbCell() {
		int res = 0;

		for(int i = 1; i < getSize()+1; i++ ) {
			if (searchCell(i).getState() != null  ) {
				res++;
			}
		}

		return res;
	}
}
