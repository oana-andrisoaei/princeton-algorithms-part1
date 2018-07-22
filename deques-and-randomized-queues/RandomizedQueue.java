import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] queue;
	
	public RandomizedQueue() {
		size = 0;
		queue = (Item[]) new Object[2];
	}
	
	public boolean isEmpty() {
	   return size == 0;
	}
   
	public int size() {
	   return size;
	}
   
	public void enqueue(Item item) {
	   if (item == null) 
		   throw new java.lang.IllegalArgumentException();
	   if (size > 0 && size == queue.length)
  			resize(queue.length*2);
	   queue[size++] = item;
	}
   
	public Item dequeue() {
	   if (isEmpty()) 
		   throw new java.util.NoSuchElementException();
	   if (size > 0 && size == queue.length/4)
  			resize(queue.length/2);
	   int random = StdRandom.uniform(size);
	   Item item = queue[random];
	   queue[random] = queue[--size];
	   queue[size] = null;
	   return item;
	}
   
   	public Item sample() {
 	   if (isEmpty()) 
 		   throw new java.util.NoSuchElementException();
 	   return queue[StdRandom.uniform(size)];
   	}
   	
   	public Iterator<Item> iterator() {
   		return new RandomizedArrayIterator();
   	}
   
   	private class RandomizedArrayIterator implements Iterator<Item> {
	   private int i = 0;
	   private final int[] randomIndexes;
	   
	   public RandomizedArrayIterator() {
		   randomIndexes = new int[size];
		   for (int j = 0; j < size; j++)
			   randomIndexes[j] = j;
		   StdRandom.shuffle(randomIndexes);
	   }
	   public boolean hasNext() {
		   return size > i;
	   }
	   
	   public void remove() {
		   throw new java.lang.UnsupportedOperationException();
	   }
	   
	   public Item next() {
		   if (!hasNext())
			   throw new java.util.NoSuchElementException();
		   return queue[randomIndexes[i++]];
	   }
   	}

   	
   	private void resize(int newSize) {
   		Item[] queueTemp = (Item[]) new Object[newSize];
   		for (int i = 0; i < size; i++) {
   			queueTemp[i] = queue[i];
   		}
   		queue = queueTemp;
   	}
}