package exercicio04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeCounter {
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static int countPrimesAtomic(int start, int end, int threads) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = start; i <= end; i++) {
            int num = i;
            executor.submit(() -> {
                if (isPrime(num)) {
                    count.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return count.get();
    }

    public static int countPrimesSyncMethod(int start, int end, int threads) throws InterruptedException {
        final Counter counter = new Counter();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = start; i <= end; i++) {
            int num = i;
            executor.submit(() -> {
                if (isPrime(num)) {
                    counter.incrementSyncMethod();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return counter.getCount();
    }

    public static int countPrimesSyncBlock(int start, int end, int threads) throws InterruptedException {
        final Counter counter = new Counter();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = start; i <= end; i++) {
            int num = i;
            executor.submit(() -> {
                if (isPrime(num)) {
                    counter.incrementSyncBlock();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return counter.getCount();
    }

    static class Counter {
        private int count = 0;

        public synchronized void incrementSyncMethod() {
            count++;
        }

        public void incrementSyncBlock() {
            synchronized (this) {
                count++;
            }
        }

        public int getCount() {
            return count;
        }
    }
}