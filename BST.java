import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BST<K extends Comparable<? super K>, V> {
    public static final class Entry<K, V> {
        private final K key;
        private final V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + " -> " + value;
        }
    }

    private static final class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> root;
    private int size;

    public void insert(K studentId, V student) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        Objects.requireNonNull(student, "student must not be null");
        root = insert(root, studentId, student);
    }

    private Node<K, V> insert(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }

        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = insert(node.left, key, value);
        } else if (comparison > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public V search(K studentId) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        Node<K, V> node = root;
        while (node != null) {
            int comparison = studentId.compareTo(node.key);
            if (comparison == 0) {
                return node.value;
            }
            node = comparison < 0 ? node.left : node.right;
        }
        return null;
    }

    public boolean containsKey(K studentId) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        Node<K, V> node = root;
        while (node != null) {
            int comparison = studentId.compareTo(node.key);
            if (comparison == 0) {
                return true;
            }
            node = comparison < 0 ? node.left : node.right;
        }
        return false;
    }

    public boolean delete(K studentId) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        if (!containsKey(studentId)) {
            return false;
        }
        root = delete(root, studentId);
        size--;
        return true;
    }

    private Node<K, V> delete(Node<K, V> node, K key) {
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = delete(node.left, key);
        } else if (comparison > 0) {
            node.right = delete(node.right, key);
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            Node<K, V> successor = minimum(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = delete(node.right, successor.key);
        }
        return node;
    }

    private Node<K, V> minimum(Node<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Entry<K, V>> inorderTraversal() {
        List<Entry<K, V>> entries = new ArrayList<>(size);
        inorderTraversal(root, entries);
        return Collections.unmodifiableList(entries);
    }

    private void inorderTraversal(Node<K, V> node, List<Entry<K, V>> entries) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, entries);
        entries.add(new Entry<>(node.key, node.value));
        inorderTraversal(node.right, entries);
    }

    public void display() {
        for (Entry<K, V> entry : inorderTraversal()) {
            System.out.println(entry);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
