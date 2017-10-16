package aleisamo.github.com.childadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildMinderDashboard extends AppCompatActivity {

    @BindView(R.id.take_photo)Button takePhoto;
    @BindView(R.id.week_info)Button weekInfo;
    @BindView(R.id.profiles)Button profiles;
    @BindView(R.id.gallery)Button gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_minder_dashborad);
        ButterKnife.bind(this);

    }

    public void sendTo (View view){
        switch (view.getId()){
            case R.id.take_photo:
                Intent intentPhoto = new Intent(this, TakePhoto.class);
                startActivity(intentPhoto);
                break;
            case R.id.week_info:
                //Intent intentPhoto = new Intent(this, TakePhoto.class);
                //startActivity(intentPhoto);
                break;
            case R.id.profiles:
               // Intent intentPhoto = new Intent(this, TakePhoto.class);
                //startActivity(intentPhoto);
                break;
            case R.id.gallery:
               // Intent intentPhoto = new Intent(this, TakePhoto.class);
               // startActivity(intentPhoto);
                break;
        }
    }


}
