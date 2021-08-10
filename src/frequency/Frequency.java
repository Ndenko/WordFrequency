/*
* This class counts the word frequency
 */
package frequency;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

/**
 * @author UMD CS
 * CMSC132 Summer 2021
 */
public class Frequency<E extends Comparable> implements Iterable<E>{
    private Node first;	//starting node
    private Node curr;//the current node
    private Node prev;
    private Node parent;//parent of currently processed node
    private int N;	//number of words (should this be number of unique words?)
    private int numOfNodes;	//number of nodes in linkedList (which may be 
    						//different	than the number of words N

    
    /**
     * Linked List Node
     */
   
    private class Node implements Comparable<Node>{
    	private E key;
    	private int count;
        private Node next;
        /**
         * 	Constructor
         */
        Node(E e){
           key = e;
           count = 1;
           next = null;
        }
        /**
         * 	Constructor
         */
        Node(E e, Node r){
            key = e;
            count = 1;
            next = r;
         }
        public E getKey() {
        	return this.key;
        }
        public int getCount() {
        	return this.count;
        }
        
        @Override 
        public String toString(){
        	return "["+key +","+count+"]";
        }
        
        //this compareTo method works by prioritizing count, then alphabetical
        //order
        //Remember: if this.data < input.data returns -1
        //Remember: A is "less than" Z
        //Remember: Count of 2 is "less than" count of 1
        //This allows sorting to be work properly, lesser value nodes are at the
        //front of the list while higher value nodes are at end
		@Override
		public int compareTo(Frequency<E>.Node o) {
			//if the current nodes count is less return 1
			if (this.count < o.count ) {
				return 1;
			}
			//if the counts are even then compare alphabetical order
			if (this.count == o.count) {
				return this.key.compareTo(o.key);
			}
				
				
				
			//if the current nodes count is more return +1
			if (this.count > o.count) {
				return -1;
			}
		
			return -1;
		}
		
}
    
