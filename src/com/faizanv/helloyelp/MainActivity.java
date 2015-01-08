package com.faizanv.helloyelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	
	private EditText searchBar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button searchButton;
        searchButton = (Button)findViewById(R.id.button1);
        searchButton.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)  //go here when button is pressed
        	{
        		String searchText = searchBar.getText().toString(); //saving the contents of the search bar
        		if(searchText.equalsIgnoreCase("Amelies"))
        		{
        			Toast.makeText(getApplicationContext(), "Search for: " + searchText,Toast.LENGTH_LONG).show(); //display search bar text
        		}
        		else
        		{
        			Toast.makeText(getApplicationContext(), "I think you meant Amelies", 0).show();
        		}
        		
        		
        		Intent goToMenu = new Intent(MainActivity.this,MenuActivity.class); //variable to save the next page
        		MainActivity.this.startActivity(goToMenu); //go to next page
        	}
        });
        
        searchBar = (EditText) findViewById(R.id.editText1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
