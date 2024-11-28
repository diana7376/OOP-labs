package Queue;

public class LinkedQueue<T> implements Queue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {
            this.data = data;  // Store data in this node.
        }
    }

    private Node<T> front, rear;
    private int size;

    @Override
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear != null) rear.next = newNode;  // Tie the new data to the last one.
        rear = newNode;  // The new data becomes the last one.
        if (front == null) front = rear;  // If the box was empty, make this the front too.
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        T item = front.data;  // Take out at the front.
        front = front.next;   // Move the front to the next data
        if (front == null) rear = null;  // If no left, there's no rear.
        size--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}
