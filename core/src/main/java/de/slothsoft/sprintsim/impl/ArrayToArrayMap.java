package de.slothsoft.sprintsim.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

public class ArrayToArrayMap<K, V> implements Iterable<Entry<K, V>> {

	class MapIterator implements Iterator<Entry<K, V>> {

		int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return this.currentIndex < ArrayToArrayMap.this.keys.size();
		}

		@Override
		public Entry<K, V> next() {
			try {
				final K key = ArrayToArrayMap.this.keys.get(this.currentIndex);
				return new MapEntry<>(key, getValue(key));
			} finally {
				this.currentIndex++;
			}
		}
	}

	static class MapEntry<K, V> implements Entry<K, V> {

		private final K key;
		private final V value;

		public MapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException("Cannot set value!"); //$NON-NLS-1$
		}

		@Override
		public V getValue() {
			return this.value;
		}

	}

	private String elementName = "element"; //$NON-NLS-1$
	final List<K> keys;
	final K[] keysAsArray;
	V[] values;

	@SuppressWarnings("unchecked")
	public ArrayToArrayMap(K... keysAsArray) {
		this.keysAsArray =  Objects.requireNonNull(keysAsArray);
		this.keys = Arrays.asList(keysAsArray);
	}

	public K[] getKeys() {
		return Arrays.copyOf(keysAsArray, keysAsArray.length);
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new MapIterator();
	}

	public V getValue(K key) {
		final int index = this.keys.indexOf(key);
		if (index < 0)
			throw new IllegalArgumentException("A " + this.elementName + " with key " + key + " could not be found!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return this.values == null ? null : this.values[index];
	}

	public V[] getValues() {
		return this.values;
	}

	@SuppressWarnings("unchecked")
	public ArrayToArrayMap<K, V> values(V... newValues) {
		setValues(newValues);
		return this;
	}

	@SuppressWarnings("unchecked")
	public void setValues(V... values) {
		if (values != null && values.length != this.keys.size())
			throw new IllegalArgumentException(
					"Only " + this.keys.size() + " " + this.elementName + "s are allowed, but was: " + values.length); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		this.values = values;
	}

	public String getElementName() {
		return this.elementName;
	}

	public ArrayToArrayMap<K, V> elementName(String newElementName) {
		setElementName(newElementName);
		return this;
	}

	public void setElementName(String elementName) {
		this.elementName = Objects.requireNonNull(elementName);
	}

}
