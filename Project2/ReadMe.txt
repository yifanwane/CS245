a) Design choices and OO decomposition of the problem:

ElevatorSimulation Class: This class manages the entire simulation process. It's responsible for reading the property file, initializing the simulation components, running the simulation loop, and reporting the results.
Elevator Class: Represents an elevator object with attributes such as capacity, current floor, movement direction, and a list of passengers. It encapsulates functionality for handling passengers, movement, and direction.
Passenger Class: Defines a passenger object with initial and destination floors, arrival tick, and conveyance tick. It's used to manage passenger-related information.
Data structures: Use of Lists, Queues, and Maps to manage elevators, passengers, and floor queues efficiently.

b) Data structure choices:

Lists: Used to maintain passengers within the elevators, passengers waiting on floors, and elevators in the simulation.
Queues: Utilized for managing the passenger queue on each floor, ensuring the first-in-first-out behavior.
Maps: Employed to store floor queues for easy access based on floor numbers, aiding in passenger handling.

c) Instructions on how to compile and run the implementation:

Compilation: Save the code in a file named ElevatorSimulation.java. Compile the code using the command javac ElevatorSimulation.java.
Running: Execute the simulation by providing a property file as a command-line argument. Use the command java ElevatorSimulation <property_file>, where <property_file> is the path to the property file.

d) Additional notes:

Modifiability: The code can be modified to accommodate more complex scenarios, such as different elevator behaviors, passenger types, or additional simulation features.
Enhancements: Depending on specific requirements, the elevator movement algorithm or passenger generation logic could be further refined for increased realism or complexity.
These points outline the design choices, data structures used, instructions for compilation and execution, and potential areas for enhancement in the code. Adjustments or additions can be made based on specific project needs or requirements.





