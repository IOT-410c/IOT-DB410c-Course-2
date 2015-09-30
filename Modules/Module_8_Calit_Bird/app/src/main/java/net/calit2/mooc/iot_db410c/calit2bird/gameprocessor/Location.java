package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        Location
 * Description: This class defines the coordinate system for the Calit2 Bird
 *              Game
 */
public class Location {
    private int x;
    private int y;

    /**
     * Name:        Location
     * Description: Constructor for the Location class. Instantiates an
     *              X and Y coordinate with the passed in parameters.
     *
     * @param       x horizontal coordinate
     * @param       y vertical coordinate
     */
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Name:        getX
     * Description: Getter method for the X
     *
     * @return      X coordinate of the current location
     */
    public int getX() {
        return x;
    }

    /**
     * Name:        getY
     * Description: Getter method for Y
     *
     * @return      X coordinate of the current location
     */
    public int getY() {
        return y;
    }

    /**
     * Name:        incrementX
     * Description: Increments/ moves the X coordinate by 1
     */
    public void incrementX() {
        x++;
    }

    /**
     * Name:        incrementY
     * Description: Increments/ moves the Y coordinate by 1
     */
    public void incrementY() {
        y--;
    }

    /**
     * Name:        decrementX
     * Description: Decrements/ moves the X coordinate by 1
     */
    public void decrementX() {
        x--;
    }

    /**
     * Name:        decrementY
     * Description: Decrements/ moves the Y coordinate by 1
     */
    public void decrementY() {
        y++;
    }

    /**
     * Name:        setX
     * Description: Sets/ Moves the X coordinate to the specified parameter
     *
     * @param       x - The specified coordinate to change the current
     *                  Location's X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Name:        setY
     * Description: Sets/ Moves the Y coordinate to the specified parameter
     *
     * @param       y - The specified coordinate to change the current
     *                  Location's Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Name:        intersects
     * Description: Determines if the current location intersects (has the
     *              same X and Y coordinate) as the passed in location
     *
     * @param       location - The other location object to compare this
     *                         location to
     * @return      Boolean indicating if the passed in location and the
     *              current location have the same X and Y coordinate
     */
    public boolean intersects(Location location){
        return this.getY() == location.getY() &&
                this.getX() == location.getX();
    }
}