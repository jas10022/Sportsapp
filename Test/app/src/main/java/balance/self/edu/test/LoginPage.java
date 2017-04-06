package balance.self.edu.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


public class LoginPage extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;
    FirebaseDatabase database;
    public static User user;
    public int Age;
    public double weight;
    public String Username;
    public String Sex;
    public String Email;
    DatabaseReference myRef;
    ProgressBar Loading;
    Button login;
    String UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();



        login =  (Button)findViewById(R.id.login_button);
        mPassword = (EditText)findViewById(R.id.Password);
        mEmail = (EditText)findViewById(R.id.Email);
        Loading = (ProgressBar)findViewById(R.id.Loading);
        //Loading.setVisibility(View.GONE);

        if(Loading.isActivated()) {
            Log.d(TAG, "working");
        }else{
            Log.d(TAG, "no");

        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        } ;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(LoginPage.this, R.string.auth_failed,
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    mAuth = FirebaseAuth.getInstance();
                                    firebaseUser = mAuth.getCurrentUser();
                                    database = FirebaseDatabase.getInstance();


                                    myRef = database.getReference(firebaseUser.getUid());
                                    mEmail.setVisibility(View.GONE);
                                    mPassword.setVisibility(View.GONE);
                                    login.setVisibility(View.GONE);
                                    Loading.setVisibility(View.VISIBLE);
                                    Loading.setActivated(true);
                                    if(Loading.isActivated()) {
                                        Log.d(TAG, "working");
                                    }else{
                                        Log.d(TAG, "no");

                                    }

                                    new dataRetriver().execute("");
                                }


                            }
                        });
            }
        });
    }
    private class dataRetriver extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            Log.d("LoginPage","working");

            myRef.child("Age").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("LoginPage",dataSnapshot.getValue().toString());
                    Age = Integer.parseInt(dataSnapshot.getValue().toString());
                    user.setAge(Age);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.child("Email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Email = dataSnapshot.getValue().toString();
                    user.setEmail(Email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Username = dataSnapshot.getValue().toString();
                    user.setUserName(Username);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.child("Weight").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    weight = Double.parseDouble(dataSnapshot.getValue().toString());
                    user.setWeight(weight);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            myRef.child("Sex").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Sex = dataSnapshot.getValue().toString();
                    user.setSex(Sex);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.child("UserType").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    UserType = dataSnapshot.getValue().toString();
                    user.setUserType(UserType);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            user.setID(firebaseUser.getUid());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Sex;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(LoginPage.this, R.string.wait,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity.user = user;

            if(user.getUserType().equals("Student")) {
                Intent i = new Intent(LoginPage.this, Page.class);
                startActivity(i);
            }else if (user.getUserType().equals("Coach")){
                Intent a = new Intent(LoginPage.this, CoachPage.class);
                startActivity(a);
            }
        }

        }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

