package impl;

import java.util.Iterator;

import adt.Bag;
import adt.List;

/**
 * ListBag
 * 
 * An implementation of Bag using a List as the underlying implementation.
 * 
 * Recall that our Bag interface differs from what Sedgewick calls a "bag" (but
 * he's wrong).
 * 
 * CSCI 345, Wheaton College Spring 2016
 * 
 * @param <E>
 *            The base-type of the bag
 */
public class ListBag<E> implements Bag<E> {

	/**
	 * The internal representation (can be any implementation of List)
	 */
	private List<E> internal;

	public ListBag() {
		internal = new MapList<E>();
	}

	/**
	 * Return an iterator over this collection (remove() is unsupported, nor is
	 * concurrent modification checked).
	 */
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			// current position
			int pos = 0;

			// true if position is less than size
			public boolean hasNext() {
				return pos < internal.size();
			}

			// return position and increment pos
			public E next() {
				return internal.get(pos++);
			}

		};
	}

	/**
	 * Add an item to the bag, increasing its count if it's already there.
	 * 
	 * @param item
	 *            The item to add
	 */
	public void add(E item) {
		
		if (internal.size() == 0) {
			internal.add(item);
			return;
		}
		
		// if the item is in the list, insert another one at that spot
		for (int i = 0; i < internal.size(); i++) {
			if (internal.get(i).equals(item)) {
				internal.insert(i, item);
				return;
			}
		}
		
		// if the item is not in the list add it to the end of the list
		internal.add(item);
	}

	/**
	 * How many times does this bag contain this item?
	 * 
	 * @param item
	 *            The item to check
	 * @return The number of occurrences of this item in the bag
	 */
	public int count(E item) {
		int count = 0;

		if (internal.size() == 0)
			return 0;

		// count all occurrences of the item
		for (int i = 0; i < internal.size(); i++) {
			if (internal.get(i).equals(item))
				count++;
		}
		return count;
	}

	/**
	 * Remove (all occurrences of) an item from the bag, if it's there (ignore
	 * otherwise).
	 * 
	 * @param item
	 *            The item to remove
	 */
	public void remove(E item) {

		if (internal.size() == 0)
			return;

		// remove all occurrences of the item rather than the first
		for (int i = 0; i < internal.size(); i++)
			if (internal.get(i).equals(item)) {
				internal.remove(i);
				i--;
			}
	}

	/**
	 * The number of items in the bag. This is the sum of the counts, not the number
	 * of unique items.
	 * 
	 * @return The number of items.
	 */
	public int size() {
		return internal.size();
	}

	/**
	 * Is the bag empty?
	 * 
	 * @return True if the bag is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return internal.size() == 0;
	}

	@Override
	public String toString() {
		String toReturn = "[";
		boolean prefix = false;
		for (E item : this) {
			if (prefix)
				toReturn += ", ";
			toReturn += item;
			prefix = true;
		}
		return toReturn + "]";
	}

}
