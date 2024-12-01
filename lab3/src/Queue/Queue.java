package Queue;

public interface Queue<T> {
    void enqueue(T item);   // Add
    T dequeue();           // Take out
    T peek();              // Look at the first
    boolean isEmpty();     // Check if is empty.
    int size();
}

