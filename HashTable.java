import java.util.Objects;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;

    private static final class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node<K, V>[] buckets;
    private int size;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initialCapacity must be positive");
        }
        buckets = (Node<K, V>[]) new Node<?, ?>[initialCapacity];
    }

    public void insert(K studentId, V student) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        Objects.requireNonNull(student, "student must not be null");

        int index = bucketIndex(studentId, buckets.length);
        for (Node<K, V> node = buckets[index]; node != null; node = node.next) {
            if (node.key.equals(studentId)) {
                node.value = student;
                return;
            }
        }

        if ((size + 1) > buckets.length * MAX_LOAD_FACTOR) {
            resize();
            index = bucketIndex(studentId, buckets.length);
        }
        buckets[index] = new Node<>(studentId, student, buckets[index]);
        size++;
    }

    public V search(K studentId) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        for (Node<K, V> node = buckets[bucketIndex(studentId, buckets.length)];
             node != null;
             node = node.next) {
            if (node.key.equals(studentId)) {
                return node.value;
            }
        }
        return null;
    }

    public boolean containsKey(K studentId) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        for (Node<K, V> node = buckets[bucketIndex(studentId, buckets.length)];
             node != null;
             node = node.next) {
            if (node.key.equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    public boolean delete(K studentId) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        int index = bucketIndex(studentId, buckets.length);
        Node<K, V> previous = null;
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(studentId)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public void display() {
        for (int i = 0; i < buckets.length; i++) {
            StringBuilder chain = new StringBuilder();
            for (Node<K, V> node = buckets[i]; node != null; node = node.next) {
                if (chain.length() > 0) {
                    chain.append(" -> ");
                }
                chain.append(node.key).append(" -> ").append(node.value);
            }
            System.out.println(i + ": [" + chain + "]");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int bucketIndex(K key, int capacity) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        if (buckets.length > Integer.MAX_VALUE / 2) {
            throw new IllegalStateException("Hash table capacity limit reached");
        }
        Node<K, V>[] oldBuckets = buckets;
        buckets = (Node<K, V>[]) new Node<?, ?>[oldBuckets.length * 2];
        for (Node<K, V> bucket : oldBuckets) {
            for (Node<K, V> node = bucket; node != null; node = node.next) {
                int index = bucketIndex(node.key, buckets.length);
                buckets[index] = new Node<>(node.key, node.value, buckets[index]);
            }
        }
    }
}
