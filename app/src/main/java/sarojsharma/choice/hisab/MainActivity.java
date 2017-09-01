package sarojsharma.choice.hisab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button btnAdd,btnSave,btnCancel;
    private ProgressDialog pDialog;
    EditText Amount,Details;
    String Topic;
    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList<String> list = new ArrayList<String>();

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> adapter;
    public SharedPreferences app_preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //I made the changes to the first hisab
        pDialog = new ProgressDialog(this);

        list.add("Salary");
        list.add("Lemon");
        list.add("Salt");
        list.add("Goods");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        final Spinner sItems = (Spinner) findViewById(R.id.spinner1);
        sItems.setAdapter(adapter);
        btnSave = (Button)findViewById(R.id.btnSave);
        Amount = (EditText)findViewById(R.id.Amount);
                Details = (EditText)findViewById(R.id.Details);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list.add(txtItem.getText().toString());
                list.add("saroj");

                adapter.notifyDataSetChanged();
            }


            });


btnSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //save the below to database

        Log.d("values", Amount.getText().toString());
        String a = Amount.getText().toString();
        String b = Details.getText().toString();
        String c = sItems.getSelectedItem().toString();

        registerUser(a, b,c);
    }
});


    }



    //method used to register a user
    private void registerUser(final String Amount, final String Details,final String Topic) {

        pDialog.setTitle("Registration in progress");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.Register_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("hatas",response.toString());
                pDialog.hide();

                app_preferences =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if(response.equalsIgnoreCase("success")){

                    Toast.makeText(MainActivity.this, "Successfully inserted", Toast.LENGTH_SHORT).show();



                }
                else{
                    Toast.makeText(MainActivity.this, "username already exists", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "No internet connection,Registration failed", Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register Register_Config.Register_URL
                Map<String, String> params = new HashMap<String, String>();

                params.put("Amount", Amount);
                params.put("Details", Details);
                params.put("Topic", Topic);
               // Log.d("hata", Amount.toString());
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Topic = parent.getItemAtPosition(position).toString();


        Toast.makeText(parent.getContext(), "Selected: " + Topic, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Topic = parent.getItemAtPosition(0).toString();


    }
}
