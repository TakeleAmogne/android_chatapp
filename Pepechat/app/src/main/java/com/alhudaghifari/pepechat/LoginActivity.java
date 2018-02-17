package com.alhudaghifari.pepechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    TextView registerUser;
    EditText username, password;
    Button loginButton;
    String user, pass;

    private SessionManager session;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerUser = (TextView)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);

        mAuth = FirebaseAuth.getInstance();
        // Session manager
        session = new SessionManager(getApplicationContext());

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{
                    mAuth.signInWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        // Create login session
                                        session.setLogin(true);
                                        Intent intent = new Intent(LoginActivity.this,
                                                Users.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                    String url = "https://androidchatapp-76776.firebaseio.com/users.json";
//                    final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
//                    pd.setMessage("Loading...");
//                    pd.show();
//
//                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
//                        @Override
//                        public void onResponse(String s) {
//                            if(s.equals("null")){
//                                Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
//                            }
//                            else{
//                                try {
//                                    JSONObject obj = new JSONObject(s);
//
//                                    if(!obj.has(user)){
//                                        Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
//                                    }
//                                    else if(obj.getJSONObject(user).getString("password").equals(pass)){
//                                        UserDetails.username = user;
//                                        UserDetails.password = pass;
//                                        startActivity(new Intent(LoginActivity.this, Users.class));
//                                    }
//                                    else {
//                                        Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            pd.dismiss();
//                        }
//                    },new Response.ErrorListener(){
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//                            System.out.println("" + volleyError);
//                            pd.dismiss();
//                        }
//                    });
//
//                    RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
//                    rQueue.add(request);
                }

            }
        });
    }
}
