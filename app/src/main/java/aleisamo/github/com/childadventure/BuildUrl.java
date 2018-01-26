package aleisamo.github.com.childadventure;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class BuildUrl {

    private static final String LOG_TAG = BuildUrl.class.getSimpleName();
    private String url;

    private final Context context;

    public BuildUrl(Context context , String url) {

        this.context = context;
        this.url = url;
    }

    public URL buildUrl() throws MalformedURLException {
        Uri pictureUrl = Uri.parse(url).buildUpon()
                 .build();
        URL url = new URL(pictureUrl.toString());
        Log.v(LOG_TAG, "Uribuild:" + url);
        return url;
    }
}
