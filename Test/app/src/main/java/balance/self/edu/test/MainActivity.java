package balance.self.edu.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class MainActivity extends FragmentActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText mPassword;
    EditText mEmail;
    EditText mUsername;
    EditText mAge;
    EditText mWeight;
    RadioButton Male;
    RadioButton Female;
    String email;
    String password;
    FirebaseUser FirebaseUser;
    FirebaseDatabase database;
    public static User user;
    String Sex = "";
    RadioButton Coach;
    RadioButton Student;
    String UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();


        // Initialize Facebook Login button
        mAuth = FirebaseAuth.getInstance();


        Button mSignUpButton = (Button) findViewById(R.id.SignUp);
        mPassword = (EditText) findViewById(R.id.Password);
        mEmail = (EditText) findViewById(R.id.Email);
        mUsername = (EditText)findViewById(R.id.Username);
        mAge = (EditText)findViewById(R.id.Age);
        mWeight = (EditText)findViewById(R.id.Weight);
        Male = (RadioButton)findViewById(R.id.Male);
        Female = (RadioButton)findViewById(R.id.Female);
        Coach = (RadioButton) findViewById(R.id.Coach);
        Student = (RadioButton)findViewById(R.id.Student);

        Male.setText("Male");
        Female.setText("Female");
        Coach.setText("Coach");
        Student.setText("Student");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Female.isChecked()){
                    Female.setActivated(false);

                }
                Sex = "Male";
            }
        });

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Male.isChecked()){
                    Male.setActivated(false);

                }
                Sex = "Female";
            }

        });
        Coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Student.isChecked()){
                    Student.setActivated(false);

                }
                UserType = "Coach";
            }
        });
        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Coach.isChecked()){
                    Coach.setActivated(false);

                }
                UserType = "Student";
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                String Username = mUsername.getText().toString();
                int Age = Integer.parseInt(mAge.getText().toString());
                double weight = Double.parseDouble(mWeight.getText().toString());

                user = new User(Age,email,Sex,Username,weight,FirebaseUser.getUid(),UserType);

                Log.d(TAG,email + " " + password);

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                                }
                                FirebaseUser = mAuth.getCurrentUser();

                                DatabaseReference myRef = database.getReference(FirebaseUser.getUid());
                                myRef.child("Email").setValue(user.getEmail());
                                myRef.child("Username").setValue(user.getUserName());
                                myRef.child("Age").setValue(user.getAge());
                                myRef.child("Weight").setValue(user.getWeight());
                                myRef.child("Sex").setValue(user.getSex());
                                myRef.child("UserType").setValue(user.getUserType());
                                myRef.child("Skills");

                                if(user.getUserType().equals("Student")) {
                                    Intent i = new Intent(MainActivity.this, Page.class);
                                    startActivity(i);
                                }else if (user.getUserType().equals("Coach")){
                                    Intent a = new Intent(MainActivity.this, CoachPage.class);
                                    startActivity(a);
                                }

                            }
                        });

            }
        });






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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
