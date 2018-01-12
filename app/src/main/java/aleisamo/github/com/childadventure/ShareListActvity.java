package aleisamo.github.com.childadventure;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import aleisamo.github.com.childadventure.Data.FirebaseStorageImplementation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareListActvity extends AppCompatActivity {

    @BindView(R.id.share_list)
    RecyclerView mShareList;
    private final String activityName = "ShareListActivity";
    private LinearLayoutManager childShareLayoutManager;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDataReference;
    private FirebaseRecyclerAdapter<Child, ChildShareViewHolder> mChildShareAdapter;
    private String getDescription;
    private String getImageName;
    //private Bitmap mBitmap;

    FirebaseStorageImplementation mFirebaseStorageImp;
    FirebaseStorage mFirebaseStorage;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;
    private String getImagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);
        ButterKnife.bind(this);
        getDescription = getIntent().getStringExtra("updatedChildPicture");
        getImageName = Uri.parse(getIntent().getStringExtra("imagePath")).getLastPathSegment();
        getImagePath = getIntent().getStringExtra("imagePath");
        Bitmap mBitmap = PictureAttributes.resamplePicture(this, getImagePath);
        // firebase storage and database references
        // access to firebase storage
        mFirebaseStorage = FirebaseStorage.getInstance();
        // create storage ref
        mStorageRef = mFirebaseStorage.getReference();
        // database reference
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFireBaseDataBase.getReference().child("pictures_uploaded");
        mFirebaseStorageImp = new FirebaseStorageImplementation(mBitmap, mStorageRef,
                this, mDatabaseRef);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDataReference = mFirebaseDatabase.getReference().child("child");
        childShareLayoutManager = new LinearLayoutManager(getApplicationContext());
        readChild(childShareLayoutManager);
    }

    public void readChild(LinearLayoutManager manager) {

        FirebaseRecyclerOptions<Child> options =
                new FirebaseRecyclerOptions.Builder<Child>()
                        .setQuery(mDataReference, Child.class)
                        .build();
        mChildShareAdapter = new FirebaseRecyclerAdapter<Child, ChildShareViewHolder>(options) {

            @Override
            public ChildShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_share_list, parent, false);
                return new ChildShareViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final ChildShareViewHolder holder, final int position, Child model) {
                String childPictureUrl = model.getPictureUrl();
                final String childName = model.getName();
                if (childPictureUrl.isEmpty()) {
                    holder.mChildPhoto.setImageResource(R.mipmap.ic_profile);
                } else {
                    Picasso.with(getApplicationContext()).load(childPictureUrl).into(holder.mChildPhoto);
                }
                holder.mChildName.setText(childName);
                //assign photo to the child
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // save photo path within childPicture object
                        savePicture(childName, getString(R.string.childFolder));
                    }
                });

            }
        };
        mShareList.setLayoutManager(manager);
        mShareList.setAdapter(mChildShareAdapter);
    }

    // change method name assignChildPicture
    private void savePicture(String profileName, String folderName) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        mFirebaseStorageImp.storeAndSavePicturePath(getImageName, folderName, progressDialog,
                profileName, getDescription,activityName);
    }

    @Override
    public void onStart() {
        super.onStart();
        mChildShareAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mChildShareAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
