package aleisamo.github.com.childadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import aleisamo.github.com.childadventure.Model.ChildPicture;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildminderGalleryFragment extends Fragment {

    private FirebaseRecyclerAdapter<ChildPicture, GalleryViewHolder> mGalleryAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDataRef;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;

    @BindView(R.id.recycle_gallery)
    RecyclerView mRecycleGallery;

    public ChildminderGalleryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_minder_gallery, container, false);
        ButterKnife.bind(this, rootView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = mFirebaseStorage.getReference();
        mDataRef = mFirebaseDatabase.getReference().child("pictures_uploaded").child("childminderPictures");
        int numberColumns = 3;
        GridLayoutManager childminderGridLayout = new GridLayoutManager(getContext(), numberColumns);
        displayImage(childminderGridLayout);
        return rootView;
    }

    public void displayImage(GridLayoutManager manager) {
        FirebaseRecyclerOptions<ChildPicture> options =
                new FirebaseRecyclerOptions.Builder<ChildPicture>()
                        .setQuery(mDataRef, ChildPicture.class).build();
        mGalleryAdapter = new FirebaseRecyclerAdapter<ChildPicture, GalleryViewHolder>(options) {
            @Override
            public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.photo, parent, false);
                return new GalleryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final GalleryViewHolder holder, final int position, ChildPicture model) {
                //String pictureDescription = model.getChildPictureDescription();
                String fileName = model.getStorageFileName();
                StorageReference imageRef = mStorageRef.child("image" + getString(R.string.childminderFolder))
                        .child(fileName);
                Glide.with(getContext())
                        .using(new FirebaseImageLoader())
                        .load(imageRef)
                        .into(holder.mPhotoGallery);
                //holder.mComment.setText(pictureDescription);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path_detail = mGalleryAdapter.getRef(position).toString();
                        Intent intentPicture = new Intent(getContext(),PictureDetails.class);
                        intentPicture.putExtra("dataRef", path_detail);
                        startActivity(intentPicture);
                    }
                });
            }
        };
        mRecycleGallery.setLayoutManager(manager);
        mRecycleGallery.setAdapter(mGalleryAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        mGalleryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGalleryAdapter.stopListening();
    }
}
