package balance.self.edu.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class Students extends AppCompatActivity {

    String[] StudentUsernameArray;
    String[] StudentIDArray;
    ArrayAdapter adapter;
    FirebaseAuth mAuth;
    DatabaseReference UserRef;
    FirebaseDatabase database;
    ListView listView;
    public static User StudentUser;
    public int Age;
    public double weight;
    public String Username;
    public String Sex;
    public String Email;

    String StudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        UserRef = database.getReference(LoginPage.user.getID());


    }

    @Override
    protected void onStart() {
        super.onStart();

        new dataRetriver().execute("");
        listView = (ListView) findViewById(R.id.mobile_list);


        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Object o = listView.getItemAtPosition(position);
                Log.d("Students",o.toString());
                Log.d("Students", StudentIDArray[position]);
                StudentUser = new User();

                StudentID = StudentIDArray[position];

                new StudentInformation().execute(StudentID);

            }
        });
    }

    private class dataRetriver extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            UserRef.child("Students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //td = (HashMap<String,String>) dataSnapshot.getValue();

                    StudentUsernameArray = new String[(int)dataSnapshot.getChildrenCount()];
                    StudentIDArray = new String[(int)dataSnapshot.getChildrenCount() ];

                    int i =0;
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        System.out.println(childSnapshot.getKey());

                        StudentUsernameArray[i] = childSnapshot.getKey();
                        StudentIDArray[i]= childSnapshot.getValue().toString();

                        i++;
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

            adapter = new ArrayAdapter<String>(Students.this, R.layout.listview, StudentUsernameArray);
            listView.setAdapter(adapter);

        }
    }

    private class StudentInformation extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {

            Log.d("Students", "Student Info" + strings[0]);
            DatabaseReference StudentRef = database.getReference(strings[0]);
            StudentUser.setID(strings[0]);
            StudentRef.child("Age").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Age = Integer.parseInt(dataSnapshot.getValue().toString());
                    StudentUser.setAge(Age);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            StudentRef.child("Email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Email = dataSnapshot.getValue().toString();
                    StudentUser.setEmail(Email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            StudentRef.child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Username = dataSnapshot.getValue().toString();
                    StudentUser.setUserName(Username);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            StudentRef.child("Weight").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    weight = Double.parseDouble(dataSnapshot.getValue().toString());
                    StudentUser.setWeight(weight);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            StudentRef.child("Sex").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Sex = dataSnapshot.getValue().toString();
                    StudentUser.setSex(Sex);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Students",StudentUser.getEmail() + StudentUser.getUserName());
            Intent i = new Intent(Students.this,StudentView.class);
            startActivity(i);

        }
    }
}
