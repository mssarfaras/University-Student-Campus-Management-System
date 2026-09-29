package com.university.campus.app;

import java.io.ByteArrayInputStream;

/**
 * Automated test runner that executes all 16 menu features non-interactively.
 */
public class TestDriver {
    public static void main(String[] args) {
        // Construct simulated input stream for options 1 to 16
        StringBuilder inputs = new StringBuilder();

        // 1. Add Student
        inputs.append("1\nS1006\nEve Adams\nCyber Security\n89.5\n\n");

        // 2. Update Student S1001
        inputs.append("2\nS1001\nAlice Smith-Jones\nSoftware Engineering\n95.0\n\n");

        // 3. Delete Student S1003
        inputs.append("3\nS1003\n\n");

        // 4. Display All Records (Linked List)
        inputs.append("4\n\n");

        // 5. Add Service Request
        inputs.append("5\nS1002\n2\nRequesting duplicate card after loss\n\n");

        // 6. Process Service Request
        inputs.append("6\n\n");

        // 7. Display Recent Actions (Stack)
        inputs.append("7\n\n");

        // 8. Display Students (BST)
        inputs.append("8\n\n");

        // 9. Search Student (Hashing)
        inputs.append("9\nS1002\n\n");

        // 10. Add Campus Location
        inputs.append("10\nScience Lab\n\n");

        // 11. Remove Campus Location
        inputs.append("11\nHostel A\n\n");

        // 12. Add Campus Connection
        inputs.append("12\nLibrary\nScience Lab\n120\n\n");

        // 13. Remove Campus Connection
        inputs.append("13\nMain Gate\nLibrary\n\n");

        // 14. Display Campus Connections
        inputs.append("14\n\n");

        // 15. Traverse Campus Locations (BFS & DFS)
        inputs.append("15\nLibrary\n3\n\n");

        // 16. Exit
        inputs.append("16\n");

        System.setIn(new ByteArrayInputStream(inputs.toString().getBytes()));

        System.out.println("===============================================================================");
        System.out.println("               AUTOMATED COMPREHENSIVE TEST RUNNER (OPTIONS 1-16)             ");
        System.out.println("===============================================================================");
        
        Main.main(new String[0]);
    }
}
