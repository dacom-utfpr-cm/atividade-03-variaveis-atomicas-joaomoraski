package exercicio02;


import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
        this.next = null;
    }
}

public class NonBlockingStack<T> {
    private AtomicReference<Node<T>> head = new AtomicReference<>(null);

    public void push(T t) {
        Node<T> newHead = new Node<>(t);
        Node<T> currentHead;
        do {
            currentHead = head.get();
            newHead.next = currentHead;
        } while (!head.compareAndSet(currentHead, newHead));
    }

    public T pop() {
        Node<T> currentHead;
        Node<T> nextHead;
        do {
            currentHead = head.get();
            if (currentHead == null) {
                return null;
            }
            nextHead = currentHead.next;
        } while (!head.compareAndSet(currentHead, nextHead));
        return currentHead.value;
    }
}
