public class LinkedListDeque<T> {
    private class StuffNode {
        public T item;
        public StuffNode prev;
        public StuffNode next;


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

    public void addFirst(T item) {
        sentinel.next = new StuffNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StuffNode temp = sentinel;
        while (temp.next.item != null) {
            System.out.print(temp.next.item);
            System.out.print(" ");
            temp = temp.next;
        }
    }

    public T removeFirst() {
        T res = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return res;
    }

    public T removeLast() {
        T res = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return res;
    }

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
        if (index== 0) {
            return curr.item;
        }
        return getRecursive(index - 1, curr.next);
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }



}
