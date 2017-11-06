package com.example.cvoya.scariconumeri;

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class LetturaContatti extends Service {

    public ArrayList<String> arrayNumeri = new ArrayList<String>();


    public LetturaContatti() {
        Log.d("lifecycle", "servizio LetturaContatti() creato");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean controlloSeContinuare()
    {

        try {
            FileInputStream fileInputStream = new FileInputStream("/data/data/com.example.cvoya.scariconumeri/files/f.txt" );

            if ( fileInputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";


                receiveString = bufferedReader.readLine();
                fileInputStream.close();
                Log.e("Leggo da f->",receiveString);
                if (receiveString.contains("fine"))
                {
                    Log.e("torno" ,"true");
                    return true;

                }else {
                    Log.e("torno" ,"false");
                    return false;

                }


            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return false;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            return false;
        }


        return false;
    }

    public void scrivoSuFileNonFinito() throws IOException {
       // PrintWriter writer = new PrintWriter("/data/data/com.example.cvoya.scariconumeri/files/f.txt" , "UTF-8");
       // writer.println("AAAA");

        String path = "/data/data/com.example.cvoya.scariconumeri/files/";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("f.txt", Context.MODE_PRIVATE);
            outputStream.write("MADONNA".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        try {
            scrivoSuFileNonFinito();
        } catch (IOException e) {
            e.printStackTrace();
        }


/*


        //INIZIO LA CANCELLAZIONE

        try {
            cancellaRubbrica();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String path = "348.txt";

        String line = null;

        int count = 0;

        try {
            FileInputStream fileInputStream = new FileInputStream("/data/data/com.example.cvoya.scariconumeri/files/" +path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                count += 1;
                Log.e("LEGGO->",line);
                aggiorna(line);
                if (count == 100)
                {
                    // ATTENDO CHE IL IL FILE CONDIVISO VENGA SETTATO A FINITO,
                    // quando viene settato a finito allora rinizio il ciclo
                    while (!controlloSeContinuare())
                    {
                        try {
                            Thread.sleep(1234);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("Continuo a controllare","sono fermo");

                    }

                    Log.e("Finito il controllo","PARTOOO");
                    // cosi almeno il programma java sta in attesa
                    scrivoSuFileNonFinito();
                    verificaWhatsapp();
                    Log.e("Finito di verificare whatt","rinizio il ciclo");


                    break;


                }

                //stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d("ERRORE", ex.getMessage());
        }
        catch(IOException ex) {
            Log.d("ERRORE", ex.getMessage());
        }




       // try {
           // cancellaRubbrica();
       // } catch (InterruptedException e) {
        //    e.printStackTrace();
       // }
        /*
        for (int j = 0; j < 1; j++) {
            Log.d("zzz", "numFileDaleggere");
            Ion.with(getApplicationContext())
                    .load(URL_NUMERI + "numeri_" + j + "0.txt")
                    .asInputStream()
                    .setCallback(new FutureCallback<InputStream>() {
                        @Override
                        public void onCompleted(Exception e, InputStream result) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(result));
                            try {
                                String s;
                                String nomeFile = reader.readLine();
                                Log.d("lettura_file_web", "ho letto dal web il file: " + nomeFile);
                                while ((s = reader.readLine()) != null) {
                                    aggiorna(s);
                                }
                                reader.close();
                                result.close();
                                Log.d("scatto", "fine lettura file e rubbrica riempita");
                                // ho letto un file e ho riempito la rubbrica
                                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                                startActivity(launchIntent);

                                // devo aggiornare whatsapp
                                // e successivamente verificare i numeri che sono dei contatti whatsapp


                                try {
                                    Log.d("test_sleep", "prima");
                                    Thread.sleep(20000); // tempo per aggiornare whatsapp
                                    Log.d("test_sleep", "dopo");
                                    verificaWhatsapp(nomeFile);
                                    Log.d("scatto", "fine verifica whats");
                                    Thread.sleep(8000);
                                    cancellaRubbrica();
                                    Thread.sleep(8000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }


        Log.d("stop_ser", "fine servizio");*/

        //stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }


    public void aggiorna(String MobileNumber) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Mobile Number

        if (MobileNumber != null) {
            Log.d("sincro_rubbrica", MobileNumber);
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        // Asking the Contact provider to create a new contact
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void verificaWhatsapp() {
        //This class provides applications access to the content model.
        ContentResolver cr = getApplicationContext().getContentResolver();
        //RowContacts for filter Account Types
        Cursor contactCursor = cr.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID,
                        ContactsContract.RawContacts.CONTACT_ID},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.whatsapp"},
                null);

        //ArrayList for Store Whatsapp Contact
        ArrayList<String> myWhatsappContacts = new ArrayList<>();

        if (contactCursor != null) {
            if (contactCursor.getCount() > 0) {
                if (contactCursor.moveToFirst()) {
                    do {
                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                        String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                        if (whatsappContactId != null) {
                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                            Cursor whatsAppContactCursor = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{whatsappContactId}, null);

                            if (whatsAppContactCursor != null) {
                                whatsAppContactCursor.moveToFirst();
                                String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                whatsAppContactCursor.close();

                                //Add Number to ArrayList
                                myWhatsappContacts.add(number);
                                aggiungi(number);

                                Log.d("numeri_verificati", number);

                                //Log.d(TAG, " WhatsApp contact number :  " + number);
                            }
                        }
                    } while (contactCursor.moveToNext());
                    contactCursor.close();
                }
            }
        }
        //Log.d("numero_whats", " WhatsApp contact size :  " + myWhatsappContacts.size()+" da "+nomeFile);
    }


    public void aggiungi(final String d) {
        /*StringRequest request = new StringRequest(Request.Method.POST, URL_SALVA_NUMERI, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test_volley", response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> mapParam = new HashMap<String, String>();

                mapParam.put("NUMERO", d);

                return mapParam;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(request);*/

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput("num_ver.txt", getApplicationContext().MODE_PRIVATE));
            outputStreamWriter.write(d);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }


    }

    public void cancellaRubbrica() throws InterruptedException {
        int i = 0;
        Thread.sleep(1000);
        Log.d("sincro_cancella", "inizio cancellazione");
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            i++;
            if (i%100 == 0) {
                Thread.sleep(1000);
            }
            Log.d("svuota_rubbrica", "cancella");
            String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
            contentResolver.delete(uri, null, null);
        }
        Log.d("sincro_cancella", "fine cancellazione");
    }

    @Override
    public void onDestroy() {
        Log.d("uccisione", "destroy servizio");
        super.onDestroy();
    }
}
