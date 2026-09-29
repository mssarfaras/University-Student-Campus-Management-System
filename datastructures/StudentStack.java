package com.university.campus.datastructures;

import com.university.campus.model.ActionLog;

/**
 * LIFO stack implementation for system action history and recent logs.
 */
public class StudentStack {

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

    public StudentStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(ActionLog log) {
        if (log == null) return;
        Node newNode = new Node(log);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public ActionLog pop() {
        if (isEmpty()) {
            return null;
        }
        ActionLog data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public ActionLog peek() {
        return isEmpty() ? null : top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void display() {
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
