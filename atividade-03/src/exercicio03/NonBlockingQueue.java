package exercicio03;

import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    T value;
    AtomicReference<Node<T>> next;

    Node(T value) {
        this.value = value;
        this.next = new AtomicReference<>(null);
    }
}

public class NonBlockingQueue<T> {
    private AtomicReference<Node<T>> head, tail;

    public NonBlockingQueue() {
        Node<T> dummy = new Node<>(null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);
        while (true) {
            Node<T> currentTail = tail.get();  // Pega a tail atual
            Node<T> tailNext = currentTail.next.get(); // pega a tail seguinte

            if (currentTail == tail.get()) { // Verifica se ainda é a mesma, para caso de alguem ter alterado ja
                if (tailNext != null) { // Se a proxima nao for nula, significa que alguem alterou a seguinte, entao
                    tail.compareAndSet(currentTail, tailNext); // precisamos trocar a currentTail para tailNext
                } else if (currentTail.next.compareAndSet(null, newNode)) { // Se não, ele adiciona o novo nó no fim
                    tail.compareAndSet(currentTail, newNode); // Entao troca o current para ser o novo no
                    return;
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> headNext = currentHead.next.get();

            if (currentHead == head.get()) {
                if (currentHead == currentTail) {
                    if (headNext == null) {
                        return null;
                    }
                    tail.compareAndSet(currentTail, headNext);
                } else {
                    T value = headNext.value;
                    if (head.compareAndSet(currentHead, headNext)) {
                        return value;
                    }
                }
            }
        }
    }
}