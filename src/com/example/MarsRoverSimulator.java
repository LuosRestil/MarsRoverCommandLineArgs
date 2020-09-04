package com.example;

import java.util.ArrayList;
import java.util.List;

public class MarsRoverSimulator {
    public static List<Rover> rovers = new ArrayList<>();
    public static List<Rover> destroyedRovers = new ArrayList<>();
    public static Grid grid = new Grid();

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("ERROR: INCOMPLETE COMMAND LINE ARGUMENT");
            System.exit(-1);
        }

        grid.setDimensions(args[0]);

        for (int i = 1; i < args.length; i += 2) {
            Rover rover = new Rover();
            boolean successfullyPlaced = rover.setPosition(args[i], rovers, destroyedRovers);
            rover.setInstructions(args[i + 1]);
            if (successfullyPlaced) {
                rovers.add(rover);
            }
        }


        for (Rover rover: rovers) {
            if (!destroyedRovers.contains((rover))) {
                rover.executeInstructions(grid, rovers, destroyedRovers);
            }
        }

        for (Rover rover : destroyedRovers) {
            if (rovers.contains(rover)) {
                rovers.remove(rover);
            }
        }

        if (rovers.size() == 0) {
            System.out.println("No rovers survived the simulation.");
        } else {
            for (Rover rover: rovers) {
                System.out.println(rover.getPosition());
            }
        }
    }
}

