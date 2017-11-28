package aleisamo.github.com.childadventure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakePicture extends AppCompatActivity {

    @BindView(R.id.share_list_fragment)
    FrameLayout mShareList;
    @BindView(R.id.camera_preview)
    ImageView mImagePreview;
    @BindView(R.id.clear)
    FloatingActionButton mClearPicture;
    @BindView(R.id.save)
    FloatingActionButton mSavePicture;
    @BindView(R.id.share)
    FloatingActionButton mSharePicture;
    @BindView(R.id.insert_comment)
    FloatingActionButton mInsertComment;


    Bitmap mBitmap;
    private String mPath;
    Uri pictureUri;
    FirebaseStorage mFirebaseStorage;
    StorageReference mStorageRef, mPictureRef;
    UploadTask uploadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        ButterKnife.bind(this);
        setImagePreview();
        if (savedInstanceState == null){
            createFragmentActivities();
        }

        // access to firebase storage
        mFirebaseStorage = FirebaseStorage.getInstance();

        // create storage ref
        mStorageRef = mFirebaseStorage.getReference();

    }


    public void setImagePreview() {
        Intent photoPath = getIntent();
        mPath = photoPath.getStringExtra(String.valueOf(R.string.path));
        mBitmap = PictureAttributes.resamplePicture(this, mPath);
        mImagePreview.setImageBitmap(mBitmap);
    }

    @OnClick(R.id.clear)
    public void clearPicture() {
        mImagePreview.setImageResource(0);
        PictureAttributes.deletePictureFile(this, mPath);
        Intent go_back = new Intent(this, ChildMinderDashboard.class);
        startActivity(go_back);
    }

    @OnClick(R.id.save)
    public void savePicture() {
        pictureUri = Uri.parse(mPath);
        String imageName = pictureUri.getLastPathSegment();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        StorageReference storageReference = mStorageRef.child("image/"+imageName);
        storageReference.putStream(inputStream)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Log.v("url",taskSnapshot.getDownloadUrl().toString());
                        Toast.makeText(getApplicationContext(), "Picture uploaded to ChildMinder gallery",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error uploading picture",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded" + ((int) progress) + "%..");
                    }
                });


    }

    @OnClick(R.id.share)
    public void assignPic(){
        mShareList.setVisibility(View.VISIBLE);
        // get intent if intent is !null then setVisibility
        mSavePicture.setVisibility(View.GONE);
        mSharePicture.setVisibility(View.GONE);
        mInsertComment.setVisibility(View.GONE);
       // Uri assignedPicture = Uri.parse(mPath);
    }

    @OnClick(R.id.insert_comment)
    public void insertComment (){
        Toast.makeText(this, "write a short description", Toast.LENGTH_SHORT).show();
    }



    // create Fragment
    private void createFragmentActivities() {
        // create widget_list_ingredients card fragment
        ShareListFragment shareListFragment = new ShareListFragment();

        // add fragment to the activity using Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // transaction
        fragmentManager.beginTransaction()
                .replace(R.id.share_list_fragment,shareListFragment)
                .commit();

    }





}
