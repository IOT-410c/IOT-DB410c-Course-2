package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        Renderable
 * Description: Creates a guideline of methods needed for the Calit2 Bird
 *              Game to render the game in the desired platform.
 */
public interface Renderable {
    void begin();
    void render(char [][] data, final int score);
    void clear(final int score);
}


