package projet;

public enum Color {
	RED((char)27+"[38;5;196m",false),
	PINK((char)27+"[38;5;205m",false),
	GREEN((char)27+"[38;5;40m",false),
	YELLOW((char)27+"[38;5;226m",false),
	BLUE((char)27+"[38;5;27m",false),
	PURPLE((char)27+"[38;5;127m",false),
	ORANGE((char)27+"[38;5;208m",false),
	BROWN((char)27+"[38;5;130m",false),
	CYAN((char)27+"[38;5;51m",false);

	public final String ansiColor;
	public boolean used;


	private Color(String ansiColor,boolean used) {
		this.ansiColor=ansiColor;
		this.used=used;
	}

	/**
	 * @return true
	 */
	public boolean isUsed() {
		return used;
	}

	public void use() {
		this.used=true;
	}
	
	public static Color getUnusedColor() {
		for (Color c : Color.values()) {
			if (!c.used) {
				c.use();
				return c;
			}
		}
		return null;
	}


	public static String ToString() {
		String str="";
		for (Color c : Color.values()) {
			if (!c.used) {
				str=str+c.ansiColor+c+" "+(char)27+"[0m";
			}
		}
		return str;
	}
}
