package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Name:        Map
 * Description: Creates the game from a programming perspective. The Map class
 *              contains the necessary information to populate the screen
 *              layout. GameProcessor calls on these methods to "play" the game
 */
public class Map {
    public static final int HEIGHT = 8;
    public static final int WIDTH  = 8;

    private static final int MINIMUM_PIPE    = 2;
    private static final int NUMBER_OF_HOLES = 1;

    private static final char EMPTY     = '0';
    private static final char OBSTACLE  = '1';
    private static final char CALIBIRD  = '2';

    private List<Actor> actors;
    private char map[][];

    /**
     * Name:        Map
     * Description: Constructor for the class. Instantiates the Actors in the
     *              Map by adding CaliBird into the List, and the char array
     *
     * @param       caliBird A CaliBird Actor to populate the actors list
     */
    public Map(CaliBird caliBird) {
        actors = new ArrayList<>();
        actors.add(caliBird);
        map    = new char[HEIGHT][WIDTH];
    }

    /**
     * Name:        updateMap
     * Description: Updates the char[][] map to house values indicating
     *              the Obstacle/ Actor at the space.
     *              0 indicates an empty space
     *              1 indicates an obstacle/ actor
     *              2 indicates the CaliBird
     *
     * @return      char[][] containing the grid/ actors/ obstacles of the
     *              Map.
     */
    public char[][] updateMap() {
        // Populates Map with Empty "Spaces"
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                map[i][j] = EMPTY;
            }
        }

        // Populates Map where Actors/ Obstacles are located
        for (Actor iter : actors) {
            map[iter.getY()][iter.getX()] = OBSTACLE;
        }

        // Populates Map where the CaliBird is located
        map[getCaliBird().getY()][getCaliBird().getX()] = CALIBIRD;

        return map;
    }

    /**
     * Name:        collisions
     * Description: Iterates through the List of Actors and determines if
     *              CaliBird intersects with any of the obstacles.
     *
     * @return      Boolean indicating whether or not CaliBird hit another
     *              Actor/ Obstacle.
     */
    public boolean collisions() {
        Actor caliBird = getCaliBird();
        Iterator<Actor> iter = actors.iterator();
        iter.next();
        while (iter.hasNext()) {
            if (iter.next().intersects(caliBird)) {
                removeCaliBird();
                return true;
            }
        }
        return false;
    }

    /**
     * Name:        randomize
     * Description: Randomly places the Pipes/ Obstacles in the List of Actors.
     */
    public void randomize() {
        // Determines number of pipes needed on top of the minimum four + hole
        int numOfPipes = HEIGHT - NUMBER_OF_HOLES - (MINIMUM_PIPE * 2);

        // Generates a Random int from 0 - numOfPipes to determine the number
        // of pipes to place starting from the bottom
        Random random = new Random();
        int position = random.nextInt(numOfPipes) + MINIMUM_PIPE;

        // Populates the List of Actors with the Pipes at the bottom
        for (int j = 0; j < position; j++) {
            actors.add(new Obstacle(WIDTH - 1, j));
        }

        // Populates the List of Actors with the Pipes at the top
        for (int k = position + MINIMUM_PIPE; k < HEIGHT; k++){
            actors.add(new Obstacle(WIDTH - 1, k));
        }
    }

    /**
     * Name:        move
     * Description: Iterates through the List of Actors and calls on their
     *              respective move method.
     */
    public void move() {
        Iterator<Actor> iter = actors.iterator();
        while (iter.hasNext()) {
            if (!(iter.next()).move()) {
                iter.remove();
            }
        }
    }

    /**
     * Name:        getCaliBird
     * Description: Getter method to return the CaliBird
     *
     * @return      CaliBird object in the game
     */
    private CaliBird getCaliBird() {
        return (CaliBird) actors.get(0);
    }

    /**
     * Name:        removeCaliBird
     * Description: Removes the CaliBird object from the List of Actors
     *              currently in the game.
     */
    public void removeCaliBird() {
        actors.remove(0);
    }

    /**
     * Name:        checkCaliBirdScore
     * Description: Performs a check whether or not CaliBird passes a Pipe.
     *
     * @return      Boolean indicating whether or not we increment the score.
     */
    public boolean checkCaliBirdScore() {
        Actor caliBird = getCaliBird();
        Iterator<Actor> iter = actors.iterator();
        iter.next();
        while(iter.hasNext()) {
            Actor obstacle = iter.next();
            if(obstacle.getX() == (caliBird.getX() - 1)) {
                return true;
            }
        }
        return false;
    }
}
