public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        // Java does not allow to create new generic array directly. So need cast.
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = items.length - 1;
        nextLast = 0;
    }
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, nextLast);
        System.arraycopy(items, nextFirst + 1,
                         a, nextFirst + 1 + a.length - items.length,
                  items.length - nextFirst - 1);
        items = a;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[plusOne(nextFirst + i)] + " ");
        }
    }

    private int plusOne(int i) {
        return (i + 1) % items.length;
    }

    private int minusOne(int i) {
        return (i - 1 + items.length) % items.length;
    }


    public T removeFirst() {
        nextFirst = plusOne(nextFirst);
        T res = items[nextFirst];
        items[nextFirst] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(size / 2);
        }
        return res;
    }

    public T removeLast() {
        nextLast = minusOne(nextLast);
        T res = items[nextLast];
        items[nextLast] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(size / 2);
        }
        return res;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }


}
