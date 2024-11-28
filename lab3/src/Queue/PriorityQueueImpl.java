package Queue;

import java.util.PriorityQueue;

public class PriorityQueueImpl<T extends Comparable<T>> implements Queue<T> {
    private PriorityQueue<T> queue;

    public PriorityQueueImpl() {
        queue = new PriorityQueue<>();
    }

    @Override
    public void enqueue(T item) {
        queue.add(item);  // Add in the right spot based on importance.
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return queue.poll();  // Take out the most important
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return queue.peek();  // Look at the most important
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
