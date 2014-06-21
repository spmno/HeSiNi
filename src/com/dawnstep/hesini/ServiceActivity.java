package com.dawnstep.hesini;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class ServiceActivity extends Activity {

	private WifiManager wifiManager; 
	private TextView stateInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);
		stateInfo = (TextView)findViewById(R.id.serverStateInfo);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiSettingTask settingTask = new WifiSettingTask();
		settingTask.execute(1);
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
    public class WifiSettingTask extends AsyncTask<Integer, Integer, String> {

    	@Override
    	protected String doInBackground(Integer... arg0) {
    		// TODO Auto-generated method stub
    		if (setWifiApEnabled(true)) {
    			return "�����ɹ�";
    		} else {
    			return "����ʧ��";
    		}
    	}
    	
    	@Override
        protected void onPostExecute(String result) {
            // ����HTMLҳ�������
    		stateInfo.setText(result);
        }
    }
}


