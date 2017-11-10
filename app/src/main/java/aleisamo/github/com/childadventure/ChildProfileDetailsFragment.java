package aleisamo.github.com.childadventure;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildProfileDetailsFragment extends Fragment {

    @BindView(R.id.edit_profile)
    FloatingActionButton mEditProfile;
    @BindView(R.id.profile_picture)
    ImageView mProfilePicture;
    @BindView(R.id.childName)
    EditText mChildName;
    @BindView(R.id.childAge)
    EditText mChildAge;
    @BindView(R.id.dob)
    EditText mDOB;
    @BindView(R.id.child_address)
    EditText mChildAddresse;
    @BindView(R.id.family_member1)
    EditText mFamilyMember1;
    @BindView(R.id.family_member2)
    EditText mFamilyMember2;
    @BindView(R.id.phone1)
    EditText mPhone1;
    @BindView(R.id.phone2)
    EditText mPhone2;
    @BindView(R.id.email1)
    EditText mEmail1;
    @BindView(R.id.email2)
    EditText mEmail2;
    @BindView(R.id.address1)
    EditText mAddress1;
    @BindView(R.id.address2)
    EditText mAddress2;
    @BindView(R.id.languages)
    EditText mLanguage;
    @BindView(R.id.allergies)
    EditText mAllergies;
    @BindView(R.id.relevant_info)
    EditText mRelevantInfo;

    private DatabaseReference mDataRef;
    private FirebaseDatabase mFirebase;


    public ChildProfileDetailsFragment() {
    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_profile_details, container, false);
        ButterKnife.bind(this, rootView);
        mFirebase = FirebaseDatabase.getInstance();
        ;
        Integer pathLength = mFirebase.getReference().child("child").toString().length();

        if (getArguments() != null) {
            String pathDetails = getArguments().getString("pathDetails").substring(pathLength + 1);
            mDataRef = mFirebase.getReference().child("child").child(pathDetails).child("childDetails");
            mUpdatedDetails(mDataRef);

        }
        return rootView;
    }

    public void mUpdatedDetails(DatabaseReference mDataRef) {

        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ChildDetails childDetails = dataSnapshot.getValue(ChildDetails.class);
                    //Log.v("ChildDetails",childDetails.getName());
                    mChildName.setText(childDetails.getName());
                    mProfilePicture.setImageResource(R.mipmap.ic_profile);
                    mChildAge.setText(childDetails.getAge());
                    mDOB.setText(childDetails.getDob());
                    mChildAddresse.setText(childDetails.getAddress());
                   /* mFamilyMember1.setText(childDetails.getFamilyMember().getMemberName1());
                    mAddress1.setText(childDetails.getFamilyMember().getAddress1());
                    mEmail1.setText(childDetails.getFamilyMember().getEmail1());
                    mPhone1.setText(childDetails.getFamilyMember().getPhoneNumber1());
                    mFamilyMember2.setText(childDetails.getFamilyMember().getMemberName2());
                    mAddress2.setText(childDetails.getFamilyMember().getAddress2());
                    mEmail2.setText(childDetails.getFamilyMember().getEmail2());
                    mPhone2.setText(childDetails.getFamilyMember().getPhoneNumber2());*/
                    mAllergies.setText(childDetails.getAllergies());
                    mLanguage.setText(childDetails.getLanguages());
                    mRelevantInfo.setText(childDetails.getRelevantInformation());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

   /* @OnClick(R.id.dob)
    public void pick() {
        mCalendarView.setVisibility(View.VISIBLE);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


            }
        });*/




}