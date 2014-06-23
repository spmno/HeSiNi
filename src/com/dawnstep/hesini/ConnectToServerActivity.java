package com.dawnstep.hesini;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ConnectToServerActivity extends Activity {

	private WifiManager wifiManager;
	private List<ScanResult> wifiList;
	private List<String> passableHotsPot;
	private WifiReceiver wifiReceiver;
	private boolean isConnected=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect_to_server);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiReceiver = new WifiReceiver();
		registerReceiver(wifiReceiver,  new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
	}
	
	private final class WifiReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			wifiList = wifiManager.getScanResults();
			if (wifiList == null || wifiList.size() == 0 || isConnected)
				return;
			onReceiveNewNetworks(wifiList);
		}
	}
	
	public void onReceiveNewNetworks(List<ScanResult> wifiList){
		passableHotsPot = new ArrayList<String>();
		
		for(ScanResult result:wifiList){
			if((result.SSID).contains("HeSiNi"))
				passableHotsPot.add(result.SSID);
		}
		synchronized (this) {
			connectToHotpot();
		}
		if (isConnected) {
			Toast.makeText(getApplicationContext(), "连接成功",
				     Toast.LENGTH_SHORT).show();
			int ipaddress = wifiManager.getConnectionInfo().getIpAddress();
		} else {
			Toast.makeText(getApplicationContext(), "未找到主机",
				     Toast.LENGTH_SHORT).show();
		}
	}
	
	public void connectToHotpot(){
		if(passableHotsPot==null || passableHotsPot.size()==0)
			return;
		WifiConfiguration wifiConfig = this.setWifiParams(passableHotsPot.get(0));
		int wcgID = wifiManager.addNetwork(wifiConfig);
		boolean flag=wifiManager.enableNetwork(wcgID, true);
		isConnected=flag;
	}
	
	public WifiConfiguration setWifiParams(String ssid){
		WifiConfiguration apConfig=new WifiConfiguration();
		apConfig.SSID="\""+ssid+"\"";
		apConfig.preSharedKey="\"12345678\"";
		apConfig.hiddenSSID = true;
		apConfig.status = WifiConfiguration.Status.ENABLED;
		apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		return apConfig;
	}
	
	@Override
	protected void onDestroy() {
	super.onDestroy();
	 
		unregisterReceiver(wifiReceiver);
	}
	
}
