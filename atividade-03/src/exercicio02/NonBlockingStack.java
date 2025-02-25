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
            currentHead = head.get(); // Pega o valor da head
            newHead.next = currentHead; // Faz a head atual apontar para o newHead
        } while (!head.compareAndSet(currentHead, newHead)); // Tenta trocar o currentHead e o newHead de forma atomica
    }

    public T pop() {
        Node<T> currentHead;
        Node<T> nextHead;
        do {
            currentHead = head.get(); // Peg a head atual, se for null é pq nao tem nada
            if (currentHead == null) {
                return null;
            }
            nextHead = currentHead.next; // A proxima head é obtida da sequencia da current
        } while (!head.compareAndSet(currentHead, nextHead)); // Tenta trocar o valor seguinte pelo valor atual, ate conseguir
        return currentHead.value;
    }
}
