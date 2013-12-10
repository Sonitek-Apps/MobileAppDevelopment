package com.example.tasklist;

import java.sql.SQLException;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

public class TaskActivity extends Activity {

	private static final String SAVE_EDIT_TASK = "SaveEditTask";
	private EditText title;
	private EditText description;
	private Spinner priority;
	private Button save;
	private Button cancel;
	private DatePicker datePicker;
	private ToDoDBHelper toDoDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		// Get DBHelper
		initViews();
		setListeners();

		toDoDbHelper = new ToDoDBHelper();
		toDoDbHelper.getHelper(this);
		// Check if edit or create
		if (savedInstanceState == null) {
			// updateViews();
		} else {
			// Create new task
		}
	}

	// Listener werden definiert
	private void setListeners() {
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				save();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancel();
			}
		});

		Integer[] prioArray = { 1, 2, 3 };
		// priority.setAdapter(new PrioSpinnerAdapter(this,
		// R.layout.prio_spinner_std, prioArray));
	}

	/**
	 * 
	 * @throws InvalidPrioException
	 *             wenn Prio nicht zwischen 1 und 3
	 */
	private void save() {

		String taskTitle;
		// Save title
		try {
			taskTitle = title.getText().toString();
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_title_empty), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		String taskDes;
		// Save description
		try {
			taskDes = description.getText().toString();
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_description_null),
					Toast.LENGTH_SHORT).show();
			return;
		}

		Category kategorie = new Category("TestCat");
		Priority prio = new Priority("Testprioname", 5);
		
		ToDoTask task = new ToDoTask(taskTitle, taskDes, prio, kategorie, datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());

		try {

			toDoDbHelper.insert(this, task);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e("SQL-Error", "Konnte Task nicht ablegen",e);
		}

		finish();
	}

	/*
	 * Wird nach DetailActivity abbrechen aufgerufen
	 */
	private void cancel() {
		setResult(RESULT_CANCELED);
		finish();
	}

	// Reference views
	private void initViews() {
		save = (Button) findViewById(R.id.detail_bt_save);
		cancel = (Button) findViewById(R.id.detail_bt_cancel);
		title = (EditText) findViewById(R.id.edit_title);
		description = (EditText) findViewById(R.id.edit_description);
		priority = (Spinner) findViewById(R.id.spinner_priority);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);

	}

	// Update views with new task
	private void updateViews(ToDoTask task) {
		// this.task = task;
		title.setText(task.getTitle());
		description.setText(task.getDescription());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.detail_menu, menu);
		return true;
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle item selection
	// switch (item.getItemId()) {
	// case R.id.menu_delete:
	// Intent intent = new Intent();
	// intent.putExtra(ToDoListActivity.RECIEVE_TASK, task);
	// // Go back to main activity
	// setResult(ToDoListActivity.RESULT_DELETE, intent);
	// finish();
	// break;
	//
	// default:
	// break;
	// }
	// return true;
	// }

}