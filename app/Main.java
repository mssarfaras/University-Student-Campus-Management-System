package com.university.campus.app;

import com.university.campus.datastructures.CampusGraph;
import com.university.campus.datastructures.CustomLinkedList;
import com.university.campus.datastructures.ServiceQueue;
import com.university.campus.datastructures.StudentStack;
import com.university.campus.datastructures.StudentBST;
import com.university.campus.datastructures.StudentHashTable;
import com.university.campus.model.ActionLog;
import com.university.campus.model.ServiceRequest;
import com.university.campus.model.Student;

import java.util.Scanner;

/**
 * Main application class providing a menu-driven console interface for managing
 * university student records and campus location networks.
 */
public class Main {

    private static CustomLinkedList studentLinkedList = new CustomLinkedList();
    private static StudentStack actionStack = new StudentStack();
    private static ServiceQueue serviceQueue = new ServiceQueue();
    private static StudentBST studentBST = new StudentBST();
    private static StudentHashTable studentHashTable = new StudentHashTable(16);
    private static CampusGraph campusGraph = new CampusGraph();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Pre-populate system with realistic sample data
        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Select an option (1-16): ");
            System.out.println();

            switch (choice) {
                case 1:
                    addStudentRecord();
                    break;
                case 2:
                    updateStudentRecord();
                    break;
                case 3:
                    deleteStudentRecord();
                    break;
                case 4:
                    displayAllRecordsLinkedList();
                    break;
                case 5:
                    addServiceRequestToQueue();
                    break;
                case 6:
                    processNextServiceRequest();
                    break;
                case 7:
                    displayRecentActionsStack();
                    break;
                case 8:
                    displayStudentsBST();
                    break;
                case 9:
                    searchStudentHashing();
                    break;
                case 10:
                    addCampusLocation();
                    break;
                case 11:
                    removeCampusLocation();
                    break;
                case 12:
                    addCampusConnection();
                    break;
                case 13:
                    removeCampusConnection();
                    break;
                case 14:
                    displayCampusConnections();
                    break;
                case 15:
                    traverseCampusLocations();
                    break;
                case 16:
                    System.out.println("===============================================================================");
                    System.out.println("   Thank you for using University Campus Management System. Exiting...        ");
                    System.out.println("===============================================================================");
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Invalid selection! Please enter a number between 1 and 16.");
            }
            if (running) {
                System.out.println("\nPress Enter to return to main menu...");
                scanner.nextLine();
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===============================================================================");
        System.out.println("        UNIVERSITY STUDENT RECORD & CAMPUS NETWORK MANAGEMENT SYSTEM           ");
        System.out.println("===============================================================================");
        System.out.println("  --- STUDENT RECORD MANAGEMENT ---");
        System.out.println("  1. Add Student Record");
        System.out.println("  2. Update Student Record");
        System.out.println("  3. Delete Student Record");
        System.out.println("  4. Display All Records using Linked List");
        System.out.println("  8. Display Students using BST/AVL (In-Order Sorted)");
        System.out.println("  9. Search Student using Hashing (O(1) Lookup)");
        System.out.println();
        System.out.println("  --- SERVICE REQUESTS & HISTORY ---");
        System.out.println("  5. Add Service Request to Queue");
        System.out.println("  6. Process Next Service Request");
        System.out.println("  7. Display Recent Actions using Stack (Undo/History)");
        System.out.println();
        System.out.println("  --- CAMPUS NETWORK & GRAPH ---");
        System.out.println("  10. Add Campus Location");
        System.out.println("  11. Remove Campus Location");
        System.out.println("  12. Add Campus Connection/Road");
        System.out.println("  13. Remove Campus Connection/Road");
        System.out.println("  14. Display Campus Connections");
        System.out.println("  15. Traverse Campus Locations using BFS or DFS");
        System.out.println();
        System.out.println("  16. Exit");
        System.out.println("===============================================================================");
    }

    // =========================================================================
    // 1. ADD STUDENT RECORD
    // =========================================================================
    private static void addStudentRecord() {
        System.out.println(">>> [1] ADD STUDENT RECORD <<<");
        String id = readNonEmptyString("Enter Student ID (e.g. S1001): ").toUpperCase();

        if (studentLinkedList.search(id) != null) {
            System.out.println("  [!] Error: Student ID '" + id + "' already exists in system!");
            return;
        }

        String name = readNonEmptyString("Enter Student Name: ");
        String programme = readNonEmptyString("Enter Degree Programme: ");
        double marks = readDoubleInput("Enter Student Marks (0.0 to 100.0): ", 0.0, 100.0);

        Student student = new Student(id, name, programme, marks);

        // Synchronize across data structures
        studentLinkedList.add(student);
        studentBST.insert(student);
        studentHashTable.put(id, student);

        // Log action on stack
        ActionLog log = new ActionLog("ADD", "Added student record for " + name, id);
        actionStack.push(log);

        System.out.println("  [+] Success: Student record added successfully across all data structures!");
    }

