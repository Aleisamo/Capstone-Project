package aleisamo.github.com.childadventure;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    View mView;
    @BindView(R.id.photo_id)
    ImageView mPhotoGallery;
    @BindView(R.id.text_id)
    TextView mComment;
    public GalleryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mView = itemView;
    }



}
