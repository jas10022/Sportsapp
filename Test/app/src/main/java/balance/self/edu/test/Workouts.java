package balance.self.edu.test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Workouts extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;
    ProgressBar loading;
    String[] StudentWorkouts;
    String[] StudentWorkoutAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Page.CurrentUser.getID());
        loading = (ProgressBar)findViewById(R.id.loading);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new dataRetriver().execute("");
        loading.setVisibility(View.VISIBLE);

    }

    private class dataRetriver extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            myRef.child("feedback").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    StudentWorkouts = new String[(int)dataSnapshot.getChildrenCount()];
                    StudentWorkoutAmount = new String[(int)dataSnapshot.getChildrenCount() ];

                    int a =0;
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        System.out.println(childSnapshot.getKey());

                        StudentWorkouts[a] = childSnapshot.getKey();
                        StudentWorkoutAmount[a]= childSnapshot.getValue().toString().replaceAll("/", " ");

                        a++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "hi";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.setVisibility(View.GONE);

            for(int a = 0; a< StudentWorkouts.length; a++){
                addSection(R.id.WorkoutSection, StudentWorkoutAmount[a], StudentWorkouts[a]);
                Log.d("SkillLevel", StudentWorkoutAmount[a] + " : " + StudentWorkouts[a]);
            }
        }
    }

    private void addSection(int sectionID, String value, String title){
        LinearLayout section = (LinearLayout)findViewById(sectionID);

        Log.d("StudentView","worked");
        TextView et = new TextView(Workouts.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setText(title + ": " + value);
        section.addView(et);
    }


}
