package headfuck.lists;

public interface CellList {
	
	public void set(int index, int val);
	
	public void inc(int index);
	
	public void dec(int index);
	
	public boolean cmp(int index, int val);
	
	public int get(int index);
	
	public void clear(int index);
	
	
	public int getCapacity();
	
}
