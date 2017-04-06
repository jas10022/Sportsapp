package balance.self.edu.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.dial;
import static android.R.attr.name;

public class StudentView extends AppCompatActivity {

    TextView Username;
    TextView Sex;
    TextView Age;
    TextView Weight;
    public static User Student;
    FloatingActionButton actionButton;
    FloatingActionButton NewSkillsAdd;
    FloatingActionButton NewFeedback;
    FloatingActionButton NewWorkout;
    TextView NewSkillsText;
    TextView Skills;
    Animation hide;
    Animation show;
    Boolean i =false;
    String Skill;
    DatabaseReference StudentRef;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    String[] StudentSkillArray;
    String[] StudentSkillLevelArray;
    TextView NewFeedbackText;
    TextView NewWorkoutText;
    TextView Workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentview);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Username = (TextView)findViewById(R.id.Username);
        Sex = (TextView)findViewById(R.id.Sex);
        Age = (TextView)findViewById(R.id.Age);
        Weight = (TextView)findViewById(R.id.Weight);
        actionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        NewSkillsAdd = (FloatingActionButton)findViewById(R.id.newSkill);
        NewFeedback = (FloatingActionButton)findViewById(R.id.newFeedback);
        NewWorkout = (FloatingActionButton)findViewById(R.id.newWorkout);
        NewSkillsText = (TextView)findViewById(R.id.NewSkillText);
        Skills = (TextView)findViewById(R.id.Skills);
        NewFeedbackText = (TextView)findViewById(R.id.newFeedbackText);
        NewWorkoutText = (TextView)findViewById(R.id.newWorkoutText);
        hide = AnimationUtils.loadAnimation(getApplication(), R.anim.hide);
        show = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate);
        Workouts = (TextView)findViewById(R.id.Workouts);

        NewSkillsAdd.hide();
        NewSkillsText.setVisibility(View.INVISIBLE);
        NewFeedback.hide();
        NewFeedbackText.setVisibility(View.INVISIBLE);
        NewWorkout.hide();
        NewWorkoutText.setVisibility(View.INVISIBLE);

        Student = Students.StudentUser;

    }

    @Override
    protected void onStart() {
        super.onStart();

        Username.setText("Username: " + Student.getUserName());
        Sex.setText("Sex: " + Student.getSex());
        Age.setText("Age: " + Student.getAge());
        Weight.setText("eight: " + Double.toString(Student.getWeight()));
        new dataRetriver().execute("");


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!i) {
                    NewSkillsAdd.show();
                    NewSkillsAdd.startAnimation(show);
                    NewSkillsAdd.setClickable(true);
                    NewSkillsText.setVisibility(View.VISIBLE);
                    NewFeedback.show();
                    NewFeedback.startAnimation(show);
                    NewFeedback.setClickable(true);
                    NewFeedbackText.setVisibility(View.VISIBLE);
                    NewWorkout.show();
                    NewWorkout.startAnimation(show);
                    NewWorkout.setClickable(true);
                    NewWorkoutText.setVisibility(View.VISIBLE);
                    i = true;
                }else if (i){
                    NewSkillsAdd.hide();
                    NewSkillsAdd.startAnimation(hide);
                    NewSkillsAdd.setClickable(false);
                    NewSkillsText.setVisibility(View.INVISIBLE);
                    NewFeedback.hide();
                    NewFeedback.startAnimation(hide);
                    NewFeedback.setClickable(false);
                    NewFeedbackText.setVisibility(View.INVISIBLE);
                    NewWorkout.hide();
                    NewWorkout.startAnimation(hide);
                    NewWorkout.setClickable(false);
                    NewWorkoutText.setVisibility(View.INVISIBLE);
                    i = false;

                }
            }
        });

        NewSkillsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(StudentView.this);
                dialog.setContentView(R.layout.dialog_skills);
                dialog.setTitle("Skill");

                final NumberPicker rating = (NumberPicker)dialog.findViewById(R.id.rating);
                rating.setMinValue(1);
                rating.setMaxValue(10);
                rating.setWrapSelectorWheel(true);
                Button Enter = (Button) dialog.findViewById(R.id.Enter);
                Enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText edit=(EditText)dialog.findViewById(R.id.Skill);
                        StudentRef = database.getReference(Student.getID());
                        int a = rating.getValue();
                        StudentRef.child("Skills").child(edit.getText().toString().toLowerCase()).setValue(a);
                        Skill = edit.getText().toString() + ": " + a;
                        Log.d("StudentView",Integer.toString(a));
                        addToSection(R.id.SkillsSection, Skill);


                        dialog.dismiss();
                    }
                });

                Button Close = (Button) dialog.findViewById(R.id.close);
                Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        NewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(StudentView.this);
                dialog.setContentView(R.layout.feedback);
                dialog.setTitle("Feedback");

                final EditText feedbacktext = (EditText)dialog.findViewById(R.id.feedbackenter);
                final EditText feedbacktitletext = (EditText)dialog.findViewById(R.id.Title);

                Button Enter = (Button) dialog.findViewById(R.id.Enter);
                Enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StudentRef = database.getReference(Student.getID());
                        StudentRef.child("feedback").child(feedbacktitletext.getText().toString()).setValue(feedbacktext.getText().toString().replaceAll(" ", "/"));
                        dialog.dismiss();
                    }
                });

                Button Close = (Button) dialog.findViewById(R.id.close);
                Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        NewWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(StudentView.this);
                dialog.setContentView(R.layout.feedback);
                dialog.setTitle("Workout");

                final EditText WorkoutTitle = (EditText)findViewById(R.id.workoutTitle);
                final EditText WorkoutAmount = (EditText)findViewById(R.id.workoutAmount);

                Button Enter = (Button) dialog.findViewById(R.id.Enter);
                Enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StudentRef = database.getReference(Student.getID());

                        StudentRef.child("Workouts").child(WorkoutTitle.getText().toString().replaceAll(" ","/")).setValue(WorkoutAmount.getText().toString().replaceAll(" ", "/"));

                        dialog.dismiss();
                    }
                });

                Button Close = (Button) dialog.findViewById(R.id.close);
                Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
    }

    private void addToSection(int sectionID, String values){
        LinearLayout section = (LinearLayout)findViewById(sectionID);

        Log.d("StudentView","worked");

        TextView et = new TextView(StudentView.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(values);
            section.addView(et);
    }
    private void addToSection(int sectionID, String values,int score){
        LinearLayout section = (LinearLayout)findViewById(sectionID);

        Log.d("StudentView","worked");

        TextView et = new TextView(StudentView.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setText(values + ": " + score);
        section.addView(et);
    }

    private class dataRetriver extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            StudentRef = database.getReference(Student.getID());
            StudentRef.child("Skills").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    StudentSkillArray = new String[(int)dataSnapshot.getChildrenCount()];
                    StudentSkillLevelArray = new String[(int)dataSnapshot.getChildrenCount() ];

                    int c =0;
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        System.out.println(childSnapshot.getKey());

                        StudentSkillArray[c] = childSnapshot.getKey();
                        StudentSkillLevelArray[c]= childSnapshot.getValue().toString();

                        c++;
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

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for(int b = 0;b<StudentSkillArray.length; b++){
                addToSection(R.id.SkillsSection,StudentSkillArray[b], Integer.parseInt(StudentSkillLevelArray[b]));
                Log.d("SkillLevel",StudentSkillLevelArray[b]);
            }

        }
    }


}

