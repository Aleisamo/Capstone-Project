package aleisamo.github.com.childadventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class DownloadChildPicture extends AsyncTask<Void, Void, Boolean> {
    private URL downnloadUrl;
    Context context;
    String storageFileName;

    public DownloadChildPicture(URL downnloadUrl,String storageFileName,Context context) {
        this.downnloadUrl = downnloadUrl;
        this.context = context;
        this.storageFileName = storageFileName;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        //downloadChildPicture(downnloadUrl);
        PictureAttributes pictureAttributes = new PictureAttributes();
        pictureAttributes.savePictureDeviceStorage(context, downloadChildPicture(downnloadUrl), storageFileName);
        return true;
    }
    private Bitmap downloadChildPicture(URL url) {
        // http service
        // call class Picture Attributes
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // read input stream in to String
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Toast.makeText(context,"finish download",Toast.LENGTH_SHORT).show();
    }
}


