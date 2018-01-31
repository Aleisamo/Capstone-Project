package aleisamo.github.com.childadventure;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aleisamo.github.com.childadventure.Data.OnClickListener;
import aleisamo.github.com.childadventure.Model.Child;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    View mView;

    @BindView(R.id.child_cardView)
    CardView mCardView;
    @BindView(R.id.child_name)
    TextView mChildName;
    @BindView(R.id.age)
    TextView mChildAge;
    @BindView(R.id.photo_profile)
    ImageView mChildPhoto;

    private final List<Child> children;
    private final OnClickListener clickListener;


    public ChildListViewHolder(View itemView, List<Child> children, OnClickListener clickListener) {
        super(itemView);
        this.children = children;
        this.clickListener = clickListener;
        ButterKnife.bind(this, itemView);
        mView = itemView;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        //this.clickListener = clickListener;
        //this.children = children;
        //itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (clickListener != null){
            clickListener.onClick(v,getAdapterPosition(),children);
        }

    }

    @Override
    public boolean onLongClick(View v) {
        if (clickListener != null){
            clickListener.onLongClick(v,getAdapterPosition(),children);
        }
        return true;
    }
}
