package exercicio01;

import java.util.concurrent.atomic.AtomicLong;

public class NonBlocking {
    private AtomicLong counter = new AtomicLong(0);

    public long getNext() {
        long current, next;
        do {
            current = counter.get();
            next = current + 1;
        } while (!counter.compareAndSet(current, next));

        return next;
    }
}