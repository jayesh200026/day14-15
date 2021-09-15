package com.linkedstock;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<Node<T>> {
	Node<T> head;

	public LinkedList() {
		head = null;
	}

	/**
	 * @param data value of the node Adds a node at the end of linked list
	 */
	public void add(T data) {

		if (head == null) {
			head = new Node<T>(data);
			return;
		}

		Node<T> newNode = new Node<T>(data);
		Node<T> temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = newNode;
	}

	/**
	 * @param data value of node Deletes a node with value=data from linked list
	 */
	public void remove(T data) {
		if (head == null) {
			System.out.println("List is empty");
		}

		Node<T> temp = head;
		Node<T> prev = temp;

		if (head.data == data) {
			head = head.next;
			return;
		}

		while (temp != null) {
			if (temp.data == data) {
				prev.next = temp.next;
				break;
			}
			prev = temp;
			temp = temp.next;
		}
	}

	/**
	 * @param index position of node in linked list
	 * @return a node who is at position=index
	 */
	public Node<T> get(int index) {
		int count = 0;
		if (head == null) {
			System.out.println("List is empty");
		}

		Node<T> temp = head;
		while (count != index) {
			temp = temp.next;
			if (temp == null) {
				System.out.println("Not found");
			}
			count++;
		}

		return temp;
	}

	@Override
	public Iterator iterator() {
		return new LinkedListIterator<T>(head);
	}

}
