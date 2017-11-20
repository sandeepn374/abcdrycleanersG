package com.ravikiraninfotech.abcdrycleaners;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeliveryActivity extends Activity implements SearchView.OnQueryTextListener {


    SearchView simpleSearchView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.delivery);

        simpleSearchView = (SearchView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener(this);



    }


    private void Toastmsg(DeliveryActivity collectionActivity, String p1)
    {

        Toast.makeText(this,p1,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        final String text = query;

        setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.delivery);

        simpleSearchView = (SearchView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener(this);


        FirebaseDatabase.getInstance().getReference().child("usersG")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int totDue=0;
                        ArrayList<User> users=new ArrayList<User>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final User user = snapshot.getValue(User.class);

                            System.out.println("db" + user.ph);
                            if (user.ph.equals(text)) {
                                users.add(user);

                            }
                        }
                        java.util.Collections.reverse(users);
                        int l=0;
                        int tot=0;
                        for(int h=0;h<users.size();h++){
                            tot=tot+users.get(h).due;
                        }
                        for(final User user:users) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT);
                            TextView tv1 = new TextView(DeliveryActivity.this);
                            TextView tv2 = new TextView(DeliveryActivity.this);

                            TextView tv3 = new TextView(DeliveryActivity.this);

                            TextView tv4 = new TextView(DeliveryActivity.this);

                            TextView tv5 = new TextView(DeliveryActivity.this);

                            TextView tv0 = new TextView(DeliveryActivity.this);

							
							String rem="";
							for(int p=0;l<user.billDetailsArrayList.size();p++){
								
								rem=rem+"Cloth Type  "+user.billDetailsArrayList.get(p).clothType+"\n";
								
								rem=rem+"Quantity  "+user.billDetailsArrayList.get(p).qty+"\n";
								
								rem=rem+"Price  "+user.billDetailsArrayList.get(p).price+"\n";
								
								}

                            tv1.setText("Name " + user.name);
                            tv2.setText("Bill Number " + user.billNumber);
                            tv3.setText("Amount " + user.total);
                            tv4.setText("Due  " + user.due);
                            tv5.setText("Discount  " + user.discount+"\n"+rem);
                            totDue += user.due;
                            TableRow.LayoutParams trparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                            tv1.setLayoutParams(trparams);
                            tv2.setLayoutParams(trparams);
                            tv3.setLayoutParams(trparams);
                            tv4.setLayoutParams(trparams);
                            tv5.setLayoutParams(trparams);
                            tv0.setLayoutParams(trparams);
                            tv0.setTextColor(Color.RED);
                            
                            tv0.setText("Total Due " + tot);
                            TableRow tr0 = new TableRow(DeliveryActivity.this);

                            TableLayout layoutINNER = new TableLayout(DeliveryActivity.this);

                            tr0.setLayoutParams(params);
                            tr0.addView(tv0);
                            if(l==0) {
                                layoutINNER.addView(tr0);
                            l++;
                            }


                            layoutINNER.setLayoutParams(params);
                            TableRow tr = new TableRow(DeliveryActivity.this);

                            tr.setLayoutParams(params);
                            tr.addView(tv1);
                            TableRow tr2 = new TableRow(DeliveryActivity.this);

                            tr2.setLayoutParams(params);
                            tr2.addView(tv2);

                            TableRow tr3 = new TableRow(DeliveryActivity.this);

                            tr3.setLayoutParams(params);
                            tr3.addView(tv3);


                            TableRow tr6 = new TableRow(DeliveryActivity.this);

                            tr6.setLayoutParams(params);
                            tr6.addView(tv4);

                            TableRow tr7 = new TableRow(DeliveryActivity.this);

                            tr7.setLayoutParams(params);
                            tr7.addView(tv5);

                            layoutINNER.addView(tr);
                            layoutINNER.addView(tr2);

                            layoutINNER.addView(tr3);


                            layoutINNER.addView(tr6);

                            layoutINNER.addView(tr7);

                            TableRow tr4 = new TableRow(DeliveryActivity.this);


                            Button paid = new Button(DeliveryActivity.this);
                            paid.setText("Paid And Delivered");
                            paid.setTextColor(Color.BLACK);
                            paid.setGravity(Gravity.LEFT);
                            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                            paid.setLayoutParams(new TableRow.LayoutParams(width, height));
                            // paid.setWidth(5);

                            paid.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    user.due = 0;


                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    mDatabase.keepSynced(true);
                                    Query query = mDatabase.child("usersG").orderByChild("billNumber");
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            //String billNumber=null;
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                Log.d("User key", child.getKey());
                                                Log.d("User val", child.child("billNumber").getValue().toString());
                                                String billNumber = child.child("billNumber").getValue().toString();
                                                if (billNumber.equals(user.billNumber)) {
                                                    child.getRef().child("due").setValue(0);

                                                    child.getRef().child("discount").setValue(0);

                                                    setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.delivery);

                                                    simpleSearchView = (SearchView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.simpleSearchView);
                                                    simpleSearchView.setOnQueryTextListener(DeliveryActivity.this);
                                                    Toastmsg(DeliveryActivity.this, "Bill Has been Updated");

                                                }

                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    ;


                                }
                            });

                            Button paid2 = new Button(DeliveryActivity.this);
                            paid2.setText("Credited And Delivered");
                            paid2.setTextColor(Color.BLACK);
                            paid2.setGravity(Gravity.LEFT);
                            // paid2.setLayoutParams(new TableRow.LayoutParams(width, height));
                            paid2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    user.due = 0;


                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    mDatabase.keepSynced(true);
                                    Query query = mDatabase.child("usersG").orderByChild("billNumber");
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            //String billNumber=null;
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                Log.d("User key", child.getKey());
                                                Log.d("User val", child.child("billNumber").getValue().toString());
                                                String billNumber = child.child("billNumber").getValue().toString();
                                                if (billNumber.equals(user.billNumber)) {
                                                    child.getRef().child("due").setValue(user.total);

                                                    child.getRef().child("discount").setValue(0);

                                                    setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.delivery);

                                                    simpleSearchView = (SearchView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.simpleSearchView);
                                                    simpleSearchView.setOnQueryTextListener(DeliveryActivity.this);
                                                    Toastmsg(DeliveryActivity.this, "Bill Has been Updated");

                                                }

                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    ;

                                }
                            });


                            tr4.setLayoutParams(params);
                            tr4.addView(paid);
                            tr4.addView(paid2);
                            Button paid3 = new Button(DeliveryActivity.this);
                            paid3.setText("Partially paid and delivered");
                            paid3.setTextColor(Color.BLACK);
                            paid3.setWidth(10);
                            paid3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    user.due = 0;


                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    mDatabase.keepSynced(true);
                                    Query query = mDatabase.child("usersG").orderByChild("billNumber");
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            //String billNumber=null;
                                            for (final DataSnapshot child : dataSnapshot.getChildren()) {
                                                Log.d("User key", child.getKey());
                                                Log.d("User val", child.child("billNumber").getValue().toString());
                                                String billNumber = child.child("billNumber").getValue().toString();
                                                final String[] partial = {""};
                                                // child.getRef().child("due").setValue(0);
                                                if (billNumber.equals(user.billNumber)) {

                                                    final AlertDialog.Builder alert = new AlertDialog.Builder(DeliveryActivity.this);


                                                    final EditText edittext = new EditText(DeliveryActivity.this);
                                                    alert.setMessage("Please enter the partial paid amount");
                                                    alert.setTitle("Amount");

                                                    alert.setView(edittext);


                                                    alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            //What ever you want to do with the value
                                                            //Editable YouEditTextValue = edittext.getText();
                                                            //OR
                                                            String total = child.child("total").getValue().toString();
                                                            int totalInt = Integer.parseInt(total);
                                                            String partial = edittext.getText().toString();
                                                            int partialInt = Integer.parseInt(partial);
                                                            child.getRef().child("due").setValue(totalInt - partialInt);

                                                            child.getRef().child("discount").setValue(0);
                                                            // alert1.dismiss();
                                                            // Toastmsg(DeliveryActivity.this,"Bill Has been Updated");
                                                            Toastmsg(DeliveryActivity.this, "Bill Has been Updated");


                                                        }
                                                    });

                                                    alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            // what ever you want to do with No option.
                                                            //alert1.dismiss();
                                                        }
                                                    });

                                                    final AlertDialog alert1 = alert.create();

                                                    alert1.show();


                                                    setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.delivery);

                                                    simpleSearchView = (SearchView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.simpleSearchView);
                                                    simpleSearchView.setOnQueryTextListener(DeliveryActivity.this);

                                                }

                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    ;

                                }
                            });


                            Button paid4 = new Button(DeliveryActivity.this);
                            paid4.setText("Discounted  and delivered");
                            paid4.setTextColor(Color.BLACK);
                            paid4.setGravity(Gravity.LEFT);
                            //  paid4.setWidth(5);
                            paid4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    user.due = 0;


                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    mDatabase.keepSynced(true);
                                    Query query = mDatabase.child("usersG").orderByChild("billNumber");
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            //String billNumber=null;
                                            for (final DataSnapshot child : dataSnapshot.getChildren()) {
                                                Log.d("User key", child.getKey());
                                                Log.d("User val", child.child("billNumber").getValue().toString());
                                                String billNumber = child.child("billNumber").getValue().toString();
                                                final String[] partial = {""};
                                                // child.getRef().child("due").setValue(0);
                                                if (billNumber.equals(user.billNumber)) {

                                                    final AlertDialog.Builder alert = new AlertDialog.Builder(DeliveryActivity.this);


                                                    final EditText edittext = new EditText(DeliveryActivity.this);
                                                    alert.setMessage("Please enter the discount amount");
                                                    alert.setTitle(" Discount Amount");

                                                    alert.setView(edittext);


                                                    alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            //What ever you want to do with the value
                                                            //Editable YouEditTextValue = edittext.getText();
                                                            //OR
                                                            String total = child.child("total").getValue().toString();
                                                            int totalInt = Integer.parseInt(total);
                                                            String partial = edittext.getText().toString();
                                                            int partialInt = Integer.parseInt(partial);
                                                            child.getRef().child("due").setValue(0);
                                                            child.getRef().child("discount").setValue(partialInt);
                                                            Toastmsg(DeliveryActivity.this, "Bill Has been Updated");

                                                            // alert1.dismiss();
                                                            // Toastmsg(DeliveryActivity.this,"Bill Has been Updated");


                                                        }
                                                    });

                                                    alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            // what ever you want to do with No option.
                                                            //alert1.dismiss();
                                                        }
                                                    });

                                                    final AlertDialog alert1 = alert.create();

                                                    alert1.show();


                                                    setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.delivery);

                                                    simpleSearchView = (SearchView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.simpleSearchView);
                                                    simpleSearchView.setOnQueryTextListener(DeliveryActivity.this);

                                                }

                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    ;


                                }
                            });

                            TableRow tr5 = new TableRow(DeliveryActivity.this);

                            tr5.setLayoutParams(params);
                            tr5.addView(paid3);
                            tr5.addView(paid4);

                            layoutINNER.addView(tr4);

                            layoutINNER.addView(tr5);

                            // tv0.setText("Total Due---"+totDue[0]);
                            //tv0.setTextColor(Color.RED);

                            LinearLayout main = (LinearLayout) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.main_layout);

                            main.addView(layoutINNER);

                        }

                          //  }
                            //System.out.println(user.email);
                       // }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length()==10) {
            String text = newText;
        }
        return false;
    }

    public void onBackPressed(){
        Intent i=new Intent(DeliveryActivity.this,SampleActivity.class);
        startActivity(i);
        setContentView(R.layout.activity_sample);

    }


}
