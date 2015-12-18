package com.patrykscheffler.world;

/**
 * Created by Patryk on 05/12/2015.
 */
public class Journey {
    private JourneyType journeyType;
    private Point mStartPoint;
    private Point mTargetPoint;

    public void Journey(JourneyType journeyType, Point startPoint, Point targetPoint) {
        this.journeyType = journeyType;
        this.mStartPoint = startPoint;
        this.mTargetPoint = targetPoint;
    }

    public enum JourneyType {
        WORK,
        PRIVATE
    }
}
