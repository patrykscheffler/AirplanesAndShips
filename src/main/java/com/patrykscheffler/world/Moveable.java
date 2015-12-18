package com.patrykscheffler.world;

/**
 * An interface that allows implementing classes to move.
 */
public interface Moveable extends Runnable {
    public void move(Place A, Place B);
    public void move(Path path);
}
