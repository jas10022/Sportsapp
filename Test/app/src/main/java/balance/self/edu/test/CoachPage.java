package balance.self.edu.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CoachPage extends AppCompatActivity {

    public static User CurrentUser;
    TextView username;
    Button connect;
    Button skilllevelButton;
    Button StudentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_page);

        CurrentUser = MainActivity.user;

        username = (TextView)findViewById(R.id.username);
        connect = (Button)findViewById(R.id.connect);
        skilllevelButton = (Button)findViewById(R.id.skillLevel);
        StudentsButton = (Button)findViewById(R.id.Students);

    }

    @Override
    protected void onStart() {
        super.onStart();

        username.setText("Welcome " +  CurrentUser.getUserName());
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CoachPage.this,Connect.class);
                startActivity(i);
            }
        });

        skilllevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CoachPage.this,SkillLevel.class);
                startActivity(i);
            }
        });
        StudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CoachPage.this,Students.class);
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
