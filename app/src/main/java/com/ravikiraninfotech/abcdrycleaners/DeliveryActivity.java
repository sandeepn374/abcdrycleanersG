package com.ravikiraninfotech.abcdrycleaners;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DeliveryActivity extends Activity implements SearchView.OnQueryTextListener {


    SearchView simpleSearchView;


    static {
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


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
                            TextView tv6 = new TextView(DeliveryActivity.this);

                            String rem="";
                            if(user!=null && user.billDetailsArrayList!=null) {
                                for (int p = 0; p < user.billDetailsArrayList.size(); p++) {

                                    rem = rem + "Cloth Type  " + user.billDetailsArrayList.get(p).clothType + "\n";

                                    rem = rem + "Quantity  " + user.billDetailsArrayList.get(p).qty + "\n";

                                    rem = rem + "Price  " + user.billDetailsArrayList.get(p).price + "\n";

                                }
                            }

                            tv1.setText("Name :  " + user.name);
                            tv2.setText("Bill Number :  " + user.billNumber);
                            tv3.setText("Amount : " + user.total);
                            tv6.setText("Date : " +user.pickUpDate);
                            tv4.setText("Due : " + user.due);


                            if (user.due==0)
                                tv4.setTextColor(Color.GREEN);
                            else
                                tv4.setTextColor(Color.RED);


                            tv5.setText("Discount : " + user.discount);

                            totDue += user.due;
                            TableRow.LayoutParams trparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                            tv1.setLayoutParams(trparams);
                            tv2.setLayoutParams(trparams);
                            tv3.setLayoutParams(trparams);
                            tv4.setLayoutParams(trparams);
                            tv5.setLayoutParams(trparams);
                            tv0.setLayoutParams(trparams);
                            tv6.setLayoutParams(trparams);
                            tv0.setTextColor(Color.RED);
                            tv0.setTextSize(15);
                            tv0.setText("Total Due : " + tot);
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
                            TableRow tr8 = new TableRow(DeliveryActivity.this);

                            tr8.setLayoutParams(params);
                            tr8.addView(tv6);

                            layoutINNER.addView(tr);
                            layoutINNER.addView(tr2);

                            layoutINNER.addView(tr3);


                            layoutINNER.addView(tr6);

                            layoutINNER.addView(tr7);
                            layoutINNER.addView(tr8);

                            TableRow tr4 = new TableRow(DeliveryActivity.this);


                            Button paid = new Button(DeliveryActivity.this);
                            paid.setText("Paid And Delivered");
                            paid.setTextColor(Color.BLACK);
                            paid.setGravity(Gravity.CENTER);
                            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                            paid.setLayoutParams(new TableRow.LayoutParams(width, height));
                            //

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
                            TableRow tr11 = new TableRow(DeliveryActivity.this);
                            Button paid2 = new Button(DeliveryActivity.this);
                            paid2.setText("Credited And \n Delivered");
                            paid2.setTextColor(Color.BLACK);
                            paid2.setGravity(Gravity.LEFT);
                           // paid2.setLayoutParams(new TableRow.LayoutParams(width, height));
                          //  paid2.setWidth(5);
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

                            tr11.setLayoutParams(params);
                            tr4.setLayoutParams(params);
                            tr4.addView(paid);
                            tr4.addView(paid2);
                            Button paid3 = new Button(DeliveryActivity.this);
                            paid3.setText("Partially paid and delivered");
                            paid3.setTextColor(Color.BLACK);
                            paid3.setGravity(Gravity.CENTER);
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

                                                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            // what ever you want to do with No option.
                                                            //alert1.dismiss();
                                                        }
                                                    });
                                                    alert.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            //What ever you want to do with the value
                                                            //Editable YouEditTextValue = edittext.getText();
                                                            //OR
                                                            String total = child.child("due").getValue().toString();
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

                            TableRow tr12 = new TableRow(DeliveryActivity.this);
                            Button paid4 = new Button(DeliveryActivity.this);
                            paid4.setText("Discounted and delivered");
                            paid4.setTextColor(Color.BLACK);
                            paid4.setGravity(Gravity.CENTER);
                              paid4.setWidth(10);
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


                                                    alert.setPositiveButton("Pay ", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            //What ever you want to do with the value
                                                            //Editable YouEditTextValue = edittext.getText();
                                                            //OR
                                                            String total = child.child("due").getValue().toString();
                                                            int totalInt = Integer.parseInt(total);
                                                            String partial = edittext.getText().toString();
                                                            int partialInt = Integer.parseInt(partial);
                                                            child.getRef().child("due").setValue(totalInt - partialInt);
                                                            child.getRef().child("discount").setValue(partialInt);
                                                            Toastmsg(DeliveryActivity.this, "Bill Has been Updated");

                                                            // alert1.dismiss();
                                                            // Toastmsg(DeliveryActivity.this,"Bill Has been Updated");


                                                        }
                                                    });

                                                    alert.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
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



                            Button returnB = new Button(DeliveryActivity.this);
                            returnB.setText("Return");
                            returnB.setTextColor(Color.BLACK);
                            returnB.setGravity(Gravity.CENTER);
                            //  paid4.setWidth(5);
                            returnB.setOnClickListener(new View.OnClickListener() {
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


                                                    final Spinner spinnerforcloth = new Spinner(DeliveryActivity.this);

                                                    ArrayList<HashMap<String,String>> listB = (ArrayList<HashMap<String,String>>) child.child("billDetailsArrayList").getValue();

                                                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DeliveryActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);
                                                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    spinnerforcloth.setAdapter(spinnerAdapter);


                                                    spinnerAdapter.add("Select the cloth to be returned ");

                                                    for(int p=0;p<listB.size();p++){


                                                        spinnerAdapter.add(listB.get(p).get("clothType")+"- "+listB.get(p).get("remark"));
                                                    }

                                                    spinnerAdapter.notifyDataSetChanged();



                                                    alert.setMessage("Please enter which Cloth to Return");
                                                    alert.setTitle("Return Cloth ");

                                                    alert.setView(spinnerforcloth);


                                                    alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                            //What ever you want to do with the value
                                                            //Editable YouEditTextValue = edittext.getText();
                                                            //OR
                                                            ArrayList<HashMap<String,String>> listB = (ArrayList<HashMap<String,String>>) child.child("billDetailsArrayList").getValue();
                                                            // int totalInt = Integer.parseInt(total);
                                                            String cloth = spinnerforcloth.getSelectedItem().toString().substring(0,spinnerforcloth.getSelectedItem().toString().indexOf("-"));

                                                         //   HashMap<String,String> b=listB.get(0);

                                                            for(int m=0;m<listB.size();m++) {

                                                                final HashMap<String,String> b=listB.get(m);

                                                                if (b.get("clothType").equals(cloth)) {


                                                                    final Spinner qtycloth=new Spinner(DeliveryActivity.this);

                                                                     AlertDialog.Builder alertnew = new AlertDialog.Builder(DeliveryActivity.this);


                                                                    alertnew.setMessage("Please enter which Qty to Return");
                                                                    alertnew.setTitle("Qty to return ");

                                                                    alertnew.setView(qtycloth);



                                                                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DeliveryActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);
                                                                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



                                                                    spinnerAdapter.add("Select the qty to be returned ");

                                                                    int count=Integer.parseInt(b.get("qty"));


                                                                    for(int i=1;i<=count;i++){

                                                                        spinnerAdapter.add(""+i);
                                                                    }

                                                                    qtycloth.setAdapter(spinnerAdapter);

                                                                    spinnerAdapter.notifyDataSetChanged();





                                                                    alertnew.setPositiveButton("Return", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {



                                                                            String total = child.child("total").getValue().toString();
                                                                            int totalInt = Integer.parseInt(total);
                                                                            int sub=0;
                                                                            if(!qtycloth.getSelectedItem().toString().equals("Select the qty to be returned "))
                                                                             sub = Integer.parseInt(qtycloth.getSelectedItem().toString()) * Integer.parseInt(b.get("price"));
                                                                            child.getRef().child("total").setValue(totalInt - sub);
                                                                            child.getRef().child("due").setValue(totalInt - sub);


                                                                            Toastmsg(DeliveryActivity.this, "Bill Has been Updated");



                                                                            try {
                                                                                SmsManager smsManager = SmsManager.getDefault();

                                                                                String message = "Bill Number: "+user.billNumber;
                                                                                message+="\n Cloth Type : "+b.get("clothType");
                                                                                message+="Is Returned";
                                                                                Calendar today = Calendar.getInstance();
                                                                                today.set(Calendar.HOUR_OF_DAY, 0);

                                                                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                                                                System.out.println(today.getTime());
                                                                                message+="\n"+today.getTime();




                                                                                PendingIntent sentPI = PendingIntent.getBroadcast(DeliveryActivity.this, 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
                                                                                PendingIntent deliveredPI = PendingIntent.getBroadcast(DeliveryActivity.this, 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


                                                                                SmsManager sms = SmsManager.getDefault();
                                                                                ArrayList<String> parts = sms.divideMessage(message);

                                                                                ArrayList<PendingIntent> sendList = new ArrayList<PendingIntent>();
                                                                                sendList.add(sentPI);

                                                                                ArrayList<PendingIntent> deliverList = new ArrayList<PendingIntent>();
                                                                                deliverList.add(deliveredPI);

                                                                                sms.sendMultipartTextMessage("+91" + user.ph, null, parts, sendList, deliverList);
                                                                                //smsManager.sendTextMessage("+91"+phone, null,message, null, null);
                                                                                Toast.makeText(getApplicationContext(), "SMS Sent!",
                                                                                        Toast.LENGTH_LONG).show();
                                                                            } catch (Exception e) {
                                                                                Toast.makeText(getApplicationContext(),
                                                                                        "SMS failed, please try again later!",
                                                                                        Toast.LENGTH_LONG).show();
                                                                                e.printStackTrace();
                                                                            }




                                                                        }
                                                                    });
                                                                    alertnew.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                                        }
                                                                    });


                                                                    final AlertDialog alertDialognew = alertnew.create();





                                                                    alertDialognew.show();









                                                                }
                                                                   // Toastmsg(DeliveryActivity.this, "ClothType not found");




                                                            }



                                                            //int partialInt = Integer.parseInt(partial);
                                                            //child.getRef().child("due").setValue(0);
                                                            //child.getRef().child("discount").setValue(partialInt);

                                                            // alert1.dismiss();
                                                            // Toastmsg(DeliveryActivity.this,"Bill Has been Updated");


                                                        }
                                                    });

                                                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                            tr12.setLayoutParams(params);


                            TableRow tr18 = new TableRow(DeliveryActivity.this);

                            tr18.setLayoutParams(params);
                            tr18.addView(returnB);


                            layoutINNER.addView(tr4);
                            layoutINNER.addView(tr11);
                            layoutINNER.addView(tr12);
                            layoutINNER.addView(tr5);
                            layoutINNER.addView(tr18);

                            View line = new View(DeliveryActivity.this);
                            line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
                            line.setBackgroundColor(Color.rgb(51, 51, 51));
                            layoutINNER.addView(line);

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




}
