class Node<T> {
	public T data;
	public Node<T> next;
	
	public Node (T val) {
		data = val;
		next = null;
	}
}


public class LinkedList<T> implements List<T> {

	private Node<T> head;
	private Node<T> current;
	
	public void insert(T e){ //--add element after the current
		Node<T> tmp = new Node<T>(e);
		if(current != null){ //if not empty
			tmp.next = current.next;
			current.next = tmp;
			current=tmp;
		}
		else head = current = tmp;//if empty
	}
	
	public void insertbefore(T e){
		Node<T> tmp = new Node<T>(e);//tmp is the new node
		Node<T> tmp2 = head;//tmp2 is the pointer that move along the list
		
		if(current == head){
			tmp.next = head;
			head = current = tmp;
		}
		else{
			while(tmp2.next != current)
				tmp2 = tmp2.next;
			tmp.next = current;
			tmp2.next = tmp;
		}
	}
	
	public void remove(){ //--remove the element at current. move the current to the next element or the start if removing the last element
		if(current == head){
			head = current = head.next;//(removed the 1st element)
		}
		else{
			Node<T> tmp = head;
			while(tmp.next != current)tmp = tmp.next;
			tmp.next = current.next;
			if(tmp.next != null)current = tmp.next; // current = next (removed any element in the mid)
			else current = head; // current = start (removed last element)
		}
	}
	
	public void update(T e){ //--change the data of the current element
		current.data = e;
	}
	
	public void findFirst(){ //--move the current to the start		
		current = head;
	}
	
	public void findNext(){ //--move the current to the start		
		current = current.next;
	}
	
	public boolean full(){ //-- FALSE
		return false;
	}
	
	public boolean empty(){ //--return if the list is empty or not
		return head == null;
	}
	
	public T retrieve(){ //--return the data in the current
		
		return current.data;
	}
	
	public boolean last () {
		return current.next == null;
	}
	
}
