// (maybe not complete) not sure if the list need more methods or need edit!

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
