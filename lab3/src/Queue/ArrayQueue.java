package Queue;

public class ArrayQueue<T> implements Queue<T> {
    private T[] items;
    private int front, rear, size;

    public ArrayQueue(int capacity) {
        items =(T[]) new Object[capacity];
        front = 0;  // Where we take out
        rear = 0;   // Where we add
        size = 0;
    }

    @Override
    public void enqueue(T item) {
        if (size == items.length) throw new IllegalStateException("Queue is full");
        items[rear] = item;   // Put a new in the next available spot
        rear = (rear + 1) % items.length;  // Move the rear to the next spot.
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        T item = (T) items[front];  // Take out from the front.
        items[front] = null;        // Clear that spot.
        front = (front + 1) % items.length;  // Move the front to the next spot.
        size--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return (T) items[front];
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
