package Service;

public class Semaphore {
    private final java.util.concurrent.Semaphore semaphore;

    public Semaphore(int permits) {
        this.semaphore = new java.util.concurrent.Semaphore(permits);
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release() {
        semaphore.release();
    }
}
