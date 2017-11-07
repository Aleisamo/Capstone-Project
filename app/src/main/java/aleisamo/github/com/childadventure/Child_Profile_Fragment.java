package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Child_Profile_Fragment extends Fragment {

    private static final String LAYOUT_CHILD = "layoutManagerChildList";
    private LinearLayoutManager childListLayoutManager;
    private OnItemClickListener callback;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseChildReference;
    private FirebaseRecyclerAdapter<Child,Child_View_Holder> mChildAdapter;


    @BindView(R.id.list_children)
    RecyclerView mListChildren;

    public Child_Profile_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_profile, container, false);
        ButterKnife.bind(this,rootView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseChildReference = firebaseDatabase.getReference().child("child");
        childListLayoutManager = new LinearLayoutManager(getContext());
        //mListChildren.setLayoutManager(childListLayoutManager);
        // get list of child read firebasedatabase
        // child_adapter.setClickListener(callback);
        readChild(childListLayoutManager);
        return rootView;
    }

    public void readChild(LinearLayoutManager manager) {
        //final ArrayList<Child>child = new ArrayList<>();
        FirebaseRecyclerOptions<Child> options =
                new FirebaseRecyclerOptions.Builder<Child>()
                        .setQuery(databaseChildReference, Child.class)
                        .build();

        mChildAdapter = new FirebaseRecyclerAdapter<Child, Child_View_Holder>(options) {
            @Override
            public Child_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child, parent, false);
                return new Child_View_Holder(view);
            }

            @Override
            protected void onBindViewHolder(Child_View_Holder holder, int position, Child model) {
                String child_Name = model.getName();
                String child_Age = model.getAge();
                String imageUrl = model.getPictureUrl();
                if (imageUrl.isEmpty()) {
                    holder.mChildPhoto.setImageResource(R.mipmap.ic_profile);
                } else {
                    Picasso.with(getContext()).load(imageUrl).into(holder.mChildPhoto);

                }
                holder.mChildName.setText(child_Name);
                holder.mChildAge.setText(child_Age);


            }
        };
        mListChildren.setLayoutManager(manager);
        mListChildren.setAdapter(mChildAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mChildAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mChildAdapter.startListening();
    }
}
