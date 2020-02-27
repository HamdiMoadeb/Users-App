package com.outsider.reclamationqualipro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class UserActivity extends AppCompatActivity {

    EditText editSearch;
    Button searchBtn;
    TextView gender, dob, email, phone, address, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        searchBtn = findViewById(R.id.searchBtn);
        editSearch = findViewById(R.id.searchET);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.emaill);
        phone = findViewById(R.id.phonenb);
        address = findViewById(R.id.address);
        fullname = findViewById(R.id.fullname);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = editSearch.getText().toString();

                new SoapTask(UserActivity.this, id).execute();


            }
        });
    }

    public class SoapTask extends AsyncTask<Void, Void, Void> {

        Context context;
        String id;



        public SoapTask( Context ctx, String id)
        {
            this.context=ctx;
            this.id=id;

        }
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                Ion.with(context)
                        .load("https://gorest.co.in/public-api/users/"+id+"?_format=json&access-token=b_bnSp57xV4j3TxHT1FiOdIq-_c2ekNteZ4t")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result != null){
                                    JsonObject resultt = result.getAsJsonObject("result");
                                    fullname.setText(resultt.get("first_name").getAsString()+" "+resultt.get("last_name").getAsString());
                                    gender.setText(resultt.get("gender").getAsString());
                                    dob.setText(resultt.get("dob").getAsString());
                                    email.setText(resultt.get("email").getAsString());
                                    phone.setText(resultt.get("phone").getAsString());
                                    address.setText(resultt.get("address").getAsString());
                                }
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }

}

