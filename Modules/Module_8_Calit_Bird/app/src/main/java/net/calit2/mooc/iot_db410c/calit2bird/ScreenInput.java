package net.calit2.mooc.iot_db410c.calit2bird;

import net.calit2.mooc.iot_db410c.calit2bird.gameprocessor.Inputtable;

/**
 * Name:        ScreenInput
 * Description: Defines a concrete class for the Inputtable Interface.
 *              ScreenInput facilitates the communication between the input
 *              from the layouts and the game. Note: The actual onClick method
 *              is defined in Main because of the restrictions/ limitations
 *              of the Activity class.
 */
public class ScreenInput implements Inputtable{
    private boolean input;
    private boolean running;

    /**
     * Name:        setInputState
     * Description: Setter method for the private instance input
     *
     * @param       state is a boolean indicating whether or not the game is
     *                    receiving input.
     */
    @Override
    public void setInputState(boolean state) {
        input = state;
    }

    /**
     * Name:        getInputState
     * Description: Getter method for the private instance input
     *
     * @return      private instance input indicating the input state
     */
    @Override
    public boolean getInputState() {
        return input;
    }

    /**
     * Name:        setRunningState
     * Description: Setter method for the private instance running
     *
     * @param       running is a boolean indicating whether or not the game is
     *                      still running.
     */
    @Override
    public void setRunningState(boolean running) {
        this.running = running;
    }

    /**
     * Name:        getRunningState
     * Description: Getter method for the private instance running
     *
     * @return      private instance running indicating the running state of
     *              the input
     */
    @Override
    public boolean getRunningState() {
        return running;
    }
}
