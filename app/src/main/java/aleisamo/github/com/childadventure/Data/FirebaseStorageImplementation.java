package aleisamo.github.com.childadventure.Data;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import aleisamo.github.com.childadventure.ChildPicture;

public class FirebaseStorageImplementation {
    Bitmap mBitmap;
    Context context;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    public FirebaseStorageImplementation(Bitmap mBitmap, StorageReference mStorageRef,
                                         Context context, DatabaseReference mDatabaseRef) {
        this.mBitmap = mBitmap;
        this.mStorageRef = mStorageRef;
        this.mDatabaseRef = mDatabaseRef;
        this.context = context;

    }

    // Image name name use when the picture is temp saved
    // folder where the picture will be stored
    public void storeAndSavePicturePath(String imageName, final String folderName,
                                        final ProgressDialog progressDialog,
                                        final String profileName, final String description) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        //InputStream inputStream = new ByteArrayInputStream(byteArray);
        // final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference storageReference = mStorageRef.child("image" + folderName + "/" + imageName);
        storageReference.putStream(inputStream)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        ChildPicture childPicture = new ChildPicture(
                                profileName,
                                taskSnapshot.getDownloadUrl().toString(),
                                description
                        );

                        String pictureUploadKey = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(pictureUploadKey).setValue(childPicture);
                        Toast.makeText(context, "Picture uploaded to ChildMinder gallery",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error uploading picture",
                                Toast.LENGTH_SHORT).show();
                    }
                });
               /* .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        // redefine the percentage "wrong numbers"
                        double progress = (taskSnapshot.getBytesTransferred()*100) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded" + ((int) progress) + "%..");
                    }
                });*/
    }


}