    // =========================================================================
    // 2. UPDATE STUDENT RECORD
    // =========================================================================
    private static void updateStudentRecord() {
        System.out.println(">>> [2] UPDATE STUDENT RECORD <<<");
        if (studentLinkedList.isEmpty()) {
            System.out.println("  [!] No student records available to update.");
            return;
        }

        String id = readNonEmptyString("Enter Student ID to Update: ").toUpperCase();
        Student existing = studentLinkedList.search(id);

        if (existing == null) {
            System.out.println("  [!] Error: Student ID '" + id + "' not found.");
            return;
        }

        System.out.println("  Current Record: " + existing);
        System.out.println("  (Leave field blank to keep current value)");

        System.out.print("Enter New Name [" + existing.getName() + "]: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) newName = existing.getName();

        System.out.print("Enter New Programme [" + existing.getProgramme() + "]: ");
        String newProgramme = scanner.nextLine().trim();
        if (newProgramme.isEmpty()) newProgramme = existing.getProgramme();

        System.out.print("Enter New Marks [" + existing.getMarks() + "] (or press Enter to keep): ");
        String marksStr = scanner.nextLine().trim();
        double newMarks = existing.getMarks();
        if (!marksStr.isEmpty()) {
            try {
                double val = Double.parseDouble(marksStr);
                if (val >= 0 && val <= 100) {
                    newMarks = val;
                } else {
                    System.out.println("  [!] Invalid marks entered. Keeping original marks: " + existing.getMarks());
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input format. Keeping original marks.");
            }
        }

        // Update in Linked List
        studentLinkedList.update(id, newName, newProgramme, newMarks);

        // Re-sync in BST & Hash Table
        Student updatedStudent = studentLinkedList.search(id);
        studentHashTable.put(id, updatedStudent);

        ActionLog log = new ActionLog("UPDATE", "Updated details for student " + newName, id);
        actionStack.push(log);

        System.out.println("  [+] Success: Student record updated successfully!");
    }

    // =========================================================================
    // 3. DELETE STUDENT RECORD
    // =========================================================================
    private static void deleteStudentRecord() {
        System.out.println(">>> [3] DELETE STUDENT RECORD <<<");
        if (studentLinkedList.isEmpty()) {
            System.out.println("  [!] No student records available to delete.");
            return;
        }

        String id = readNonEmptyString("Enter Student ID to Delete: ").toUpperCase();
        Student deleted = studentLinkedList.delete(id);

        if (deleted == null) {
            System.out.println("  [!] Error: Student ID '" + id + "' not found.");
            return;
        }

        // Remove from BST and Hash Table
        studentBST.delete(id);
        studentHashTable.remove(id);

        // Log on Stack
        ActionLog log = new ActionLog("DELETE", "Deleted record of " + deleted.getName(), id);
        actionStack.push(log);

        System.out.println("  [-] Success: Removed student record [" + id + " - " + deleted.getName() + "]");
    }

    // =========================================================================
    // 4. DISPLAY ALL RECORDS USING LINKED LIST
    // =========================================================================
    private static void displayAllRecordsLinkedList() {
        System.out.println(">>> [4] DISPLAY ALL RECORDS (LINKED LIST) <<<");
        studentLinkedList.displayAll();
    }

    // =========================================================================
    // 5. ADD SERVICE REQUEST TO QUEUE
    // =========================================================================
    private static void addServiceRequestToQueue() {
        System.out.println(">>> [5] ADD SERVICE REQUEST TO QUEUE <<<");
        String studentId = readNonEmptyString("Enter Student ID making request: ").toUpperCase();

        if (studentLinkedList.search(studentId) == null) {
            System.out.println("  [!] Warning: Student ID '" + studentId + "' is not registered in system, but proceeding with request.");
        }

        System.out.println("Select Request Type:");
        System.out.println(" 1. Transcript Request");
        System.out.println(" 2. Student ID Card Renewal");
        System.out.println(" 3. Course Enrollment Inquiry");
        System.out.println(" 4. Financial Aid / Scholarship Inquiry");
        System.out.println(" 5. Other General Service");
        int typeChoice = readIntInput("Choice (1-5): ");

        String typeStr;
        switch (typeChoice) {
            case 1: typeStr = "Transcript Request"; break;
            case 2: typeStr = "ID Card Renewal"; break;
            case 3: typeStr = "Course Enrollment"; break;
            case 4: typeStr = "Financial Aid"; break;
            default: typeStr = "General Service"; break;
        }

        String description = readNonEmptyString("Enter Request Description/Details: ");

        ServiceRequest request = new ServiceRequest(studentId, typeStr, description);
        serviceQueue.enqueue(request);

        System.out.println("  [+] Success: Service Request [" + request.getRequestId() + "] added to queue!");
    }

    // =========================================================================
    // 6. PROCESS NEXT SERVICE REQUEST
    // =========================================================================
    private static void processNextServiceRequest() {
        System.out.println(">>> [6] PROCESS NEXT SERVICE REQUEST (QUEUE) <<<");
        if (serviceQueue.isEmpty()) {
            System.out.println("  [!] Queue is empty. No pending service requests to process.");
            return;
        }

        ServiceRequest processed = serviceQueue.dequeue();
        System.out.println("  [✓] PROCESSED SERVICE REQUEST:");
        System.out.println("  " + processed);

        ActionLog log = new ActionLog("PROCESS_REQ", "Processed request " + processed.getRequestId(), processed.getStudentId());
        actionStack.push(log);
    }

    // =========================================================================
    // 7. DISPLAY RECENT ACTIONS USING STACK
    // =========================================================================
    private static void displayRecentActionsStack() {
        System.out.println(">>> [7] RECENT SYSTEM ACTIONS (STACK HISTORY) <<<");
        actionStack.display();
    }

    // =========================================================================
    // 8. DISPLAY STUDENTS USING BST/AVL
    // =========================================================================
    private static void displayStudentsBST() {
        System.out.println(">>> [8] DISPLAY STUDENTS USING BST (IN-ORDER SORTED BY ID) <<<");
        studentBST.displayInOrder();
    }

    // =========================================================================
    // 9. SEARCH STUDENT USING HASHING
    // =========================================================================
    private static void searchStudentHashing() {
        System.out.println(">>> [9] SEARCH STUDENT USING HASHING (O(1) LOOKUP) <<<");
        if (studentHashTable.isEmpty()) {
            System.out.println("  [!] System has no student records.");
            return;
        }

        String id = readNonEmptyString("Enter Student ID to Search: ").toUpperCase();
        long startTime = System.nanoTime();
        Student student = studentHashTable.get(id);
        long endTime = System.nanoTime();

        if (student != null) {
            System.out.println("  [✓] STUDENT FOUND (Lookup Time: " + (endTime - startTime) + " ns):");
            System.out.println("  " + student);
        } else {
            System.out.println("  [!] Student with ID '" + id + "' was not found in Hash Table.");
        }
    }

    // =========================================================================
    // 10. ADD CAMPUS LOCATION
    // =========================================================================
    private static void addCampusLocation() {
        System.out.println(">>> [10] ADD CAMPUS LOCATION <<<");
        String location = readNonEmptyString("Enter New Campus Location Name: ");

        if (campusGraph.addLocation(location)) {
            System.out.println("  [+] Location '" + location.trim() + "' successfully added to campus graph!");
        } else {
            System.out.println("  [!] Error: Location '" + location.trim() + "' already exists or is invalid.");
        }
    }

    // =========================================================================
    // 11. REMOVE CAMPUS LOCATION
    // =========================================================================
    private static void removeCampusLocation() {
        System.out.println(">>> [11] REMOVE CAMPUS LOCATION <<<");
        String location = readNonEmptyString("Enter Location Name to Remove: ");

        if (campusGraph.removeLocation(location)) {
            System.out.println("  [-] Location '" + location.trim() + "' and all connected roads removed!");
        } else {
            System.out.println("  [!] Error: Location '" + location.trim() + "' not found in graph.");
        }
    }

    // =========================================================================
    // 12. ADD CAMPUS CONNECTION / ROAD
    // =========================================================================
    private static void addCampusConnection() {
        System.out.println(">>> [12] ADD CAMPUS CONNECTION / ROAD <<<");
        String loc1 = readNonEmptyString("Enter Source Location Name: ");
        String loc2 = readNonEmptyString("Enter Destination Location Name: ");
        double dist = readDoubleInput("Enter Distance/Weight (in meters): ", 1.0, 10000.0);

        if (campusGraph.addConnection(loc1, loc2, dist)) {
            System.out.println("  [+] Connection added: [" + loc1 + "] <---> [" + loc2 + "] (" + dist + "m)");
        } else {
            System.out.println("  [!] Error: Failed to add connection. Ensure both locations exist, are distinct, and not already connected.");
        }
    }

    // =========================================================================
    // 13. REMOVE CAMPUS CONNECTION / ROAD
    // =========================================================================
    private static void removeCampusConnection() {
        System.out.println(">>> [13] REMOVE CAMPUS CONNECTION / ROAD <<<");
        String loc1 = readNonEmptyString("Enter Source Location Name: ");
        String loc2 = readNonEmptyString("Enter Destination Location Name: ");

        if (campusGraph.removeConnection(loc1, loc2)) {
            System.out.println("  [-] Connection removed between [" + loc1 + "] and [" + loc2 + "].");
        } else {
            System.out.println("  [!] Error: No connection found between [" + loc1 + "] and [" + loc2 + "].");
        }
    }

    // =========================================================================
    // 14. DISPLAY CAMPUS CONNECTIONS
    // =========================================================================
    private static void displayCampusConnections() {
        System.out.println(">>> [14] DISPLAY CAMPUS CONNECTIONS <<<");
        campusGraph.displayConnections();
    }

    // =========================================================================
    // 15. TRAVERSE CAMPUS LOCATIONS USING BFS OR DFS
    // =========================================================================
    private static void traverseCampusLocations() {
        System.out.println(">>> [15] TRAVERSE CAMPUS LOCATIONS (GRAPH TRAVERSAL) <<<");
        if (campusGraph.getLocations().isEmpty()) {
            System.out.println("  [!] Graph is empty. No campus locations to traverse.");
            return;
        }

        System.out.println(" Available Locations: " + campusGraph.getLocations());
        String startLoc = readNonEmptyString("Enter Starting Location for Traversal: ");

        if (!campusGraph.hasLocation(startLoc)) {
            System.out.println("  [!] Error: Location '" + startLoc + "' does not exist in graph.");
            return;
        }

        System.out.println("Select Traversal Algorithm:");
        System.out.println(" 1. Breadth-First Search (BFS)");
        System.out.println(" 2. Depth-First Search (DFS)");
        System.out.println(" 3. Run Both Traversals");
        int choice = readIntInput("Choice (1-3): ");

        switch (choice) {
            case 1:
                campusGraph.bfsTraversal(startLoc);
                break;
            case 2:
                campusGraph.dfsTraversal(startLoc);
                break;
            case 3:
                campusGraph.bfsTraversal(startLoc);
                campusGraph.dfsTraversal(startLoc);
                break;
            default:
                System.out.println("  [!] Invalid traversal choice.");
        }
    }

    // =========================================================================
    // SAMPLE DATA POPULATION
    // =========================================================================
    private static void seedSampleData() {
        // Sample Students
        Student s1 = new Student("S1001", "Alice Smith", "Software Eng", 88.5);
        Student s2 = new Student("S1002", "Bob Johnson", "Computer Science", 92.0);
        Student s3 = new Student("S1003", "Charlie Brown", "Data Science", 74.0);
        Student s4 = new Student("S1004", "Diana Prince", "Cyber Security", 95.5);
        Student s5 = new Student("S1005", "Evan Wright", "Information Tech", 81.0);

        Student[] initialStudents = {s1, s2, s3, s4, s5};
        for (Student s : initialStudents) {
            studentLinkedList.add(s);
            studentBST.insert(s);
            studentHashTable.put(s.getStudentId(), s);
        }

        // Action Logs
        actionStack.push(new ActionLog("SYSTEM_INIT", "System initialized with sample data", "N/A"));
        actionStack.push(new ActionLog("ADD", "Populated sample student records", "S1001-S1005"));

        // Service Requests
        serviceQueue.enqueue(new ServiceRequest("S1001", "Transcript Request", "Official academic transcript for internship"));
        serviceQueue.enqueue(new ServiceRequest("S1002", "ID Card Renewal", "Lost student ID card during campus event"));
        serviceQueue.enqueue(new ServiceRequest("S1004", "Scholarship Inquiry", "Applying for Dean's Honor List scholarship"));

        // Campus Graph Locations
        campusGraph.addLocation("Main Gate");
        campusGraph.addLocation("Library");
        campusGraph.addLocation("IT Complex");
        campusGraph.addLocation("Student Center");
        campusGraph.addLocation("Hostel A");
        campusGraph.addLocation("Sports Complex");

        // Campus Graph Roads
        campusGraph.addConnection("Main Gate", "Library", 250);
        campusGraph.addConnection("Main Gate", "Student Center", 300);
        campusGraph.addConnection("Library", "IT Complex", 150);
        campusGraph.addConnection("Student Center", "IT Complex", 200);
        campusGraph.addConnection("Student Center", "Hostel A", 400);
        campusGraph.addConnection("IT Complex", "Sports Complex", 350);
        campusGraph.addConnection("Hostel A", "Sports Complex", 180);
    }

    // =========================================================================
    // HELPER INPUT VALIDATORS
    // =========================================================================
    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double readDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double val = Double.parseDouble(input);
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.printf("  [!] Value out of bounds. Please enter a value between %.1f and %.1f.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  [!] Field cannot be empty. Please try again.");
        }
    }
}
