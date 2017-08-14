/**
 * This class creates a priority queue implemented using a linked list. 
 * @author Iulia Murariu
 */

public class LinkedPriorityQueue<T> implements PriorityQueueADT<T>{
	   /**Attribute declarations.*/
	   private int count; //create a counter that will keep track of the nodes 
	   private PriorityNode<T> front, rear; //create pointers to the front and rear of the list 

	   /**
	    * Constructor creates an empty queue.
	    */
	   public LinkedPriorityQueue()
	   {
	      count = 0;
	      front = rear = null;
	   }

	   /**
	    * Adds the specified element to the rear of this queue.
	    *
	    * @param element the element to be added to the rear of this queue
	    */
	   public void enqueue (T element)
	   {
	      PriorityNode<T> node = new PriorityNode<T>(element);

	      if (isEmpty())
	         front = node;
	      else
	         rear.setNext(node);

	      rear = node;
	      count++;
	   }
	   

	   /**
	    * Adds the specified element to the priority queue in the proper location based on priority.
	    * @param element, the element to be added to the rear of this queue
	    */
	   public void enqueue (T element, double p){
		   PriorityNode<T> node = new PriorityNode<T>(element); //creating a new node that contains the given element
		   node.setPriority(p); //setting the priority 
		   PriorityNode<T> prev = null; //creating a variable that marks the previous node
		   PriorityNode<T> current = front; //creating a variable that points to the current node 
		   
		   //first check if the queue is empty, if so we want both rear and front pointing to null 
		   if (isEmpty()){
			   front = node;
		   }
		   //going through the queue and finding the place to insert the element in terms of priority 
		   else{
			   for(int i = 0; i < count && current!= null && current.getPriority()<p ; i ++){
				   prev = current;
				   current = current.getNext();
			   } 
			   if(current == front){
				   node.setNext(current);
				   front = node;
			   }
			   else{
				   node.setNext(current);
				   prev.setNext(node);
			   }
		   }  
		   //increase count 
		   count++;
	   }

	   /**
	    * Removes the element at the front of this queue and returns a
	    * reference to it. Throws an EmptyCollectionException if the
	    * queue is empty.
	    *
	    * @return                           the element at the front of this queue
	    * @throws EmptyCollectionException  if an empty collection exception occurs
	    */
	   public T dequeue() throws EmptyCollectionException
	   {
	      if (isEmpty())
	         throw new EmptyCollectionException ("queue");

	      T result = front.getElement();
	      front = front.getNext();
	      count--;

	      if (isEmpty())
	         rear = null;

	      return result;
	   }
	   
	   /**
	    * Returns a reference to the element at the front of this queue.
	    * The element is not removed from the queue.  Throws an
	    * EmptyCollectionException if the queue is empty.  
	    *
	    * @return                            a reference to the first element in
	    *                                    this queue
	    * @throws EmptyCollectionsException  if an empty collection exception occurs
	    */
	   public T first() throws EmptyCollectionException{
	      if (isEmpty()){
	    	  throw new EmptyCollectionException("queue");
	      }
	      return front.getElement();
	   }

	   /**
	    * Returns true if this queue is empty and false otherwise. 
	    *
	    * @return  true if this queue is empty and false if otherwise
	    */
	   public boolean isEmpty()
	   {
	       return count ==0;
	   }
	 
	   /**
	    * Returns the number of elements currently in this queue.
	    *
	    * @return  the integer representation of the size of this queue
	    */
	   public int size()
	   {
	      return count;
	   }

	   /**
	    * Returns a string representation of this queue. 
	    *
	    * @return  the string representation of this queue
	    */
	   public String toString() {
		   String s = "";
		   PriorityNode<T> current = front;
		   T elem = current.getElement();
		   while (elem != null){
			s = s + current.toString() + "\n";
			current = current.getNext();
		}
		return s;
	}
}

