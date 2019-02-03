package com.example.yamin.friendzone;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.toUpperCase;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Contact> everything = new ArrayList<>();

    SQLiteDatabase sqLiteDatabase;
    FloatingActionButton addButton;
    Vibrator vibrator;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sqLiteDatabase = openOrCreateDatabase("MyDataBase", MODE_PRIVATE, null);
        createTable();
        //searchView= (SearchView) findViewById(R.id.searchView);
        final CustomAdaper data = new CustomAdaper(this, everything);
        listView.setAdapter(data);
        getDataFromdb();
    /*//search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                data.getFilter().filter(newText);
                return false;
            }
        });*/

        //To enter ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Contact contact = everything.get(i);
                String name1 = contact.getName();
                String number1 = contact.getNumber();
                String email1 = contact.getEmail();
                String address1 = contact.getAddress();
                String bloodGroup1 = contact.getBloodGroup();
                int id = contact.getId();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("namekey", name1);
                intent.putExtra("numberkey", number1);
                intent.putExtra("emailkey", email1);
                intent.putExtra("addresskey", address1);
                intent.putExtra("bloodkey", bloodGroup1);
                intent.putExtra("db_id", id);
                vibrator.vibrate(45);
                startActivity(intent);
            }
        });

        //to Delete or edit
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Contact contact = everything.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.activit, null);
                builder.setView(view1);
                TextView textView = (TextView) view1.findViewById(R.id.textViewDeleteOrEdit);
                textView.setText(contact.getName());
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vibrator.vibrate(45);

                        int deletedId = contact.getId();
                        String deleteQuery = "Delete from my_table where id=" + deletedId;
                        sqLiteDatabase.execSQL(deleteQuery);
                        data.notifyDataSetChanged();
                        getDataFromdb();
                        Toast.makeText(MainActivity.this, "data Deleted", Toast.LENGTH_SHORT).show();

                    }

                });

                //update
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vibrator.vibrate(45);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.acivity_edit, null);
                        builder.setView(view1);
                        TextView textView = (TextView) view1.findViewById(R.id.textView2DeleteOrEdit);


                        final EditText editText1 = (EditText) view1.findViewById(R.id.editText2Name);
                        final EditText editText2 = (EditText) view1.findViewById(R.id.editText2Number);
                        final EditText editText3 = (EditText) view1.findViewById(R.id.editText2Email);
                        final EditText editText4 = (EditText) view1.findViewById(R.id.editText2Address);
                        final EditText editText5 = (EditText) view1.findViewById(R.id.editText2BloodGroup);
                        editText1.setText(contact.getName());
                        editText2.setText(contact.getNumber());
                        editText3.setText(contact.getEmail());
                        editText4.setText(contact.getAddress());
                        editText5.setText(contact.getBloodGroup());
                        textView.setText("Edit Contact");
                        builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                vibrator.vibrate(45);

                                //Convert to String

                                String name3 = editText1.getText().toString();
                                String number3 = editText2.getText().toString();
                                String email3 = editText3.getText().toString();
                                String address3 = editText4.getText().toString();
                                String bloodGroup3 = editText5.getText().toString();
                                ContentValues cv = new ContentValues();
                                if (!number3.isEmpty() && !email3.isEmpty()) {
                                    cv.put("name", name3);
                                    cv.put("number", number3);
                                    cv.put("email", email3);
                                    cv.put("address", address3);
                                    cv.put("bloodGroup", bloodGroup3);
                                    sqLiteDatabase.update("my_table", cv, "id" + " = ?", new String[]{String.valueOf(contact.getId())});
                                    data.notifyDataSetChanged();
                                    getDataFromdb();
                                    Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    getDataFromdb();
                                    Toast.makeText(MainActivity.this, "number or email can't be empty", Toast.LENGTH_SHORT).show();

                                }


                            }


                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                vibrator.vibrate(45);
                                Toast.makeText(MainActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.create().show();

                    }
                });

                builder.create().show();
                return true;
            }
        });
        //to insert
        addButton = (FloatingActionButton) findViewById(R.id.addContact);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(45);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_dialouge, null);
                builder.setView(view);
                builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vibrator.vibrate(45);
                        //getting id
                        EditText name2 = (EditText) view.findViewById(R.id.editTextName);
                        EditText number2 = (EditText) view.findViewById(R.id.editTextNumber);
                        EditText email2 = (EditText) view.findViewById(R.id.editTextEmail);
                        EditText address2 = (EditText) view.findViewById(R.id.editTextAddress);
                        EditText bloodGroup2 = (EditText) view.findViewById(R.id.editTextBloodGroup);
                        //Convert to String
                        String name3 = name2.getText().toString();
                        if (!name3.isEmpty()) {
                            name3 = name3.replace(name3.charAt(0), toUpperCase(name3.charAt(0)));
                        }
                        String number3 = number2.getText().toString();
                        String email3 = email2.getText().toString();
                        String address3 = address2.getText().toString();
                        String bloodGroup3 = bloodGroup2.getText().toString();
                        ContentValues cv = new ContentValues();
                        if (!number3.isEmpty() && !name3.isEmpty() && !email3.isEmpty()) {
                            cv.put("name", name3);
                            cv.put("number", number3);
                            cv.put("email", email3);
                            cv.put("address", address3);
                            cv.put("bloodGroup", bloodGroup3);
                            sqLiteDatabase.insert("my_table", null, cv);
                            data.notifyDataSetChanged();
                            getDataFromdb();
                            Toast.makeText(MainActivity.this, "added", Toast.LENGTH_SHORT).show();
                        }
                    else

                    {
                        getDataFromdb();
                        Toast.makeText(MainActivity.this, "name,number or email can't be empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()

            {
                @Override
                public void onClick (DialogInterface dialogInterface,int i){
                vibrator.vibrate(45);
                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
            });

                builder.create().show();

        }
    });


}


    //creating table

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS my_table " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,number VARCHAR,email VARCHAR,address VARCHAR,bloodGroup VARCHAR)";

        sqLiteDatabase.execSQL(query);

    }

    //for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //for menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.exit:
                vibrator.vibrate(50);
                finish();
                System.exit(0);
                return true;

            case R.id.rate:
                setRating();
                return true;

            case R.id.feedback:
                setFeedBack();
                return true;

            case R.id.about:
                setAboutDeveloper();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void setFeedBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.feedback, null);
        builder.setView(view1);

        builder.setPositiveButton("Feedback", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abrar.abeer0400@outlook.com"});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Friend Zone Application FeedBack");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

                startActivity(intent);

            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    private void setAboutDeveloper() {
        Intent intent = new Intent(this, developer.class);
        this.startActivity(intent);
    }


    private void setRating() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.rating, null);
        builder.setView(view1);
        final RatingBar ratingBar = (RatingBar) view1.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abrar.abeer0400@outlook.com"});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Friend Zone Application Rating");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "Hii, " +  ratingBar.getRating() + " star for your app. :)");
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }
    //getting data
    public void getDataFromdb() {
        everything.clear();
        String query = "SELECT * FROM my_table order by name;";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Contact contact;
            do {
                contact = new Contact();
                contact.setName(cursor.getString(cursor.getColumnIndex("name")));
                contact.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                contact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                contact.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                contact.setBloodGroup(cursor.getString(cursor.getColumnIndex("bloodGroup")));
                contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                everything.add(contact);
            } while (cursor.moveToNext());
        }
    }
}