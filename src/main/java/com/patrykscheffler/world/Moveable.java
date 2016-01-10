package com.patrykscheffler.world;

/**
 * An interface that allows implementing classes to move.
 */
public interface Moveable extends Runnable {
    /** Move from place A to place B
     *
     * @param A Start place
     * @param B End place
     */
    public void move(Place A, Place B);

    /** Move along the path
     *
     * @param path Path to follow
     */
    public void move(Path path);
}
