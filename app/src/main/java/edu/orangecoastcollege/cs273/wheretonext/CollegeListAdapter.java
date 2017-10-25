package edu.orangecoastcollege.cs273.wheretonext;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Helper class to provide custom adapter for the <code>College</code> list.
 */
public class CollegeListAdapter extends ArrayAdapter<College> {

    private Context mContext;
    private List<College> mCollegesList;
    private int mResourceId;



    /**
     * Creates a new <code>CollegeListAdapter</code> given a mContext, resource id and list of colleges.
     *
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param colleges The list of colleges to display
     */
    public CollegeListAdapter(@NonNull Context c, @LayoutRes int rId, @NonNull List<College> colleges) {
        super(c, rId, colleges);
        mContext = c;
        mResourceId = rId;
        mCollegesList = colleges;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the College selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        // TODO:  Write the code to correctly inflate the view (college_list_item) with
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View listItemView = inflater.inflate(mResourceId, null);

        // TODO:  all widgets filled with the appropriate College information.
        ImageView collegeListImageView = (ImageView) listItemView.findViewById(R.id.collegeListImageView);
        TextView collegeListNameTextView = (TextView) listItemView.findViewById(R.id.collegeListNameTextView);
        RatingBar collegeListRatingBar = (RatingBar) listItemView.findViewById(R.id.collegeListRatingBar);
        LinearLayout collegeListLinearLayout = (LinearLayout) listItemView.findViewById(R.id.collegeListLinearLayout);

        College selectedCollege = mCollegesList.get(pos);
        collegeListNameTextView.setText(selectedCollege.getName());
        collegeListRatingBar.setRating((float)selectedCollege.getRating());

        collegeListLinearLayout.setTag(selectedCollege);

        AssetManager am = mContext.getAssets();

        try {
            InputStream stream = am.open(selectedCollege.getImageName());
            Drawable image = Drawable.createFromStream(stream, selectedCollege.getName());
            collegeListImageView.setImageDrawable(image);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return listItemView;
    }
}
