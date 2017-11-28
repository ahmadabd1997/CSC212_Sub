
public interface List<T> {
	
	public void insert(T e);
		
	public void remove();
	
	public void update(T e);
	
	public void findFirst();
	
	public void findNext();
	
	public boolean full();
	
	public boolean empty();
	
	public T retrieve();
	
	public boolean last ();
}
