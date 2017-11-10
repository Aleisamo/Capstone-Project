package aleisamo.github.com.childadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeekInfo extends AppCompatActivity {

    @BindView(R.id.fab_edit)
    FloatingActionButton mEdit;
    @BindView(R.id.fab_save)
    FloatingActionButton mSave;
    @BindView(R.id.monday)
    TextView mMonday;
    @BindView(R.id.tuesday)
    TextView mTuesday;
    @BindView(R.id.wednesday)
    TextView mWednesday;
    @BindView(R.id.thursday)
    TextView mThursday;
    @BindView(R.id.friday)
    TextView mFriday;

    // create reference for object day and access to the data base
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReferenceDay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_week_info);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            createFragmentActivities();
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReferenceDay = mFirebaseDatabase.getReference().child("days");
    }

    // listener for save and edit week info
    @OnClick({R.id.fab_save, R.id.fab_edit})
    public void options(FloatingActionButton fab) {
        if (fab.getId() == R.id.fab_edit) {
            Toast.makeText(this, "edit text", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "save text", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday})
    public void day(TextView day) {
        final String nameDay = nameDay(day);
        mReferenceDay.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    DataSnapshot existingDaySnapshot = null;
                    for (DataSnapshot daySnapshot : dataSnapshot.getChildren()) {
                        Day day = daySnapshot.getValue(Day.class);
                        if (nameDay.equals(day.getName())) {
                            existingDaySnapshot = daySnapshot;
                            break;
                        }
                    }
                    if (existingDaySnapshot == null) {
                        newDailyMenu(nameDay);
                    } else {
                        Day newDay = existingDaySnapshot.getValue(Day.class);
                        String name = newDay.getName();
                        checkDay(name, newDay.getChildKey());
                    }
                } else {
                    newDailyMenu(nameDay);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // create new day on database and save key


    public String nameDay(TextView textView) {
        switch (textView.getId()) {
            case R.id.monday:
                return getString(R.string.monday_i);
            case R.id.tuesday:
                return getString(R.string.tuesday_i);
            case R.id.wednesday:
                return getString(R.string.wednesday_i);
            case R.id.thursday:
                return getString(R.string.thursday_i);
            case R.id.friday:
                return getString(R.string.friday_i);
            default:
                return "";
        }
    }

    public void checkDay(String nameDay, String id) {
        Intent readMenu = new Intent(this, WeeklyMenu.class);
        readMenu.putExtra("day", nameDay);
        readMenu.putExtra("id", id);
        startActivity(readMenu);
    }

    public void newDailyMenu(String nameDay) {
        String id = mReferenceDay.push().getKey();
        String breakfast = "empty";
        String snacks = "empty";
        String lunch = "empty";
        String dinner = "empty";
        Menu dailyMenu = new Menu(breakfast, snacks, lunch, dinner);
        Day newDay = new Day(nameDay, dailyMenu, id);
        mReferenceDay.child(id).setValue(newDay);
        // Intent open weekly select_menu pass id from day object and day name
        Intent openNewDailyMenu = new Intent(this, WeeklyMenu.class);
        openNewDailyMenu.putExtra("day", nameDay);
        openNewDailyMenu.putExtra("id", id);
        startActivity(openNewDailyMenu);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void createFragmentActivities() {
        // create widget_list_ingredients card fragment
        WeekActivitiesFragment weekActivitiesFragment = new WeekActivitiesFragment();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.week_activity_fragment, weekActivitiesFragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}


