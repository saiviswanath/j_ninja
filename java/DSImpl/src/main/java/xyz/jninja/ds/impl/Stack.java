package xyz.jninja.ds.impl;
import java.util.EmptyStackException;

public class Stack<E> {
	private Object[] items;
	private int top;
	
	public Stack() {
		this.items = new Object[3];
		this.top = 0;
	}
	
	public Stack(int n) {
		this.items = new Object[n];
		this.top = 0;
	}

	public int getTotalStackSize() {
		return this.items.length;
	}

	public int getStackFreeSpace() {
		return this.getTotalStackSize() - this.top;
	}

	public int getCurrentStackSize() {
		return this.top;
	}

	public boolean isEmpty() {
		return (this.top == 0);
	}
	
	private void resize() {
		Object[] newStackArray = new Object[this.getTotalStackSize() * 2];
		for (int i = 0; i < this.getTotalStackSize(); i++) {
			newStackArray[i] = this.items[i];
		}
		this.items = newStackArray;
	}

	public void push(Object obj) {
		if (this.getStackFreeSpace() == 0) {
			this.resize();
		}

		this.items[this.top++] = obj;
	}

	public Object pop() throws EmptyStackException {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		Object popObject = this.items[--this.top];
		return popObject;
	}
}
