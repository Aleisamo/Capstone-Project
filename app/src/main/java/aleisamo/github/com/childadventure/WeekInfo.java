package aleisamo.github.com.childadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_week_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        if (savedInstanceState == null){
            createFragmentActivities();
        }
    }

    // listener for save and edit week info
    @OnClick({R.id.fab_save,R.id.fab_edit})
    public void options(FloatingActionButton fab){
        if (fab.getId() == R.id.fab_edit ){
            Toast.makeText(this,"edit text",Toast.LENGTH_SHORT ).show();
        }
        else{
            Toast.makeText(this,"save text",Toast.LENGTH_SHORT ).show();
        }
    }
    @OnClick({R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday})
    public void day (TextView day){
        Intent openDailyMenu = new Intent(this,WeeklyMenu.class);
        openDailyMenu.putExtra("day", nameDay(day));
        startActivity(openDailyMenu);
    }

    public String nameDay (TextView textView) {
        switch (textView.getId()){
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




    /*private void createFragmentMenu(){
        // create widget_list_ingredients card fragment
        WeeklyMenu weeklyMenuFragment = new WeeklyMenu();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.week_fragment,weeklyMenuFragment)
                .commit();
    }*/

    private void createFragmentActivities(){
        // create widget_list_ingredients card fragment
        WeekActivitiesFragment weekActivitiesFragment = new WeekActivitiesFragment();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.week_activity_fragment,weekActivitiesFragment)
                .commit();

    }


    }


