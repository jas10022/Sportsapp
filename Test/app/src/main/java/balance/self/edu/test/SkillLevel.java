package balance.self.edu.test;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.progressBarStyleHorizontal;

public class SkillLevel extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;
    String progress;
    ProgressBar loading;
    Handler handler = new Handler();
    int i;
    ProgressBar pr;
    String[] StudentSkillArray;
    String[] StudentSkillLevelArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_level);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Page.CurrentUser.getID());
        loading = (ProgressBar)findViewById(R.id.progressBar1);
        loading.setVisibility(View.GONE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        loading.setVisibility(View.VISIBLE);
        new dataRetriver().execute("");

    }

    private class dataRetriver extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            myRef.child("Skills").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    StudentSkillArray = new String[(int)dataSnapshot.getChildrenCount()];
                    StudentSkillLevelArray = new String[(int)dataSnapshot.getChildrenCount() ];

                    int a =0;
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        System.out.println(childSnapshot.getKey());

                        StudentSkillArray[a] = childSnapshot.getKey();
                        StudentSkillLevelArray[a]= childSnapshot.getValue().toString();

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

            return progress;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.setVisibility(View.GONE);

            for(int a = 0;a<StudentSkillArray.length; a++){
                addSection(R.id.progress_bars,StudentSkillArray[a], Integer.parseInt(StudentSkillLevelArray[a]));
                Log.d("SkillLevel",StudentSkillLevelArray[a]);
            }

                }
            }

    private void addSection(int sectionID, String value , int score){
        LinearLayout section = (LinearLayout)findViewById(sectionID);

        Log.d("StudentView","worked");
        TextView et = new TextView(SkillLevel.this);
        pr = new ProgressBar(SkillLevel.this,null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        pr.setLayoutParams(lp);
        et.setText(value);
        section.addView(et);
        section.addView(pr);
        run(pr,score);
    }

    private void run (final ProgressBar pr, final int score){

        i = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while( i < score * 10) {
                    i++;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pr.setProgress(i);
                        }
                    });
                }
            };
        }).start();


    }


        }


