package projet;

import java.util.ArrayList;

public class BlockedLink {
	int start, end, duration;
	
	public BlockedLink(int start, int end, int duration) {
		this.start=start;
		this.end=end;
		this.duration=duration;
	}
	
	/* ------------------------------- */

	protected void decrementBlocked(ArrayList<BlockedLink> blocked) {
		for (BlockedLink block : new ArrayList<BlockedLink>()) {
			if (block.duration>1) {
				block.duration--;
			}else {
				blocked.remove(block);
			}
		}
	}
	
	/* ------------------------------- */
	
	public String toSave() {
		return start+"°"+end+"°"+duration+"§";
	}
	/* ------------------------------- */
	/**
	 * @return the blocked as a String
	 */
	public static String getBlockedasString(ArrayList<BlockedLink> blocked) {
		if (blocked.isEmpty()) {
			return "empty";
		}

		String blockedStr="";

		for (BlockedLink link : blocked) {
			blockedStr += link.toSave();
		}

		return blockedStr;
	}
}
