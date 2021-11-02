package projet;
/**Enum of the different shape possible for the board*/
public enum Shape {
	/**The triangle shape*/
	TRIANGLE(6), 
	/**The square shape*/
	SQUARE(8);

	int element;
	
	Shape(int element) {
		this.element = element;
	}

	public int getElement() {
		return element;
	}	

	public static String ToString() {
		String str="";
		for (Shape c : Shape.values()) {
			str += c+" ";
		}
		return str;
	}
}
