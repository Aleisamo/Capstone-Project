package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class PhotoGallery extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        if (savedInstanceState == null){
            createFragmentActivities();
        }
    }

    // Create childminder fragment
    private void createFragmentActivities() {
        // create widget_list_ingredients card fragment
        ChildminderGalleryFragment galleryFragment = new ChildminderGalleryFragment();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.child_minder_gallery, galleryFragment)
                .commit();

    }

    // TODO Create Children fragment



}
