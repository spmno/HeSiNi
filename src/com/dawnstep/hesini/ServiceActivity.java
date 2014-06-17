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

	 // wifi热点开关  
    public boolean setWifiApEnabled(boolean enabled) {  
        if (enabled) { // disable WiFi in any case  
            //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi  
            wifiManager.setWifiEnabled(false);  
        }  
        try {  
            //热点的配置类  
            WifiConfiguration apConfig = new WifiConfiguration();  
            //配置热点的名称(可以在名字后面加点随机数什么的)  
            apConfig.SSID = "HeSiNi";  
            //配置热点的密码  
            apConfig.preSharedKey="12345678";  
                //通过反射调用设置热点  
            Method method = wifiManager.getClass().getMethod(  
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);  
            //返回热点打开状态  
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);  
        } catch (Exception e) {  
            return false;  
        }  
    }
}
