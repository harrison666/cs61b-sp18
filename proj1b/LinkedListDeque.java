public class LinkedListDeque<T>implements Deque<T> {
    private class StuffNode {
        private T item;
        private StuffNode prev;
        private StuffNode next;

        public StuffNode(T i, StuffNode p, StuffNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        sentinel.next = new StuffNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        sentinel.prev = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StuffNode temp = sentinel;
        while (temp.next.item != null) {
            System.out.print(temp.next.item);
            System.out.print(" ");
            temp = temp.next;
        }
    }

    @Override
    public T removeFirst() {
        T res = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        if (!isEmpty()) {
            size -= 1;
        }
        return res;
    }

    @Override
    public T removeLast() {
        T res = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        if (!isEmpty()) {
            size -= 1;
        }
        return res;
    }

    @Override
    public T get(int index) {
        StuffNode temp = sentinel;
        while (index >= 0 && temp.next != null) {
            if (index == 0) {
                return temp.next.item;
            }
            index -= 1;
            temp = temp.next;
        }
        return null;
    }

    private T getRecursive(int index, StuffNode curr) {
        if (index == 0) {
            return curr.item;
        }
        return getRecursive(index - 1, curr.next);
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }



}
