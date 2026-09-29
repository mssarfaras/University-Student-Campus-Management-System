package com.university.campus.datastructures;

import com.university.campus.model.ActionLog;

/**
 * Custom LIFO Stack implementation for system action history & recent logs.
 * Solves Requirement 3.
 */
public class CustomStack {

    private static class Node {
        ActionLog data;
        Node next;

        Node(ActionLog data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes an ActionLog onto the stack.
     */
    public void push(ActionLog log) {
        if (log == null) return;
        Node newNode = new Node(log);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Pops and returns the top ActionLog from the stack.
     */
    public ActionLog pop() {
        if (isEmpty()) {
            return null;
        }
        ActionLog data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Peeks at the top ActionLog without removing it.
     */
    public ActionLog peek() {
        return isEmpty() ? null : top.data;
    }

    /**
     * Returns true if stack is empty.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns number of elements in stack.
     */
    public int size() {
        return size;
    }

    /**
     * Displays all action logs from most recent to oldest.
     */
    public void displayHistory() {
        if (isEmpty()) {
            System.out.println("  [!] Action history stack is empty. No recent actions logged.");
            return;
        }

        System.out.println("===============================================================================");
        System.out.println("                         RECENT ACTIONS HISTORY (STACK - LIFO)                ");
        System.out.println("===============================================================================");

        Node current = top;
        int count = 1;
        while (current != null) {
            System.out.printf(" %2d. %s\n", count++, current.data.toString());
            current = current.next;
        }
        System.out.println("===============================================================================");
        System.out.println("  Total Recent Action Logs: " + size);
    }
}
