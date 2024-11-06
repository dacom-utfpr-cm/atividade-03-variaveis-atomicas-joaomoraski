import exercicio01.NonBlocking;
import exercicio02.NonBlockingStack;
import exercicio03.NonBlockingQueue;
import exercicio04.PrimeCounter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Teste do Gerador de Números Sequenciais Não Bloqueante:");
        NonBlocking counter = new NonBlocking();
        for (int i = 0; i < 5; i++) {
            System.out.println("Número sequencial: " + counter.getNext());
        }

        System.out.println("\nTeste da Pilha Não Bloqueante:");
        NonBlockingStack<Integer> stack = new NonBlockingStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());

        System.out.println("\nTeste da Fila Não Bloqueante:");
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Dequeue: " + queue.dequeue());

        System.out.println("\nTeste do Contador de Números Primos com Threads:");
        int start = 1;
        int end = 10000;
        int threads = 4;

        // Contagem usando Atomic
        long startTime = System.nanoTime();
        int atomicCount = PrimeCounter.countPrimesAtomic(start, end, threads);
        long endTime = System.nanoTime();
        System.out.println("Contagem com Atomic: " + atomicCount + ", Tempo: " + (endTime - startTime) / 1_000_000 + " ms");

        // Contagem usando método sincronizado
        startTime = System.nanoTime();
        int syncMethodCount = PrimeCounter.countPrimesSyncMethod(start, end, threads);
        endTime = System.nanoTime();
        System.out.println("Contagem com Método Sincronizado: " + syncMethodCount + ", Tempo: " + (endTime - startTime) / 1_000_000 + " ms");

        // Contagem usando bloco sincronizado
        startTime = System.nanoTime();
        int syncBlockCount = PrimeCounter.countPrimesSyncBlock(start, end, threads);
        endTime = System.nanoTime();
        System.out.println("Contagem com Bloco Sincronizado: " + syncBlockCount + ", Tempo: " + (endTime - startTime) / 1_000_000 + " ms");
    }
}
