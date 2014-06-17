package com.dawnstep.hesini;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ServiceActivity extends Activity {

	private WifiManager wifiManager; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		setWifiApEnabled(true);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_service,
					container, false);
			return rootView;
		}
	}

	 // wifi�ȵ㿪��  
    public boolean setWifiApEnabled(boolean enabled) {  
        if (enabled) { // disable WiFi in any case  
            //wifi���ȵ㲻��ͬʱ�򿪣����Դ��ȵ��ʱ����Ҫ�ر�wifi  
            wifiManager.setWifiEnabled(false);  
        }  
        try {  
            //�ȵ��������  
            WifiConfiguration apConfig = new WifiConfiguration();  
            //�����ȵ������(���������ֺ���ӵ������ʲô��)  
            apConfig.SSID = "HeSiNi";  
            //�����ȵ������  
            apConfig.preSharedKey="12345678";  
                //ͨ��������������ȵ�  
            Method method = wifiManager.getClass().getMethod(  
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);  
            //�����ȵ��״̬  
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);  
        } catch (Exception e) {  
            return false;  
        }  
    }
}
