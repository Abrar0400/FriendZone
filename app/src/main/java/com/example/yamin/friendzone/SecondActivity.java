package com.example.yamin.friendzone;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Intent.ACTION_VIEW;

/**
 * Created by abrar on 2/5/2017.
 */
public class SecondActivity extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4,textView5;
    ImageView imageView;
    String nametxt,numbertxt,mailtxt,addresstxt,bloodtxt;
    SQLiteDatabase sqLiteDatabase;
    MenuItem callButton,emailButton,msgButton;
    Vibrator vibrator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        nametxt= (getIntent().getExtras().getString("namekey"));
        setTitle(nametxt);
        sqLiteDatabase=openOrCreateDatabase("MyDataBase",MODE_PRIVATE,null);
        //getting id
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        textView2=(TextView) findViewById(R.id.textView2Number);
        textView3=(TextView) findViewById(R.id.textView2Email);
        textView4=(TextView) findViewById(R.id.textView2Address);
        textView5=(TextView) findViewById(R.id.textView2BloodGroup);
        imageView=(ImageView)findViewById(R.id.imageView);
        callButton=(MenuItem)findViewById(R.id.call) ;
        emailButton=(MenuItem)findViewById(R.id.emailme);
        msgButton=(MenuItem)findViewById(R.id.msg);

        //getting data from another activity
        numbertxt= getIntent().getExtras().getString("numberkey");
        mailtxt= getIntent().getExtras().getString("emailkey");
        addresstxt= getIntent().getExtras().getString("addresskey");
        bloodtxt= getIntent().getExtras().getString("bloodkey");
        final int db_id1 =getIntent().getExtras().getInt("db_id");
       // textView1.setText(nametxt);
        textView2.setText(numbertxt);
        textView3.setText(mailtxt);
        textView4.setText(addresstxt);
        textView5.setText(bloodtxt);

        //for call
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numbertxt));
                if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(intent);
                        vibrator.vibrate(45);
                        Toast.makeText(SecondActivity.this, "Opening dialer", Toast.LENGTH_SHORT).show();
                        //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        ex.printStackTrace();
                        Toast.makeText(SecondActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //for message
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.INTERNET},   //request specific permission from user
                            10);
                    return;
                }
                else {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.setType("text/plain");
                    final PackageManager pm = getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                            best = info;
                            break;
                        }
                    }
                    if (best != null) {
                        intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    }
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{mailtxt});
                    startActivity(intent);

                }

            }
        });
        //image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                final View view1 = LayoutInflater.from(SecondActivity.this).inflate(R.layout.user_photo,null);
                builder.setView(view1);
                builder.setPositiveButton("Set Image",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    Toast.makeText(SecondActivity.this,"Under Development",Toast.LENGTH_SHORT).show();

                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SecondActivity.this,"Canceled",Toast.LENGTH_SHORT).show();

                    }
                });

                builder.create().show();


            }
        });
    }

    //calling
        public void setcalling(){

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numbertxt));
                if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(intent);
                        vibrator.vibrate(45);
                        Toast.makeText(SecondActivity.this, "Opening Dialer", Toast.LENGTH_SHORT).show();
                        //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        ex.printStackTrace();
                        Toast.makeText(SecondActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        //for message
    public void setmailing(){
                if (ActivityCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.INTERNET},   //request specific permission from user
                            10);
                    return;
                }
                else {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.setType("text/plain");
                    final PackageManager pm = getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                            best = info;
                            break;
                        }
                    }
                    if (best != null) {
                        intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    }
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{mailtxt});
                    startActivity(intent);

                }
    }

        //for message
 public void setmsging(){
                try{
                    Intent sendIntent = new Intent(ACTION_VIEW);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    sendIntent.setData(Uri.parse("sms:" + numbertxt));
                    sendIntent.putExtra("sms_body",  "");
                    startActivity(sendIntent);
                    vibrator.vibrate(45);
                    Toast.makeText(SecondActivity.this, "Opening Message", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(SecondActivity.this, "Failed!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
           }

    //for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    //for menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.call:
                setcalling();
                return  true;

            case R.id.msg:
                setmsging();
                return  true;

            case R.id.emailme:
                setmailing();
                return  true;
        }

        return super.onOptionsItemSelected(item);
    }


}
