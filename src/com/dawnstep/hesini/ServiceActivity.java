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
    public class WifiSettingTask extends AsyncTask<Integer, Integer, String> {

    	@Override
    	protected String doInBackground(Integer... arg0) {
    		// TODO Auto-generated method stub
    		if (setWifiApEnabled(true)) {
    			return "建立成功";
    		} else {
    			return "建立失败";
    		}
    	}
    	
    	@Override
        protected void onPostExecute(String result) {
            // 返回HTML页面的内容
    		stateInfo.setText(result);
        }
    }
}


