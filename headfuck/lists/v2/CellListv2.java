package headfuck.lists.v2;

public interface CellListv2 {
	
	public void set(int index, int val);
	public void set(int index, final Object val);
	
	public void inc(int index);
	
	public void dec(int index);
	
	public boolean cmp(int index, int val);
	public boolean cmp(int index, final Object val);
	
	public short geti(int index);
	public Object geto(int index);
	
	public void clear(int index);
	
	public int getCapacity();
	
}
