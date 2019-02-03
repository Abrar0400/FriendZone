package com.example.yamin.friendzone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import static com.example.yamin.friendzone.R.drawable.abeer;

/**
 * Created by Abrar on 2/18/2017.
 */

public class developer extends AppCompatActivity {
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);
        setTitle("Developers");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //ImageView abeer = (ImageView) findViewById(R.id.abeerpic);
        //ImageVieew yamin = (ImageView) findViewById(R.id.yaminPic);
        ImageView mishu = (ImageView) findViewById(R.id.mishu);

       /* abeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl;
                PackageManager packageManager = getPackageManager();

                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

                    boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
                    if (activated) {
                        if ((versionCode >= 3002850)) {
                            facebookUrl = "fb://facewebmodal/f?href=" + "https://www.facebook.com/abrar0400/";
                        } else {
                            facebookUrl = "fb://page/" + "abrar0400";
                        }
                    } else {
                        facebookUrl = "https://www.facebook.com/abrar0400/";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    facebookUrl = "https://www.facebook.com/abrar0400/"; //normal web url
                }
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);

            }


        });
        yamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl;
                PackageManager packageManager = getPackageManager();

                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

                    boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
                    if (activated) {
                        if ((versionCode >= 3002850)) {
                            facebookUrl = "fb://facewebmodal/f?href=" + "https://www.facebook.com/yamin.sobhan/";
                        } else {
                            facebookUrl = "fb://page/" + "yamin.sobhan";
                        }
                    } else {
                        facebookUrl = "https://www.facebook.com/yamin.sobhan/";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    facebookUrl = "https://www.facebook.com/yamin.sobhan/"; //normal web url
                }
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });
    }*/

        mishu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl;
                PackageManager packageManager = getPackageManager();

                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

                    boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
                    if (activated) {
                        if ((versionCode >= 3002850)) {
                            facebookUrl = "fb://facewebmodal/f?href=https://www.facebook.com/mishuTheScareCrow/";
                        } else {
                            facebookUrl = "fb://page/mishuTheScareCrow";
                        }
                    } else {
                        facebookUrl = "https://www.facebook.com/mishuTheScareCrow";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    facebookUrl = "https://www.facebook.com/mishuTheScareCrow"; //normal web url
                }
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });
    }
        //for menu
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_developer, menu);
            return true;
        }

        //for menu
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;

            }

            return super.onOptionsItemSelected(item);
        }

}