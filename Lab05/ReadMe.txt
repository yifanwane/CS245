Tree Edge Removal Program

Description
This program is designed to find a specific edge in a tree structure where an extra edge has been added, 
and upon its removal, the structure remains a tree. 
The solution uses the Union-Find algorithm to identify the edge that completes a cycle within the tree.

Usage
To use the program:
Compile the Main.java file.
Utilize the findCutEdge(int[][] edges) method in the Main class by passing in an array of edges represented as integer pairs.

Implementation Details
The solution utilizes a Disjoint Sets (Union-Find) data structure to analyze the edges provided and detect the edge that forms a cycle when added to the tree structure. 
It traverses through the edges, 
detecting cycles, and identifies the edge that completes the cycle.

Elaborations
The choice of using the Union-Find algorithm stems from its efficiency in detecting cycles and maintaining disjoint sets, 
making it suitable for identifying the additional edge that creates a cycle within the tree structure.

Difficulties
One challenge encountered during implementation was ensuring the code's robustness in handling various edge cases and edge scenarios where the additional edge could be added. Thorough testing was necessary to ensure correctness.