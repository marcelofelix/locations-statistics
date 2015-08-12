package com.vivareal.locations.statistic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ListConsumer<E> {
	private LinkedList<E> list = new LinkedList<>();
	private int size = 10;

	public ListConsumer(Collection<E> list) {
		this.list.addAll(list);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public List<E> consume() {
		List<E> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (list.isEmpty()) {
				break;
			}
			result.add(list.pollFirst());
		}
		return result;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Integer getLeft() {
		return list.size();
	}

}