    //helper method that finds the length of the list which will be used later
    //as the upper bound for our for loops
    private void findNumOfNodes() {
    	numOfNodes=0;
    	curr=first;
    	while(curr!=null) {
    		curr=curr.next;
    		numOfNodes++;
    	}
    }
    private Node mergeSort(Node first) {
    	 // Base case : if head is null
        if (first == null || first.next == null) {
            return first;
        }
 
        // get the middle of the list
        Node middle = getMiddle(first);
        Node nextofmiddle = middle.next;
 
        // set the next of middle node to null
        middle.next = null;
 
        // Apply mergeSort on left list
        Node left = mergeSort(first);
 
        // Apply mergeSort on right list
        Node right = mergeSort(nextofmiddle);
 
        // Merge the left and right lists
        Node sortedlist = sortedMerge(left, right);
        return sortedlist;
    }
    private Node sortedMerge(Node a, Node b) {
    	Node result = null;
        /* Base cases */
        if (a == null)
            return b;
        if (b == null)
            return a;
 
        /* Pick either a or b, and recur */
        if (a.compareTo(b) < 0) {
            result = a;
            result.next = sortedMerge(a.next, b);
        }
        else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }
    private Node getMiddle(Node first) {
    	if (first == null)
            return first;
 
        Node slow = first, fast = first;
 
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    

    //helper method that sorts the data using compareTo(). Since compareTo
    //already evaluates the count first and the alphabetical order next
    //this method puts the higher count names at front of the list
    //alphabetizes those, then puts the lower count names at the end of the
    //list and alphabetizes those. the A's will have "lower" value than the
    //Z's
   
 
    private void sort() {
    	
    	findNumOfNodes();
    	if (numOfNodes > 1) {
            for (int i = 0; i < numOfNodes; i++ ) {
            	//note that this method creates its own curr node, so the curr
            	//node instance variable of our linkedlist is not needed
                Node currentNode = first;
                Node next = first.next;
                for (int j = 0; j < numOfNodes - 1; j++) {
                    if (currentNode.compareTo(next) >= 1) {
                    	
                    	//hold our data in temp variables
                        int tempCount = currentNode.count;
                        E tempKey = currentNode.key;
                        
                        //write over the current node with the next nodes data
                        currentNode.count = next.count;
                        currentNode.key = next.key;
                        //write over the next nodes data
                        //with the data we stored in temp
                        
                        next.count = tempCount;
                        next.key = tempKey;
                    } 
                    currentNode = next;
                    next = next.next;                   
                }
                curr = first;
            }
    }
 }   
    
   /**
    * Inserts a word into linked list
    * @param key to be insterted 
    * @return true if the key is inserted successfully.
    */
    
   	
    public boolean insert(E key){
    	//if list is empty, set the insert key as the first node and return true
    	if (first == null) {
    		Node insertedNode = new Node(key);
    		first = insertedNode;
    		return true;
    	}
    	//if its not empty, before starting, sort the list
    //	sort();
    	curr = first;
    	boolean found = false;
    	//this while loop traverses the linkedlist
		while (curr != null) {
			//while traversing, check each node we visit. if the name of the 
			//visited node is the same name as our key, increase the current
			//nodes count by 1
			if(curr.key.equals(key)) {
				found = true;
				curr.count++;
		//		sort();
				return true;
			}
			//check the next node
			curr = curr.next;
		}
		//reset curr back to the head
		curr = first;
		//otherwise if there is not already an existing key, create a new node
		//with the input as that nodes key and add it to the end of the list 
		if(found == false) {
		//traverse the linkedList again, adding insert node when curr.next==null
			//if this is our first time around we must make sure list isnt empty
			//by setting first to the input node
			Node insertedNode = new Node(key);
			if(first == null) {
				first = insertedNode;
			}
			while (curr != null) {		
				if (curr.next == null) {	
					curr.next = insertedNode;
			//		sort();
					return true;
					}
				curr = curr.next;
				}
			
			}
		
		
			return false;//should this return false if a node with that key already 
			//exists? Or should it just update that key and return
			//true anyways
			
		
		
	}
			
			
		


    
   /**
    * removes the nodes with given key
    * @param key: 
    * @return the deleted node+
    */
    private Node remove(E key){
    	//if the list is empty, there is nothing to remove, return null
    	if (first == null) {
    		return null;
    	}
    	sort();
    	curr = first;
    	prev = null;
    	//if we are removing the head, set the second node to the head, so that
    	//we dont ruin the entire list
    	if(first.key.equals(key)) {
    		first = first.next;
    		curr = first;
    		sort();
    		Node removed = new Node(key);
			return removed;
    	}
    	//traverse the list
		while (curr != null) {
			//if we arrive to a node with the same key as the insert key,
			//set our previous node's pointer to the node ahead of the current
			//one, making the current node inaccessible and effectively deleting 
			//that node via its eventual garbage collection
			if(curr.key.equals(key)) {
				prev.next = curr.next;
				//make a clone of the removed node and return it. 
				//should we also clone its count by
				//giving Node class another constructor?
				sort();
				Node removed = new Node(key);
				return removed;
			}
			prev = curr;
			curr = curr.next;
		}
		sort();
		//if nothing is found return null
		return null;
	}

    /**
     * Inserts a node into correct location in the linked list
     * @param r is the node to be inserted
     * @return true if successful
     */
    private boolean insert(Node r){
    	//if list is empty, set the insert key as the first node and return true
    	if (first == null) {
    		first = r;
    		return true;
    	}
    	//if its not empty, before starting, sort the list
   // 	sort();
    	Node current = first;
    	boolean found = false;
    	//this while loop traverses the linkedlist
		while (current != null) {
			//while traversing, check each node we visit. if the name of the 
			//visited node is the same name as our key, increase the current
			//nodes count by 1
			if(current.key.equals(r.getKey())) {
				found = true;
				//this will allow an insert node such as (Kevin, 3) to be added
				//to an existing Node (kevin, 2) and turn that node into 
				//(Kevin, 5)
				current.count += 1;
		//		sort();
				//System.out.println(r.getKey());
				//System.out.println(first);
				//System.out.println(current);
				//System.out.println("adam bug");
				return true;
			}
			//check the next node
			current = current.next;
		}
		//reset curr back to the head
		current = first;
		//otherwise if there is not already an existing key, create a new node
		//with the input as that nodes key and add it to the end of the list 
		if(found == false) {
		//traverse the linkedList again, adding insert node when curr.next==null
			//if this is our first time around we must make sure list isnt empty
			//by setting first to the input node
			if(first == null) {
				first = r;
			}
			while (current != null) {		
				if (current.next == null) {	
					current.next = r;
			//		sort();
					return true;
					}
				current = current.next;
				}
			
			}
			return false;//should this return false if a node with that key already 
			//exists? Or should it just update that key and return
			//true anyways
	}
    
    
    /**
     * @param k is the key to be searched for
     * @return the node that has the word as its key
     */
    private Node find(E k){
		curr = first; 
		while (curr != null) {
			if (curr.key.equals(k)) {
				Node foundNode = new Node(k);
				
				return foundNode;
			}
			curr = curr.next;
		}
		return null;
		
	}
    
    /**
     * @return true all the nodes are sorted, otherwise return false
     * 
     */
    public boolean isValid() {
    	if(first == null || first.next == null)
    		return true;
    	Node c = first;
    	while(c.next != null) {
    		if(c.count < c.next.count || (
    					c.count == c.next.count	&& c.key.compareTo(c.next.key) >= 0 )
    				) {
    			return false;
    		}
    		c = c.next;
    	}
    	return true;
    }
    
    /**
     * 
     * @param key is the key to be searched for
     * @return frequency of the key. Returns -1 if key does not exist
     * 
     */
    public int getCount(E key){
    	sort();
    	curr = first;
    	while (curr != null) { 		
    		if (curr.key.equals(key)) {
    			//if a key is found, increment
    		return curr.getCount();
    		}
    		curr = curr.next;
    	}
    		//if given key isn't found, return -1
    		return -1;
    	}
    /**
     * Returns the first n words and count
     * @param n number of words to be returned
     * @return first n words in (word, count) format
     */
    public String getWords(int n){
    	sort();
    	//mergeSort(this.first);
    	int counter = 0;
    	String result = ""; 
		curr = first; 
		
		while (curr != null) {
			result += curr.toString();
			
			curr = curr.next;
			counter++;
			if(counter == n) {
				break;
			}
		}
		return result;
	}
    
    @Override
    public String toString(){
    	String result = "";
    	curr = first;
		while(curr!=null) {
			result += curr.toString();
			curr=curr.next;
			
		}
		return result;
    }
    /**
     * Frequency List iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new FreqIterator();
    }
    
    /**
     * 
     * Frequency List iterator class
     *
     */
    private class FreqIterator implements Iterator<E>{
 
      Node curr = first;	
      @Override
      public boolean hasNext() {
		return curr != null;
	}
      @Override
      public E next() {
    	  sort();
    	  //if there is a next node, return it, otherwise return null
    	  if(hasNext()==true) {
    		  E name = curr.key;
    		  curr = curr.next;
    		  return name;
    	  	}
      
    	   return null;
      	}
    }

    public static void main(String[] args) {
//    	Frequency<String> linkedList = new Frequency<>();
//    	for (Object name : linkedList) {
//    		System.out.println(name);
//    	}
//    	Frequency.Node node1 = linkedList.new Node("Adam");
//     	Frequency.Node node2 = linkedList.new Node("Ben");
//     	Frequency.Node node3 = linkedList.new Node("Charles");
//     	
//    	System.out.println(node1.getKey());
//    	System.out.println(linkedList.insert(node1));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node1));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node2));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node2));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node2));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node2));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node1));
//     	System.out.println(linkedList.getWords(20));
    	
//    	System.out.println(linkedList.insert("Osama"));
//     	System.out.println(linkedList.getWords(20));
//     	System.out.println(linkedList.insert("Jeffrey"));
//     	System.out.println(linkedList.getWords(20));
//     	System.out.println(linkedList.insert("Yan"));
//     	System.out.println(linkedList.getWords(20));
//     	
//     
//     	System.out.println(linkedList.insert("Yan"));
//     	System.out.println(linkedList.getWords(10));
//     	System.out.println(linkedList.insert("Yan"));
//     	System.out.println(linkedList.getWords(10));
//     	System.out.println(linkedList.getCount("Yan"));
//  	
//    	System.out.println(linkedList.insert("Yan"));
//    	System.out.println(node1.getKey());
//     	System.out.println(linkedList.getWords(10));
//     	System.out.println(linkedList.insert("Yan"));
//    	System.out.println(node1.getKey());
//     	System.out.println(linkedList.getWords(10));
//     	System.out.println(linkedList.insert("Yan"));
//     	
//     	System.out.println(linkedList.getWords(10));
//    	System.out.println(linkedList.insert("Yan"));
//     	System.out.println(linkedList.getWords(10));
//     	System.out.println("--adam should increase 4x beneath increase here--");
//    	System.out.println(linkedList.insert(node1));
//    	System.out.println(node1.getKey());
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node1));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node1));
//     	System.out.println(linkedList.getWords(20));
//    	System.out.println(linkedList.insert(node1));
//    	System.out.println(linkedList.getWords(17));
//    	System.out.println(linkedList.insert(node2));
//    	System.out.println(linkedList.getWords(17));
//    	System.out.println(linkedList.insert(node1));
//    	System.out.println(linkedList.getWords(17));
//    	System.out.println(linkedList.insert(node1));
//    	System.out.println(linkedList.getWords(30));
//    	//17
//    	System.out.println(node1.getKey());
    	
//     	test1.count=5;
//     	
//     	System.out.println(test1.compareTo(test2));
//     	System.out.println(test2.compareTo(test1));
//     	

//
//     	System.out.println(linkedList.first);
//     	System.out.println(linkedList.toString());
//     	System.out.println(linkedList.numOfNodes);
//     	System.out.println(linkedList.getWords(3));
//     	System.out.println(linkedList.getCount("Zezima"));
    	Frequency<String> freq = new Frequency<>();
		freq.insert("alice");
	
		freq.insert("alice");
		freq.insert("cat");
	
		freq.insert("bob");
		freq.insert("cat");
	
		freq.insert("bob");
		freq.insert("cat");
		System.out.println(freq.getCount("cat"));
    }
}
