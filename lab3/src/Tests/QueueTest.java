package Tests;
import Queue.*;
import Queue.Queue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueueTest {
    @org.junit.Test
    @Test
    public void testArrayQueue() {
        Queue<Integer> queue = new ArrayQueue<Integer>(5);
        queue.enqueue(1);
        queue.enqueue(2);
        Assertions.assertEquals(2, queue.size());  // Check if there are 2
        Assertions.assertEquals(1, queue.dequeue());  // Make sure the first taken out is the first one added.
        Assertions.assertEquals(1, queue.size());  // There should be 1 left.
    }

    @Test
    public void testLinkedQueue() {
        Queue<String> queue = new LinkedQueue<String>();
        queue.enqueue("A");
        queue.enqueue("B");
        Assertions.assertEquals("A", queue.peek());  // Peek to see that the front is "A".
        Assertions.assertEquals("A", queue.dequeue());  // Take out "A".
        Assertions.assertFalse(queue.isEmpty());  // The box should still have more data
    }

    @Test
    public void testPriorityQueue() {
        Queue<Integer> queue = new PriorityQueueImpl<Integer>();
        queue.enqueue(10);
        queue.enqueue(5);
        queue.enqueue(20);
        Assertions.assertEquals(5, queue.dequeue());  // The smallest (most important) should come out first.
        Assertions.assertEquals(10, queue.peek());  // Next should be 10.
    }
}
