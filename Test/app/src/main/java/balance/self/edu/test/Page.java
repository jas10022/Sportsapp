package balance.self.edu.test;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Page extends AppCompatActivity {

    public static User CurrentUser;
    TextView username;
    Button connect;
    Button skilllevelButton;
    Button profileButton;
    Button PlayersNear;
    Button FeedBack;
    Button Workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        CurrentUser = MainActivity.user;

        username = (TextView)findViewById(R.id.username);
        connect = (Button)findViewById(R.id.connect);
        skilllevelButton = (Button)findViewById(R.id.skillLevel);
        profileButton = (Button)findViewById(R.id.Profile);
        PlayersNear = (Button)findViewById(R.id.Map);
        FeedBack = (Button)findViewById(R.id.Feedback);
        Workouts = (Button)findViewById(R.id.Workouts);

    }

    @Override
    protected void onStart() {
        super.onStart();

        username.setText("Welcome " +  CurrentUser.getUserName());
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page.this,Connect.class);
                startActivity(i);
            }
        });

        skilllevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page.this,SkillLevel.class);
                startActivity(i);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page.this,StudentProfile.class);
                startActivity(i);
            }
        });

        PlayersNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page.this,PlayersNear.class);
                startActivity(i);
            }
        });

        FeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page.this,FeedBack.class);
                startActivity(i);
            }
        });

        Workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page.this,Workouts.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        username.setText("Welcome " +  CurrentUser.getUserName());

    }
}
