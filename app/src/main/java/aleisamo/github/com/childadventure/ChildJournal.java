package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import aleisamo.github.com.childadventure.Model.ChildPicture;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildJournal extends AppCompatActivity {

    String childKey;
    @BindView(R.id.pager)
    ViewPager mPager;
    private DatabaseReference mReferenceChild;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_child_journal);
        ButterKnife.bind(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReferenceChild = mFirebaseDatabase.getReference().
                child(getString(R.string.pictures_folder)).
                child("foo");
        createListPhoto();

    }


    private void createListPhoto (){
        final ArrayList<ChildPicture> listChild = new ArrayList<>();
        mReferenceChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChild.clear();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        listChild.add(data.getValue(ChildPicture.class));
                    }
                    JournalPagerAdapter pagerAdapter =
                            new JournalPagerAdapter(getApplicationContext(),listChild);
                    mPager.setAdapter(pagerAdapter);
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("list of children error", "list is not populated");
            }
        });




    }












}
