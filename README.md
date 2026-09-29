# University Student Record & Campus Network Management System

A comprehensive, zero-dependency Java Console Application designed to manage university student records and campus location networks using custom data structures (Singly Linked List, LIFO Stack, FIFO Queue, Binary Search Tree, Hash Table with Separate Chaining, and Graph with BFS/DFS traversals).

---

## 👥 Group Member Information & Responsibilities

| Member Name | Student ID | Designated Module / Responsibility | Key Contributions |
| :--- | :--- | :--- | :--- |
| **Member 1** | *[Insert ID]* | Linked List Implementation & Student Record Management | Implemented `CustomLinkedList.java`, student CRUD logic (Add, Update, Delete, Search), and tabular display formatting. |
| **J. Nisath** | 23da2-727 | Stack & Queue Implementation & Service Request Management | Implemented `StudentStack.java` for action history/undo tracking and `ServiceQueue.java` for service request processing. |
| **Member 3** | *[Insert ID]* | Binary Search Tree & Hash Table Implementation | Implemented `StudentBST.java` (In-Order, Pre-Order traversals) and `StudentHashTable.java` for $O(1)$ lookup by ID. |
| **Member 4** | 23da2-0684 | Graph Implementation & Campus Network Traversal | Implemented `CampusGraph.java` (Adjacency List), vertex/edge management, and BFS & DFS graph traversals. |
| **All Members** | *All IDs* | System Integration, Testing, Validation & Documentation | Integration of all components in `Main.java`, input validation, testing, code comments, and final README documentation. |

---

## 🏗️ Architecture & Data Structures Overview

### 1. Singly Linked List (`CustomLinkedList.java`)
- **Purpose**: Dynamic storage and management of student records.
- **Operations**: `add()`, `update()`, `delete()`, `search()`, `displayAll()`.
- **Complexity**: Search/Delete $O(N)$, Append $O(N)$ or $O(1)$.

### 2. LIFO Action Stack (`StudentStack.java`)
- **Purpose**: Maintains a history log of recent system operations for audit trails and undo capabilities.
- **Operations**: `push()`, `pop()`, `peek()`, `isEmpty()`, `display()`.
- **Complexity**: Push/Pop $O(1)$.

### 3. FIFO Service Request Queue (`ServiceQueue.java`)
- **Purpose**: Manages incoming student service requests (e.g., transcript issuing, ID card renewals) in order of arrival.
- **Operations**: `enqueue()`, `dequeue()`, `peek()`, `isEmpty()`, `displayPendingRequests()`.
- **Complexity**: Enqueue/Dequeue $O(1)$.

### 4. Binary Search Tree (`StudentBST.java`)
- **Purpose**: Keeps student records sorted by Student ID for ordered inspection.
- **Operations**: `insert()`, `delete()`, `search()`, `displayInOrder()`.
- **Complexity**: Search/Insert $O(\log N)$ average.

### 5. Hash Table (`StudentHashTable.java`)
- **Purpose**: Provides high-efficiency $O(1)$ average time student lookup by Student ID using custom string hash function and separate chaining bucket lists.
- **Operations**: `put()`, `get()`, `remove()`, `displayTable()`.
- **Complexity**: Lookup/Insert $O(1)$ average time.

### 6. Campus Network Graph (`CampusGraph.java`)
- **Purpose**: Models campus locations as vertices and connecting roads/paths as weighted edges using an Adjacency List.
- **Operations**:
  - `addLocation()`, `removeLocation()`
  - `addConnection()`, `removeConnection()`
  - `displayConnections()`
  - `bfsTraversal()` (Breadth-First Search)
  - `dfsTraversal()` (Depth-First Search)

---

## 📋 System Requirements Mapping (1-14)

| Requirement | Implementation Detail | Status |
| :--- | :--- | :---: |
| **1. Student Record Fields** | ID, Name, Programme, Marks in `Student.java` | ✅ Complete |
| **2. Linked List** | Storage and display via `CustomLinkedList.java` | ✅ Complete |
| **3. Stack History** | Action logging via `StudentStack.java` | ✅ Complete |
| **4. Service Queue** | Arrival-ordered processing via `ServiceQueue.java` | ✅ Complete |
| **5. BST / AVL Tree** | ID-ordered tree structure via `StudentBST.java` | ✅ Complete |
| **6. Hash Table** | $O(1)$ lookup searching via `StudentHashTable.java` | ✅ Complete |
| **7. Campus Graph** | Locations & connections via `CampusGraph.java` | ✅ Complete |
| **8. Graph Representation** | Adjacency List with edge weights in `CampusGraph.java` | ✅ Complete |
| **9. Location & Connection Ops** | Add/remove location, add/remove connection | ✅ Complete |
| **10. Display Campus Network** | Adjacency list display showing connected neighbors | ✅ Complete |
| **11. Graph Traversals** | Both BFS and DFS traversals implemented | ✅ Complete |
| **12. Student CRUD Operations** | Add, Update, Delete, Search, Display operations | ✅ Complete |
| **13. Menu Interface & Validation** | 16-choice CLI menu with robust validation | ✅ Complete |
| **14. Error & Edge Case Handling** | Handles duplicates, missing records, invalid marks (0-100), non-existent graph nodes, empty data structures | ✅ Complete |

---

## 💻 How to Compile and Run

### Prerequisites
- Java Development Kit (JDK 8 or higher) installed.

### Step 1: Open Terminal / Command Prompt
Navigate to the project directory:
```cmd
cd C:\Users\Nishath\.gemini\antigravity\scratch\CampusManagementSystem
```

### Step 2: Compile All Java Source Files
```cmd
javac -d bin src/com/university/campus/model/*.java src/com/university/campus/datastructures/*.java src/com/university/campus/app/*.java
```

### Step 3: Run Application
```cmd
java -cp bin com.university.campus.app.Main
```

---

## 🧪 Submission Checklist & Verification

- [x] Complete project implemented including all required data structures & graph functionality.
- [x] All 16 menu options fully operational.
- [x] Comprehensive input validation prevents application crashes on invalid numeric inputs, out-of-range marks, or duplicate IDs.
- [x] Sample data auto-populated on startup for immediate demonstration.
