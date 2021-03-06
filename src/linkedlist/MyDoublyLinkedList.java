package linkedlist;

import java.util.NoSuchElementException;

public class MyDoublyLinkedList<T> implements MyList<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException();
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException();
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void add(T obj) {
        Node<T> node = new Node(obj);
        if (first == null) {
            first = node;
            last = node;
            size++;
            return;
        }
        node.previous = last;
        last.next = node;
        last = node;
        size++;
    }

    @Override
    public void remove(int index) {
        T value = get(index);
        remove(value);
    }

    @Override
    public boolean remove(T obj) {
        if (first.value.equals(obj)) {
            first.next.previous = null;
            first = first.next;
            size--;
            return true;
        }
        Node<T> current = first;
        for (int i = 0; i < size() - 1; i++) {
            if (current.next.value.equals(obj)) {
                if (i == size - 2) {
                    current.next = null;
                    last = current;
                } else {
                    current.next.next.previous = current.next.previous;
                    current.next = current.next.next;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T obj) {
        Node<T> current = first;
        while (current != null) {
            if (current.value == obj)
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public void addFirst(T obj) {
        Node<T> temp = first;
        Node<T> newNode = new Node<T>(obj, null, temp);
        first = newNode;
        if (temp == null) { //check if list is empty
            last = newNode;
        } else {
            temp.previous = newNode;
        }
        size++;
    }

    @Override
    public void addLast(T obj) {
        Node<T> temp = last;
        Node<T> newNode = new Node<T>(obj, temp, null);
        last = newNode;
        if (temp == null) {//check if list is empty
            first = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    @Override
    public T removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Node<T> temp = first;
        T returnedItem = temp.value;
        Node<T> next = temp.next;
        temp.value = null;
        temp.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.previous = null;
        }
        size--;
        return returnedItem;
    }

    @Override
    public T removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Node<T> temp = last;
        T returnedItem = temp.value;
        Node<T> prev = temp.previous;
        temp.value = null;
        temp.previous = null;
        last = temp;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return returnedItem;
    }

    @Override
    public void push(T obj) {
        addFirst(obj);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean offerFirst(T obj) {
        addFirst(obj);
        return true;
    }

    @Override
    public boolean offerLast(T obj) {
        addLast(obj);
        return true;
    }

    @Override
    public T poll() {
        if (first == null) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T pollFirst() {
        if (first == null) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T pollLast() {
        if (last == null) {
            return null;
        }
        return removeLast();
    }

    private class Node<T> {
        private T value;
        private Node previous;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            previous = prev;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        MyDoublyLinkedList.Node current = first;
        for (int i = 0; i < size; i++) {
            result.append(current.value);
            if (i < size - 1) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public MyList.ListIterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements MyList.ListIterator<T> {
        private Node<T> current = first;
        private Node<T> tail = last;
        private int cursor;

        public ListIterator() {};

        public ListIterator(int size) {
            cursor = size();
        }

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T temp = current.value;
            current = current.next;
            cursor++;
            return temp;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            T temp = tail.value;
            tail = tail.previous;
            cursor--;
            return temp;
        }
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new DescendingIterator();
    }

    private class DescendingIterator implements Iterator<T> {
        private MyList.ListIterator<T> itr = new ListIterator(size());

        @Override
        public boolean hasNext() {
            return itr.hasPrevious();
        }

        @Override
        public T next() {
            return itr.previous();
        }
    }
}
