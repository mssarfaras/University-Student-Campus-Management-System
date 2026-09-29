package com.university.campus.datastructures;

import com.university.campus.model.Student;

/**
 * Custom Binary Search Tree (BST) for organizing and searching student records by Student ID.
 * Solves Requirement 5.
 */
public class StudentBST {

    private static class BSTNode {
        Student data;
        BSTNode left;
        BSTNode right;

        BSTNode(Student data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;
    private int count;

    public StudentBST() {
        this.root = null;
        this.count = 0;
    }

    /**
     * Inserts a student record into the BST.
     */
    public boolean insert(Student student) {
        if (student == null || student.getStudentId() == null) {
            return false;
        }
        if (search(student.getStudentId()) != null) {
            return false; // Duplicate ID
        }
        root = insertRecursive(root, student);
        count++;
        return true;
    }

    private BSTNode insertRecursive(BSTNode node, Student student) {
        if (node == null) {
            return new BSTNode(student);
        }

        int cmp = student.getStudentId().compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertRecursive(node.left, student);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, student);
        }
        return node;
    }

    /**
     * Searches for a student by ID in the BST.
     */
    public Student search(String studentId) {
        if (studentId == null) return null;
        BSTNode res = searchRecursive(root, studentId.trim());
        return res != null ? res.data : null;
    }

    private BSTNode searchRecursive(BSTNode node, String studentId) {
        if (node == null) return null;
        int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
        if (cmp == 0) return node;
        if (cmp < 0) return searchRecursive(node.left, studentId);
        return searchRecursive(node.right, studentId);
    }

    /**
     * Deletes a student from the BST by Student ID.
     */
    public boolean delete(String studentId) {
        if (studentId == null || search(studentId) == null) {
            return false;
        }
        root = deleteRecursive(root, studentId.trim());
        count--;
        return true;
    }

    private BSTNode deleteRecursive(BSTNode node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRecursive(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRecursive(node.right, studentId);
        } else {
            // Node to delete found
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Node with two children: find min in right subtree
            BSTNode minNode = findMin(node.right);
            node.data = minNode.data;
            node.right = deleteRecursive(node.right, minNode.data.getStudentId());
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    /**
     * Displays all student records in-order (sorted by Student ID).
     */
    public void displayInOrder() {
        if (isEmpty()) {
            System.out.println("  [!] BST is empty.");
            return;
        }
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-25s | %-20s | %-8s", "Student ID", "Name", "Programme", "Marks"));
        System.out.println("-------------------------------------------------------------------------------");
        inOrderHelper(root);
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("  Total BST Records: " + count);
    }

    private void inOrderHelper(BSTNode node) {
        if (node != null) {
            inOrderHelper(node.left);
            Student s = node.data;
            System.out.println(String.format("%-10s | %-25s | %-20s | %-8.2f",
                    s.getStudentId(), s.getName(), s.getProgramme(), s.getMarks()));
            inOrderHelper(node.right);
        }
    }
}
