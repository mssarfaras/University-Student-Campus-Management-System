package com.university.campus.datastructures;

import com.university.campus.model.ServiceRequest;

/**
 * FIFO queue implementation for managing student service requests.
 */
public class ServiceQueue {

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

    public ServiceQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

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

    public ServiceRequest peek() {
        return isEmpty() ? null : front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void displayPendingRequests() {
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
