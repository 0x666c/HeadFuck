package headfuck.lists;

public class ShortList implements CellList {
	
	private int capacity;
	private short[] positive_zero;
	// private short[] negative; TODO: Allow negative cells making the list infinite
	
	private float loadFactor = 1.5f;
	
	public ShortList(int initialCapacity) {
		capacity = initialCapacity;
		
		positive_zero = new short[initialCapacity];
	}
	
	public void set(int index, int val) {
		ensureCapacity(index);
		
		positive_zero[index] = (byte) val;
	}
	
	public void inc(int index) {
		ensureCapacity(index);
		
		positive_zero[index]++;
	}
	
	public void dec(int index) {
		ensureCapacity(index);
		
		positive_zero[index]--;
	}
	
	public boolean cmp(int index, int val) {
		ensureCapacity(index);
		
		return positive_zero[index] == (byte)val;
	}
	
	public int get(int index) {
		ensureCapacity(index);
		
		return positive_zero[index];
	}
	
	
	public void clear(int index) {
		ensureCapacity(index);
		
		positive_zero[index] = 0;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	
	private void ensureCapacity(int atLeast) {
		if(capacity <= atLeast) {
			int times = 1;
			for(;(capacity * loadFactor) < atLeast; times++);
			
			short[] ref = new short[(int)((capacity * loadFactor) * times) + 1];
			System.arraycopy(positive_zero, 0, ref, 0, capacity);
			positive_zero = ref;
			
			capacity = ref.length;
		}
	}
}