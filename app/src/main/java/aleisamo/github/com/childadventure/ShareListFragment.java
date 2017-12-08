package aleisamo.github.com.childadventure;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import butterknife.OnClick;

public class ShareListFragment extends Fragment {

    @BindView(R.id.share_list)
    RecyclerView mShareList;
    @BindView(R.id.childminder_photo)
    ImageView mChildminderPhoto;

    private GridLayoutManager childShareLayoutManager;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDataReference;
    private DatabaseReference mUploadPictureReference;
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


    public ShareListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_list, container, false);
        ButterKnife.bind(this, rootView);
        // get arguments from TakePicture activity bundle
        getDescription = getArguments().getString("updatedChildPicture");
        getImageName = Uri.parse(getArguments().getString("imagePath")).getLastPathSegment();
        getImagePath = getArguments().getString("imagePath");
        Bitmap mBitmap = PictureAttributes.resamplePicture(getContext(), getImagePath);

        // firebase storage and database references
        // access to firebase storage
        mFirebaseStorage = FirebaseStorage.getInstance();
        // create storage ref
        mStorageRef = mFirebaseStorage.getReference();
        // database reference
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFireBaseDataBase.getReference().child("pictures_uploaded");
        mFirebaseStorageImp = new FirebaseStorageImplementation(mBitmap, mStorageRef,
                getContext(), mDatabaseRef);

        // GridView params
        int numberColumns = 3;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDataReference = mFirebaseDatabase.getReference().child("child");
        childShareLayoutManager = new GridLayoutManager(getContext(), numberColumns);
        readChild(childShareLayoutManager);
        return rootView;
    }

    @OnClick(R.id.childminder_photo)
    public void saveToChildminderGallery() {
        String childminderName = getString(R.string.childminderFolder);
        savePicture(childminderName, childminderName);
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
                    Picasso.with(getContext()).load(childPictureUrl).into(holder.mChildPhoto);
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
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        mFirebaseStorageImp.storeAndSavePicturePath(getImageName, folderName, progressDialog,
                profileName, getDescription);
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
}
