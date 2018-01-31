/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package aleisamo.github.com.childadventure;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PictureAttributes {

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";


    static Bitmap resamplePicture(Context context, String imagePath) {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;

        // Get details from original bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int outWidth = bmOptions.outWidth;
        int outHeight = bmOptions.outHeight;


        // Determine min scale factor
        int scaleFactor = Math.min(outWidth / widthPixels, outHeight / heightPixels);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath);
    }

    static File tempFileCreation(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String pictureFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(
                pictureFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    static boolean deletePictureFile(Context context, String imagePath) {
        // Get the file
        File imageFile = new File(imagePath);

        // Delete the image
        boolean deleted = imageFile.delete();

        // If there is an error deleting the file, show a Toast
        if (!deleted) {
            String errorMessage = "error";
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }

        return deleted;
    }

    /**
     * Helper method for adding the photo to the system photo gallery so it can be accessed
     * from other apps.
     *
     * @param imagePath The path of the saved image
     */
    private static void galleryAddPic(Context context, String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    public static String savePictureDeviceStorage(Context context, Bitmap image, String pictureName) {

        String savedImagePath = null;

        // Create the new file in the external storage
       // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
         //       Locale.getDefault()).format(new Date());
        String imageFileName = pictureName+ ".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/ChildAdventure");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        // Save the new Bitmap
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try (OutputStream fOut = new FileOutputStream(imageFile)) {
                image.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return savedImagePath;
    }

}
