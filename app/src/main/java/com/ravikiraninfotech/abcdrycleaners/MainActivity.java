package com.ravikiraninfotech.abcdrycleaners;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ravikiraninfotech.abcdrycleaners.utils.Conts;
import com.ravikiraninfotech.abcdrycleaners.utils.DeviceReceiver;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;
import net.posprinter.utils.PosPrinterDev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity implements View.OnClickListener{

    public static String DISCONNECT="com.posconsend.net.disconnetct";
    String message1;
    String message2;

    //IMyBinder interface，All methods that can be invoked to connect and send data are encapsulated within this interface
    public static IMyBinder binder;

    //bindService connection
    ServiceConnection conn= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //Bind successfully
            binder= (IMyBinder) iBinder;
            Log.e("binder","connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("disbinder","disconnected");
        }
    };

    public static boolean ISCONNECT;
    Button BTCon,//connection button
            BTDisconnect,//disconnect button
            BTpos,
            BT76,
            BTtsc,
            BtposPrinter,
            BtSb;// start posprint button
    Spinner conPort;//spinner connetion port
    EditText showET;// show edittext
    CoordinatorLayout container;

    private View dialogView;
    BluetoothAdapter bluetoothAdapter;

    private ArrayAdapter<String> adapter1
            ,adapter2
            ,adapter3;//usb adapter
    private ListView lv1,lv2,lv_usb;
    private ArrayList<String> deviceList_bonded=new ArrayList<String>();//bonded list
    private ArrayList<String> deviceList_found=new ArrayList<String>();//found list
    private Button btn_scan; //scan button
    private LinearLayout LLlayout;
    AlertDialog dialog;
    String mac;
    int pos ;

    private DeviceReceiver myDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.activity_first);
        //bind service，get ImyBinder object
        Intent intent=new Intent(this,PosprinterService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        Bundle b = getIntent().getExtras();
       message1= (String) b.getCharSequence("MESSAGE1");
        message2= (String) b.getCharSequence("MESSAGE2");

        //init view
        initView();

        //setlistener
        setlistener();
        Toast.makeText(getApplicationContext(), message1 +"/n"+message2,
                Toast.LENGTH_LONG).show();
    }

    private void initView(){

        BTCon= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.buttonConnect);
        BTDisconnect= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.buttonDisconnect);

        BTpos= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.buttonpos);
        BT76= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.button76);
        BTtsc= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.buttonTsc);

        BtposPrinter= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.buttonPosPrinter);

        BtSb= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.buttonSB);
        conPort= (Spinner) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.connectport);
        showET= (EditText) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.showET);
        container= (CoordinatorLayout) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.container);
    }




    private void setlistener(){
        BTCon.setOnClickListener(this);
        BTDisconnect.setOnClickListener(this);

        BTpos.setOnClickListener(this);
        BT76.setOnClickListener(this);
        BTtsc.setOnClickListener(this);

        BtSb.setOnClickListener(this);
        conPort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
                switch (i){
                    case 0:
                        //wifi connect
                        showET.setText("");
                        showET.setEnabled(true);
                        BtSb.setVisibility(View.GONE);
                        showET.setHint(getString(com.ravikiraninfotech.abcdrycleaners.R.string.hint));
                        break;
                    case 1:
                        //bluetooth connect
                        showET.setText("");
                        BtSb.setVisibility(View.VISIBLE);
                        showET.setHint(getString(com.ravikiraninfotech.abcdrycleaners.R.string.bleselect));
                        showET.setEnabled(false);
                        break;
                    case 2:
                        //usb connect
                        showET.setText("");
                        BtSb.setVisibility(View.VISIBLE);
                        showET.setHint(getString(com.ravikiraninfotech.abcdrycleaners.R.string.usbselect));
                        showET.setEnabled(false);
                        break;
                    default:break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        //connect button
        if (id== com.ravikiraninfotech.abcdrycleaners.R.id.buttonConnect){
            switch (pos){
                case 0:
                    // net connection
                    connetNet();
                    break;
                case 1:
                    //bluetooth connection
                    connetBle();
                    break;
                case 2:
                    //USB connection
                    connetUSB();
                    break;
            }
        }
        //device button
        if (id== com.ravikiraninfotech.abcdrycleaners.R.id.buttonSB){
            switch (pos){
                case 0:
                    BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect));
                    break;
                case 1:
                    setBluetooth();
                    BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect));
                    break;
                case 2:
                    setUSB();
                    BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect));
                    break;
            }

        }
        //disconnect
        if (id== com.ravikiraninfotech.abcdrycleaners.R.id.buttonDisconnect){
            if (ISCONNECT){
                binder.disconnectCurrentPort(new UiExecute() {
                    @Override
                    public void onsucess() {
                        showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.toast_discon_success));
                        showET.setText("");
                        BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect));
                    }

                    @Override
                    public void onfailed() {
                        showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.toast_discon_faile));

                    }
                });
            }else {
                showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.toast_present_con));
            }
        }
        //start to pos printer
        if (id== com.ravikiraninfotech.abcdrycleaners.R.id.buttonpos){
            if (ISCONNECT){
                Intent intent=new Intent(this,PosActivity.class);

                Bundle b = new Bundle();

                //Inserts a String value into the mapping of this Bundle
                b.putBoolean("isconnect", ISCONNECT);
                b.putString("MESSAGE1",message1);
                b.putString("MESSAGE2",message2);


                //Add the bundle to the intent.
                intent.putExtras(b);



               // intent.putExtra("isconnect",ISCONNECT);
               // intent.putExtra("MESSAGE",message);
                startActivity(intent);
            }else {
                showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect_first));
            }

        }
        //start to 76 printer
        if (id== com.ravikiraninfotech.abcdrycleaners.R.id.button76){
            if (ISCONNECT){
                Intent intent=new Intent(this,Z76Activity.class);
                intent.putExtra("isconnect",ISCONNECT);
                startActivity(intent);
            }else {
                showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect_first));
            }
        }
        //start to barcode(TSC) printer
        if (id== com.ravikiraninfotech.abcdrycleaners.R.id.buttonTsc){
            if (ISCONNECT){
                Intent intent=new Intent(this,TscActivity.class);
                intent.putExtra("isconnect",ISCONNECT);
                startActivity(intent);
            }else {
                showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.connect_first));
            }
        }


    }




    /*
    net connection
     */
    private void connetNet(){

        String ipAddress=showET.getText().toString();
        if (ipAddress.equals(null)||ipAddress.equals("")){

            showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.none_ipaddress));
        }else {
            //ipAddress :ip address; portal:9100
            binder.connectNetPort(ipAddress,9100, new UiExecute() {
                @Override
                public void onsucess() {

                    ISCONNECT=true;
                    showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_success));
                    //in this ,you could call acceptdatafromprinter(),when disconnect ,will execute onfailed();
                    binder.acceptdatafromprinter(new UiExecute() {
                        @Override
                        public void onsucess() {

                        }

                        @Override
                        public void onfailed() {
                            ISCONNECT=false;
                            showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_failed));
                            Intent intent=new Intent();
                            intent.setAction(DISCONNECT);
                            sendBroadcast(intent);

                        }
                    });
                }

                @Override
                public void onfailed() {
                    //Execution of the connection in the UI thread after the failure of the connection
                    ISCONNECT=false;
                    showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_failed));
                   BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_failed));


                }
            });

        }

    }

    /*
   USB connection
    */
    String usbAdrresss;
    private void connetUSB() {
        usbAdrresss=showET.getText().toString();
        if (usbAdrresss.equals(null)||usbAdrresss.equals("")){
            showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.usbselect));
        }else {
            binder.connectUsbPort(getApplicationContext(), usbAdrresss, new UiExecute() {
                @Override
                public void onsucess() {
                    ISCONNECT=true;
                    showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_success));
                    BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_success));
                    setPortType(PosPrinterDev.PortType.USB);
                }

                @Override
                public void onfailed() {
                    ISCONNECT=false;
                    showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_failed));
                    BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_failed));


                }
            });
        }
    }
    /*
    bluetooth connecttion
     */
    private void connetBle(){
        String bleAdrress=showET.getText().toString();
        if (bleAdrress.equals(null)||bleAdrress.equals("")){
            showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.bleselect));
        }else {
            binder.connectBtPort(bleAdrress, new UiExecute() {
                @Override
                public void onsucess() {
                    ISCONNECT=true;
                    showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_success));
                    BTCon.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_success));

                    binder.write(DataForSendToPrinterPos80.openOrCloseAutoReturnPrintState(0x1f), new UiExecute() {
                        @Override
                        public void onsucess() {
                                binder.acceptdatafromprinter(new UiExecute() {
                                    @Override
                                    public void onsucess() {

                                    }

                                    @Override
                                    public void onfailed() {
                                        ISCONNECT=false;
                                        showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_has_discon));
                                    }
                                });
                        }

                        @Override
                        public void onfailed() {

                        }
                    });


                }

                @Override
                public void onfailed() {

                    ISCONNECT=false;
                    showSnackbar(getString(com.ravikiraninfotech.abcdrycleaners.R.string.con_failed));
                }
            });
        }


    }

    /*
     select bluetooth device
     */

    public void setBluetooth(){
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()){
            //open bluetooth
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, Conts.ENABLE_BLUETOOTH);
        }else {

            showblueboothlist();

        }
    }

    private void showblueboothlist() {
        if (!bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.startDiscovery();
        }
        LayoutInflater inflater= LayoutInflater.from(this);
        dialogView=inflater.inflate(com.ravikiraninfotech.abcdrycleaners.R.layout.printer_list, null);
        adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList_bonded);
        lv1=(ListView) dialogView.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.listView1);
        btn_scan=(Button) dialogView.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.btn_scan);
        LLlayout=(LinearLayout) dialogView.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.ll1);
        lv2=(ListView) dialogView.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.listView2);
        adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList_found);
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        dialog=new AlertDialog.Builder(this).setTitle("BLE").setView(dialogView).create();
        dialog.show();

        myDevice=new DeviceReceiver(deviceList_found,adapter2,lv2);

        //register the receiver
        IntentFilter filterStart=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter filterEnd=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(myDevice, filterStart);
        registerReceiver(myDevice, filterEnd);

        setDlistener();
        findAvalibleDevice();
    }
    private void setDlistener() {
        // TODO Auto-generated method stub
        btn_scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LLlayout.setVisibility(View.VISIBLE);
                //btn_scan.setVisibility(View.GONE);
            }
        });
        //boned device connect
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
                        bluetoothAdapter.cancelDiscovery();

                    }

                    String msg=deviceList_bonded.get(arg2);
                    mac=msg.substring(msg.length()-17);
                    String name=msg.substring(0, msg.length()-18);
                    //lv1.setSelection(arg2);
                    dialog.cancel();
                    showET.setText(mac);
                    //Log.i("TAG", "mac="+mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        //found device and connect device
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
                        bluetoothAdapter.cancelDiscovery();

                    }
                    String msg=deviceList_found.get(arg2);
                    mac=msg.substring(msg.length()-17);
                    String name=msg.substring(0, msg.length()-18);
                    //lv2.setSelection(arg2);
                    dialog.cancel();
                    showET.setText(mac);
                    Log.i("TAG", "mac="+mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    find avaliable device
     */
    private void findAvalibleDevice() {
        // TODO Auto-generated method stub

        Set<BluetoothDevice> device=bluetoothAdapter.getBondedDevices();

        deviceList_bonded.clear();
        if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
            adapter1.notifyDataSetChanged();
        }
        if(device.size()>0){
            //already
            for(Iterator<BluetoothDevice> it = device.iterator(); it.hasNext();){
                BluetoothDevice btd=it.next();
                deviceList_bonded.add(btd.getName()+'\n'+btd.getAddress());
                adapter1.notifyDataSetChanged();
            }
        }else{
            deviceList_bonded.add("No can be matched to use bluetooth");
            adapter1.notifyDataSetChanged();
        }

    }

    View dialogView3;
    private TextView tv_usb;
    private List<String> usbList,usblist;

   /*
   uSB connection
    */
    private void setUSB(){
        LayoutInflater inflater= LayoutInflater.from(this);
        dialogView3=inflater.inflate(com.ravikiraninfotech.abcdrycleaners.R.layout.usb_link,null);
        tv_usb= (TextView) dialogView3.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.textView1);
        lv_usb= (ListView) dialogView3.findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.listView1);


        usbList= PosPrinterDev.GetUsbPathNames(this);
        if (usbList==null){
            usbList=new ArrayList<>();
        }
        usblist=usbList;
        tv_usb.setText(getString(com.ravikiraninfotech.abcdrycleaners.R.string.usb_pre_con)+usbList.size());
        adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usbList);
        lv_usb.setAdapter(adapter3);


        AlertDialog dialog=new AlertDialog.Builder(this)
                .setView(dialogView3).create();
        dialog.show();

        setUsbLisener(dialog);

    }
    String usbDev="";
    public void setUsbLisener(final AlertDialog dialog) {

        lv_usb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usbDev=usbList.get(i);
                showET.setText(usbDev);
                dialog.cancel();
                Log.e("usbDev: ",usbDev);
            }
        });



    }

    /**
     * show the massage
     * @param showstring content
     */
    private void showSnackbar(String showstring){
        Snackbar.make(container, showstring,Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(com.ravikiraninfotech.abcdrycleaners.R.color.button_unable)).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.disconnectCurrentPort(new UiExecute() {
            @Override
            public void onsucess() {

            }

            @Override
            public void onfailed() {

            }
        });
        unbindService(conn);
    }

    public static PosPrinterDev.PortType portType;//connect type
    private void setPortType(PosPrinterDev.PortType portType){
        this.portType=portType;

    }
    public void onBackPressed(){
        Intent i=new Intent(MainActivity.this,SampleActivity.class);
        startActivity(i);

    }

}
