package aleisamo.github.com.childadventure;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildShareViewHolder extends RecyclerView.ViewHolder {
    View mView;

    @BindView(R.id.childName)
    TextView mChildName;
    @BindView(R.id.photo_profile)
    ImageView mChildPhoto;

    public ChildShareViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mView = itemView;
    }



}
