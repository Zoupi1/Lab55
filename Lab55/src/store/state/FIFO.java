package store.state;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represent a First-In-First-Out queue.
 * 
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 * 
 * @version 1.0
 *
 * @param <E> Object type for each item in queue
 */
public class FIFO<E> implements Iterable<E> {

	private Item first, last;
	private int size = 0;

	/**
	 * Construct a new instance of a {@code FIFO} object
	 */
	public FIFO() {
		first = new Item(null);
		last = first;
	}

	/**
	 * Get size of queue.
	 * 
	 * @return size of queue where 0 is empty.
	 */
	public int size() {
		return size;
	}

	/**
	 * Determine if queue is empty and does not contain any objects.
	 * 
	 * @return true if queue is empty, otherwise false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Add an item to end of queue.
	 * 
	 * @param item Item to add
	 */
	public void add(E item) {
		last.next = new Item(item);
		last = last.next;
		if (isEmpty()) {
			first = last;
		}
		size++;
	}

	/**
	 * Add all elements in a {@code Collection} to end of queue.
	 * 
	 * @param collection Collection of items to add
	 * @see Collection
	 */
	public void addAll(Collection<? extends E> collection) {
		for (E e : collection) {
			this.add(e);
		}
	}

	/**
	 * Add all elements in an array to end of queue.
	 * 
	 * @param arr Array of items to add
	 */
	public void addAll(E[] arr) {
		for (E e : arr) {
			this.add(e);
		}
	}

	/**
	 * Get first item in queue, if queue is empty a {@link NoSuchElementException}
	 * will be thrown.
	 * 
	 * @return First item in queue
	 * @throws NoSuchElementException Occurs when queue is empty
	 * @see NoSuchElementException
	 */
	public E getFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		E firstItem = first.content;
		first = first.next;
		size--;
		return firstItem;
	}

	/**
	 * Remove first element in queue.
	 */
	public void removeFirst() {
		if (size() == 1) {
			first = new Item(null);
			last = first;
			size--;
		}
		first = first.next;
	}

	/**
	 * Remove all items in queue.
	 */
	public void removeAll() {
		first = new Item(null);
		last = first;
		size = 0;
	}

	@Override
	public String toString() {
		String items = "{";
		FIFO<E> fifo = this.clone();
		while (!fifo.isEmpty()) {
			items += fifo.getFirst().toString() + (fifo.size == 0 ? "}" : ", ");
		}
		return items;
	}

	@Override
	protected FIFO<E> clone() {
		FIFO<E> clone = new FIFO<E>();

		Item currentItem = first;
		while (currentItem != null) {
			clone.add(currentItem.content);
			currentItem = currentItem.next;
		}
		return clone;
	}

	/**
	 * Get iterator for queue. Iteration in the queue does not change anything in
	 * queue.
	 */
	@Override
	public Iterator<E> iterator() {
		return new ItemIterator();
	}

	/**
	 * This class represents an {@code Iterator}.
	 * 
	 * @author Rickard Bemm
	 * @version 1.0
	 */
	private class ItemIterator implements Iterator<E> {

		private Item currentItem;

		public ItemIterator() {
			this.currentItem = FIFO.this.first;
		}

		@Override
		public boolean hasNext() {
			return currentItem != null;
		}

		@Override
		public E next() {
			E content = currentItem.content;
			currentItem = currentItem.next;
			return content;
		}
	}

	/**
	 * This class represents an {@code Item} in a FIFO-queue.
	 * 
	 * @author Rickard Bemm
	 * @version 1.0
	 */
	private class Item {

		/**
		 * Reference to next Item. If there is no next {@code item}, {@code next} is
		 * null.
		 */
		public Item next;

		/**
		 * Content of this Item
		 */
		public E content;

		/**
		 * Create a new {@code Item} with next item set to null.
		 * 
		 * @param content content to add to item
		 */
		public Item(E itemContent) {
			next = null;
			content = itemContent;
		}
	}
}
