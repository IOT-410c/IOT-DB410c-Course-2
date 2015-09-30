package net.calit2.mooc.iot_db410c.calit2bird;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import net.calit2.mooc.iot_db410c.calit2bird.gameprocessor.GameProcessor;
import net.calit2.mooc.iot_db410c.calit2bird.gameprocessor.Inputtable;
import net.calit2.mooc.iot_db410c.calit2bird.gameprocessor.Renderable;

/**
 * Name:        Calit2BirdActivity
 * Description: The Main Activity of the game.
 *
 */
public class Calit2BirdActivity extends Activity {

    GameProcessor gameProcessor;
    private Inputtable inputter;
    private Renderable renderer;

    /**
     * Name:        onCreate
     * Description: Instantiates the Inputtable and Renderable instances
     *              as ScreenInput and ScreenRender object, respectively
     *
     * @param       savedInstanceState Reference to bundle object. Activities
     *                                 can be restored to a former state using
     *                                 data saved in this bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        renderer = new ScreenRender(this);
        inputter = new ScreenInput();
    }

    /**
     * Name:        onResume
     * Description: Issues a call to the helper method beginNewGame().
     *              We begin the game here because this method is called when
     *              the screen is live.
     */
    @Override
    protected void onResume() {
        super.onResume();
        beginNewGame();
    }

    /**
     * Name:        onPause
     * Description: Issues a call to the helper method endGame().
     *              We end the game here because this method is called when
     *              the screen is dead.
     */
    @Override
    protected void onPause() {
        super.onPause();
        endGame();
    }

    /**
     * Name:        beginNewGame
     * Description: Notifies inputter that the game is now running
     *              and instantiates the GameProcessor object to
     *              essentially start and play the game.
     */
    public void beginNewGame() {
        inputter.setRunningState(true);
        gameProcessor = new GameProcessor(inputter, renderer);
        gameProcessor.start();
    }

    /**
     * Name:        endGame
     * Description: Notifies the inputter that the game is over (or the running
     *              state is false).
     */
    public void endGame() {
        inputter.setRunningState(false);
    }

    /**
     * Name:        setInput
     * Description: Method called when the User taps on activity_main.xml.
     *              This method communicates the User's input into the game.
     *
     * @param       view The view associated with the method call
     */
    public void setInput(View view) {
        if(inputter != null)
            inputter.setInputState(true);
    }
}
