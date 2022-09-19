package system.helper;
/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */
public interface ILinkedList<T> {
	public Integer size();
	public boolean isEmpty();
	public void addFirst(T item);
	public void addLast(T item);
	public T removeFirst();
	public T first();
	public T last();
}
