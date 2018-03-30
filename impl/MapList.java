package impl;

import java.util.Iterator;
import adt.Map;
import adt.List;

/**
 * MapList
 * 
 * An implementation of List that uses a Map as its underlying implementation.
 * 
 * CSCI 345, Wheaton College Spring 2016
 * 
 * @param <E>
 *            The base-type of the list
 */
public class MapList<E> implements List<E> {

	/**
	 * The internal representation (can be any implementation of map).
	 */
	private Map<Integer, E> internal;
	int size = 0;

	/**
	 * Constructor that is given the internal representation. From a software
	 * development perspective, that's a bad idea (breaks encapsulation), but for
	 * the purpose of this project it allows us to parameterize this class by what
	 * implementation of Map we use. (Maybe in a future version we'll use reflection
	 * instead).
	 */
	public MapList() {
		this.internal = new ArrayMap<Integer, E>();

	}

	/**
	 * Return an iterator over this collection (remove() is unsupported, nor is
	 * concurrent modification checked).
	 */
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int pos = 0;

			public boolean hasNext() {
				return pos < size;
			}

			public E next() {
				if (hasNext())
					return internal.get(pos++);
				throw new IndexOutOfBoundsException();
			}
		};
	}

	/**
	 * Append the specified element to the end of this list. This increases the size
	 * by one.
	 * 
	 * @param element
	 *            The element to be appended
	 */
	public void add(E element) {
		internal.put(size, element);
		size++;
	}

	/**
	 * Replace the element at the specified position in this list with the specified
	 * element. If the index is invalid, an IndexOutOfBoundsException is thrown.
	 * 
	 * @param index
	 *            The index of the element to return
	 * @param element
	 *            The element at the specified position
	 */
	public void set(int index, E element) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		internal.put(index, element);
	}

	/**
	 * Retrieve the element at the specified position in this list. If the index is
	 * invalid, an IndexOutOfBoundsException is thrown.
	 * 
	 * @param index
	 *            The index of the element to return
	 * @return The element at the specified position
	 */
	public E get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		return internal.get(index);
	}

	/**
	 * Insert a new item at the specified position, shifting the item already at the
	 * position and everything after it over one position. If the index is equal to
	 * the length of the list, then this is equivalent to the add method. If the
	 * index is negative or is greater than the length, an IndexOutOfBoundsException
	 * is thrown.
	 * 
	 * @param index
	 *            The index into which to insert the element
	 * @param element
	 *            The element which to insert
	 */
	public void insert(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();

		// shift everything over to make room for the new item
		int position;
		for (position = size - 1; position >= index; position--) {
			E shift = internal.get(position);
			internal.put(position + 1, shift);
		}

		// put the new item in the array and increase size
		internal.put(index, element);
		size++;
	}

	/**
	 * Remove (and return) the element at the specified position. This reduces the
	 * size of the list by one and, if necessary, shifts other elements over. If the
	 * index is invalid, an IndexOutOfBoundsException is thrown.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The item removed
	 */
	public E remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		// item to remove
		E removed = internal.get(index);

		// if item is not in the list return null
		if (removed == null)
			return null;

		// find the item and takes it out, shifting all items after it over one
		for (int position = index; position < size - 1; position++) {
			E shift = internal.get(position + 1);
			internal.put(position, shift);
		}

		// take out the last element in the internal array and decrease size
		internal.remove(size - 1);
		size--;
		return removed;
	}

	/**
	 * Return the number of elements in this list.
	 * 
	 * @return The number of elements in this list.
	 */
	public int size() {
		return size;
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
