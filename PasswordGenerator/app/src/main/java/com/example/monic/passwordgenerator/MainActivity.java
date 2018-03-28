package com.example.monic.passwordgenerator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    CharSequence[] items;
    List<String> listItems = new ArrayList<String>();
    List<String> threadPasswords = new ArrayList<String>();
    ExecutorService threadpool = Executors.newFixedThreadPool(2);
    Handler handler;
    ProgressDialog progress;
    TextView tv6;
    String personName;
    String personDept;
    int personAge;
    int personZipcode;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.edit1);
        final EditText dept = (EditText) findViewById(R.id.edit2);
        final EditText age = (EditText) findViewById(R.id.edit3);
        final EditText zip = (EditText) findViewById(R.id.edit4);

        final Button b1 = (Button) findViewById(R.id.button1);
        final Button b2 = (Button) findViewById(R.id.button2);
        final Button b3 = (Button) findViewById(R.id.button3);
        final Button b4 = (Button) findViewById(R.id.button4);

        tv6 = (TextView) findViewById(R.id.textview6);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                dept.setText("");
                age.setText("");
                zip.setText("");
                tv6.setText("");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tname="";
                String tdept="";
                int tage=0;
                int tzip=0;

                tname = name.getText().toString();
                tdept = dept.getText().toString();
                try{
                    tage = Integer.parseInt(age.getText().toString());
                } catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Invalid age", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    tzip = Integer.parseInt(zip.getText().toString());
                } catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Invalid zip", Toast.LENGTH_SHORT).show();
                    return;
                }


                String namematch = "/^[A-Za-z]?[A-Za-z ]*$/";
                if(tname.equals("") || !(tname.matches("[a-zA-Z]+")))
                {
                    Toast.makeText(MainActivity.this, "Name should include only characters", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(age.getText().toString()) < 0 || !(tage == (int) tage)) {
                    Toast.makeText(MainActivity.this, "Age should be positive Integer", Toast.LENGTH_LONG).show();
                } else if (zip.getText().toString().length() > 5) {
                    Toast.makeText(MainActivity.this, "Zipcode should be only 5 characters", Toast.LENGTH_LONG).show();
                } else {
                    progress = new ProgressDialog(MainActivity.this);
                    progress.setMessage("Generating Password...");
                    progress.setMax(100);
                    progress.setCancelable(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progress.show();
                    for (int i = 0 ; i < 5; i++) {
                        count++;
                        threadpool.execute(new ThreadPasswordGenerator(tname,tdept,tage,tzip));
                    }
                }


                handler = new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what){
                            case ThreadPasswordGenerator.STATUS_STEP:
                                progress.setProgress(msg.getData().getInt("progress"));
                                break;
                            case ThreadPasswordGenerator.STATUS_DONE:
                                progress.dismiss();
                                items = threadPasswords.toArray(new CharSequence[threadPasswords.size()]);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Choose a Password")
                                        .setItems(items, new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                tv6.setText(items[which]);
                                                dialogInterface.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                                threadPasswords.clear();
                                break;
                            default:
                                break;

                        }
                        return false;
                    }
                });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personName = name.getText().toString();
                personDept = dept.getText().toString();
                try{
                    personAge = Integer.parseInt(age.getText().toString());
                } catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Invalid age", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    personZipcode = Integer.parseInt(zip.getText().toString());
                } catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Invalid zip", Toast.LENGTH_SHORT).show();
                    return;
                }

                String namematch = "/^[A-Za-z]?[A-Za-z ]*$/";
                if(personName.equals("") || !(personName.matches("[a-zA-Z]+")))
                {
                    Toast.makeText(MainActivity.this, "Name should include only characters", Toast.LENGTH_LONG).show();
                } else if (personAge < 0 || !(personAge == (int) personAge)) {
                    Toast.makeText(MainActivity.this, "Age should be positive Integer", Toast.LENGTH_LONG).show();
                } else if (zip.getText().toString().length() > 5) {
                    Toast.makeText(MainActivity.this, "Zipcode should be only 5 characters", Toast.LENGTH_LONG).show();
                } else {
                    new AsyncGenerator().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
    }

    class AsyncGenerator extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMax(100);
            progress.setCancelable(false);
            progress.setMessage("Generating Password...");
            progress.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
            items = listItems.toArray(new CharSequence[listItems.size()]);
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Choose a Password")
                    .setItems(items, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            tv6.setText(items[which]);
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            listItems.clear();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int k = 0; k < 5 ; k++) {
                 Util u = new Util();
                 listItems.add(Util.getPassword(personName, personDept, personAge, personZipcode));
                 publishProgress((k+1)*20);
            }
            return null;
        }
    }

    class ThreadPasswordGenerator implements Runnable{

        static final int STATUS_STEP=1;
        static final int STATUS_DONE=2;

        String passname,passdept;
        int passage,passzip;
        public ThreadPasswordGenerator(String name,String dept,int age,int zip) {
            this.passname=name;
            this.passdept=dept;
            this.passage=age;
            this.passzip=zip;
        }

        @Override
        public void run() {

            Message message;
                threadPasswords.add(Util.getPassword(passname, passdept, passage, passzip));
                Bundle bundle = new Bundle();
                message = new Message();
                bundle.putInt("progress", threadPasswords.size()*20);
                message.setData(bundle);
                message.what = STATUS_STEP;
                handler.sendMessage(message);

            if (threadPasswords.size() == 5) {
                message = new Message();
                message.what=STATUS_DONE;
                handler.sendMessage(message);
            }
        }
    }
}
