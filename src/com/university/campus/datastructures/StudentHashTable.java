package com.university.campus.datastructures;

import com.university.campus.model.Student;

/**
 * Custom Hash Table using separate chaining for O(1) average time student searching.
 * Solves Requirement 6.
 */
public class StudentHashTable {

    private static class HashNode {
        String key;
        Student value;
        HashNode next;

        HashNode(String key, Student value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private HashNode[] buckets;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public StudentHashTable(int capacity) {
        this.capacity = capacity > 0 ? capacity : 16;
        this.buckets = new HashNode[this.capacity];
        this.size = 0;
    }

    public StudentHashTable() {
        this(16);
    }

    /**
     * Custom hash function for string keys.
     */
    private int getBucketIndex(String key) {
        if (key == null) return 0;
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) {
            hashCode = 31 * hashCode + Character.toLowerCase(key.charAt(i));
        }
        return Math.abs(hashCode) % capacity;
    }

    /**
     * Puts a student into the hash table. Updates if key exists.
     */
    public void put(String key, Student value) {
        if (key == null || value == null) return;
        String normalizedKey = key.trim().toUpperCase();
        int bucketIndex = getBucketIndex(normalizedKey);

        HashNode head = buckets[bucketIndex];
        while (head != null) {
            if (head.key.equalsIgnoreCase(normalizedKey)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        // Insert at beginning of bucket chain
        HashNode newNode = new HashNode(normalizedKey, value);
        newNode.next = buckets[bucketIndex];
        buckets[bucketIndex] = newNode;
        size++;
    }

    /**
     * Retrieves a student by Student ID in O(1) average time.
     */
    public Student get(String key) {
        if (key == null) return null;
        String normalizedKey = key.trim().toUpperCase();
        int bucketIndex = getBucketIndex(normalizedKey);

        HashNode head = buckets[bucketIndex];
        while (head != null) {
            if (head.key.equalsIgnoreCase(normalizedKey)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * Removes a student by key from the hash table.
     */
    public Student remove(String key) {
        if (key == null) return null;
        String normalizedKey = key.trim().toUpperCase();
        int bucketIndex = getBucketIndex(normalizedKey);

        HashNode head = buckets[bucketIndex];
        HashNode prev = null;

        while (head != null) {
            if (head.key.equalsIgnoreCase(normalizedKey)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    buckets[bucketIndex] = head.next;
                }
                size--;
                return head.value;
            }
            prev = head;
            head = head.next;
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Displays bucket distribution and table contents.
     */
    public void displayTable() {
        if (isEmpty()) {
            System.out.println("  [!] Hash Table is empty.");
            return;
        }

        System.out.println("===============================================================================");
        System.out.println("                      HASH TABLE STORAGE & BUCKETS SUMMARY                     ");
        System.out.println("===============================================================================");
        for (int i = 0; i < capacity; i++) {
            HashNode current = buckets[i];
            if (current != null) {
                System.out.printf(" Bucket [%2d]: ", i);
                while (current != null) {
                    System.out.printf("[%s -> %s] ", current.key, current.value.getName());
                    current = current.next;
                }
                System.out.println();
            }
        }
        System.out.println("===============================================================================");
        System.out.println("  Total Hash Table Entries: " + size + " | Capacity: " + capacity);
    }
}
