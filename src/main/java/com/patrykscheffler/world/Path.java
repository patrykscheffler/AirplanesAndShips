package com.patrykscheffler.world;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class that represents concept of path
 */
public class Path implements Serializable {
    /** Path length */
    private double length;
    /** */
    private double kFactor;
    /** Name of start place */
    private String aName;
    /** Name of end place */
    private String bName;
    /** Start point */
    private Point aPoint;
    /** End point */
    private Point bPoint;
    /** Angle of path */
    private Double angle;
    /** Direction of path */
    private Direction direction = Direction.NONE;
    /** Path collection of point */
    public final LinkedList<Point> path = new LinkedList<>();

    /** Default constructor */
    public Path() {}

    /** Constructor which creates path from place A to B
     *
     * @param A Start place
     * @param B End place
     */
    public Path(Place A, Place B) {
        int intXA, intYA, intXB, intYB;
        double xA, yA, xB, yB;

        intXA = A.getXPosition();
        intYA = A.getYPosition();
        intXB = B.getXPosition();
        intYB = B.getYPosition();

        xA = (double) intXA;
        yA = (double) intYA;
        xB = (double) intXB;
        yB = (double) intYB;

        aPoint = new Point((int) xA, (int) yA);
        bPoint = new Point((int) xB, (int) yB);

        aName = A.getName();
        bName = B.getName();

        length =  Math.sqrt((((xA-xB)*(xA-xB) + (yA - yB)*(yA - yB))));
        kFactor = (yB - yA)/(xB-xA);
        angle = Math.toDegrees(Math.atan2(yB - yA, xB - xA)) + 90;

        path.add(A.getPosition());

        if ((xA == xB) && (yA ==yB)) {
            direction = Direction.NONE;
        } else if (xA == xB) {
            if (yB > yA) {
                direction = Direction.SOUTH;
            } else {
                direction = Direction.NORTH;
            }

            for (int i = 1; i < (int) length; i++) {
                int x = (int) xA;
                int y;

                if (yB > yA) {
                    y = (int) (yA) + i;
                } else {
                    y = (int) (yA) - i;
                }

                path.add(new Point(x, y));
            }
        } else if (yA == yB) {
            if (xB > xA) {
                direction = Direction.SOUTH;
            } else {
                direction = Direction.NORTH;
            }

            for (int i = 1; i < (int) length; i++) {
                int y = (int) yA;
                int x;

                if (xB > xA) {
                    x = (int) (xA) + i;
                } else {
                    x = (int) (xA) - i;
                }

                path.add(new Point(x, y));
            }
        } else {
            for (int i=1; i<(int)length; i++) {
                int x;
                int y;

                double c = (double) i;
                double a = c / (Math.sqrt((kFactor * kFactor + 1.0)));
                double b = Math.abs(a * kFactor);

                if ((xB > xA) && (yB > yA)) {
                    x = (int) (xA + a);
                    y = (int) (yA + b);
                } else if ((xB > xA) && (yB < yA)) {
                    x = (int) (xA + a);
                    y = (int) (yA - b);
                } else if ((xB < xA) && (yB > yA)) {
                    x = (int) (xA - a);
                    y = (int) (yA + b);
                } else if ((xB < xA) && (yB < yA)) {
                    x = (int) (xA - a);
                    y = (int) (yA - b);
                } else {
                    x = 100;
                    y = 100;
                }

                path.add(new Point(x, y));
            }
        }

        path.add(B.getPosition());
    }

    /** Creates reversed path
     *
     * @return Reversed path
     */
    public Path createReversePath() {
        Path p = new Path();

        p.aPoint = this.bPoint;
        p.bPoint = this.aPoint;
        p.aName = this.aName;
        p.bName = this.bName;
        if (this.angle >= 180) {
            p.angle = this.angle - 180;
        } else {
            p.angle = this.angle + 180;
        }

        p.kFactor = this.kFactor;
        p.length = this.length;

        switch(this.direction) {
            case NONE:
                p.direction = Direction.NONE;
                break;
            case NORTH:
                p.direction = Direction.SOUTH;
                break;
            case SOUTH:
                p.direction = Direction.NORTH;
                break;
            case WEST:
                p.direction = Direction.EAST;
                break;
            case EAST:
                p.direction = Direction.WEST;
                break;
        }

        for(Point position : this.path) {
            p.path.addFirst(position);
        }

        return p;
    }

    public double getLength() {
        return length;
    }

    public Point getAPoint() {
        return aPoint;
    }

    public Point getBPoint() {
        return bPoint;
    }

    public String getAName() {
        return aName;
    }

    public void setAName(String aName) {
        this.aName = aName;
    }

    public String getBName() {
        return bName;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getAngle() {
        return angle;
    }
}
