package com.example.mada_ueb05;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AlarmActivity extends Activity {

	private static final int ALARMTIME = 300000;
	private static final int MILLIS = 60000;
	private static final long[] vibration_theme = {0,500,110,500,110,450,110,200,110,170,40,450,110,200,110,170,40,500};
	
	private SharedPreferences prefs;
	private Button off;
	private Button snooze;
	private MediaPlayer player;
	private Vibrator vibrator;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_alarm);

		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		referenceViews();
		setListeners();
		
		player = MediaPlayer.create(this, R.raw.alarm);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		unlockScreen();
		startAlarm();
		
		AlarmNotify.showNotification(this);
	}

	private void referenceViews() {
		off = (Button) findViewById(R.id.button_off);
		snooze = (Button) findViewById(R.id.button_snooze);
	}
	
	private void setListeners() {
		off.setOnClickListener(offListener);
		snooze.setOnClickListener(snoozeListener);
	}
	
	// Plays the alarm sound
	private void startAlarm() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				player.setLooping(true);
				if (checkVibration()) vibrator.vibrate(vibration_theme,0);
				player.start();
				SystemClock.sleep(ALARMTIME);
				vibrator.cancel();
				player.stop();
			}
		}).start();
	}

	// Stops the alarm sound
	private void stopAlarm() {
		vibrator.cancel();
		player.stop();
		prefs.edit().putBoolean(ConfAlarm.KEY_ALARM, false).commit();
		Toast.makeText(this, R.string.msg_alarm_off, Toast.LENGTH_SHORT).show();
		SystemClock.sleep(500);
		this.finish();
	}

	// Set new alarm and stop current alarm sound
	private void snoozeAlarm() {
		vibrator.cancel();
		player.stop();
		int snoozetime = Integer.parseInt(prefs.getString(SettingsActivity.SNOOZE_TIME, SettingsActivity.DEFAULT_SNOOZE_TIME));
		if (snoozetime < 1) snoozetime = 1;
		
		// Set new alarm
		AlarmManager mng = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent alarmIntent = new Intent(this, AlarmActivity.class);
		PendingIntent pAlarmIntent = PendingIntent.getActivity(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Convert snoozetime from minutes to milliseconds
		mng.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + snoozetime*MILLIS, pAlarmIntent);
		// Update shared preferences
		updatePrefsDate(snoozetime);
		
		String message = getString(R.string.msg_alarm_snooze_part1) + snoozetime + getString(R.string.msg_alarm_snooze_part2);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		
		SystemClock.sleep(500);
		this.finish();
	}
	
	// Update new alarm time and date after snooze
	@SuppressWarnings("deprecation")
	private void updatePrefsDate(int snoozetime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis() + snoozetime*MILLIS);
		Date date = calendar.getTime();
		prefs.edit().putInt(ConfAlarm.KEY_YEAR, date.getYear()+1900).commit();
		prefs.edit().putInt(ConfAlarm.KEY_MONTH, date.getMonth()).commit();
		prefs.edit().putInt(ConfAlarm.KEY_DAY, date.getDay()+1).commit();
		prefs.edit().putInt(ConfAlarm.KEY_HOUR, date.getHours()).commit();
		prefs.edit().putInt(ConfAlarm.KEY_MINUTE, date.getMinutes()).commit();
	}
	
	// Unlock and activate screen
	@SuppressWarnings("deprecation")
	private void unlockScreen() {
		PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | 
        											 PowerManager.ACQUIRE_CAUSES_WAKEUP | 
        											 PowerManager.ON_AFTER_RELEASE, "INFO");
        wakeLock.acquire();
        wakeLock.release();
	}
	
	private Boolean checkVibration() {
		Boolean vibrate = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
				.getBoolean(SettingsActivity.VIBRATE, SettingsActivity.DEFAULT_VIBRATE);
		return vibrate;
	}
	
	private OnClickListener offListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			stopAlarm();
		}
	};

	private OnClickListener snoozeListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			snoozeAlarm();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		// Do nothing
	}

}
