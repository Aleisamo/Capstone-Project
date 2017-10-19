package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WeeklyMenu extends AppCompatActivity {

    private static final String TAG = "WEEKLY_MENU" ;
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
    private FirebaseDatabase mFireBaseChildAdventure;
    private DatabaseReference mMenuReference;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_menu);
        ButterKnife.bind(this);
        mFireBaseChildAdventure = FirebaseDatabase.getInstance();
        mMenuReference = mFireBaseChildAdventure.getReference().child("menu");
    }
    @OnClick(R.id.save_menu)
    public void getMenu(){
        String breakfast = mBreakfast.getText().toString().trim();
        String snacks = mSnacks.getText().toString().trim();
        String lunch = mLunch.getText().toString().trim();
        String dinner = mDinner.getText().toString().trim();
        String dayName = getIntent().getStringExtra("day");
        Log.v(TAG, "getMenu: "+dayName);
        Menu dailyMenu = new Menu (breakfast,snacks,lunch,dinner,dayName);
        mMenuReference.push().setValue(dailyMenu);
        Toast.makeText(this,dayName+"/n"+"menu save",Toast.LENGTH_SHORT).show();
    }

  }
