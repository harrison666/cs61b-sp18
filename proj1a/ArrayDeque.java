public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int first;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = items.length;
    }
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size + first - items.length);
        System.arraycopy(items, first, a, first, items.length - first);
        items = a;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[first - 1] = item;
        size += 1;
        first -= 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size + first - items.length] = item;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = first; i < items.length; i++) {
            System.out.print(items[i] + " ");

        }
        for (int i = 0; i < size + first - items.length; i++) {
            System.out.print(items[i] + " ");
        }
    }

    public T removeFirst() {
        T res = items[first];
        items[first] = null;
        first += 1;
        size -= 1;
        if (items.length >= 16 && size < items.length/4) {
            resize(size / 2);
        }
        return res;
    }

    public T removeLast() {
        T res = items[size + first - items.length - 1];
        items[size + first - items.length - 1] = null;
        size -= 1;
        if (items.length >= 16 && size < items.length/4) {
            resize(size / 2);
        }
        return res;
    }

    public T get(int index) {
        if (index >= size ) {
            return null;
        }
        if (index < items.length - first) {
            return items[index + first];
        }
        return items[index + first - items.length - 1];
    }


}
