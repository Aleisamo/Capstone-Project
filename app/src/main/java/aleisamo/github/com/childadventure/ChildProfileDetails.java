package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ChildProfileDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile_details);


        if (savedInstanceState == null) {
            createFragmentActivities(getIntent().getStringExtra("path_details"));
        }
    }

    // create Fragment
    private void createFragmentActivities(String pathDetails) {
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

    }

}
