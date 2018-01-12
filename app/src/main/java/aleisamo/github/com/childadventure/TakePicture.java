package aleisamo.github.com.childadventure;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import aleisamo.github.com.childadventure.Data.FirebaseStorageImplementation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO check lifecycle to restore activity when is on background

public class TakePicture extends AppCompatActivity {

    private static final String PREFERENCE = "preference";
    private static final String FILE_PROVIDER_AUTHORITY = "aleisamo.github.com.fileprovider";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
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
    @BindView(R.id.check)
    FloatingActionButton mKeepComment;
    @BindView(R.id.photo_description)
    EditText mPhotoDescription;


    private String mTempPath;
    private SharedPreferences.Editor pictureName;


    FirebaseStorageImplementation mFirebaseStorageImp;
    FirebaseStorage mFirebaseStorage;
    FirebaseDatabase mFireBaseDataBase;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;
    private SharedPreferences.Editor getDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        ButterKnife.bind(this);
        Intent photoPath = getIntent();
        if (photoPath != null) {
            String currentPath = photoPath.getStringExtra(String.valueOf(R.string.path));
            setImagePreview(currentPath);
            savePath(currentPath);
        }
        // access to firebase storage
        mFirebaseStorage = FirebaseStorage.getInstance();
        // create storage ref
        mStorageRef = mFirebaseStorage.getReference();
        // database reference
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFireBaseDataBase.getReference().child("childminder_uploaded");

    }

    public void setImagePreview(String mPath) {
        Bitmap mBitmap = PictureAttributes.resamplePicture(this, mPath);
        mImagePreview.setImageBitmap(mBitmap);
    }

    public void savePath(String mPath) {
        pictureName = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit();
        pictureName.putString("getPath", mPath);
        pictureName.apply();
    }

    @OnClick(R.id.clear)
    public void clearPicture() {
        launchCamera();
    }

    @OnClick(R.id.save)
    public void saveChildminderPhoto() {
        String childminderName = getString(R.string.childminderFolder);
        savePicture(childminderName, childminderName);
    }


    @OnClick(R.id.share)
    public void assignPic() {
        intentShareListActivity();
    }

    @OnClick(R.id.insert_comment)
    public void insertComment() {
        AlertDialog.Builder addDescription = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogAddDescription = inflater.inflate(R.layout.dialog_add_description, null);
        final EditText mWritePhotoDescription = (EditText) dialogAddDescription.findViewById(R.id.write_description);
        addDescription.setView(dialogAddDescription);
        addDescription.setTitle("Add description");
        addDescription.setPositiveButton("add", (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String photoDescription = mWritePhotoDescription.getText().toString();
                getDescription =
                        getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit();
                getDescription.putString("getDescription", photoDescription);
                getDescription.apply();
            }
        }));

        addDescription.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Don't forget to write description later", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog setDescription = addDescription.create();
        WindowManager.LayoutParams wlp = setDescription.getWindow().getAttributes();
        wlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        setDescription.show();
    }

    // create Fragment
    private void intentShareListActivity() {
        Intent intent = new Intent(TakePicture.this, ShareListActvity.class);
        // create widget_list_ingredients card fragment
        SharedPreferences uploadDescription = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences picturePath = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String saveDescription = uploadDescription.getString("getDescription", null);
        String savePath = picturePath.getString("getPath", null);
        // Create bundle put arguments
        intent.putExtra("updatedChildPicture", saveDescription);
        intent.putExtra("imagePath", savePath);
        startActivity(intent);
    }

    private void savePicture(String profileName, String folderName) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        SharedPreferences uploadDescription = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String getDescription = uploadDescription.getString("getDescription", null);
        SharedPreferences picturePath = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        String getImageName = picturePath.getString("getPath", null);
        File file = new File(getImageName);
        String name = file.getName();
        Bitmap mBitmap = PictureAttributes.resamplePicture(this, getImageName);
        mFirebaseStorageImp = new FirebaseStorageImplementation(mBitmap, mStorageRef,
                this, mDatabaseRef);

        mFirebaseStorageImp.storeAndSavePicturePath(name, folderName, progressDialog,
                profileName, getDescription, getLocalClassName());
    }

    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = PictureAttributes.tempFileCreation(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPath = photoFile.getAbsolutePath();
                savePath(mTempPath);
                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            SharedPreferences newPath = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
            String path = newPath.getString("getPath", null);
            setImagePreview(path);
        } else {
            // Otherwise, delete the temporary image file
            PictureAttributes.deletePictureFile(this, mTempPath);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
