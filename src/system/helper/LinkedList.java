package system.helper;
/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */
public class LinkedList<T> implements ILinkedList<T>{

	private Node<T> head = null;
	private Node<T> tail = null;
	private Integer size = 0;
	@Override
	public Integer size() {
		return size;
	}
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	@Override
	public void addFirst(T t) {
		head= new Node<>(head,t);
		if(size==0)
		{
			tail= head;
			size++;
		}
			
	}
	@Override
	public void addLast(T t) {
		Node<T> newNode= new Node<>(null,t);
		if(isEmpty())
		{
			head=newNode;
		}else
		{
			tail.setNext(newNode);
			size++;
		}
	}
	@Override
	public T removeFirst() {
		if(isEmpty())
		{
			return null;
		}
			
		T first = head.getElement();
		head= head.getNext();
		size--;
		if(size==0)
		{
			tail=null;
		}
		
		return first;

	}
	@Override
	public T first() {
		if(isEmpty())
		{
			return null;
		}
		
		return head.getElement();
	}
	@Override
	public T last() {
		if(isEmpty())
		{
			return null;
		}
		
		return tail.getElement();
	}
	
	
}
