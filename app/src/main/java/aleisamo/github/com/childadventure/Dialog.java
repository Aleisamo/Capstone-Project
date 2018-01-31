package aleisamo.github.com.childadventure;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Dialog {

    String key;
    Context context;


    public Dialog(String key, Context context) {
        this.key = key;
        this.context = context;

    }

    public Dialog(Context context) {
        this.context = context;
    }


    public String[] addChildToList() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogAddChild = inflater.inflate(R.layout.dialog_add_child, null);
        dialogBuilder.setView(dialogAddChild);
        final EditText mWriteChildName = (EditText) dialogAddChild.findViewById(R.id.write_child_name);
        //final EditText mWriteChildAge = (EditText) dialogAddChild.findViewById(R.id.write_child_age);
        final TextView mWriteChildAge = (TextView) dialogAddChild.findViewById(R.id.write_child_age);
        final ImageButton mDatePicker = (ImageButton) dialogAddChild.findViewById(R.id.pick_date);
        final DatePicker mPicker = (DatePicker) dialogAddChild.findViewById(R.id.picker);

        mWriteChildAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setVisibility(View.VISIBLE);
                mPicker.setVisibility(View.VISIBLE);

            }
        });
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = mPicker.getDayOfMonth() + "." + mPicker.getMonth() + 1 + "."
                        + mPicker.getYear();
                mWriteChildAge.setText(date);
                mPicker.setVisibility(View.GONE);
                mDatePicker.setVisibility(View.GONE);
            }
        });

        dialogBuilder.setTitle("Add child to List");
        dialogBuilder.setMessage("Write Child's name and age  below");
        dialogBuilder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send data to
                String childName = mWriteChildName.getText().toString();
                String childAge = mWriteChildAge.getText().toString();

                //writeChildProfile(childName[0], childAge[0]);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog addChild = dialogBuilder.create();
        addChild.show();
        return new String[]{mWriteChildName.getText().toString(),mWriteChildAge.getText().toString()};
    }


      /* private void updatedChild(String key) {
        final DatabaseReference getChildRef = mReferenceChild.child(key);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogAddChild = inflater.inflate(R.layout.dialog_add_child, null);
        dialogBuilder.setView(dialogAddChild);
        dialogBuilder.setTitle("Add child to List");
        dialogBuilder.setMessage("Write Child's name and age  below");
        final EditText mWriteChildName = (EditText) dialogAddChild.findViewById(R.id.write_child_name);
        final TextView mWriteChildAge = (TextView) dialogAddChild.findViewById(R.id.write_child_age);
        final ImageButton mDatePicker = (ImageButton) dialogAddChild.findViewById(R.id.pick_date);
        final DatePicker mPicker = (DatePicker) dialogAddChild.findViewById(R.id.picker);
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicker.setVisibility(View.VISIBLE);
                String day  = "Day =" + mPicker.getDayOfMonth();
                String month  = "Month =" + mPicker.getMonth()+1;
                String year  = "Year =" + mPicker.getYear();
                mWriteChildAge.setText(day + "-" + month +"_" + year);
                //
            }
        });

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

    }*/




}
    
    


