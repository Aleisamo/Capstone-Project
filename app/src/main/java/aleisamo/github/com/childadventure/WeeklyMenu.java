package aleisamo.github.com.childadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    private String id;
    private String name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_menu);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("day");

        mFireBaseChildAdventure = FirebaseDatabase.getInstance();
        readDailyMenu();
    }

    @OnClick(R.id.save_menu)
    public void UpdatedMenu() {
        mMenuReference = mFireBaseChildAdventure.getReference().child("days").child(id).child("menu");
        String breakfast = mBreakfast.getText().toString().trim();
        String snacks = mSnacks.getText().toString().trim();
        String lunch = mLunch.getText().toString().trim();
        String dinner = mDinner.getText().toString().trim();
        Menu dailyMenu = new Menu(breakfast, snacks, lunch, dinner);
        mMenuReference.setValue(dailyMenu);
        Log.v(TAG, "getMenu: " + id);
        Toast.makeText(this, name + "\n" + "menu save", Toast.LENGTH_SHORT).show();
        goBack();
    }

    public void goBack() {
        Intent backToDay = new Intent(this, WeekInfo.class);
        startActivity(backToDay);
    }

    public void readDailyMenu() {
        mMenuReference = mFireBaseChildAdventure.getReference().child("days").child(id).child("menu");
        mMenuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menu menu = dataSnapshot.getValue(Menu.class);
                mBreakfast.setText(menu.getBreakfast());
                mSnacks.setText(menu.getSnacks());
                mLunch.setText(menu.getLunch());
                mDinner.setText(menu.getDinner());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
