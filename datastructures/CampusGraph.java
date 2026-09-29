package com.university.campus.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Custom Graph data structure representing campus locations (vertices) and roads/paths (edges).
 * Adjacency List implementation with support for BFS and DFS traversals.
 * Solves Requirements 7, 8, 9, 10, 11.
 */
public class CampusGraph {

    public static class Edge {
        private String destination;
        private double distance; // Distance in meters or km

        public Edge(String destination, double distance) {
            this.destination = destination;
            this.distance = distance;
        }

        public String getDestination() {
            return destination;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return String.format("%s (%.1fm)", destination, distance);
        }
    }

    private Map<String, List<Edge>> adjacencyList;

    public CampusGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a campus location (vertex).
     */
    public boolean addLocation(String locationName) {
        if (locationName == null || locationName.trim().isEmpty()) {
            return false;
        }
        String name = locationName.trim();
        if (adjacencyList.containsKey(name)) {
            return false; // Location already exists
        }
        adjacencyList.put(name, new ArrayList<>());
        return true;
    }

    /**
     * Removes a campus location (vertex) and all connecting roads/edges.
     */
    public boolean removeLocation(String locationName) {
        if (locationName == null) return false;
        String name = locationName.trim();
        if (!adjacencyList.containsKey(name)) {
            return false;
        }

        // Remove the vertex
        adjacencyList.remove(name);

        // Remove incoming edges from other vertices
        for (List<Edge> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getDestination().equalsIgnoreCase(name));
        }

        return true;
    }

    /**
     * Adds a bidirectional road/connection between two campus locations.
     */
    public boolean addConnection(String loc1, String loc2, double distance) {
        if (loc1 == null || loc2 == null || distance <= 0) {
            return false;
        }
        String name1 = loc1.trim();
        String name2 = loc2.trim();

        if (name1.equalsIgnoreCase(name2)) {
            return false; // Cannot connect location to itself
        }

        if (!adjacencyList.containsKey(name1) || !adjacencyList.containsKey(name2)) {
            return false; // One or both locations do not exist
        }

        // Check if edge already exists
        List<Edge> edges1 = adjacencyList.get(name1);
        for (Edge e : edges1) {
            if (e.getDestination().equalsIgnoreCase(name2)) {
                return false; // Connection already exists
            }
        }

        // Add bidirectional edge
        adjacencyList.get(name1).add(new Edge(name2, distance));
        adjacencyList.get(name2).add(new Edge(name1, distance));
        return true;
    }

    /**
     * Removes a road/connection between two campus locations.
     */
    public boolean removeConnection(String loc1, String loc2) {
        if (loc1 == null || loc2 == null) return false;
        String name1 = loc1.trim();
        String name2 = loc2.trim();

        if (!adjacencyList.containsKey(name1) || !adjacencyList.containsKey(name2)) {
            return false;
        }

        boolean removed1 = adjacencyList.get(name1).removeIf(e -> e.getDestination().equalsIgnoreCase(name2));
        boolean removed2 = adjacencyList.get(name2).removeIf(e -> e.getDestination().equalsIgnoreCase(name1));

        return removed1 || removed2;
    }

    /**
     * Checks if a location exists.
     */
    public boolean hasLocation(String locationName) {
        return locationName != null && adjacencyList.containsKey(locationName.trim());
    }

    /**
     * Gets all locations in the graph.
     */
    public Set<String> getLocations() {
        return adjacencyList.keySet();
    }

    /**
     * Displays all campus locations and connected roads (Adjacency List format).
     */
    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("  [!] Campus graph is empty. No locations added.");
            return;
        }

        System.out.println("===============================================================================");
        System.out.println("                   CAMPUS LOCATION NETWORK (ADJACENCY LIST)                   ");
        System.out.println("===============================================================================");
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            String loc = entry.getKey();
            List<Edge> edges = entry.getValue();
            System.out.printf(" Location [%-20s] --> Connected to: %s\n",
                    loc, edges.isEmpty() ? "(No direct connections)" : edges.toString());
        }
        System.out.println("===============================================================================");
        System.out.println("  Total Campus Locations: " + adjacencyList.size());
    }

    /**
     * Performs Breadth-First Search (BFS) traversal starting from a location.
     */
    public void bfsTraversal(String startLocation) {
        if (startLocation == null || !hasLocation(startLocation)) {
            System.out.println("  [!] Error: Starting location not found in campus network.");
            return;
        }

        String start = startLocation.trim();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        System.out.println("\n-------------------------------------------------------------------------------");
        System.out.println(" Breadth-First Search (BFS) Traversal starting from: [" + start + "]");
        System.out.println("-------------------------------------------------------------------------------");

        List<String> traversalOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            traversalOrder.add(current);

            for (Edge edge : adjacencyList.get(current)) {
                String neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        System.out.println(" Order of Visit: " + String.join(" -> ", traversalOrder));
        System.out.println(" Total Visited Locations: " + traversalOrder.size());
        System.out.println("-------------------------------------------------------------------------------");
    }

    /**
     * Performs Depth-First Search (DFS) traversal starting from a location.
     */
    public void dfsTraversal(String startLocation) {
        if (startLocation == null || !hasLocation(startLocation)) {
            System.out.println("  [!] Error: Starting location not found in campus network.");
            return;
        }

        String start = startLocation.trim();
        Set<String> visited = new HashSet<>();
        List<String> traversalOrder = new ArrayList<>();

        dfsHelper(start, visited, traversalOrder);

        System.out.println("\n-------------------------------------------------------------------------------");
        System.out.println(" Depth-First Search (DFS) Traversal starting from: [" + start + "]");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(" Order of Visit: " + String.join(" -> ", traversalOrder));
        System.out.println(" Total Visited Locations: " + traversalOrder.size());
        System.out.println("-------------------------------------------------------------------------------");
    }

    private void dfsHelper(String current, Set<String> visited, List<String> traversalOrder) {
        visited.add(current);
        traversalOrder.add(current);

        for (Edge edge : adjacencyList.get(current)) {
            String neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited, traversalOrder);
            }
        }
    }
}
