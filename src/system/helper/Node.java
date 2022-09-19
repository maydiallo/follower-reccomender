package system.helper;
/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */

public class Node<T> {
	T element;
	Node<T> next;
	
	public Node(Node<T> next, T element) {
		this.next = next;
		this.element = element;
	}
	
	public void setElement(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
	public void setNext(Node<T> n) {
		this.next = n;
	}
	
}