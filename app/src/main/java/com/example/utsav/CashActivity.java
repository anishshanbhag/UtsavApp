package com.example.utsav;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import com.firebase.client.Firebase;
import com.spark.submitbutton.SubmitButton;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CashActivity extends AppCompatActivity{

    EditText name,college,number,email,event,cashReceived,tktnum;
    SubmitButton submit;
    Session session;
    String rec,event1,name1,college1,number1,cash1,e,tnum;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        name = (EditText)findViewById(R.id.name);
        college = (EditText)findViewById(R.id.college);
        number = (EditText)findViewById(R.id.number);
        email = (EditText)findViewById(R.id.email);
        event = (EditText)findViewById(R.id.event);
        cashReceived = (EditText)findViewById(R.id.cash);
        tktnum = findViewById(R.id.tkno);
        submit = findViewById(R.id.submit);
        Firebase.setAndroidContext(this);
        Intent i = getIntent();
        e = i.getStringExtra("email");
        e = e.split("@",2)[0];
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, CashOrOnline.class));
    }

    public void onSub(View view) {
        if(name.getText().toString().matches("") ||college.getText().toString().matches("")||number.getText().toString().matches("") ||email.getText().toString().matches("") ||event.getText().toString().matches("") ||cashReceived.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(),"Please enter all details",Toast.LENGTH_SHORT).show();
            return;
        }
        if(number.getText().toString().length()!=10){
            Toast.makeText(getApplicationContext(),"Please enter correct number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isEmailValid(email.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please enter correct email address",Toast.LENGTH_SHORT).show();
            return;
        }

        //EmailPart..
        final AlertDialog.Builder alert = new AlertDialog.Builder(this).setTitle("Alert!!!")
                .setMessage("Is Payment Received?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rec = email.getText().toString();
                        event1 = event.getText().toString();
                        name1 = name.getText().toString();
                        college1 = college.getText().toString();
                        number1 = number.getText().toString();
                        cash1 = cashReceived.getText().toString();
                        tnum = tktnum.getText().toString();
                        mRef = new Firebase("https://utsav-d61a0.firebaseio.com/"+event1+"/"+e+"/"+name1);
                        Toast.makeText(getApplicationContext(),"FireBase Updated.",Toast.LENGTH_LONG).show();
                        Intent i2 = new Intent(CashActivity.this,CashOrOnline.class);
                        i2.putExtra("email",e);
                        startActivity(i2);
                        Properties prop = new Properties();
                        prop.put("mail.smtp.host","smtp.gmail.com");
                        prop.put("mail.smtp.socketFactory.port","465");
                        prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                        prop.put("mail.smtp.auth","true");
                        prop.put("mail.smtp.port","465");

                        session = Session.getDefaultInstance(prop, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("utsavbmsce2k19@gmail.com", "utsav@2019");
                            }
                        });

                        RetreiveFeedTask task = new RetreiveFeedTask();
                        task.execute();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
 public class RetreiveFeedTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("utsavbmsce2k19@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rec));
                message.setSubject(event1+" Ticket");
                message.setContent("Hey,"+"<br/><br/>"+"Thank You for registering in Utsav 2K19. Here are the details of your Ticket:<br/>(Kindly bring this ticket on the day of event)"+"<br/><br/>"+"Ticket Number :"+tnum+"<br/>"+"Name : "+name1+"<br/>"+"College : "+college1+"<br/>"+"Contact Number : "+number1+"<br/>"+"Email : "+rec+"<br/><br/><br/><br/>"+"Regards,"+"<br/>"+"BMSCE Utsav Team"+"<br/><br/><br/>"+"NOTE : Kindly Do not reply to this Machine generated Message.","text/html; charset=utf-8");
                Transport.send(message);

            }catch(MessagingException e){
                Toast.makeText(getApplicationContext(),"Email Not Sent!!",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Email Not Sent!!!",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Toast.makeText(getApplicationContext(),"E-Mail Sent.",Toast.LENGTH_LONG).show();
            Firebase mRefChild1 = mRef.child("College");
            mRefChild1.setValue(college1);
            Firebase mRefChild2 = mRef.child("Email");
            mRefChild2.setValue(rec);
            Firebase mRefChild3 = mRef.child("Number");
            mRefChild3.setValue(number1);
            Firebase mRefChild5 = mRef.child("Cash");
            mRefChild5.setValue(cash1);
        }


    }

}
