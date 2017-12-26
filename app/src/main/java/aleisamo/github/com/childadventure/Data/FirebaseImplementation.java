package aleisamo.github.com.childadventure.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseImplementation {

    private FirebaseDatabase mFirebaseChildAdventure;
    private DatabaseReference mDataReferenceMenu;

    public FirebaseImplementation(FirebaseDatabase mFirebaseChildAdventure,
                                  DatabaseReference mDataReferenceMenu) {
        this.mFirebaseChildAdventure = mFirebaseChildAdventure;
        this.mDataReferenceMenu = mDataReferenceMenu;

    }


    public void dayReadFunction() {
        mFirebaseChildAdventure = FirebaseDatabase.getInstance();
        mDataReferenceMenu = mFirebaseChildAdventure.getReference().child("menu");

    }


}
