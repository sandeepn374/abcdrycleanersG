package com.ravikiraninfotech.abcdrycleaners;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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


public class CollectionActivity extends Activity
{


	EditText edt_name;
	EditText edt_phone;
	TextView edt_email;
	Spinner spinner,spinner1,spinner2,spinner3;
	Button btn_add,btn_submit;
	String message;
	String message1="";
	String message2="";
	String name,phone,billNumber;

	String time;
	final String[] text = {""};

	final String[] text1 = {""};

	final String[] text2 = {""};

	final String[] text3 = {""};

	int total=0;

	ArrayList<BillDetails> billDetailsArrayList= new ArrayList<BillDetails>();

	static {
		//FirebaseDatabase.getInstance().setPersistenceEnabled(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd : HH:mm");// HH:mm:ss");
		String reg_date = df.format(c.getTime());
		//showtoast("Currrent Date Time : "+reg_date);

		c.add(Calendar.DATE, 4);  // number of days to add
		time = df.format(c.getTime());
		super.onCreate(savedInstanceState);
		setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.collection);
		edt_name= (EditText) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.edt_name);

		edt_phone= (EditText) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.edt_phone);

		edt_email= (TextView) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.edt_email);

		btn_add = (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.btn_add);

		btn_submit = (Button)findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.btn_submit);

		spinner = (Spinner) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.spinner);

		spinner1 = (Spinner) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.spinner1);

		spinner2 = (Spinner) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.spinner2);

		spinner3 = (Spinner) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.spinner3);



		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// your code here

				if (spinner.getSelectedItem().toString().equals("Others")){

					final AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);


					final EditText edittext = new EditText(CollectionActivity.this);
					alert.setMessage("Please enter the cloth type");
					alert.setTitle("Cloth Type");

					alert.setView(edittext);


					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							//What ever you want to do with the value
							//Editable YouEditTextValue = edittext.getText();
							//OR

							text[0] =edittext.getText().toString();





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





				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});



		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// your code here

				if (spinner1.getSelectedItem().toString().equals("Others")){

					final AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);


					final EditText edittext = new EditText(CollectionActivity.this);
					alert.setMessage("Please enter the price");
					alert.setTitle("Price");

					alert.setView(edittext);


					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							//What ever you want to do with the value
							//Editable YouEditTextValue = edittext.getText();
							//OR

							text1[0] =edittext.getText().toString();





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





				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});




		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// your code here

				if (spinner3.getSelectedItem().toString().equals("Others")){

					final AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);


					final EditText edittext = new EditText(CollectionActivity.this);
					alert.setMessage("Please enter the Quantity");
					alert.setTitle("Quantity");

					alert.setView(edittext);


					alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							//What ever you want to do with the value
							//Editable YouEditTextValue = edittext.getText();
							//OR

							text3[0] =edittext.getText().toString();





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





				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
		edt_phone.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

				// you can call or do what you want with your EditText here
				if (s.length() == 10) {
					FirebaseDatabase.getInstance().getReference().child("usersG")
							.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot dataSnapshot) {
									for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
										User user = snapshot.getValue(User.class);

										System.out.println("db" + user.ph);
										System.out.println("et" + edt_phone.getText());
										if (user.ph.equals(edt_phone.getText())) {
											name=user.name;
											edt_name.setText(user.name);
										}
										//System.out.println(user.email);
									}
								}

								@Override
								public void onCancelled(DatabaseError databaseError) {
								}
							});
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(final CharSequence s, int start, int before, int count) {

if (s.length()==10){


	FirebaseDatabase.getInstance().getReference().child("usersG")
			.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
						User user = snapshot.getValue(User.class);
						System.out.println("db"+user.ph);
						System.out.println("et"+edt_phone.getText());
						String db=user.ph;
						String et=s.toString();
						if(db.equals(et)){
							edt_name.setText(user.name);
						}
						//System.out.println(user.email);
					}
				}
				@Override
				public void onCancelled(DatabaseError databaseError) {
				}
			});
}


			}
		});




		DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference().child("usersG");
		mDatabase.keepSynced(true);

		Query query = mDatabase.orderByKey().limitToLast(1);
		query.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				//String billNumber=null;
				for (DataSnapshot child: dataSnapshot.getChildren()) {
					Log.d("User key", child.getKey());
					Log.d("User val", child.child("billNumber").getValue().toString());
					billNumber=child.child("billNumber").getValue().toString();

				}



				if(billNumber!=null) {
					String number = billNumber.substring(1);
					int foo = Integer.parseInt(number);
					foo++;
					if (foo < 9) {
						String last = String.valueOf(foo);
						billNumber = "G000" + last;
						edt_email.setText(billNumber);
					} else if (foo < 99) {
						String last = String.valueOf(foo);
						billNumber = "G00" + last;

						edt_email.setText(billNumber);
					}
				}
				else{

					billNumber="G0001";
					edt_email.setText(billNumber);
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});;






		btn_add.setOnClickListener(new OnClickListener(){

			public void onClick(View view){

				//String temp=null;
				 name=edt_name.getText().toString();
					//temp = edt_name.getText().toString();
//validation
				if(name.length() == 0) {
					Toastmsg(CollectionActivity.this, "All fields are required");

				}
				else if(name.length() != 0){
					// awesomeValidation.addValidation(this, R.id.edt_event_remarks, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
					Log.v("EditText411", edt_name.getText().toString());
				}

				 billNumber=edt_email.getText().toString();

				 phone=edt_phone.getText().toString();

//validation
				if(phone.length() == 0) {
					Toastmsg(CollectionActivity.this, "All fields are required");

				}
				else if(phone.length() != 0){
					// awesomeValidation.addValidation(this, R.id.edt_event_remarks, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
					Log.v("EditText425", edt_phone.getText().toString());
				}

				if (spinner.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}
					else {
					text[0] = spinner.getSelectedItem().toString();
				}

				//validation
				if (spinner.getSelectedItem().toString().trim().equals("Select Cloth Type")) {
					Toastmsg(CollectionActivity.this, "Please Select Cloth Type");
				}
				else{
					Log.v("Spinner440",spinner.toString());
				}



				if (spinner1.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}else {
					text1[0] = spinner1.getSelectedItem().toString();
				}
				//String text2 = spinner2.getSelectedItem().toString();
//validation
				if (spinner1.getSelectedItem().toString().trim().equals("Select Price")) {
					Toastmsg(CollectionActivity.this, "Please Select Price");
				}
				else{
					Log.v("Spinner456",spinner1.toString());
				}

				if (spinner3.getSelectedItem().toString().equals("Others")){
					System.out.print("cool");
				}else {
					text3[0] = spinner3.getSelectedItem().toString();
				}
				//validation
				if (spinner3.getSelectedItem().toString().trim().equals("Select Quantity")) {
					Toastmsg(CollectionActivity.this, "Please Select Quantity");
				}
				else{
					Log.v("Spinner469",spinner3.toString());
				}

				text2[0]=spinner2.getSelectedItem().toString();
				//if(text[0].equals("Others")){
//validation
				if (spinner2.getSelectedItem().toString().trim().equals("Select Remark")) {
					Toastmsg(CollectionActivity.this, "Please Select Remark");
				}
				else{
					Log.v("Spinner479",spinner2.toString());
				}
				billdetails();


				//}

               /* BillDetails b=new BillDetails(text[0],text1[0],text2[0],text3[0]);
				String qty=text3[0];
				String price=text1[0];
				total+= Integer.parseInt(text1[0])* Integer.parseInt(text3[0]);
				billDetailsArrayList.add(b);
				spinner.setSelection(0);
				spinner1.setSelection(0);
				spinner2.setSelection(0);
				spinner3.setSelection(0);
				Toastmsg(CollectionActivity.this,"Added");*/



			}



		});



		btn_submit.setOnClickListener(new OnClickListener() {
	public void onClick(View view) {




		final Dialog dialog = new Dialog(CollectionActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.layout);

		TableLayout stk = (TableLayout) dialog.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.table_main);
		TableRow tbrow99 = new TableRow(CollectionActivity.this);
		TextView tv99 = new TextView(CollectionActivity.this);
		tv99.setText("Bill Number - "+billNumber);
		tv99.setGravity(Gravity.CENTER);
		tbrow99.addView(tv99);
		stk.addView(tbrow99);

		TableRow tbrow0 = new TableRow(CollectionActivity.this);
		TextView tv0 = new TextView(CollectionActivity.this);
		tv0.setText(" Cloth Type ");
		tv0.setTextColor(Color.BLACK);
		tv0.setGravity(Gravity.CENTER);
		tbrow0.addView(tv0);
		TextView tv1 = new TextView(CollectionActivity.this);
		tv1.setText(" Remark ");
		tv1.setTextColor(Color.BLACK);
		tv1.setGravity(Gravity.CENTER);
		tbrow0.addView(tv1);
		TextView tv2 = new TextView(CollectionActivity.this);
		tv2.setText(" Quantity ");
		tv2.setTextColor(Color.BLACK);
		tv2.setGravity(Gravity.CENTER);
		tbrow0.addView(tv2);
		TextView tv3 = new TextView(CollectionActivity.this);
		tv3.setText(" Price ");
		tv3.setTextColor(Color.BLACK);
		tv3.setGravity(Gravity.CENTER);
		tbrow0.addView(tv3);
		stk.addView(tbrow0);
		for (int i = 0; i < billDetailsArrayList.size(); i++) {
			TableRow tbrow = new TableRow(CollectionActivity.this);
			TextView t1v = new TextView(CollectionActivity.this);
			t1v.setText(billDetailsArrayList.get(i).getClothType());
			t1v.setTextColor(Color.BLACK);
			t1v.setGravity(Gravity.CENTER);
			tbrow.addView(t1v);

			TextView t2v = new TextView(CollectionActivity.this);
			t2v.setText(billDetailsArrayList.get(i).getRemark());
			t2v.setTextColor(Color.BLACK);
			t2v.setGravity(Gravity.CENTER);
			tbrow.addView(t2v);
			TextView t3v = new TextView(CollectionActivity.this);
			t3v.setText(billDetailsArrayList.get(i).getQty());
			t3v.setTextColor(Color.BLACK);
			t3v.setGravity(Gravity.CENTER);
			tbrow.addView(t3v);
			TextView t4v = new TextView(CollectionActivity.this);
			t4v.setText(billDetailsArrayList.get(i).getPrice());
			t4v.setTextColor(Color.BLACK);
			t4v.setGravity(Gravity.CENTER);
			tbrow.addView(t4v);
			stk.addView(tbrow);
		}


		TableRow tr81=new TableRow(CollectionActivity.this);
		TextView tv81=new TextView(CollectionActivity.this);
		tv81.setText("Total Bill - "+ total);
		tv81.setTextColor(Color.BLACK);
		tv81.setGravity(Gravity.CENTER);
		tr81.addView(tv81);
		stk.addView(tr81);

		/*TableRow tr90=new TableRow(CollectionActivity.this);
		TextView tv90=new TextView(CollectionActivity.this);
		tv90.setText("Delivery Date - "+ time);
		tv90.setTextColor(Color.BLACK);
		tv90.setGravity(Gravity.LEFT);
		tr90.addView(tv90);
		stk.addView(tr90);
		*/



		Button confirm = (Button) dialog.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.btn_dialog);
		confirm.setText("Confirm");
		confirm.setTextColor(Color.BLACK);





		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {


				DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference("usersG");
				mDatabase.keepSynced(true);
				String userId = mDatabase.push().getKey();

				User user = new User(name, phone,billDetailsArrayList,billNumber,total,total,0);

				mDatabase.child(userId).setValue(user);
				dialog.dismiss();
				//Toastmsg(CollectionActivity.this,"Your Order has been placed Successfully");
				int qtyTotal=0;
				try {
					SmsManager smsManager = SmsManager.getDefault();

					message= "Your order has been done successfully";
					for(int i=0;i<billDetailsArrayList.size();i++){

						String qty=billDetailsArrayList.get(i).getQty();
						qtyTotal+=Integer.parseInt(qty);

					}
					message1+="ABC Drycleaners \nThanks for your order \n"+"Bill no:"+billNumber+" Quantity: "+qtyTotal+"\nTotal Price :Rs "+total+"\nDelivery date: "+time+"\nFor terms and conditions please refer abcdrycleaners.com";
					message2+="\n\nCustomer Name: "+name+"\nBill no:"+billNumber+" Quantity: "+qtyTotal;
					message+="Bill no:"+billNumber+"Quantity: "+qtyTotal+"Total Price :Rs "+total+"Delivery date:      "+time+"For terms and conditions please refer abcdrycleaners.com";
					PendingIntent sentPI = PendingIntent.getBroadcast(CollectionActivity.this, 0, new Intent("SENT_SMS_ACTION_NAME"), 0);
					PendingIntent deliveredPI = PendingIntent.getBroadcast(CollectionActivity.this, 0, new Intent("DELIVERED_SMS_ACTION_NAME"), 0);


					SmsManager sms = SmsManager.getDefault();
					ArrayList<String> parts = sms.divideMessage(message);

					ArrayList<PendingIntent> sendList = new ArrayList<PendingIntent>();
					sendList.add(sentPI);

					ArrayList<PendingIntent> deliverList = new ArrayList<PendingIntent>();
					deliverList.add(deliveredPI);

					sms.sendMultipartTextMessage("+91"+phone, null, parts, sendList, deliverList);
					//smsManager.sendTextMessage("+91"+phone, null,message, null, null);
					Toast.makeText(getApplicationContext(), "SMS Sent!",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"SMS failed, please try again later!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
				edt_name.setText("");
				edt_phone.setText("");
				AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);

				builder.setTitle("Confirm");
				builder.setMessage("DO you want to Print the Bill ?");

				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do nothing but close the dialog

						//dialog.dismiss();
						Toast.makeText(getApplicationContext(),
								message,
								Toast.LENGTH_LONG).show();
						Intent i=new Intent(CollectionActivity.this,MainActivity.class);
						Bundle b = new Bundle();

						//Inserts a String value into the mapping of this Bundle
						b.putString("MESSAGE1", message1);
						b.putString("MESSAGE2", message2);
						i.putExtras(b);
						startActivity(i);
					}
				});

				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// Do nothing
						dialog.dismiss();

						Intent i=new Intent(CollectionActivity.this,SampleActivity.class);


						startActivity(i);
					}
				});

				AlertDialog alert = builder.create();
				alert.show();

				//new RetrieveFeedTask().execute("subbu");
				//sendLongSMS("hi hi");


			}
		});

		Button cancel = (Button) dialog.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.btn_dialog3);
		cancel.setText("Cancel");
		cancel.setTextColor(Color.BLACK);

		cancel.setOnClickListener(new OnClickListener(){
			public void onClick(View view ){

				dialog.dismiss();
			}
		});


		dialog.show();

	}

});

	}

	private void billdetails() {

		BillDetails b=new BillDetails(text[0],text1[0],text2[0],text3[0]);
		String qty=text3[0];
		String price=text1[0];
		total+= Integer.parseInt(text1[0])* Integer.parseInt(text3[0]);
		billDetailsArrayList.add(b);
		spinner.setSelection(0);
		spinner1.setSelection(0);
		spinner2.setSelection(0);
		spinner3.setSelection(0);
		Toastmsg(CollectionActivity.this,"Added");
	}


	private void Toastmsg(CollectionActivity collectionActivity, String p1)
	{
		
		Toast.makeText(this,p1,
					   Toast.LENGTH_SHORT).show();
	}


	
	}





	
