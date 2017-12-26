package aleisamo.github.com.childadventure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildMinderDashboard extends AppCompatActivity {

    private static final String FILE_PROVIDER_AUTHORITY = "aleisamo.github.com.fileprovider";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final String PREFERENCE = "reference" ;
    @BindView(R.id.take_photo)
    Button takePhoto;
    @BindView(R.id.week_info)
    Button weekInfo;
    @BindView(R.id.profiles)
    Button profiles;
    @BindView(R.id.gallery)
    Button gallery;
    private String mTempPath;
    private SharedPreferences.Editor sharePreferTempPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_minder_dashborad);
        ButterKnife.bind(this);
    }

    public void sendTo(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                takePhoto();
                break;
            case R.id.week_info:
                Intent intentWeekInfo = new Intent(this, WeekInfo.class);
                startActivity(intentWeekInfo);
                break;
            case R.id.profiles:
                Intent intentChildProfile = new Intent(this, ChildProfile.class);
                startActivity(intentChildProfile);
                break;
            case R.id.gallery:
                Intent galleryPhoto = new Intent(this, PhotoGallery.class);
                startActivity(galleryPhoto);
                break;
        }
    }

    public void takePhoto() {
        // Check for the external storage permission
       if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Launch the camera if the permission exists
            launchCamera();
        }
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
                sharePreferTempPath =
                        getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit();
                sharePreferTempPath.putString("getUriPath", mTempPath);
                sharePreferTempPath.apply();


                // Get the content URI for the image file

                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);
               /* Bitmap bitmaprotated = BitmapFactory.decodeFile(mTempPath);
                if (bitmaprotated.getWidth() >bitmaprotated.getHeight()){
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmaprotated  = Bitmap.createBitmap(bitmaprotated,0,0,
                            bitmaprotated.getWidth(),bitmaprotated.getHeight(),matrix,true);
                }*/

                // Add the URI so the camera can store the image
               takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
               startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera();
                   //mTempPath = cameraServices.launchCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {
            // Otherwise, delete the temporary image file
            PictureAttributes.deletePictureFile(this, mTempPath);
        }
    }

    private void processAndSetImage() {
        Intent intent = new Intent(this, TakePicture.class);
        SharedPreferences sendUriString = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        intent.putExtra(String.valueOf(R.string.path), sendUriString.getString("getUriPath",null));
        startActivity(intent);
    }

}
