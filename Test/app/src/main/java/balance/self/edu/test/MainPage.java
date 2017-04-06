package balance.self.edu.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainPage extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button mLoginButton = (Button)findViewById(R.id.login_button);
        Button mSignupButton = (Button)findViewById(R.id.SignUp);
        Button TestButton = (Button)findViewById(R.id.Test);

        mDatabase = FirebaseDatabase.getInstance().getReference("Message");

        mDatabase.setValue("Hello, World!");

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPage.this,LoginPage.class);
                startActivity(i);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPage.this,MainActivity.class);
                startActivity(i);
            }
        });

        TestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(MainPage.this,StudentPage.class);
                //startActivity(i);
            }
        });
    }
}
