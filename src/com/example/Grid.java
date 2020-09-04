package com.example;

public class Grid {
    private int xLimit;
    private int yLimit;

    public int getxLimit() {
        return xLimit;
    }

    public void setxLimit(int xLimit) {
        this.xLimit = xLimit;
    }

    public int getyLimit() {
        return yLimit;
    }

    public void setyLimit(int yLimit) {
        this.yLimit = yLimit;
    }

    public void setDimensions(String dimensionsString) {
        int xLimit = -1;
        int yLimit = -1;
        String[] dimensionsArray = dimensionsString.split(" ");
        if (dimensionsArray.length != 2) {
            System.out.println("ERROR: INVALID GRID DELIMITER");
            System.exit(-1);
        }
        try {
            xLimit = Integer.parseInt(dimensionsArray[0]);
            yLimit = Integer.parseInt(dimensionsArray[1]);
            if (xLimit < 0 || yLimit < 0) {
                System.out.println("ERROR: INVALID GRID DELIMITER");
                System.exit(-1);
            } else {
                this.setxLimit(xLimit);
                this.setyLimit(yLimit);
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: INVALID GRID DELIMITER");
            System.exit(-1);
        }
    }
}