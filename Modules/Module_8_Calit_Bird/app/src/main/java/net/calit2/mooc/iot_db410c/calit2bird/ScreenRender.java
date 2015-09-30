package net.calit2.mooc.iot_db410c.calit2bird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import net.calit2.mooc.iot_db410c.calit2bird.gameprocessor.Map;
import net.calit2.mooc.iot_db410c.calit2bird.gameprocessor.Renderable;

import java.util.ArrayList;

/**
 * Name:        ScreenRender
 * Description:
 */
public class ScreenRender implements Renderable {

    private final Calit2BirdActivity activity;
    private TextView scoreView;
    private GridView grid;

    private Bitmap bird;
    private Bitmap block;
    private Bitmap blank;

    /**
     * Name:        ScreenRender
     * Description: Constructor for the class
     *
     * @param       activity an Activity to handle the UI changes for the game
     */
    public ScreenRender(Calit2BirdActivity activity){
        this.activity = activity;
    }

    /**
     * Name:        begin
     * Description: Instantiates the several components of the display and sets
     *              a listener for the layout.
     */
    @Override
    public void begin() {
        activity.setContentView(R.layout.activity_main);
        scoreView = (TextView) activity.findViewById(R.id.score);

        activity.findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setInput(v);
            }
        });

        grid = (GridView) activity.findViewById(R.id.gridView);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.setInput(view);
            }
        });

        bird = BitmapFactory.decodeResource(activity.getResources(),
                                            R.drawable.bird);
        block = BitmapFactory.decodeResource(activity.getResources(),
                                            R.drawable.block);
        blank = BitmapFactory.decodeResource(activity.getResources(),
                                            R.drawable.blank);
    }

    /**
     * Name:        render
     * Description: Populates an ArrayList with the appropriate BitMap
     *              based on the values in the two-dimensional data array.
     *              render then passes arrayList to the GridAdapter to
     *              populate the GridView instance.
     *
     * @param       data  two-dimensional array containing the encoded values
     *                    of the blank, block, and bird.
     * @param       score The user's score
     */
    @Override
    public void render(char[][] data, final int score) {
        ArrayList<Bitmap> arrayList = new ArrayList<>();

        for(int i = 0; i < Map.WIDTH; i++) {
            for(int j = 0; j < Map.HEIGHT; j++) {
                if (data[i][j] == '0') {
                    arrayList.add(blank);
                } else if (data[i][j] == '1') {
                    arrayList.add(block);
                } else { 
                    arrayList.add(bird);
                }
            }
        }

        final GridAdapter gridAdapter = new GridAdapter(arrayList, activity);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreView.setText("Score: " + score);
                grid.setAdapter(gridAdapter);

            }
        });
    }

    /**
     * Name:        clear
     * Description: Runs a separate UI Thread to handle the gameover screen
     *              and changes the layout to gameover_main.xml
     *
     * @param       score is the user's final score when the game ended
     */
    @Override
    public void clear(final int score) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setContentView(R.layout.gameover_main);
                ((TextView) activity.findViewById(R.id.gameover_score))
                        .setText("Score: " + score);

                Button restartButton = (Button) activity.findViewById(R.id.restart);
                restartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.beginNewGame();
                    }
                });
            }
        });
    }
}
