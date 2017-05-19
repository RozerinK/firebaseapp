package com.firebaseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database extends AppCompatActivity implements View.OnClickListener {


    private FirebaseDatabase db;
    private EditText edt;
    private TextView txt;
    private Button btn;
    private Button btnread;
    int count = 0;
    private TextView txtreadvalue;
    private Button btnreadvalue;
    private  LinearLayout layoutlinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        initView();
    }

    public void initView() {


        edt = (EditText) findViewById(R.id.activity_database_edtdb);
        txt = (TextView) findViewById(R.id.activity_database_txtread);
        btn = (Button) findViewById(R.id.activity_database_btndb);
        btnread = (Button) findViewById(R.id.activity_database_btnread);
        txtreadvalue = (TextView) findViewById(R.id.activity_database_txtreadvalue);
        btnreadvalue = (Button) findViewById(R.id.activity_database_btnreadvalue);
        layoutlinear = (LinearLayout) findViewById(R.id.activity_database_linearlayout);
        db = FirebaseDatabase.getInstance();
        initEvent();
    }

    public void initEvent() {
        btn.setOnClickListener(this);
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                readdb();


            }
        });
        btnreadvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbrefReadValue = db.getReference("turki");
                edt.clearFocus();
                layoutlinear.removeAllViews();
                       dbrefReadValue.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString().trim(), Toast.LENGTH_SHORT);
                             /*   StringBuilder builder = new StringBuilder();
                                for (String details : list2) {
                                    builder.append(details + "\n");
                                }

                                custName.setText(builder.toString());*/

                                long count =  dataSnapshot.child(edt.getText().toString()).child("meanings").getChildrenCount();
                                int i;
                                //View view = LayoutInflater.from(Database.this).inflate(R.layout.items,null);

                                for(i =0;i<count ; i++){

                                    String key = String.valueOf(dataSnapshot.child(edt.getText().toString()).child("meanings").child(String.valueOf(i)).getValue(String.class));


                                        TextView textView = new TextView(Database.this);
                                        textView.setText(key);
                                        layoutlinear.addView(textView);

                                  //  txtreadvalue.setText(tmp);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    @Override
    public void onClick(View v) {
        count++;
        writedb(edt.getText().toString(), count);
    }

    public void writedb(String word, int count) {

        DatabaseReference dbref = db.getReference("turki");
        String key = dbref.push().getKey();

        DatabaseReference dbrefchild = db.getReference("turki/" + count);
        dbrefchild.setValue(word);
    }

    public void readdb() {
        DatabaseReference readDataRef = db.getReference("turki");
        readDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for (DataSnapshot key : keys) {

                    txt.append(key.getValue().toString() + "\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
