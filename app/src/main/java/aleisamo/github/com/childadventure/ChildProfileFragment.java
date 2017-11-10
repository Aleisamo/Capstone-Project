package aleisamo.github.com.childadventure;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildProfileFragment extends Fragment {

    private static final String LAYOUT_CHILD = "layoutManagerChildList";
    private LinearLayoutManager childListLayoutManager;
    private OnItemClickListener callback;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseChildReference;
    private FirebaseRecyclerAdapter<Child, ChildViewHolder> mChildAdapter;
    private ActionMode.Callback mActionModeCallback;
    private ActionMode mActionMode;


    @BindView(R.id.list_children)
    RecyclerView mListChildren;
    boolean multiSelect = false;
    private int selectedPosition = -1;


    public ChildProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_profile, container, false);
        ButterKnife.bind(this, rootView);
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

        FirebaseRecyclerOptions<Child> options =
                new FirebaseRecyclerOptions.Builder<Child>()
                        .setQuery(databaseChildReference, Child.class)
                        .build();
        mChildAdapter = new FirebaseRecyclerAdapter<Child, ChildViewHolder>(options) {
            public DatabaseReference getChildRef;

            @Override
            public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child, parent, false);
                return new ChildViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final ChildViewHolder holder, final int position, Child model) {
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

                if (selectedPosition == position)
                    holder.mCardView.setCardBackgroundColor(Color.CYAN);
                else
                    holder.mCardView.setCardBackgroundColor(Color.TRANSPARENT);

                mActionModeCallback = new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        multiSelect = true;
                        getActivity().getMenuInflater().inflate(R.menu.select_menu, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove:
                                Toast.makeText(getContext(), "remove", Toast.LENGTH_SHORT).show();
                                removeChild(selectedPosition);
                                mode.finish();
                                break;
                            case R.id.updated:
                                Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
                                updatedChild(getChildRef);
                                break;
                        }
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        multiSelect = false;
                        mActionMode = null;


                    }
                };
                // longClick Action to activate the contextual action mode
                holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        selectedPosition = position;
                        getChildRef = mChildAdapter.getRef(position);
                        notifyDataSetChanged();
                        //Toast.makeText(getContext(), "long press action", Toast.LENGTH_LONG).show();
                        // check how to un select item previously selected TODO
                        if (mActionMode != null)
                            return false;
                        else
                            ((AppCompatActivity) v.getContext()).startSupportActionMode(mActionModeCallback);
                        return true;

                    }
                });


                // onclick Action to launch Details profile Activity for item selected

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path_detail =mChildAdapter.getRef(position).toString();
                        Intent childDetails = new Intent(getActivity(),ChildProfileDetails.class);
                        childDetails.putExtra("path_details",path_detail);
                        startActivity(childDetails);
                       // Toast.makeText(getContext(), "Child Removed from the list" + mChildAdapter.getRef(position).child("childDetails").child("age"), Toast.LENGTH_SHORT).show();
                    }
                });


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

    public void removeChild(int position) {
        mChildAdapter.getRef(position).removeValue();
    }

    public void updatedChild(final DatabaseReference getChildRef) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogAddChild = inflater.inflate(R.layout.dialog_add_child, null);
        final EditText mWriteChildName = (EditText) dialogAddChild.findViewById(R.id.write_child_name);
        final EditText mWriteChildAge = (EditText) dialogAddChild.findViewById(R.id.write_child_age);

        dialogBuilder.setView(dialogAddChild);

        dialogBuilder.setTitle("Add child to List");
        dialogBuilder.setMessage("Write Child's name and age  below");
        dialogBuilder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String childName = mWriteChildName.getText().toString();
                String childAge = mWriteChildAge.getText().toString();
                if (!childName.isEmpty()) {
                    getChildRef.child("childDetails").child("name").setValue(childName);
                    getChildRef.child("name").setValue(childName);
                }
                if (!childAge.isEmpty()) {
                    getChildRef.child("childDetails").child("age").setValue(childAge);
                    getChildRef.child("age").setValue(childAge);
                }

            }

        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog addChild = dialogBuilder.create();
        addChild.show();

    }
}
