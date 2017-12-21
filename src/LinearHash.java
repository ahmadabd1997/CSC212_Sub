public class LinearHash<T> {

	public enum Status {
		empty, occupied, deleted
	};

	private int maxSize;
	private int size;
	private int c;
	private int current;
	private int[] keys;
	private Status[] statusT;
	private T[] data;

	public LinearHash(int maxSize, int c) {

		this.maxSize = maxSize;
		this.c = c;
		size = 0;
		current = -1;
		keys = new int[maxSize];
		statusT = new Status[maxSize];
		data = (T[]) new Object[maxSize];

		// Initialize all cells to empty
		for (int i = 0; i < maxSize; i++) {
			statusT[i] = Status.empty;
		}
	}

	public int size() {
		return size;
	}

	public boolean full() {
		return size == maxSize;
	}

	public T retrieve() {
		return data[current];
	}

	public void update(T val) {
		data[current] = val;
	}

	public void delete() {
		statusT[current] = Status.deleted;
		size--;
	}

	public int insert(int key, T val) {
		if(full())
			return -1;
		int index = key%maxSize;
		int i = key%maxSize;
		int stp = 1;
		if(statusT[index] != Status.occupied){
			keys[index] = key;
			data[index] = val;
			statusT[index] = Status.occupied;
		}else{
			index = (index+c)%maxSize;
			stp++;
			while(statusT[index] != Status.occupied && stp<maxSize){
				index = (index+c)%maxSize;
				stp++;
			}
			if(statusT[index] != Status.occupied){
				keys[index] = key;
				data[index] = val;
				statusT[index] = Status.occupied;
			}
		}
		return stp;
	}

	public boolean find(int key) {
		int index = key%maxSize;
		int i = key%maxSize;
		if (keys[index] == key) {
			current = index;
			return true;
		}else{
			index = (index+c)%maxSize;
			while(keys[index] != key && index != i){
				index = (index+c)%maxSize;
		}
		if (keys[index] == key) {
			current = index;
			return true;
		}
		return false;
		}
	}
}
