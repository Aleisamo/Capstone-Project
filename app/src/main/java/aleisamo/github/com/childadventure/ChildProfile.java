package aleisamo.github.com.childadventure;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChildProfile extends AppCompatActivity implements OnClickListener {

    @BindView(R.id.add_profile)
    FloatingActionButton mAddChild;
    private String childName;
    private String childAge;

    private ActionMode.Callback mActionModeCallback;
    private ActionMode mActionMode;
    private int selectedPosition = -1;


    // firebase entry
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReferenceChild;


    // two pane mode
    boolean mTwoPane;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        mTwoPane = findViewById(R.id.fragment) != null;
        ButterKnife.bind(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReferenceChild = mFirebaseDatabase.getReference().child("child");

        if (savedInstanceState == null) {

            if (mTwoPane) {
                ChildProfileDetailsFragment childProfileDetailsFragment = new ChildProfileDetailsFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, childProfileDetailsFragment)
                        .commit();
            }
            createFragmentWithArray();
        }

        //if (savedInstanceState == null) {
           /* if (mTwoPane){
                ChildProfileDetailsFragment childProfileDetailsFragment = new ChildProfileDetailsFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment,childProfileDetailsFragment)
                        .commit();
            }*/

        //  createFragmentActivities();
        // }
        menuBar();
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
        id = mReferenceChild.push().getKey();
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
        Child child = new Child(childName, id, childAge, picture_path, childDetails);
        Log.v("full child", child.toString());
        mReferenceChild.child(id).setValue(child);
    }

    // populate ArrayList

 /*   public void populateChildList() {

        mReferenceChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChildList.clear();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        mChildList.add(data.getValue(Child.class));
                    }
                    //createFragmentWithArray(mChildList);
                    Log.v("listofchildren", mChildList.get(0).getAge());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("list of children error", "list is not populated");
            }

        });

    }*/

    private void createFragmentWithArray() {
        Bundle args = new Bundle();
        args.putString(getString(R.string.key), id);
        ChildProfileFragmentArray childProfileFragmentArray = new ChildProfileFragmentArray();
        childProfileFragmentArray.setArguments(args);
        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.profile_fragment_array, childProfileFragmentArray)
                .commit();
    }

    @Override
    public void onClick(View view, int position, List<?> list) {

        if (mTwoPane) {

            Child child = (Child) list.get(position);
            String keyChild = child.getKey();
            Bundle arg = new Bundle();
            arg.putString(getString(R.string.key), keyChild);
            ChildProfileDetailsFragment childProfileDetailsFragment = new ChildProfileDetailsFragment();
            childProfileDetailsFragment.setArguments(arg);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, childProfileDetailsFragment)
                    .commit();
        } else {

            Child child = (Child) list.get(position);
            String childKey = child.getKey();
            Intent childDetailsIntent = new Intent(this, ChildProfileDetails.class);
            //childDetailsIntent.putParcelableArrayListExtra(getString(R.string.childDetails), child.getChildDetails());
            childDetailsIntent.putExtra(getString(R.string.key), childKey);
            startActivity(childDetailsIntent);
        }
    }

    @Override
    public int onLongClick(View view, final int position, List<?> list) {
        selectedPosition = position;
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(mActionModeCallback);
            //((AppCompatActivity) view.getContext()).startSupportActionMode(mActionModeCallback);

        }
        return selectedPosition;
    }

    public void menuBar() {
        mActionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                getMenuInflater().inflate(R.menu.select_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.remove:
                        Toast.makeText(getApplication(), "remove" + selectedPosition, Toast.LENGTH_SHORT).show();
                        //removeChild(selectedPosition);
                        mode.finish();
                        break;
                    case R.id.updated:
                        Toast.makeText(getApplication(), "updated", Toast.LENGTH_SHORT).show();
                        //updatedChild();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
            }
        };


    }

    private boolean isTwoPane() {
        return getResources().getBoolean(R.bool.isTwoPane);
    }


    private void updatedChild() {
    }

    private void removeChild(int position) {
    }

    // create widget_list_ingredients card fragment

}



