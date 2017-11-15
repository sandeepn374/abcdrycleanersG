package com.ravikiraninfotech.abcdrycleaners;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ravikiraninfotech.abcdrycleaners.utils.StringUtils;

import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterPos76;
import net.posprinter.utils.DataForSendToPrinterTSC;

import java.util.ArrayList;
import java.util.List;

public class Z76Activity extends AppCompatActivity {

    Button bttext,btimage , btCls;
    CoordinatorLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ravikiraninfotech.abcdrycleaners.R.layout.activity_z76);
        initView();

    }
    // init the view
    private void initView(){
        Log.e("bttext","2");
        bttext= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.bttext);
        btimage= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.btpic);
        btCls= (Button) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.cls);
        container= (CoordinatorLayout) findViewById(com.ravikiraninfotech.abcdrycleaners.R.id.activity_z76);

        bttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               showSnackbar("test11");
                printText();
            }
        });

        btimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });
        btCls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cls();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("test",requestCode+"  "+resultCode);
        if (requestCode==0&&resultCode==RESULT_OK){
            // deal with the bitmap object
            try{
                Uri imagepath=data.getData();
                ContentResolver resolver = getContentResolver();
                Bitmap b= MediaStore.Images.Media.getBitmap(resolver,imagepath);
                prinPic(b);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    //print text
    private void printText() {


        MainActivity.binder.writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                showSnackbar("ok");
            }

            @Override
            public void onfailed() {
//                showSnackbar("failed");

            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {

                Log.e("bttext","bttext1");
                List<byte[]> list=new ArrayList<byte[]>();
                //creat a text and make it to byte[],add to the list
                String str = "Welcome to use the impact and thermal printer manufactured by professional POS receipt printer company!";
                byte[] data= StringUtils.strTobytes(str);
                list.add(DataForSendToPrinterPos76.initializePrinter());
                list.add(data);
                //should add the command of print and feed line,because print only when one line is complete, not one line, no print
                list.add(DataForSendToPrinterPos76.printAndFeedLine());

                return list;
            }
        });

    }


    /*
    print picture
     */
    private void prinPic(final Bitmap biimap) {
        MainActivity.binder.writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                showSnackbar("ok");

            }

            @Override
            public void onfailed() {

            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                List<byte[]> list=new ArrayList<byte[]>();
                // initialise
                list.add(DataForSendToPrinterPos76.initializePrinter());
                list.add(DataForSendToPrinterPos76.selectBmpModel(0,biimap, BitmapToByteData.BmpType.Dithering));
                return list;
            }
        });

    }
    /*
    Clear Cache
     */
    private void cls(){
        MainActivity.binder.writeDataByYouself(new UiExecute() {
            @Override
            public void onsucess() {
                showSnackbar("cls ok");

            }

            @Override
            public void onfailed() {
                showSnackbar("cls failed");

            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
                List<byte[]> list=new ArrayList<byte[]>();
                list.add(DataForSendToPrinterTSC.initialPrinter());
                list.add(DataForSendToPrinterTSC.cls());
                return list;
            }
        });
    }

    /**
     * display the  message
     * @param showstring show content
     */
    private void showSnackbar(String showstring){
        Snackbar.make(container, showstring,Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(com.ravikiraninfotech.abcdrycleaners.R.color.button_unable)).show();
    }

}
