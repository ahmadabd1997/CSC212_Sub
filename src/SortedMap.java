// The sorted map interface. K represents the key type, which must extend Comparable,
// and T representds the stored data type.
public class SortedMap<K extends Comparable<K>, T> {
	
	int Size,MaxSize;
	SBSTNode<K,T> current ,head ,tail,root;
	
	// Return the size of the map. Must be O(1).
	int size(){
		return Size;
	}

	// Return true if the tree is empty. Must be O(1).
	boolean empty(){
		return Size == 0;
	}

	// Return true if the tree is full. Must be O(1).
	boolean full(){
		return Size == MaxSize;
	}

	// Move current to the first element (the smallest key). The map must not be
	// empty. Must be O(1).
	void findFirst(){
		current = head;
	}

	// Move current to the last element (the largest key). The map must not be
	// empty. Must be O(1).
	void findLast(){
		current = tail;
	}

	// Move current to the next element (the key immediately greater than the
	// current key). The map must not be empty, and current must not be last. Must
	// be O(1).
	void findNext(){
		if(current != tail)
			current = current.next;
	}

	// Move current to the previous element (the key immediately smaller than the
	// current key). The map must not be empty, and current must not be first. Must
	// be O(1).
	void findPrevious(){
		if(current != head)
			current = current.previous;
	}

	// Returns true if current is the first element (the smallest key). The map must
	// not be empty. Must be O(1).
	boolean first(){
		if(Size != 0){
			return current == head;
		}
		return false;
	}

	// Returns true if current is the last element (the largest key). The map must
	// not be empty. Must be O(1).
	boolean last(){
		if(Size != 0){
			return current == tail;
		}
		return false;
	}

	// Return the key and data of the current element
	Pair<K, T> retrieve(){
		Pair P = new Pair(current.key,current.data);
		return P;
	}

	// Update the data of current element.
	void update(T e){
		current.data = e;
	}

	// Search for element with key k and make it the current element if it exists.
	// If the element does not exist the current is unchanged and false is returned.
	// This method must be O(log(n)) in average.
	/*boolean find(K key){// find @@@ 1 @@@
		if(root == null)
			return false;
		return findrec(root,key);
	}
	
	private boolean findrec(SBSTNode<K,T> N, K key){// find @@@ 1 @@@
		if(N == null)
			return false;
		if (N.key.compareTo(key) == 0){
			current = N;
			return true;
		}
		return findrec(N.left,key) || findrec(N.right,key);
	}
	*/
	boolean find(K key){ // find @@@ 2 @@@
		if(root != null){
			SBSTNode<K,T> tmp = root;
			while(key.compareTo(tmp.key) != 0){
				if(key.compareTo(tmp.key) > 0){
					if(tmp.right != null)
						tmp = tmp.right;
					else
						break;
				}		
				else {
					if(tmp.left != null)
						tmp = tmp.left;
					else
						break;
					
				}
			}
			if(key.compareTo(tmp.key) == 0){
				current = tmp;
				return true;
			}
		}
		return false;
	}
	
	// Return the number of nodes in the search path for key. Must be O(log n).
	/*int nbNodesInSearchPath(K key){ // nbNodesInSearchPath @@@ 1 @@@
		if(root == null)
			return 0;
		return nbNodesInSearchPathrec(root,key);
	}
	private int nbNodesInSearchPathrec(SBSTNode<K,T> N, K key){
		if(N == null)
			return 0;
		if(N.key.compareTo(key) == 0){
			return 1;
		}
		int left = nbNodesInSearchPathrec(N.left,key);
		int right = nbNodesInSearchPathrec(N.right,key);
		if(left != 0)
			return left + 1;
		if(right != 0)
			return right + 1;
		return 0;
	}*/
	
	int nbNodesInSearchPath(K key){ // nbNodesInSearchPath @@@ 2 @@@
		int nb =0;
		if(root != null){
			SBSTNode<K,T> tmp = root;
			while(key.compareTo(tmp.key) != 0){
				if(key.compareTo(tmp.key) > 0){
					if(tmp.right != null){
						nb++;
						tmp = tmp.right;
					}
					else
						break;
				}		
				else {
					if(tmp.left != null){
						nb++;
						tmp = tmp.left;
					}
					else
						break;
					
				}
			}
			if(key.compareTo(tmp.key) == 0)
				return nb;
		}
		return 0;
	}

	// Update the current key to the new value newKey if possible and returns true.
	// If newKey does not respect the order, no change is made and false is
	// returned. Must be O(1).
	boolean updateKey(K newKey){
		if((current.previous.key.compareTo(newKey) < 0) && (current.next.key.compareTo(newKey) > 0)){
			current.key = newKey;
			return true;
		}
		return false;
	}

	// Insert a new element if does not exist and return true. The current points to
	// the new element. If the element already exists, current does not change and
	// false is returned. This method must be O(log(n)) in average.
	boolean insert(K key, T data){
		if(root != null){
			while(key.compareTo(current.key) != 0){
				if(key.compareTo(current.key) > 0){
					if(current.right != null)
						current = current.right;
					else
						break;
				}		
				else {
					if(current.left != null)
						current = current.left;
					else
						break;
					
				}
			}
			if(key.compareTo(current.key) != 0){
				SBSTNode<K,T> tmp , NewN = new SBSTNode<K,T>(key,data);
				
				if(key.compareTo(current.key) > 0){
					tmp = current.next;
					
					current.right = NewN;//BST
					
					current.next = NewN;//DLL
					NewN.next = tmp;//DLL
					NewN.previous = current;//DLL
					if(tmp != null)//DLL
						tmp.previous = NewN;//DLL
					else//DLL
						tail = NewN;//DLL
				}
				else{
					tmp = current.previous;
					
					current.left = NewN;//BST
					
					current.previous = NewN;//DLL
					NewN.previous = tmp;//DLL
					NewN.next = current;//DLL					
					if(tmp != null)//DLL
						tmp.next = NewN;//DLL
					else//DLL
						head = NewN;//DLL
				}
				return true;
			}
			else{
				return false;
			}
		}
		SBSTNode<K,T> NewN = new SBSTNode<K,T>(key,data);
		head = root = tail = current = NewN ;
		return true;
	}

	// Remove the element with key k if it exists and return true. If the element
	// does not exist false is returned (the position of current is unspecified
	// after calling this method). This method must be O(log(n)) in average.
	boolean remove(K key);

	// Remove the current element. The next element if it exists is made current,
	// otherwise current moves to the first element. This method must be O(log(n))
	// in average.
	void remove();

	// Return in a list all elements with key k satisfying: k1 <= k <= k2.
	List<Pair<K, T>> inRange(K k1, K k2);
}
