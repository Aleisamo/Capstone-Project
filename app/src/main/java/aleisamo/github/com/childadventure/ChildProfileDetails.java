package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class ChildProfileDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile_details);

        // using pathDetails
      /*  if (savedInstanceState == null) {
            createFragmentActivities(getIntent().getStringExtra("path_details"));
              }*/

        if (savedInstanceState == null){
            ArrayList<Parcelable> childDetails = getIntent().
                    getParcelableArrayListExtra(getString(R.string.childDetails));
            String keyChild = getIntent().getStringExtra(getString(R.string.key));
            createFragmentActivitiesArray(childDetails, keyChild);
        }
    }

    // create Fragment
  /*  private void createFragmentActivities(String pathDetails) {
        // create widget_list_ingredients card fragment
        Bundle args = new Bundle();
        args.putString("pathDetails", pathDetails);
        ChildProfileDetailsFragment childDetailsFragment = new ChildProfileDetailsFragment();
        childDetailsFragment.setArguments(args);
        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment, childDetailsFragment)
                .commit();

    }*/

    private void createFragmentActivitiesArray(ArrayList<Parcelable> details, String keyChild) {
        // create widget_list_ingredients card fragment
        Bundle args = new Bundle();
        args.putParcelableArrayList("arrayDetails", details);
        args.putString(getString(R.string.key), keyChild);
        ChildProfileDetailsFragment childDetailsFragment = new ChildProfileDetailsFragment();
        childDetailsFragment.setArguments(args);
        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, childDetailsFragment)
                .commit();

    }

}
