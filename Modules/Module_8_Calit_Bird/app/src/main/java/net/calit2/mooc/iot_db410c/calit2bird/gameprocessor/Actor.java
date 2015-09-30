package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        Actor
 * Description: Defines an abstract class for the Actors in the Game.
 */
public abstract class Actor {

    protected Location location;
    protected boolean running;

    /**
     * Name:        Actor
     * Description: Constructor for Actor. Instantiates the location of the
     *              Actor using x and y coordinates
     *
     * @param       x horizontal coordinate
     * @param       y vertical coordinate
     */
    public Actor(int x, int y) {
        location = new Location(x,y);
        running = true;
    }

    /**
     * Name:        Actor
     * Description: Constructor for Actor. Instantiates the location of
     *              the Actor using the passed in Location
     *
     * @param       location Location object to denote x and y coordinate
     */
    public Actor(Location location) {
        this(location.getX(),location.getY());
    }

    /**
     * Name:        getLocation
     * Description: Getter method to return location
     *
     * @return      Return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Name:        getX
     * Description: Getter method to return x coordinate
     *
     * @return      Return X coordinate of location
     */
    public int getX() {
        return location.getX();
    }

    /**
     * Name:        getY
     * Description: Getter method to return y coordinate
     *
     * @return      Return Y coordinate of location
     */
    public int getY() {
        return location.getY();
    }

    /**
     * Name:        decrementX
     * Description: Decrements X coordinate of location by 1
     *
     * @return      Boolean indicating if the X variable is less than 0
     */
    public boolean decrementX() {
        location.decrementX();
        return getX() >= 0;
    }

    /**
     * Name:        incrementX
     * Description: Increments X coordinate of location by 1
     *
     * @return      Boolean indicating if the X variable is greater than the
     *              Map width
     */
    public boolean incrementX() {
        location.incrementX();
        return getX() < Map.WIDTH;
    }

    /**
     * Name:        decrementY
     * Description: Decrements Y coordinate of location by 1
     *
     * @return      Boolean indicating if the Y variable is less than the
     *              Map height
     */
    public boolean decrementY() {
        location.decrementY();
        return getY() < Map.HEIGHT;

    }

    /**
     * Name:        incrementY
     * Description: Increments Y coordinate of location by 1
     *
     * @return      Boolean indicating if the Y variable is greater than 0
     */
    public boolean incrementY(){
        location.incrementY();
        return getY() >= 0;

    }

    /**
     * Name:        intersects
     * Description: This method is used for collision detection. This will
     *              determine if the current Actor is in the same location
     *              as the passed in Actor
     *
     * @param       actor is another Actor.
     * @return      Boolean indicating if this Actor intersects with the
     *              passed in Actor
     */
    public boolean intersects(Actor actor){
        return location.intersects(actor.getLocation());
    }

    /**
     * Name:        move
     * Description: Abstract method to allow for subclass definition.
     */
    public abstract boolean move();
}
