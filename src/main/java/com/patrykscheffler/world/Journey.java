package com.patrykscheffler.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class which represents concept of journey
 */
public class Journey implements Serializable {
    /** Field which holds journey type */
    private JourneyType journeyType;
    /** Start building of journey */
    private Building startBuilding;
    /** End building of journey */
    private Building endBuilding;

    /** Constructor which creates Journey */
    public Journey() {
        Random r = new Random();

        List<Building> list = new ArrayList<>(World.BUILDINGS.values());
        do {
            startBuilding = list.get(r.nextInt(list.size()));
            endBuilding = list.get(r.nextInt(list.size()));
        } while (startBuilding.getName().equals(endBuilding.getName()));

        journeyType = JourneyType.values()[r.nextInt(JourneyType.values().length)];
    }

    public enum JourneyType {
        WORK,
        PRIVATE
    }

    public JourneyType getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(JourneyType journeyType) {
        this.journeyType = journeyType;
    }

    public Building getStartBuilding() {
        return startBuilding;
    }

    public void setStartBuilding(Building startBuilding) {
        this.startBuilding = startBuilding;
    }

    public Building getEndBuilding() {
        return endBuilding;
    }

    public void setEndBuilding(Building endBuilding) {
        this.endBuilding = endBuilding;
    }
}
