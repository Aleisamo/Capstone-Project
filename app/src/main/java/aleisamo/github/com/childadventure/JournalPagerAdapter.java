package aleisamo.github.com.childadventure;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import aleisamo.github.com.childadventure.Model.ChildPicture;


public class JournalPagerAdapter extends PagerAdapter{
    Context context;
    LayoutInflater mLayoutInflater;
    List<ChildPicture> pictures;
    private FirebaseStorage firebaseStorage;
    private StorageReference storagePictureRef;

    public JournalPagerAdapter(Context context ,List<ChildPicture>pictures){
        this.context = context;
        this.pictures =pictures;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String pictureName = pictures.get(position).getStorageFileName();
        firebaseStorage = FirebaseStorage.getInstance();
        storagePictureRef = firebaseStorage.getReference().child("imagechildFolder")
                .child(pictureName);
        View view = mLayoutInflater.inflate(R.layout.pager_item, container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
        Glide.with(context).using(new FirebaseImageLoader()).load(storagePictureRef)
                .into(imageView);
        TextView description = (TextView) view.findViewById(R.id.text2);
        description.setText(pictures.get(position).getChildPictureDescription());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
