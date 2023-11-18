//Yifan Wan
//11/17/2023

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ElevatorSimulation {
    private String structures;
    private int floors;
    private double passengersProb;
    private int numElevators;
    private int elevatorCapacity;
    private int duration;

    private List<Elevator> elevators;
    private Map<Integer, Queue<Passenger>> floorQueues;
    private List<Passenger> passengers;
    private int tick;

    public ElevatorSimulation(String propertyFile) {
        readProperties(propertyFile);
        initializeSimulation();
    }

    private void readProperties(String propertyFile) {
        Properties properties = new Properties();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(propertyFile));
            properties.load(reader);

            structures = properties.getProperty("Structures", "linked");
            floors = Integer.parseInt(properties.getProperty("Floors", "32"));
            passengersProb = Double.parseDouble(properties.getProperty("Passengers", "0.03"));
            numElevators = Integer.parseInt(properties.getProperty("Elevators", "1"));
            elevatorCapacity = Integer.parseInt(properties.getProperty("Elevator Capacity", "10"));
            duration = Integer.parseInt(properties.getProperty("Duration", "500"));

            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void initializeSimulation() {
        elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(elevatorCapacity));
        }

        floorQueues = new HashMap<>();
        for (int i = 1; i <= floors; i++) {
            floorQueues.put(i, new LinkedList<>());
        }

        passengers = new ArrayList<>();
        tick = 0;
    }

    public void runSimulation() {
        Random rand = new Random();
        while (tick < duration) {
            handleElevatorLoadUnload();
            handleElevatorTravel(rand);
            handleNewPassengerGeneration(rand);

            tick++;
        }

        reportResults();
    }

    private void handleElevatorLoadUnload() {
        for (Elevator elevator : elevators) {
            int currentFloor = elevator.getCurrentFloor();

            Iterator<Passenger> passengerIterator = elevator.getPassengers().iterator();
            while (passengerIterator.hasNext()) {
                Passenger passenger = passengerIterator.next();
                if (passenger.getDestinationFloor() == currentFloor) {
                    passenger.setConveyanceTick(tick);
                    passengerIterator.remove();
                }
            }

            Queue<Passenger> floorQueue = floorQueues.get(currentFloor);
            while (!floorQueue.isEmpty() && elevator.hasSpace()) {
                Passenger nextPassenger = floorQueue.peek();
                if ((nextPassenger.getDestinationFloor() > currentFloor && elevator.isMovingUp()) ||
                        (nextPassenger.getDestinationFloor() < currentFloor && !elevator.isMovingUp())) {
                    floorQueue.poll();
                    elevator.addPassenger(nextPassenger);
                } else {
                    break; // Stop adding passengers if not going in the same direction
                }
            }
        }
    }

    private void handleElevatorTravel(Random rand) {
        for (Elevator elevator : elevators) {
            int currentFloor = elevator.getCurrentFloor();

            if (elevator.getPassengers().isEmpty()) {
                // If no passengers, idle or change direction if at top/bottom floor
                if (currentFloor == 1) {
                    elevator.setMovingUp(true);
                } else if (currentFloor == floors) {
                    elevator.setMovingUp(false);
                }
            } else {
                boolean shouldStop = false;
                for (Passenger passenger : elevator.getPassengers()) {
                    if (passenger.getDestinationFloor() == currentFloor) {
                        shouldStop = true;
                        break;
                    }
                }

                if (shouldStop) {
                    elevator.setCurrentFloor(currentFloor);
                } else {
                    int floorsToMove = rand.nextInt(6);
                    int newFloor = currentFloor + (elevator.isMovingUp() ? floorsToMove : -floorsToMove);

                    if (newFloor >= 1 && newFloor <= floors) {
                        elevator.setCurrentFloor(newFloor);
                    }
                }
            }
        }
    }

    private void handleNewPassengerGeneration(Random rand) {
        for (int floor = 1; floor <= floors; floor++) {
            if (rand.nextDouble() < passengersProb) {
                int destFloor = rand.nextInt(floors) + 1;
                while (destFloor == floor) {
                    destFloor = rand.nextInt(floors) + 1;
                }

                Passenger newPassenger = new Passenger(floor, destFloor, tick);
                floorQueues.get(floor).offer(newPassenger);
                passengers.add(newPassenger);
            }
        }
    }

    private void reportResults() {
        int totalConveyanceTime = 0;
        int longestTime = Integer.MIN_VALUE;
        int shortestTime = Integer.MAX_VALUE;

        for (Passenger passenger : passengers) {
            int conveyanceTime = passenger.getConveyanceTick() - passenger.getArrivalTick();
            totalConveyanceTime += conveyanceTime;
            if (conveyanceTime > longestTime) {
                longestTime = conveyanceTime;
            }
            if (conveyanceTime < shortestTime) {
                shortestTime = conveyanceTime;
            }
        }

        double averageTime = (double) totalConveyanceTime / passengers.size();

        System.out.println("Average length of time between passenger arrival and conveyance: " + averageTime);
        System.out.println("Longest time between passenger arrival and conveyance: " + longestTime);
        System.out.println("Shortest time between passenger arrival and conveyance: " + shortestTime);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ElevatorSimulation <property_file>");
            return;
        }

        String propertyFile = args[0];
        ElevatorSimulation simulation = new ElevatorSimulation(propertyFile);
        simulation.runSimulation();
    }

    private static class Elevator {
        private int capacity;
        private int currentFloor;
        private boolean movingUp;
        private List<Passenger> passengers;

        public Elevator(int capacity) {
            this.capacity = capacity;
            this.currentFloor = 1;
            this.movingUp = true;
            this.passengers = new ArrayList<>();
        }

        public int getCurrentFloor() {
            return currentFloor;
        }

        public void setCurrentFloor(int floor) {
            this.currentFloor = floor;
        }

        public boolean isMovingUp() {
            return movingUp;
        }

        public boolean hasSpace() {
            return passengers.size() < capacity;
        }

        public List<Passenger> getPassengers() {
            return passengers;
        }

        public void addPassenger(Passenger passenger) {
            passengers.add(passenger);
        }

        public void setMovingUp(boolean movingUp) {
            this.movingUp = movingUp;
        }
    }

    private static class Passenger {
        private int initialFloor;
        private int destinationFloor;
        private int arrivalTick;
        private int conveyanceTick;

        public Passenger(int initialFloor, int destinationFloor, int arrivalTick) {
            this.initialFloor = initialFloor;
            this.destinationFloor = destinationFloor;
            this.arrivalTick = arrivalTick;
            this.conveyanceTick = -1; // Initialize conveyanceTick as -1 (not conveyed yet)
        }

        public int getDestinationFloor() {
            return destinationFloor;
        }

        public int getArrivalTick() {
            return arrivalTick;
        }

        public int getConveyanceTick() {
            return conveyanceTick;
        }

        public void setConveyanceTick(int conveyanceTick) {
            this.conveyanceTick = conveyanceTick;
        }
    }
}
