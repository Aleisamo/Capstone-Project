package aleisamo.github.com.childadventure.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import aleisamo.github.com.childadventure.Model.Child;
import aleisamo.github.com.childadventure.R;

public class ChildAdventureViewFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    private ArrayList<Child> childList = new ArrayList<>();

    public ChildAdventureViewFactory(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        createChildList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.child);
        Child child = childList.get(position);
        remoteViews.setTextViewText(R.id.child_name,child.getName());
        remoteViews.setTextViewText(R.id.age,child.getAge());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void createChildList (){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("child");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    childList.add(data.getValue(Child.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
