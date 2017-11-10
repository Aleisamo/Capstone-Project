package aleisamo.github.com.childadventure;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Child_Adapter extends RecyclerView.Adapter<ChildViewHolder> {

    private final List<Child> children;
    private OnItemClickListener clickListener;
    private Context context;

    public Child_Adapter(List<Child> children, Context context) {
        this.children = children;
        this.context = context;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child, parent, false);
        return new ChildViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {

        // implement read function firebase

        String child_Name = children.get(position).getName();
        String child_Age = children.get(position).getAge();
        holder.mChildName.setText(child_Name);
        holder.mChildAge.setText(child_Age);

        // load image using glide or picasso


    }

    public void hasPhotoProfile(int position, ChildViewHolder child_view_holder, Context context) {
        String imageUrl = children.get(position).getPictureUrl();
        if (imageUrl.isEmpty()) {
            child_view_holder.mChildPhoto.setImageResource(R.mipmap.ic_profile);
        } else {
            Picasso.with(context).load(imageUrl).into(child_view_holder.mChildPhoto);

        }
    }

    @Override
    public int getItemCount() {
        return children.size();
    }


    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener =itemClickListener;

    }
}
