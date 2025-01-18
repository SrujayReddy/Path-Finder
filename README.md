
```md
# UW Path Finder

**UW Path Finder** is a Java-based application for finding shortest walking paths around the UW–Madison campus. It uses a graph-based approach (with **Dijkstra’s Algorithm**) for computing optimal routes, backed by a custom hashtable that stores map data efficiently.

---

## Table of Contents

1. [Project Overview](#project-overview)  
2. [Features](#features)  
3. [Technologies & Tools](#technologies--tools)  
4. [Setup & Installation](#setup--installation)  
5. [Usage](#usage)  
6. [Testing](#testing)  
7. [Project Structure](#project-structure)  
8. [Contributors](#contributors)  
9. [License](#license)  

---

## Project Overview

This project allows users to:
- Load campus map data and display building information.
- Compute and display the shortest path between two buildings.
- Show total walking time and route details using a text-based user interface.

It includes a separate frontend and backend, ensuring a modular structure for user interaction vs. data processing. A custom-chained **HashtableMap** and a **DijkstraGraph** class support efficient lookups and shortest path calculations.

---

## Features

- **Campus Data Management**: Load building and path data (e.g., from `campus.dot`) into a graph.  
- **Shortest Path Calculation**: Uses Dijkstra’s Algorithm to quickly compute walking routes around campus.  
- **Frontend & Backend Separation**: Clear distinction between the user interface (`Frontend`) and core logic (`Backend`).  
- **Extensive Testing**: Includes multiple JUnit-based test suites for both frontend and backend functionalities.

---

## Technologies & Tools

- **Java (11+)**  
- **JUnit 5** for testing  
- **Makefile** for compiling and running tests  
- **Graph Data** (e.g., `.dot` files) for campus paths  
- **Git** for version control  

---

## Setup & Installation

1. **Clone the repository**:
```bash
git clone https://github.com/SrujayReddy/Path-Finder.git
cd Path-Finder

```

2.  **Ensure Java 11+** is installed.  
    If you plan to run tests locally, confirm you have `junit5.jar` accessible on your machine (or set up in your IDE).
    
3.  **Review the files** to confirm you see `.java` sources, `Makefile`, and data files like `campus.dot`.
    
       

----------

## Usage

You can compile and run using the **Makefile** or standard `javac`/`java` commands.

-   **Compile & run the application**:

```bash
make run

```

This compiles `Frontend.java` (and necessary classes) and runs it.

Alternatively, compile manually:

```bash
javac Frontend.java
java Frontend

```

_(Adjust the main class if needed; some groups may start in `Backend.java`.)_

----------

## Testing

We have two main sets of tests: **`BackendDeveloperTests`** and **`FrontendTests`**.

-   **Backend Tests**:

```bash
make runBDTests

```

Compiles and runs all JUnit 5 tests for the backend.

-   **Frontend Tests**:

```bash
make runFDTests

```

Compiles and runs all JUnit 5 tests for the frontend.

----------

## Project Structure

```
.
├── Backend.java
├── BackendDeveloperTests.java
├── BackendInterface.java
├── BaseGraph.java
├── DijkstraGraph.java
├── Frontend.java
├── FrontendInterface.java
├── FrontendDeveloperTests.java
├── GraphADT.java
├── HashtableMap.java
├── MapADT.java
├── PlaceholderMap.java
├── ShortestPathInterface.java
├── TextUITester.java
├── campus.dot
├── Makefile
└── ...

```

-   **`campus.dot`** is a sample graph data file with building and path info.
-   **`PlaceholderMap.java`** was used as a stub during development.
-   **`TextUITester.java`** helps simulate CLI input/output for test scenarios.

----------

## Contributors

-   **Frontend Developer**: _Srujay Reddy Jakkidi_
-   **Backend Developer**: _Steve Hu_

----------

## License

This project was developed as part of **CS400** at the University of Wisconsin–Madison and is shared strictly for educational purposes.

**Important Notes:**

-   Redistribution or reuse of this code for academic submissions is prohibited and may violate academic integrity policies.
-   For external usage outside the course context, please include a clear reference or attribution.