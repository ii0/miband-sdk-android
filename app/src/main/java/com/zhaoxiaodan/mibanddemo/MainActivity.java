package com.zhaoxiaodan.mibanddemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoxiaodan.miband.ActionCallback;
import com.zhaoxiaodan.miband.MiBand;
import com.zhaoxiaodan.miband.NotifyListener;
import com.zhaoxiaodan.miband.RealtimeStepsNotifyListener;
import com.zhaoxiaodan.miband.model.BatteryInfo;
import com.zhaoxiaodan.miband.model.LedColor;
import com.zhaoxiaodan.miband.model.UserInfo;
import com.zhaoxiaodan.miband.model.VibrationMode;

public class MainActivity extends Activity
{
	private static final String	TAG		= "==[mibandtest]==";
	private MiBand				miband;

	private static final int Message_What_ShowLog = 1;

	private TextView logView ;

	private Handler handler = new Handler(Looper.getMainLooper()){
		public void handleMessage(Message m) {
			switch (m.what)
			{
				case Message_What_ShowLog:
					String text = (String)m.obj;
					logView.setText(text);
					break;
			}
		}
	};
	
	static final String[]		BUTTONS	= new String[] {
			"Connect",
			"setUserInfo",
			"pair",
			"read_rssi",
			"battery_info",
			"miband.startVibration(VibrationMode.VIBRATION_WITH_LED);",
			"miband.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);",
			"miband.startVibration(VibrationMode.VIBRATION_UNTIL_CALL_STOP);",
			"stopVibration",
			"setNormalNotifyListener",
			"setRealtimeStepsNotifyListener",
			"enableRealtimeStepsNotify",
			"disableRealtimeStepsNotify",
			"miband.setLedColor(LedColor.ORANGE);",
			"miband.setLedColor(LedColor.BLUE);",
			"miband.setLedColor(LedColor.RED);",
			"miband.setLedColor(LedColor.GREEN);",
			"selfTest",
			"setSensorDataNotifyListener",
			"enableSensorDataNotify",
			"disableSensorDataNotify",
										};


	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		this.logView = (TextView)findViewById(R.id.textView);

		miband = new MiBand(this);
		ListView lv = (ListView)findViewById(R.id.listView);
		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.item, BUTTONS));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				int menuIndex = 0;
				if (position == menuIndex++)
				{
					final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "努力运行中, 请稍后......");
					miband.connect(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							pd.dismiss();
							Log.d(TAG,
									"连接成功");
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							pd.dismiss();
							Log.d(TAG, "connect fail, code:" + errorCode + ",mgs:" + msg);
						}
					});
				}
				else if (position == menuIndex++)
				{
					UserInfo userInfo = new UserInfo(20111111, 1, 32, 180, 55, "胖梁", 0);
					Log.d(TAG, "setUserInfo:" + userInfo.toString() + ",data:" + Arrays.toString(userInfo.getBytes(miband.getDevice().getAddress())));
					miband.setUserInfo(userInfo);
				}
				else if (position == menuIndex++)
				{
					miband.pair(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							Log.d(TAG, "pair succ");
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "pair fail");
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.readRssi(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							Log.d(TAG, "rssi:" + (int) data);
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "readRssi fail");
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.getBatteryInfo(new ActionCallback() {
						
						@Override
						public void onSuccess(Object data)
						{
							BatteryInfo info = (BatteryInfo) data;
							Log.d(TAG, info.toString());
						}
						
						@Override
						public void onFail(int errorCode, String msg)
						{
							Log.d(TAG, "getBatteryInfo fail");
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.startVibration(VibrationMode.VIBRATION_WITH_LED);
				}
				else if (position == menuIndex++)
				{
					miband.startVibration(VibrationMode.VIBRATION_WITHOUT_LED);
				}
				else if (position == menuIndex++)
				{
					miband.startVibration(VibrationMode.VIBRATION_UNTIL_CALL_STOP);
				}
				else if (position == menuIndex++)
				{
					miband.stopVibration();
				}
				else if (position == menuIndex++)
				{
					miband.setNormalNotifyListener(new NotifyListener() {
						
						@Override
						public void onNotify(byte[] data)
						{
							Log.d(TAG, "NormalNotifyListener:" + Arrays.toString(data));
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.setRealtimeStepsNotifyListener(new RealtimeStepsNotifyListener() {
						
						@Override
						public void onNotify(int steps)
						{
							Log.d(TAG, "RealtimeStepsNotifyListener:" + steps);
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.enableRealtimeStepsNotify();
				}
				else if (position == menuIndex++)
				{
					miband.disableRealtimeStepsNotify();
				}
				else if (position == menuIndex++)
				{
					miband.setLedColor(LedColor.ORANGE);
				}
				else if (position == menuIndex++)
				{
					miband.setLedColor(LedColor.BLUE);
				}
				else if (position == menuIndex++)
				{
					miband.setLedColor(LedColor.RED);
				}
				else if (position == menuIndex++)
				{
					miband.setLedColor(LedColor.GREEN);
				}
				else if (position == menuIndex++)
				{
					miband.selfTest();
				} else if (position == menuIndex++)
				{
					miband.setSensorDataNotifyListener(new NotifyListener()
					{
						@Override
						public void onNotify(byte[] data)
						{
							ByteBuffer byteBuffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
							int i = 0;

							int index = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;
							int d1 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;
							int d2 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;
							int d3 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;

							Message m = new Message();
							m.what = Message_What_ShowLog;
							m.obj = index + "," + d1 + "," + d2 + "," + d3;

							handler.sendMessage(m);
						}
					});
				}
				else if (position == menuIndex++)
				{
					miband.enableSensorDataNotify();
				}
				else if (position == menuIndex++)
				{
					miband.disableSensorDataNotify();
				}
			}
		});
		
	}
}
