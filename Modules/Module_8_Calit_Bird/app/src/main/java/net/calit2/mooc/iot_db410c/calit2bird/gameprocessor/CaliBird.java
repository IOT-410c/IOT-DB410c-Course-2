package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        CaliBird
 * Description: Defines a concrete Actor class that represents the
 *              CaliBird
 */
public class CaliBird extends Actor {

    /**
     * Name:        CaliBird
     * Description: Constructor for the class. Instantiates the location
     *              coordinates of the Actor with the passed in parameters.
     *
     * @param       x X coordinate for the Actor
     * @param       y Y coordinate for the Actor
     */
    public CaliBird(int x, int y) {
        super(x, y);
    }

    /**
     * Name:        CaliBird
     * Description: Constructor for the class. Instantiates an actor
     *              using the passed in location
     *
     * @param       location Location for the Actor
     */
    public CaliBird(Location location){
        super(location);
    }

    /**
     * Name:        move
     * Description: Empty method because the actual move is implemented
     *              elsewhere
     *
     * @return      Boolean
     */
    @Override
    public boolean move() {
        return true;
    }
}
