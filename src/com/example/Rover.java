package com.example;

import java.util.List;

public class Rover {
    private int xCoord;
    private int yCoord;
    private char heading;
    private String instructions;

    public int getxCoord() {
        return xCoord;
    }

    private void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    private void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public char getHeading() {
        return heading;
    }

    private void setHeading(char heading) {
        this.heading = heading;
    }

    public boolean setPosition(String positionString, List<Rover> rovers, List<Rover> destroyedRovers) {
        String[] position = positionString.split(" ");
        if (position.length != 3) {
            System.out.println("ERROR: INVALID ROVER POSITION");
            return false;
        }
        try {
            int xCoord = Integer.parseInt(position[0]);
            int yCoord = Integer.parseInt(position[1]);
            char heading = position[2].toUpperCase().charAt(0);
            if (heading != 'N' && heading != 'S' && heading != 'E' && heading != 'W') {
                System.out.println("ERROR: INVALID ROVER POSITION");
                return false;
            }
            for (Rover rover : rovers) {
                if (destroyedRovers.contains(rover)) {
                    continue;
                }
                if (rover.getxCoord() == xCoord && rover.getyCoord() == yCoord) {
                    System.out.println("ERROR: A ROVER WAS PLACED ON TOP OF ANOTHER ROVER AND BOTH WERE DESTROYED");
                    destroyedRovers.add(rover);
                    return false;
                }
            }
            this.xCoord = xCoord;
            this.yCoord = yCoord;
            this.heading = heading;
            return true;
        } catch (NumberFormatException e){
            System.out.println("ERROR: INVALID ROVER POSITION");
            return false;
        }
    }

    public String getPosition() {
        return String.format("%d %d %c", this.xCoord, this.yCoord, this.heading);
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void executeInstructions(Grid grid, List<Rover> rovers, List<Rover> destroyedRovers) {
        if (destroyedRovers.contains(this)) {
            return;
        }
        for (int i = 0; i < this.instructions.length(); i++) {
            if (this.instructions.charAt(i) == 'L' ) {
                this.turnLeft();
            } else if (this.instructions.charAt(i) == 'R') {
                this.turnRight();
            } else if (this.instructions.charAt(i) == 'M') {
                if (!this.move(grid, rovers, destroyedRovers)) {
                    return;
                }
            } else {
                System.out.println("ERROR: INVALID MOVEMENT INSTRUCTION ENCOUNTERED. ROVER SELF-DESTRUCTS.");
                destroyedRovers.add(this);
            }
        }
    }

    private void turnLeft() {
        if (this.heading == 'W') {
            this.heading = 'S';
        } else if (this.heading == 'E') {
            this.heading = 'N';
        } else if (this.heading == 'N') {
            this.heading = 'W';
        } else if (this.heading == 'S') {
            this.heading = 'E';
        }
    }

    private void turnRight() {
        if (this.heading == 'W') {
            this.heading = 'N';
        } else if (this.heading == 'E') {
            this.heading = 'S';
        } else if (this.heading == 'N') {
            this.heading = 'E';
        } else if (this.heading == 'S') {
            this.heading = 'W';
        }
    }

    private boolean move(Grid grid, List<Rover> rovers, List<Rover> destroyedRovers) {
        if (destroyedRovers.contains(this)) {
            return false;
        }
        if (this.heading == 'W') {
            if (this.xCoord > 0) {
                this.xCoord -= 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        } else if (this.heading == 'E') {
            if (this.xCoord < grid.getxLimit()) {
                this.xCoord += 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        } else if (this.heading == 'N') {
            if (this.yCoord < grid.getyLimit()) {
                this.yCoord += 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        } else if (this.heading == 'S') {
            if (this.yCoord > 0) {
                this.yCoord -= 1;
            } else {
                System.out.println("ERROR: ROVER HAS LEFT THE GRID AND BEEN DESTROYED");
                destroyedRovers.add(this);
                return false;
            }
        }
        for (Rover rover: rovers) {
            if (rover == this) {
                continue;
            } else if (destroyedRovers.contains(rover)) {
                continue;
            } else {
                if (rover.getxCoord() == this.xCoord && rover.getyCoord() == this.yCoord) {
                    System.out.println("ERROR: ROVER HAS COLLIDED WITH ANOTHER ROVER AND BOTH HAVE BEEN DESTROYED");
                    destroyedRovers.add(this);
                    destroyedRovers.add(rover);
                    return false;
                }
            }
        }
        return true;
    }
}