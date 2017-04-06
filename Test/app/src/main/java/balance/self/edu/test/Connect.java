package balance.self.edu.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Connect extends AppCompatActivity {

    TextView IDtext;
    EditText ConnectorsID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference UserRef;
    String ConectorsUsername;
    Button ButtonConnect;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        IDtext = (TextView)findViewById(R.id.UserID);
        ConnectorsID = (EditText)findViewById(R.id.ConnectorID);
        ButtonConnect = (Button)findViewById(R.id.connect);

    }

    @Override
    protected void onStart() {
        super.onStart();

        IDtext.setText("Your ID: " + Page.CurrentUser.getID() );

        ButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UserRef = database.getReference(ConnectorsID.getText().toString());
                    myRef = database.getReference(Page.CurrentUser.getID());

                    new dataRetriver().execute("");
                }catch(Error e){
                    Toast.makeText(Connect.this, "There was an error in the ID given.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class dataRetriver extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            Log.d("LoginPage","working");

            UserRef.child("Username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ConectorsUsername = dataSnapshot.getValue().toString();
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
            return ConectorsUsername;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(Connect.this, R.string.wait,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Connect.this, "You have connected to " + ConectorsUsername,
                    Toast.LENGTH_SHORT).show();
            myRef.child("Coach").child(ConectorsUsername).setValue(ConnectorsID.getText().toString());
            DatabaseReference connectorRef = database.getReference(ConnectorsID.getText().toString());
            connectorRef.child("Students").child(Page.CurrentUser.getUserName()).setValue(Page.CurrentUser.getID());

        }

    }
}
