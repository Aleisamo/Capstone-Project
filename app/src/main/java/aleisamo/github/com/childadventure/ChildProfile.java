package aleisamo.github.com.childadventure;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChildProfile extends AppCompatActivity {

    @BindView(R.id.add_profile)
    FloatingActionButton mAddChild;
    @BindView(R.id.delete_profile)
    FloatingActionButton mDeleteProfile;
    private String childName;
    private String childAge;
    private final String FILL = "FILL";
    // firebase entry
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReferenceChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            createFragmentActivities();
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReferenceChild = mFirebaseDatabase.getReference().child("child");
    }

    @OnClick(R.id.add_profile)
    public void openDialog() {
        addChildToList();
    }

    // create a dialog to enter child's name and age
    public void addChildToList() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogAddChild = inflater.inflate(R.layout.dialog_add_child, null);
        final EditText mWriteChildName = (EditText) dialogAddChild.findViewById(R.id.write_child_name);
        final EditText mWriteChildAge = (EditText) dialogAddChild.findViewById(R.id.write_child_age);

        dialogBuilder.setView(dialogAddChild);

        dialogBuilder.setTitle("Add child to List");
        dialogBuilder.setMessage("Write Child's name and age  below");
        dialogBuilder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send data to
                childName = mWriteChildName.getText().toString();
                childAge = mWriteChildAge.getText().toString();
                writeChildProfile(childName, childAge);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog addChild = dialogBuilder.create();
        addChild.show();
    }

    // Method write firebase child profile

    public void writeChildProfile(String childName, String childAge) {
        String id = mReferenceChild.push().getKey();
        String picture_path = "";
        FamilyMember familyMember = new FamilyMember("",
                "",
                "",
                "",
                "",
                "",
                "",
                "");
        ChildDetails childDetails = new ChildDetails(childName,
                childAge,
                "",
                "",
                familyMember,
                "",
                "",
                "",
                "");
        Child child = new Child(childName, childAge, picture_path, childDetails);
        mReferenceChild.child(id).setValue(child);
    }

    // create Fragment
    private void createFragmentActivities() {
        // create widget_list_ingredients card fragment
        ChildProfileFragment childProfileFragment = new ChildProfileFragment();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.profile_fragment, childProfileFragment)
                .commit();

    }


}
