package com.university.campus.datastructures;

import com.university.campus.model.Student;

/**
 * Custom Singly Linked List implementation for managing Student records.
 * Solves Requirement 2 & 12.
 */
public class CustomLinkedList {

    private static class Node {
        Student data;
        Node next;

        Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds a student record to the end of the list.
     * Returns true if successful, false if student with same ID already exists.
     */
    public boolean add(Student student) {
        if (student == null || student.getStudentId() == null) {
            return false;
        }
        if (search(student.getStudentId()) != null) {
            return false; // Duplicate Student ID
        }

        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    /**
     * Searches for a student by ID.
     */
    public Student search(String studentId) {
        if (studentId == null || head == null) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId.trim())) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Updates an existing student's record.
     */
    public boolean update(String studentId, String newName, String newProgramme, double newMarks) {
        Student student = search(studentId);
        if (student == null) {
            return false;
        }
        if (newName != null && !newName.trim().isEmpty()) {
            student.setName(newName.trim());
        }
        if (newProgramme != null && !newProgramme.trim().isEmpty()) {
            student.setProgramme(newProgramme.trim());
        }
        if (newMarks >= 0 && newMarks <= 100) {
            student.setMarks(newMarks);
        }
        return true;
    }

    /**
     * Deletes a student record by ID and returns the deleted Student object.
     */
    public Student delete(String studentId) {
        if (studentId == null || head == null) {
            return null;
        }

        String targetId = studentId.trim();

        if (head.data.getStudentId().equalsIgnoreCase(targetId)) {
            Student removed = head.data;
            head = head.next;
            size--;
            return removed;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.getStudentId().equalsIgnoreCase(targetId)) {
                Student removed = current.next.data;
                current.next = current.next.next;
                size--;
                return removed;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Returns total number of students in list.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Displays all student records in tabular format.
     */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("  [!] Linked List is empty. No student records found.");
            return;
        }

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(String.format("%-5s | %-10s | %-25s | %-20s | %-8s", "No.", "Student ID", "Name", "Programme", "Marks"));
        System.out.println("-------------------------------------------------------------------------------");

        Node current = head;
        int count = 1;
        while (current != null) {
            Student s = current.data;
            System.out.println(String.format("%-5d | %-10s | %-25s | %-20s | %-8.2f",
                    count++, s.getStudentId(), s.getName(), s.getProgramme(), s.getMarks()));
            current = current.next;
        }
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("  Total Records (Linked List): " + size);
    }
}
