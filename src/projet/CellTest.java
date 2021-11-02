package projet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CellTest {

	@Test
	void testIsNextTo() {
		Board board = 	new Board(5, Shape.TRIANGLE,1, null);
		assertTrue(board.searchCell(18).isNextTo(board,board.searchCell(17)));
		assertTrue(board.searchCell(17).isNextTo(board,board.searchCell(18)));

		assertTrue(board.searchCell(21).isNextTo(board,board.searchCell(16)));
		assertTrue(board.searchCell(16).isNextTo(board,board.searchCell(21)));
		assertTrue(board.searchCell(16).isNextTo(board,board.searchCell(11)));
		assertTrue(board.searchCell(11).isNextTo(board,board.searchCell(16)));
		assertTrue(board.searchCell(11).isNextTo(board,board.searchCell(6)));
		assertTrue(board.searchCell(6).isNextTo(board,board.searchCell(11)));
		assertTrue(board.searchCell(6).isNextTo(board,board.searchCell(1)));
		assertTrue(board.searchCell(1).isNextTo(board,board.searchCell(6)));
		assertTrue(board.searchCell(1).isNextTo(board,board.searchCell(26)));
		assertTrue(board.searchCell(26).isNextTo(board,board.searchCell(21)));
		assertTrue(board.searchCell(21).isNextTo(board,board.searchCell(26)));

		
		assertTrue(board.searchCell(22).isNextTo(board,board.searchCell(17)));
		assertTrue(board.searchCell(17).isNextTo(board,board.searchCell(22)));
		assertTrue(board.searchCell(17).isNextTo(board,board.searchCell(12)));
		assertTrue(board.searchCell(12).isNextTo(board,board.searchCell(17)));
		assertTrue(board.searchCell(12).isNextTo(board,board.searchCell(7)));
		assertTrue(board.searchCell(7).isNextTo(board,board.searchCell(12)));
		assertTrue(board.searchCell(7).isNextTo(board,board.searchCell(2)));
		assertTrue(board.searchCell(2).isNextTo(board,board.searchCell(7)));
		assertTrue(board.searchCell(2).isNextTo(board,board.searchCell(27)));
		assertTrue(board.searchCell(27).isNextTo(board,board.searchCell(2)));
		assertTrue(board.searchCell(27).isNextTo(board,board.searchCell(22)));
		assertTrue(board.searchCell(22).isNextTo(board,board.searchCell(27)));

		assertTrue(board.searchCell(25).isNextTo(board,board.searchCell(20)));
		assertTrue(board.searchCell(20).isNextTo(board,board.searchCell(25)));
		assertTrue(board.searchCell(20).isNextTo(board,board.searchCell(15)));
		assertTrue(board.searchCell(15).isNextTo(board,board.searchCell(20)));
		assertTrue(board.searchCell(15).isNextTo(board,board.searchCell(10)));
		assertTrue(board.searchCell(10).isNextTo(board,board.searchCell(15)));
		assertTrue(board.searchCell(10).isNextTo(board,board.searchCell(5)));
		assertTrue(board.searchCell(5).isNextTo(board,board.searchCell(10)));
		assertTrue(board.searchCell(5).isNextTo(board,board.searchCell(30)));
		assertTrue(board.searchCell(30).isNextTo(board,board.searchCell(5)));
		assertTrue(board.searchCell(30).isNextTo(board,board.searchCell(25)));
		assertTrue(board.searchCell(25).isNextTo(board,board.searchCell(30)));

		assertTrue(board.searchCell(26).isNextTo(board,board.searchCell(27)));
		assertTrue(board.searchCell(27).isNextTo(board,board.searchCell(26)));
		assertTrue(board.searchCell(27).isNextTo(board,board.searchCell(28)));
		assertTrue(board.searchCell(28).isNextTo(board,board.searchCell(27)));
		assertTrue(board.searchCell(28).isNextTo(board,board.searchCell(29)));
		assertTrue(board.searchCell(29).isNextTo(board,board.searchCell(28)));
		assertTrue(board.searchCell(29).isNextTo(board,board.searchCell(30)));
		assertTrue(board.searchCell(30).isNextTo(board,board.searchCell(29)));

		assertTrue(board.searchCell(16).isNextTo(board,board.searchCell(17)));
		assertTrue(board.searchCell(17).isNextTo(board,board.searchCell(16)));
		assertTrue(board.searchCell(17).isNextTo(board,board.searchCell(18)));
		assertTrue(board.searchCell(18).isNextTo(board,board.searchCell(17)));
		assertTrue(board.searchCell(18).isNextTo(board,board.searchCell(19)));
		assertTrue(board.searchCell(19).isNextTo(board,board.searchCell(18)));
		assertTrue(board.searchCell(19).isNextTo(board,board.searchCell(20)));
		assertTrue(board.searchCell(20).isNextTo(board,board.searchCell(19)));
		
		assertTrue(board.searchCell(6).isNextTo(board,board.searchCell(7)));
		assertTrue(board.searchCell(7).isNextTo(board,board.searchCell(6)));
		assertTrue(board.searchCell(7).isNextTo(board,board.searchCell(8)));
		assertTrue(board.searchCell(8).isNextTo(board,board.searchCell(7)));
		assertTrue(board.searchCell(8).isNextTo(board,board.searchCell(9)));
		assertTrue(board.searchCell(9).isNextTo(board,board.searchCell(8)));
		assertTrue(board.searchCell(9).isNextTo(board,board.searchCell(10)));
		assertTrue(board.searchCell(10).isNextTo(board,board.searchCell(9)));


		assertFalse(board.searchCell(11).isNextTo(board,board.searchCell(12)));
		assertFalse(board.searchCell(12).isNextTo(board,board.searchCell(11)));
		assertFalse(board.searchCell(12).isNextTo(board,board.searchCell(13)));
		assertFalse(board.searchCell(13).isNextTo(board,board.searchCell(12)));
		assertFalse(board.searchCell(13).isNextTo(board,board.searchCell(14)));
		assertFalse(board.searchCell(14).isNextTo(board,board.searchCell(13)));
		assertFalse(board.searchCell(14).isNextTo(board,board.searchCell(15)));
		assertFalse(board.searchCell(15).isNextTo(board,board.searchCell(14)));
		
		
		assertFalse(board.searchCell(21).isNextTo(board,board.searchCell(22)));
		assertFalse(board.searchCell(22).isNextTo(board,board.searchCell(21)));
		assertFalse(board.searchCell(22).isNextTo(board,board.searchCell(23)));
		assertFalse(board.searchCell(23).isNextTo(board,board.searchCell(22)));
		assertFalse(board.searchCell(23).isNextTo(board,board.searchCell(24)));
		assertFalse(board.searchCell(24).isNextTo(board,board.searchCell(23)));
		assertFalse(board.searchCell(24).isNextTo(board,board.searchCell(25)));
		assertFalse(board.searchCell(25).isNextTo(board,board.searchCell(24)));
		
		assertFalse(board.searchCell(1).isNextTo(board,board.searchCell(2)));
		assertFalse(board.searchCell(2).isNextTo(board,board.searchCell(1)));
		assertFalse(board.searchCell(2).isNextTo(board,board.searchCell(3)));
		assertFalse(board.searchCell(3).isNextTo(board,board.searchCell(2)));
		assertFalse(board.searchCell(3).isNextTo(board,board.searchCell(4)));
		assertFalse(board.searchCell(4).isNextTo(board,board.searchCell(3)));
		assertFalse(board.searchCell(4).isNextTo(board,board.searchCell(5)));
		assertFalse(board.searchCell(5).isNextTo(board,board.searchCell(4)));

		assertFalse(board.searchCell(30).isNextTo(board,board.searchCell(26)));
		assertFalse(board.searchCell(26).isNextTo(board,board.searchCell(30)));
		assertFalse(board.searchCell(30).isNextTo(board,board.searchCell(27)));
		assertFalse(board.searchCell(27).isNextTo(board,board.searchCell(30)));
		assertFalse(board.searchCell(30).isNextTo(board,board.searchCell(28)));
		assertFalse(board.searchCell(28).isNextTo(board,board.searchCell(30)));
		assertFalse(board.searchCell(3).isNextTo(board,board.searchCell(2)));
		assertFalse(board.searchCell(1).isNextTo(board,board.searchCell(16)));
		assertFalse(board.searchCell(16).isNextTo(board,board.searchCell(1)));
	}

}
