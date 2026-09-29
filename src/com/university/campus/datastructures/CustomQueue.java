package com.university.campus.datastructures;

import com.university.campus.model.ServiceRequest;

/**
 * Custom FIFO Queue implementation for managing student service requests.
 * Solves Requirement 4.
 */
public class CustomQueue {

    private static class Node {
        ServiceRequest data;
        Node next;

        Node(ServiceRequest data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Adds a new service request to the end of the queue.
     */
    public void enqueue(ServiceRequest request) {
        if (request == null) return;
        Node newNode = new Node(request);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Dequeues and returns the next pending service request (FIFO order).
     */
    public ServiceRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        ServiceRequest data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * Peeks at the next service request without removing it.
     */
    public ServiceRequest peek() {
        return isEmpty() ? null : front.data;
    }

    /**
     * Checks if queue is empty.
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Returns size of queue.
     */
    public int size() {
        return size;
    }

    /**
     * Displays all pending service requests in arrival order.
     */
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("  [!] Queue is empty. No pending student service requests.");
            return;
        }

        System.out.println("===============================================================================");
        System.out.println("                   PENDING STUDENT SERVICE REQUESTS (QUEUE - FIFO)            ");
        System.out.println("===============================================================================");

        Node current = front;
        int position = 1;
        while (current != null) {
            System.out.printf(" Position [%d]:\n %s\n", position++, current.data.toString());
            System.out.println("-------------------------------------------------------------------------------");
            current = current.next;
        }
        System.out.println("  Total Pending Requests: " + size);
    }
}
