import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DownloadChildPicture extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private String downnloadUrl;
    Context context;
    public DownloadChildPicture(String downnloadUrl,Context context){
        this.downnloadUrl = downnloadUrl;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        downloadChildPicture(downnloadUrl);
        return null;
    }

    private Void downloadChildPicture(String url) {
        // http service
        // call class Picture Attributes
        return null;

    }
}
