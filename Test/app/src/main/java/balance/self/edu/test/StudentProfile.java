package balance.self.edu.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StudentProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        TextView username = (TextView)findViewById(R.id.username);
        TextView weight = (TextView)findViewById(R.id.weight);
        TextView age = (TextView)findViewById(R.id.age);
        TextView sex = (TextView)findViewById(R.id.sex);
        TextView email = (TextView)findViewById(R.id.email);

        username.setText(Page.CurrentUser.getUserName());
        sex.setText(Page.CurrentUser.getSex());
        weight.setText(Double.toString(Page.CurrentUser.getWeight()));
        age.setText(Integer.toString(Page.CurrentUser.getAge()));
        email.setText(Page.CurrentUser.getEmail());

    }
}
