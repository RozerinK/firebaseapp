package com.firebaseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Ferheng extends AppCompatActivity implements TextWatcher, ValueEventListener, View.OnClickListener {


    private AutoCompleteTextView mySearchView = null;
    private TextView txt = null;
    ArrayAdapter<String> autoCompleteAdapter;
    private FirebaseDatabase db;
    DatabaseReference dbRef = db.getInstance().getReference("turki");
    private LinearLayout linearlayout;
    List<String> dict =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferheng);
        initView();
    }

    public void initView() {


        mySearchView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        linearlayout = (LinearLayout) findViewById(R.id.activity_ferheng_linearlayout);
        autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mySearchView.setThreshold(3);
        mySearchView.setAdapter(autoCompleteAdapter);

        txt = (TextView) findViewById(R.id.activity_ferheng_textView);




        initEvent();

    }

    public void initEvent() {


        mySearchView.addTextChangedListener(this);
        mySearchView.setOnClickListener(this);


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()>=3)
            dbRef.addListenerForSingleValueEvent(this);

    }



    @Override
    public void onDataChange (DataSnapshot dataSnapshot){
        //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.

        String key = mySearchView.getText().toString();

        for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
            String word = suggestionSnapshot.getKey();
            if (word.startsWith(key)) {
                if(dict.contains(word)==false)
                // String key = String.valueOf(dataSnapshot.child(mySearchView.getText().toString()).child("meanings").child(String.valueOf(i)).getValue(String.class));
                dict.add(word);
                autoCompleteAdapter.add(word);
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onClick(View v) {
        //String word = mySearchView.getText().toString();

        /*long count = dataSnapshot.child(mySearchView.getText().toString()).child("meanings").getChildrenCount();
        int i;
        for (i = 0; i < count; i++) {

            String key = String.valueOf(dataSnapshot.child(mySearchView.getText().toString().substring(0,3)).child("meanings").child(String.valueOf(i)).getValue(String.class));

            // String key = String.valueOf(dataSnapshot.child(mySearchView.getText().toString()).child("meanings").child(String.valueOf(i)).getValue(String.class));
            autoCompleteAdapter.add(key);
            TextView textView = new TextView(Ferheng.this);
            textView.setText(key);
            linearlayout.addView(textView);
        }*/
    }
}
