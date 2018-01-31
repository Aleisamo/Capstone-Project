package aleisamo.github.com.childadventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import aleisamo.github.com.childadventure.Model.Child;
import aleisamo.github.com.childadventure.Model.ChildDetails;
import aleisamo.github.com.childadventure.Model.FamilyMember;
import aleisamo.github.com.childadventure.Model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChildProfileDetailsFragment extends Fragment {

    @BindView(R.id.edit_profile)
    FloatingActionButton mEditProfile;
    @BindView(R.id.allow_user)
    FloatingActionButton mAllowUser;
    @BindView(R.id.profile_picture)
    ImageView mProfilePicture;
    @BindView(R.id.childName)
    EditText mChildName;
    @BindView(R.id.childAge)
    EditText mChildAge;
    @BindView(R.id.dob)
    EditText mDOB;
    @BindView(R.id.child_address)
    EditText mChildAddress;
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
    private DatabaseReference mDataRefName;
    private DatabaseReference mDataRefUser;

    private FirebaseDatabase mFirebase;
    private String keyChild;
    private String userName;
    private String email;


    public ChildProfileDetailsFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_child_profile_details, container, false);
        ButterKnife.bind(this, rootView);
        mFirebase = FirebaseDatabase.getInstance();
        //Integer pathLength = mFirebase.getReference().child("child").toString().length();
        // using pathDetails
       /* if (getArguments() != null) {
            String pathDetails = getArguments().getString("pathDetails").substring(pathLength + 1);
            mDataRef = mFirebase.getReference().child("child").child(pathDetails).child("childDetails");
            mDataRefName = mFirebase.getReference().child("child").child(pathDetails);
            mUpdatedDetails(mDataRef);

        }*/

        // using ArrayList
        if (getArguments() != null) {
            ArrayList<ChildDetails> details = getArguments().
                    getParcelableArrayList("arrayDetails");
            keyChild = getArguments().getString(getString(R.string.key));
            mDataRef = mFirebase.getReference().child("child").child(keyChild).child("childDetails");
            mDataRefName = mFirebase.getReference().child("child").child(keyChild);
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
                    mProfilePicture.setImageResource(R.drawable.ic_face);
                    mChildAge.setText(childDetails.getAge());
                    mDOB.setText(childDetails.getDob());
                    mChildAddress.setText(childDetails.getAddress());
                    mFamilyMember1.setText(childDetails.getFamilyMember().getMemberName1());
                    mAddress1.setText(childDetails.getFamilyMember().getAddress1());
                    mEmail1.setText(childDetails.getFamilyMember().getEmail1());
                    mPhone1.setText(childDetails.getFamilyMember().getPhoneNumber1());
                    mFamilyMember2.setText(childDetails.getFamilyMember().getMemberName2());
                    mAddress2.setText(childDetails.getFamilyMember().getAddress2());
                    mEmail2.setText(childDetails.getFamilyMember().getEmail2());
                    mPhone2.setText(childDetails.getFamilyMember().getPhoneNumber2());
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

    @OnClick(R.id.edit_profile)
    public void updatedDetails() {
        String childName = mChildName.getText().toString().trim();
        String age = mChildAge.getText().toString().trim();
        String dob = mDOB.getText().toString().trim();
        String address = mChildAddress.getText().toString().trim();
        String address1 = mAddress1.getText().toString().trim();
        String address2 = mAddress2.getText().toString().trim();
        String email1 = mEmail1.getText().toString().trim();
        String email2 = mEmail2.getText().toString().trim();
        String phone1 = mPhone1.getText().toString().trim();
        String phone2 = mPhone2.getText().toString().trim();
        String member1 = mFamilyMember1.getText().toString().trim();
        String member2 = mFamilyMember2.getText().toString().trim();
        String allergies = mAllergies.getText().toString().trim();
        String languages = mLanguage.getText().toString().trim();
        String relevantInfo = mRelevantInfo.getText().toString().trim();

        FamilyMember familyMember = new FamilyMember(
                member1, member2,
                phone1, phone2,
                email1, email2,
                address1, address2);

        ChildDetails childDetails = new ChildDetails(childName, age, "", dob, familyMember, address, allergies, languages, relevantInfo);
        mDataRef.setValue(childDetails);
        Child child = new Child(childName, keyChild, age, "", childDetails);
        mDataRefName.setValue(child);
        Toast.makeText(getContext(), getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.allow_user)
    public void assignUser() {
        final ArrayList<String> keyList = new ArrayList<>();
        keyList.add(keyChild);
        createDialog(keyList);
    }

    //  TODO alert dialog move to a class later

    private void createDialog(final ArrayList<String> keyList) {
        AlertDialog.Builder addUser = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogAssignUser = inflater.inflate(R.layout.dialog_assign_user, null);
        final EditText mWriteUserName = (EditText) dialogAssignUser
                .findViewById(R.id.write_userName);
        final EditText mWriteUserEmail = (EditText) dialogAssignUser
                .findViewById(R.id.write_user_email);
        addUser.setView(dialogAssignUser);
        addUser.setTitle(getString(R.string.dialog_user_title));
        addUser.setMessage(getString(R.string.dialog_user_message));
        addUser.setPositiveButton("Assign", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // create user assignment
                userName = mWriteUserName.getText().toString();
                email = mWriteUserEmail.getText().toString();
                createUser(keyList, userName, email);
            }

        });

        addUser.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Action has been cancelled",
                        Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog add = addUser.create();
        add.show();
    }

    private void createUser(ArrayList<String> key, String userName, String userEmail) {
        mDataRefUser = mFirebase.getReference().child(getString(R.string.childminder_user));
        String id = mDataRefUser.push().getKey();
        Integer role = Integer.valueOf(getString(R.string.user_role));
        User user = new User(userName, userEmail, key, role);
        mDataRefUser.child(id).setValue(user);
    }
}
