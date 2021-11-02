package projet;

/**The class that create one Cell with all his parameter*/
public class Cell {
	/**The state of the cell, it can have 3 state: null when nobody own the cell or one of the two players that own the cell*/
	private Player state;
	/**An int that permit to increment the id*/
	protected static int idInit = 1;
	/**The position in the layer of the Cell*/
	private int pos;
	/**The layer of the Cell*/
	private int layer;
	/**The id to identify the cell*/ 
	private int id;
	
	private boolean trapped = false;
	
	private Player trappedBy = null;

	/** The constructor of Cell
	 * @param state The state of the Cell
	 * @param The position in the layer
	 * @param The layer in which the cell is
	 * */
	public Cell(Player state, int pos, int layer, boolean trapped, Player trappedBy) {
		this.state = state;
		this.pos = pos;
		this.layer = layer;
		this.trapped=trapped;
		this.trappedBy = trappedBy;
		
		this.id = idInit;
		idInit++;
	}

	/**A less precise constructor for Cell
	 * @param The position in the layer
	 * @param The layer in which the cell is
	 */
	public Cell(int pos, int layer) {
		this(null,pos,layer,false,null);
	}

	/** Change the state of the Cell
	 * @param p The new state
	 * */
	public void setState(Player p) {
		this.state = p;
	}
	/** Change if the cell is trapped or not
	 * @param trapped The new state
	 * */
	public void setTrapped(boolean trapped) {
		this.trapped = trapped;
	}
	/** Get if the cell is trapped*/
	public boolean getTrapped() {
		return this.trapped;
	}
	/** Get by whom cell is trapped*/
	public Player getTrappedBy() {
		return trappedBy;
	}
	/** Change name of the player whom set the trap
	 * @param trappedBy The name of the player whom set the trap
	 * */
	public void setTrappedBy(Player trappedBy) {
		this.trappedBy = trappedBy;
	}

	/** Get the id of the Cell*/
	public int getId() {
		return this.id;
	}
	/** Change the id of the Cell
	 * @param id The new id
	 * */
	public void setId(int id) {
		this.id = id;
	}
	/** Get the	position*/
	public int getPos() {
		return pos;
	}
	/** Change the position of the Cell
	 * @param pos The new pos
	 * */
	public void setPos(int pos) {
		this.pos = pos;
	}
	/** Get the layer*/
	public int getLayer() {
		return layer;
	}
	/** Change the layer of the Cell
	 * @param layer The new layer
	 * */
	public void setLayer(int layer) {
		this.layer = layer;
	}
	/** Get the state*/
	public Player getState() {
		return state;
	}
	/**Look if the Cell given in parameter is next to the Cell
	 * @param b The board
	 * @param c The Cell that look if it's next to the the cell
	 * @return A boolean, true if the Cell is next to the other one, false if it's not the case*/
	public boolean isNextTo(Board b, Cell c){
		int nb = 0;
		if(b.getType()==Shape.SQUARE) {
			nb = 8;
		}else if(b.getType()==Shape.TRIANGLE){
			nb = 6;
		}
		if((this.getPos()+1)%2==0) {
			if(this.getLayer()!= b.getNbLayer()-1 && this.getLayer()!=0) {
				if(b.getBoard()[this.getLayer()+1][this.getPos()] == c || b.getBoard()[this.getLayer()-1][this.getPos()] == c ) {
					return true;
				}
			}else if(this.getLayer()== b.getNbLayer()-1){
				if(b.getBoard()[this.getLayer()][(this.getPos()+1)%(nb)] == c || b.getBoard()[this.getLayer()][(this.getPos()+1)%(nb)] == c || b.getBoard()[this.getLayer()][((this.getPos()-1)%(nb)+nb)%nb] == c) {
					return true;
				}else if(b.getBoard()[this.getLayer()-1][this.getPos()] == c ) {
					return true;
				}
			}else if(this.getLayer()==0){
				if(b.getBoard()[this.getLayer()+1][this.getPos()] == c) {
					return true;
				}
			}
			if(b.getBoard()[this.getLayer()][(this.getPos()+1)%(nb)] == c || b.getBoard()[this.getLayer()][(this.getPos()+1)%(nb)] == c || b.getBoard()[this.getLayer()][((this.getPos()-1)%(nb)+nb)%nb] == c) {
				return true;
			}
		}else{
			if(b.getBoard()[this.getLayer()][(this.getPos()+1)%(nb)] == c || b.getBoard()[this.getLayer()][((this.getPos()-1)%(nb)+nb)%nb] == c) {
				return true;
			}if(this.getPos()-1==-1) {
				if(b.getBoard()[this.getLayer()][nb-1] == c) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public String toString() {
		String str=id + "#" + pos + "#" + layer + "#";
		if (state!=null) {
			str += state.getName()+ "#";
		}else {
			str += state+ "#";
		}
		return str += trapped + "#" + trappedBy;
	}


}
