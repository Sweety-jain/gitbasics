package com.example.android.booklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button)findViewById(R.id.goButton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Perform action on click
                Intent activityChangeIntent = new Intent(MainActivity.this, BooksListActivity.class);
                final String keyword = getBookTitle();

                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("keyword", keyword);
                startActivity(activityChangeIntent);
            }
        });
    }

    private String getBookTitle()
    {
        final EditText titleField = (EditText) findViewById(R.id.searchEditText);
        return titleField.getText().toString();
    }

}
