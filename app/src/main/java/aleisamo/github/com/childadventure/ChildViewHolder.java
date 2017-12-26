package aleisamo.github.com.childadventure;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildViewHolder extends RecyclerView.ViewHolder {
    View mView;

    @BindView(R.id.child_cardView)
    CardView mCardView;
    @BindView(R.id.child_name)
    TextView mChildName;
    @BindView(R.id.age)
    TextView mChildAge;
    @BindView(R.id.photo_profile)
    ImageView mChildPhoto;

    //private final List<Child> children;


    public ChildViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mView = itemView;
        //this.clickListener = clickListener;
        //this.children = children;
        //itemView.setOnClickListener(this);
    }


}
