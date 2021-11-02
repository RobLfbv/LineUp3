package projet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class boardTest {

	@Test
	void testIsFinish() {
		Board board = 	new Board(4, Shape.SQUARE,1, null);

		Player p1 = new Player("toto",Color.RED);
		board.putCell(1, p1);
		board.putCell(29, p1);
		board.putCell(25, p1);
		assertTrue(board.isFinish(1));
		assertTrue(board.isFinish(29));
		assertTrue(board.isFinish(25));
		assertFalse(board.isFinish(5));
		assertFalse(board.isFinish(9));
		assertFalse(board.isFinish(13));
		board.removeCell(1);
		board.removeCell(29);
		board.removeCell(25);
		
		board.putCell(9, p1);
		board.putCell(13, p1);
		board.putCell(17, p1);
		assertTrue(board.isFinish(9));
		assertTrue(board.isFinish(13));
		assertTrue(board.isFinish(17));
		assertFalse(board.isFinish(1));
		assertFalse(board.isFinish(7));
		assertFalse(board.isFinish(10));
		
		board.putCell(1, p1);
		board.putCell(5, p1);
		board.putCell(9, p1);
		assertTrue(board.isFinish(9));
		assertTrue(board.isFinish(5));
		assertTrue(board.isFinish(1));

		board.putCell(2, p1);
		board.putCell(6, p1);
		board.putCell(10, p1);
		assertTrue(board.isFinish(2));
		assertTrue(board.isFinish(6));
		assertTrue(board.isFinish(10));
		
		board.putCell(8, p1);
		board.putCell(6, p1);
		board.putCell(7, p1);
		assertTrue(board.isFinish(8));
		assertTrue(board.isFinish(6));
		assertTrue(board.isFinish(7));
	}

}
