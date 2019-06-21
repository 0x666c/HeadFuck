package headfuck.lists.v2;

public class RefShortList implements CellListv2 {
	
	private static final class Cell {
		
		private short sval = 0;
		private Object refval = null;
		private boolean isref;
		
		private Cell(final short value) {
			isref = false;
			sval = value;
		}
		private Cell(final Object refValue) {
			isref = true;
			refval = refValue;
		}
		
		private final void setr(final Object ref) {
			isref = true;
			sval = 0;
			refval = ref;
		}
		private final void sets(final short s) {
			isref = false;
			refval = null;
			sval = s;
		}
	}
	
	
	
	private final Cell[] cells;
	private int capacity;
	
	public RefShortList(int initialCapacity) {
		capacity = initialCapacity;
		cells = new Cell[initialCapacity];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(0);
		}
	}
	
	
	public void set(int index, int val) {
		cells[index].sets((short)val);
	}
	public void set(int index, final Object val) {
		cells[index].setr(val);
	}
	
	public void inc(int index) {
		Cell c = cells[index];
		if(!c.isref) {
			c.sval++;
		} else {
			// Exception
		}
	}
	
	public void dec(int index) {
	}
	
	public boolean cmp(int index, int val) {
		return false;
	}
	public boolean cmp(int index, final Object val) {
		return false;
	}
	
	public short geti(int index) {
		return 0;
	}
	public Object geto(int index) {
		return 0;
	}
	
	public void clear(int index) {
		
	}
	
	public int getCapacity() {
		return 0;
	}
	
}