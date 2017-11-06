package com.example.cvoya.scariconumeri;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.io.BufferedReader;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {



    private Button buttonRemove;
    private Button buttonAvvia;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(getApplicationContext(), LetturaContatti.class);
        startService();

        Log.d("lifecycle", "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);








        buttonRemove = (Button) findViewById(R.id.buttonRemove);
        buttonAvvia = (Button) findViewById(R.id.buttonAvvia);

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                    contentResolver.delete(uri, null, null);
                }
            }
        });

        buttonAvvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), LetturaContatti.class));
            }
        });

    }private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    /**
     * Show the contacts in the ListView.
     */
    private void startService() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            startService(intent);
        } else {

            startService(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                startService();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onStart() {
        Log.d("lifecycle", "onStart");

        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("lifecycle", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("lifecycle", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("lifecycle", "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("lifecycle", "onDestroy");
        //stopService(intent);
        super.onDestroy();
    }

}

