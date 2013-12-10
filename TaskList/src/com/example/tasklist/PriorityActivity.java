package com.example.tasklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PriorityActivity extends Activity {
	
	private EditText name;
	private EditText value;
	private Button save;
	private Button cancel;
	private Button delete;
	
	private Priority priority;
	private Boolean newItem = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority);
		
		referenceViews();
		setListeners();
		initFields(savedInstanceState);
	}

	private void referenceViews() {
		name = (EditText) findViewById(R.id.editTextName);
		value = (EditText) findViewById(R.id.editTextValue);
		save = (Button) findViewById(R.id.buttonSave);
		cancel = (Button) findViewById(R.id.buttonCancel);
		delete = (Button) findViewById(R.id.buttonDelete);
	}
	
	private void setListeners() {
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// #####
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// #####
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// #####
			}
		});
		
	}
	
	private void initFields(Bundle savedInstanceState) {
		priority = (Priority) savedInstanceState.getSerializable(MainActivity.KEY_PRIORITY);
		if (priority != null) {
			name.setText(priority.getName());
			value.setText(priority.getValue());
		} else {
			newItem = true;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority, menu);
		return true;
	}

}
