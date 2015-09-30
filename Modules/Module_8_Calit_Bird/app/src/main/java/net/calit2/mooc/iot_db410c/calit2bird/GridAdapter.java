package net.calit2.mooc.iot_db410c.calit2bird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Name:        GridAdapter
 * Description: Adapter used to populate the GridView of activity_main.xml.
 */
public class GridAdapter extends BaseAdapter {
    private ArrayList<Bitmap> arrayList;
    private Context context;

    /**
     * Name:        GridAdapter
     * Description: Constructor for the GridAdapter. Instantiates the private
     *              instances using the passed in parameters
     *
     * @param       arrayList containing the Bitmap images of the game in the
     *                        proper order
     * @param       context   of an activity to enable the populating of the
     *                        GridView Instance in activity_main.xml
     */
    public GridAdapter(ArrayList<Bitmap> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context   = context;
    }

    /**
     * Name:        getView
     * Description: Uses LayoutInflater to populate the layout of context
     *              with the corresponding Bitmap from the ArrayList
     *
     * @param       position    int representing the position on the GridView
     * @param       convertView Unused parameter
     * @param       parent      ViewGroup used to inflate the layout
     * @return      View        to populate the specific Grid block
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout coordinate;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        coordinate = (LinearLayout) inflater.inflate(R.layout.coordinate,
                parent, false);

        ImageView image = (ImageView) coordinate.getChildAt(0);
        image.setImageBitmap(arrayList.get(position));

        return coordinate;
    }

    /**
     * Name:        getCount
     * Description: Returns the size of the BitMap ArrayList
     *
     * @return      int value of the size of the ArrayList
     */
    @Override
    public int getCount() {
        return arrayList.size();
    }

    /**
     * Name:        getItem
     * Description: Unused method inherited from extending BaseAdapter
     *
     * @param       position unused parameter
     * @return      null
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Name:        getItemId
     * Description: Unused method inherited from BaseAdapter
     *
     * @param       position unused parameter
     * @return      0
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }
}
