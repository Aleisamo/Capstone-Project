package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PictureDetails extends AppCompatActivity {
    @BindView(R.id.picture_zoom)
    ImageView mImageView;
    @BindView(R.id.description)
    TextView mTextView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_details);
        ButterKnife.bind(this);
        String dataRef = getIntent().getStringExtra("dataRef");
        File file = new File(dataRef);
        String name = file.getName();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.folderName)).child(name);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChildPicture childPicture = dataSnapshot.getValue(ChildPicture.class);
                mStorageRef = mFirebaseStorage.getReference().child("image" +
                        getString(R.string.childminderFolder)).
                        child(childPicture.getStorageFileName());
                Glide.with(getApplicationContext())
                        .using(new FirebaseImageLoader())
                        .load(mStorageRef)
                        .into(mImageView);
                mTextView.setText(childPicture.getChildPictureDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @OnClick(R.id.picture_zoom)
    public void download(){



    }
}
