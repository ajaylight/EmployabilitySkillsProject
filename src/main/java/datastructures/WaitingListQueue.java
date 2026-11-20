package datastructures;

import model.WaitListEntry;

/**
 * A simple FIFO queue implemented using a linked list.
 * The first customer who joins the waiting list is served first.
 */
public class WaitingListQueue {

    // Each node stores one waiting-list entry and a link to the next node.
    private static class Node {
        WaitListEntry data;
        Node next;

        Node(WaitListEntry data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int count;

    /**
     * Creates an empty waiting-list queue.
     */
    public WaitingListQueue() {
        front = null;
        rear = null;
        count = 0;
    }

    /**
     * Adds a customer's request at the end of the queue.
     */
    public void enqueue(WaitListEntry entry) {
        Node newNode = new Node(entry);

        if (rear == null) {
            // The queue is empty, so the new node is both front and rear.
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        count++;
    }

    /**
     * Removes and returns the first request in the queue.
     * Returns null when the queue is empty.
     */
    public WaitListEntry dequeue() {
        if (front == null) {
            return null;
        }

        WaitListEntry entry = front.data;
        front = front.next;

        // If the last node was removed, rear must also become null.
        if (front == null) {
            rear = null;
        }

        count--;
        return entry;
    }

    /**
     * Returns the first request without removing it.
     */
    public WaitListEntry peek() {
        if (front == null) {
            return null;
        }

        return front.data;
    }

    /**
     * Checks whether the queue has no waiting requests.
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Returns the number of requests currently in the queue.
     */
    public int getCount() {
        return count;
    }
}
