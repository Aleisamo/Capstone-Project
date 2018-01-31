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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import aleisamo.github.com.childadventure.Data.OnClickListener;
import aleisamo.github.com.childadventure.Model.Child;
import aleisamo.github.com.childadventure.Model.ChildDetails;
import aleisamo.github.com.childadventure.Model.FamilyMember;
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
    private String[] result;
    private AlertDialog addChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super   .onCreate(savedInstanceState);
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
        menuBar();
    }

    @OnClick(R.id.add_profile)
    public void openDialog() {
        createOrUpdatedChildToList(null);

    }

    // create a dialog to enter child's name and age
    public void createOrUpdatedChildToList(final String key) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogAddChild = inflater.inflate(R.layout.dialog_add_child, null);
        dialogBuilder.setView(dialogAddChild);
        final EditText mWriteChildName = (EditText) dialogAddChild.findViewById(R.id.write_child_name);
        //final EditText mWriteChildAge = (EditText) dialogAddChild.findViewById(R.id.write_child_age);

        final TextView mWriteChildAge = (TextView) dialogAddChild.findViewById(R.id.write_child_age);
        final ImageButton mDatePicker = (ImageButton) dialogAddChild.findViewById(R.id.pick_date);
        final DatePicker mPicker = (DatePicker) dialogAddChild.findViewById(R.id.picker);
        mWriteChildAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setVisibility(View.VISIBLE);
                mPicker.setVisibility(View.VISIBLE);

            }
        });
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = mPicker.getDayOfMonth() + "." + mPicker.getMonth() + 1 + "."
                        + mPicker.getYear();
                mWriteChildAge.setText(date);
                mPicker.setVisibility(View.GONE);
                mDatePicker.setVisibility(View.GONE);
            }
        });

        dialogBuilder.setTitle("Add child to List");
        dialogBuilder.setMessage("Write Child's name and age  below");
        dialogBuilder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send data to
                childName = mWriteChildName.getText().toString();
                childAge = mWriteChildAge.getText().toString();
                if (key != null) {
                    final DatabaseReference getChildRef = mReferenceChild.child(key);


                    if (!childName.isEmpty()) {
                        getChildRef.child("childDetails").child("name").setValue(childName);
                        getChildRef.child("name").setValue(childName);
                    }
                    if (!childAge.isEmpty()) {
                        getChildRef.child("childDetails").child("age").setValue(childAge);
                        getChildRef.child("age").setValue(childAge);
                    }
                    Toast.makeText(getApplication(), "updated", Toast.LENGTH_SHORT).show();
                } else {
                    writeChildProfile(childName, childAge);
                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        addChild = dialogBuilder.create();
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

        String keyChild = ((Child) list.get(position)).getKey();

        if (mTwoPane) {

            //Child child = (Child) list.get(position);
            //String keyChild = child.getKey();
            Bundle arg = new Bundle();
            arg.putString(getString(R.string.key), keyChild);
            ChildProfileDetailsFragment childProfileDetailsFragment = new ChildProfileDetailsFragment();
            childProfileDetailsFragment.setArguments(arg);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, childProfileDetailsFragment)
                    .commit();
        } else {
            //Child child = (Child) list.get(position);
            //String childKey = child.getKey();
            Intent childDetailsIntent = new Intent(this, ChildProfileDetails.class);
            childDetailsIntent.putExtra(getString(R.string.key), keyChild);
            startActivity(childDetailsIntent);
        }
    }

    @Override
    public String[] onLongClick(View view, final int position, List<?> list) {
        selectedPosition = position;
        Child child = (Child) list.get(position);
        result = new String[]{Integer.toString(selectedPosition), child.getKey()};
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(mActionModeCallback);
        }
        return result;
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
                        removeChild(result[1]);
                        mode.finish();
                        break;
                    case R.id.updated:
                        createOrUpdatedChildToList(result[1]);
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

    private void removeChild(String key) {
        mReferenceChild.child(key).removeValue();
        Toast.makeText(getApplication(), "remove" , Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onPause() {
        super.onPause();
        addChild.dismiss();

    }
}



