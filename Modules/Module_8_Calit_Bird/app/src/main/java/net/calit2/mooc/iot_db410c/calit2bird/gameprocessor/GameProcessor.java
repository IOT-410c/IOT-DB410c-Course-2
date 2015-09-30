package net.calit2.mooc.iot_db410c.calit2bird.gameprocessor;

/**
 * Name:        GameProcessor
 * Description: This class essentially "runs" the game on a separate Thread
 *              (note that a separate Thread is created when a class extends
 *              Thread and is instantiated). GameProcessor uses its private
 *              instances to call the appropriate methods to play the game.
 */
public class GameProcessor extends Thread {
    private static final int SLEEP_TIME     = 500; // In milliseconds
    private static final int INPUT_RATE     = 2;
    private static final int RANDOMIZE_RATE = 6;

    private Map map;
    private CaliBird caliBird;
    private Renderable renderer;
    private Inputtable input;
    private int score;

    /**
     * Name:        GameProcessor
     * Description: Constructor for GameProcessor. The constructor initializes
     *              the private instances for the class and begins renderer.
     */
    public GameProcessor(Inputtable input, Renderable renderer) {
        this.renderer = renderer;
        this.input    = input;

        caliBird = new CaliBird(3, 3);
        map      = new Map(caliBird);
        score    = 0;

        renderer.begin();
    }

    /**
     * Name:        run
     * Description: Runs a continuous while loop dependent on the running state
     *              of the Inputable. In the while loop, GameProcessor first
     *              moves the Actors, then checks for collisions, then updates
     *              the score and repeats the processes. Once the running state
     *              of the Inputable is false (i.e. the game isn't running), we
     *              make a call to the Renderable clear method to clear the
     *              screen.
     */
    public void run() {

        long time;
        int index = 0;

        while (input.getRunningState()) {
            time = System.currentTimeMillis();

            // Determines if CaliBird moves up or down
            if (input.getInputState()) {
                if (!caliBird.incrementY()) {
                    caliBird.decrementY();
                }
                input.setInputState(false);
            } else if (!caliBird.decrementY()) {
                map.removeCaliBird();
                input.setRunningState(false);
                break;
            }

            // Move the pipes at the INPUT_RATE
            if (index % INPUT_RATE == 0)
                map.move();

            // Randomizes the pipes at the RANDOMIZE_RATE
            if (index % RANDOMIZE_RATE == 0) {
                map.randomize();
            }

            // Get Map data
            char[][] data = map.updateMap();

            // Check for collisions
            if (map.collisions()) {
                input.setRunningState(false);
                break;
            }

            // Update Score
            if (map.checkCaliBirdScore() && index % INPUT_RATE == 0) {
                score++;
            }

            // Display the image
            renderer.render(data, score);

            // Make Thread sleep to simulate the frame rate
            long difference = System.currentTimeMillis() - time;
            if (difference > SLEEP_TIME) {
                sleepTime(0);
            } else {
                sleepTime(SLEEP_TIME - difference);
            }

            index++;
        }
        renderer.clear(score);
    }

    /**
     * Name:        sleepTime
     * Description: Make the Thread sleep for a certain amount of time
     *
     * @param       time in milliseconds for the Thread to sleep
     */
    public void sleepTime(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
