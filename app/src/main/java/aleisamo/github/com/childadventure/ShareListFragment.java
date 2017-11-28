package aleisamo.github.com.childadventure;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareListFragment extends Fragment {

    @BindView(R.id.share_list)
    RecyclerView mShareList;

    private GridLayoutManager childShareLayoutManager;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDataReference;
    private FirebaseStorage mFirebaseStorage;
    private FirebaseRecyclerAdapter<Child, ChildShareViewHolder> mChildShareAdapter;

    public ShareListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_list, container, false);
        ButterKnife.bind(this, rootView);
        int numberColumns = 3;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDataReference = mFirebaseDatabase.getReference().child("child");
        childShareLayoutManager = new GridLayoutManager(getContext(),numberColumns);
        readChild(childShareLayoutManager);
        return rootView;
    }

    public void readChild(LinearLayoutManager manager) {

        FirebaseRecyclerOptions<Child> options =
                new FirebaseRecyclerOptions.Builder<Child>()
                        .setQuery(mDataReference, Child.class)
                        .build();
        mChildShareAdapter = new FirebaseRecyclerAdapter<Child, ChildShareViewHolder>(options) {

            @Override
            public ChildShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_share_list, parent, false);
                return new ChildShareViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final ChildShareViewHolder holder, final int position, Child model) {
                String childPictureUrl = model.getPictureUrl();
                String childName = model.getName();
                if (childPictureUrl.isEmpty()) {
                    holder.mChildPhoto.setImageResource(R.mipmap.ic_profile);
                } else {
                    Picasso.with(getContext()).load(childPictureUrl).into(holder.mChildPhoto);
                }
                holder.mChildName.setText(childName);


            }
        };
        mShareList.setLayoutManager(manager);
        mShareList.setAdapter(mChildShareAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mChildShareAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mChildShareAdapter.startListening();
    }
}
