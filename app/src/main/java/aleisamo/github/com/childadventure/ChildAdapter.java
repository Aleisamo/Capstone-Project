package aleisamo.github.com.childadventure;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import aleisamo.github.com.childadventure.Data.OnClickListener;
import aleisamo.github.com.childadventure.Model.Child;

public class ChildAdapter extends RecyclerView.Adapter<ChildListViewHolder> {

    private final List<Child> childList;
    private Context context;
    private OnClickListener clickListener;

    public ChildAdapter(List<Child> childList, Context context,OnClickListener clickListener) {
        this.childList = childList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public ChildListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewChild = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child, parent, false);
        return new ChildListViewHolder(viewChild, childList, clickListener);
    }

    @Override
    public void onBindViewHolder(ChildListViewHolder holder, int position) {
        String childName = childList.get(position).getName();
        String childAge = childList.get(position).getAge();
        String imageUrl = childList.get(position).getPictureUrl();
        if (imageUrl.isEmpty()) {
            holder.mChildPhoto.setImageResource(R.drawable.ic_face);
        } else {
            Glide.with(context).load(imageUrl).into(holder.mChildPhoto);
        }
        holder.mChildName.setText(childName);
        holder.mChildAge.setText(childAge);

    }

    @Override
    public int getItemCount() {
       // return childList.size();
        return childList  == null ? 0 : childList.size();

    }


}
