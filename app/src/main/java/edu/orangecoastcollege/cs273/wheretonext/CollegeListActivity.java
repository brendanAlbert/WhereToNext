package edu.orangecoastcollege.cs273.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

/**
 * CollegeListActivity is the main controller of our "WhereToNext" app.
 *
 * Methods are provided to add colleges to the database, view a particular college's details
 * and to initialize all of the widgets that make up the View.
 */
public class CollegeListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;

    private EditText mNameEditText;
    private EditText mPopulationEditText;
    private EditText mTuitionEditText;
    private RatingBar mRatingBar;

    /**
     * onCreate is called when the app is first run.
     *
     * All widgets are hooked up between the Controller and View.
     *
     * Any existing colleges in the database are retrieved and used to populate the ListView.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mPopulationEditText = (EditText) findViewById(R.id.populationEditText);
        mTuitionEditText = (EditText) findViewById(R.id.tuitionEditText);
        mRatingBar = (RatingBar) findViewById(R.id.collegeRatingBar);
        ListView collegesListView = (ListView) findViewById(R.id.collegeListView);

        // COMPLETED: Comment this section out once the colleges below have been added to the database,
        // COMPLETED: so they are not added multiple times (prevent duplicate entries)
        db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7, "ucb.png"));
        db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3, "uci.png"));
        db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5, "ucla.png"));
        db.addCollege(new College("UC San Diego", 33735, 20212, 4.4, "ucsd.png"));
        db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5, "csuf.png"));
        db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4, "csulb.png"));

        // COMPLETED:  Fill the collegesList with all Colleges from the database
        collegesList = db.getAllColleges();
        // COMPLETED:  Connect the list adapter with the list
        collegesListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);
        // COMPLETED:  Set the list view to use the list adapter
        collegesListView.setAdapter(collegesListAdapter);
    }

    /**
     * viewCollegeDetails is called whenever a user taps on a particular college.
     * This initiates creation of an Intent and the user is sent to a new Activity to view
     * that college's details.
     * @param view
     */
    public void viewCollegeDetails(View view) {

        // COMPLETED: Implement the view college details using an Intent
        Intent collegeDetails = new Intent(this, CollegeDetailsActivity.class);

        LinearLayout selectedLayout = (LinearLayout) view;
        College selectedCollege = (College) selectedLayout.getTag();

        collegeDetails.putExtra("Name", selectedCollege.getName());
        collegeDetails.putExtra("Rating", selectedCollege.getRating());
        collegeDetails.putExtra("Population", selectedCollege.getPopulation());
        collegeDetails.putExtra("Tuition", selectedCollege.getTuition());
        collegeDetails.putExtra("ImageName", selectedCollege.getImageName());

        startActivity(collegeDetails);
    }

    /**
     * addCollege is called when the user fills in details for a new college and they tap
     * Add College.
     *
     * @param view
     */
    public void addCollege(View view) {

        // COMPLETED: Implement the code for when the user clicks on the addCollegeButton
        String name = mNameEditText.getText().toString();
        String population = mPopulationEditText.getText().toString();
        String tuition = mTuitionEditText.getText().toString();
        double rating = mRatingBar.getRating();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(population) || TextUtils.isEmpty(tuition))
            Toast.makeText(this, "All information about the college must be provided", Toast.LENGTH_LONG).show();
        else
        {
            College newCollege = new College(name, Integer.parseInt(population), Double.parseDouble(tuition), rating);
            db.addCollege(newCollege);
            collegesList.add(newCollege);
        }

        collegesListAdapter.notifyDataSetChanged();
        mPopulationEditText.setText("");
        mTuitionEditText.setText("");
        mRatingBar.setRating(0);
        mNameEditText.setText("");
        mNameEditText.setHint("Enter name of the college");
    }
}
