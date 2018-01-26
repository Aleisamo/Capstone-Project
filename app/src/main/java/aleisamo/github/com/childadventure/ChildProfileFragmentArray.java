package aleisamo.github.com.childadventure;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildProfileFragmentArray extends Fragment  {
    @BindView(R.id.list_children)
    RecyclerView mListChildren;
    private LinearLayoutManager mChildListlm;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReferenceChild;
    private OnClickListener callback;

    public ChildProfileFragmentArray() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback =(OnClickListener)context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_profile_array, container, false);
        ButterKnife.bind(this, rootView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReferenceChild = mFirebaseDatabase.getReference().child("child");
        mChildListlm = new LinearLayoutManager(getContext());
        mListChildren.setLayoutManager(mChildListlm);
        createListChildren(callback, mListChildren);
      /*  if (getArguments() != null) {
            mChildListlm = new LinearLayoutManager(getContext());
            mListChildren.setLayoutManager(mChildListlm);
            ArrayList<Child> childList = getArguments().getParcelableArrayList(getString(R.string.childList));
        }*/


        //ChildAdapter childAdapter = new ChildAdapter(childList, getContext(), this);
        //mListChildren.setAdapter(childAdapter);

        return rootView;

    }

    public void createListChildren(final OnClickListener clickListener, final RecyclerView recyclerView) {
        final ArrayList<Child> listChild = new ArrayList<>();
        mReferenceChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChild.clear();
                if (dataSnapshot.hasChildren()) {
                    for  (DataSnapshot data : dataSnapshot.getChildren()) {
                            listChild.add(data.getValue(Child.class));


                    }
                    ChildAdapter childAdapter = new ChildAdapter(listChild,
                            getContext(), clickListener);
                    recyclerView.setAdapter(childAdapter);
                    childAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("list of children error", "list is not populated");
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

   /* @Override
    public void onClick(View view, int position, List<?> list) {
        Child child = (Child) list.get(position);
        String childKey = child.getKey();
        Intent childDetailsIntent = new Intent(getActivity(), ChildProfileDetails.class);
        //childDetailsIntent.putParcelableArrayListExtra(getString(R.string.childDetails), child.getChildDetails());
        childDetailsIntent.putExtra(getString(R.string.key), childKey);
        getContext().startActivity(childDetailsIntent);

    }

    @Override
    public void onLongClick(View view, int position, List<?> list) {

        Toast.makeText(getContext(), "this is long click", Toast.LENGTH_SHORT).show();

    }*/
}
