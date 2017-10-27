package aleisamo.github.com.childadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.key;


public class WeeklyMenu extends AppCompatActivity {

    private static final String TAG = "WEEKLY_MENU";
    @BindView(R.id.breakfast_details)
    EditText mBreakfast;
    @BindView(R.id.snacks_details)
    EditText mSnacks;
    @BindView(R.id.lunch_details)
    EditText mLunch;
    @BindView(R.id.dinner_details)
    EditText mDinner;
    @BindView(R.id.save_menu)
    FloatingActionButton mSaveMenu;
    @BindView(R.id.edit_menu)
    FloatingActionButton mEditMenu;
    private FirebaseDatabase mFireBaseChildAdventure;
    private DatabaseReference mMenuReference;
    private String day;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_menu);
        ButterKnife.bind(this);
        day = getIntent().getStringExtra("day");
        String key = getIntent().getStringExtra("key");
        mFireBaseChildAdventure = FirebaseDatabase.getInstance();
        mMenuReference = mFireBaseChildAdventure.getReference().child(day + "details");
        if (key != null) {
            mSaveMenu.setEnabled(true);
            readDailyMenu();
        }
    }

    @OnClick(R.id.save_menu)
    public void getMenu() {
        String breakfast = mBreakfast.getText().toString().trim();
        String snacks = mSnacks.getText().toString().trim();
        String lunch = mLunch.getText().toString().trim();
        String dinner = mDinner.getText().toString().trim();
        Menu dailyMenu = new Menu(breakfast, snacks, lunch, dinner);
        //Map mDay = new HashMap();
        //mDay.put("menu",dailyMenu);
        String id = getIntent().getStringExtra("id");

        mMenuReference.child(id).setValue(dailyMenu);
        Log.v(TAG, "getMenu: " + key);
        Toast.makeText(this, day + "\n" + "menu save", Toast.LENGTH_SHORT).show();
        goBack(id);
    }

    public void goBack(String key) {
        Intent backToDay = new Intent(this, WeekInfo.class);
        backToDay.putExtra("key", key);
        startActivity(backToDay);
    }

    public void readDailyMenu() {
        //Query query = reference.child(key);
        mMenuReference.orderByKey()
                .limitToFirst(1)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.exists()) {
                            Menu menu = dataSnapshot.getValue(Menu.class);
                            mBreakfast.setText(menu.getBreakfast());
                            mSnacks.setText(menu.getSnacks());
                            mLunch.setText(menu.getLunch());
                            mDinner.setText(menu.getDinner());
                        } else
                            Toast.makeText(getApplication(), "no data", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
