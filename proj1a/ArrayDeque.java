public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = items.length - 1;
        nextLast = 0;
    }
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, nextLast);
        System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 + a.length - items.length, items.length - nextFirst - 1);
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
        for (int i = 1; i <= size; i++) {
            System.out.print(items[plusOne(nextFirst + i)] + " ");
        }
    }

    public int plusOne(int i) {
        if (i + 1 < items.length) {
            return i + 1;
        }
        return i + 1 - items.length;
    }

    public int minusOne(int i) {
        if (i - 1 >= 0) {
            return i - 1;
        }
        return i - 1 + items.length;
    }


    public T removeFirst() {
        if (!isEmpty()) {
            return null;
        }
        T res = items[plusOne(nextFirst)];
        items[plusOne(nextFirst)] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;
        if (items.length >= 16 && size < items.length / 4) {
            resize(size / 2);
        }
        return res;
    }

    public T removeLast() {
        T res = items[minusOne(nextLast)];
        items[minusOne(nextLast)] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        if (items.length >= 16 && size < items.length / 4) {
            resize(size / 2);
        }
        return res;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (nextFirst + 1 + index >= items.length) {
            return items[nextFirst + 1 + index - items.length];
        }
        return items[nextFirst + 1 + index];
    }


}
