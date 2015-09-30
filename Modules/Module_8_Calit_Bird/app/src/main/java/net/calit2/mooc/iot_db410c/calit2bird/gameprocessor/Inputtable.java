package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        Inputtable
 * Description: Creates a guideline of methods needed for the Calit2 Bird
 *              Game to take inputs.
 */
public interface Inputtable {
    void setInputState(boolean state);
    boolean getInputState();
    void setRunningState(boolean running);
    boolean getRunningState();
}