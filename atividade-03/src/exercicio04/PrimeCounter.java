package exercicio04;

import java.util.ArrayList;
import java.util.List;

public class PrimeCounter {
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static int countPrimesAtomic(int start, int end, int threads) throws InterruptedException {
        AtomicCounter count = new AtomicCounter();
        List<Thread> threadList = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            final int threadId = t;
            Thread thread = new Thread(() -> {
                for (int i = start + threadId; i <= end; i += threads) {
                    if (isPrime(i)) {
                        count.increment();
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        return count.getCount();
    }

    public static int countPrimesSyncMethod(int start, int end, int threads) throws InterruptedException {
        Counter counter = new Counter();
        List<Thread> threadList = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            final int threadId = t;
            Thread thread = new Thread(() -> {
                for (int i = start + threadId; i <= end; i += threads) {
                    if (isPrime(i)) {
                        counter.incrementSyncMethod();
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        return counter.getCount();
    }

    public static int countPrimesSyncBlock(int start, int end, int threads) throws InterruptedException {
        Counter counter = new Counter();
        List<Thread> threadList = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            final int threadId = t;
            Thread thread = new Thread(() -> {
                for (int i = start + threadId; i <= end; i += threads) {
                    if (isPrime(i)) {
                        counter.incrementSyncBlock();
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

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

    static class AtomicCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}
