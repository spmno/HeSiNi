package com.dawnstep.hesini;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity {

	private Button serviceButton;
	private Button connectServiceButton;
	
	private OnClickListener serviceButtonListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ServiceActivity.class);
			startActivity(intent);
		}
		
	};
	
	private OnClickListener connectButtonListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ConnectToServerActivity.class);
			startActivity(intent);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		serviceButton = (Button)findViewById(R.id.beAServer);
		connectServiceButton = (Button)findViewById(R.id.connectToServer);
		serviceButton.setOnClickListener(serviceButtonListener);
		connectServiceButton.setOnClickListener(connectButtonListener);
	}


}
