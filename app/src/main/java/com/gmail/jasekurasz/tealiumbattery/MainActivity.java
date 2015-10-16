package com.gmail.jasekurasz.tealiumbattery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    private TextView batteryLevel;
    private TextView batteryState;
    private TextView batteryConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevel = (TextView) findViewById(R.id.BatteryLevel);
        batteryState = (TextView) findViewById(R.id.ChargeState);
        batteryConnection = (TextView) findViewById(R.id.ChargeConnection);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, ifilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int chargeState = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            batteryLevel.setText(Integer.toString(level)+"%");

            switch (chargePlug) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    batteryConnection.setText(("via: AC Charger"));
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    batteryConnection.setText(("via: USB Port"));
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    batteryConnection.setText(("via: Wireless"));
                    break;
                default:
                    batteryConnection.setText((""));
            }

            switch (chargeState) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    batteryState.setText(("charging"));
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    batteryState.setText(("fully charged"));
                    break;
                default:
                    batteryState.setText(("not charging"));
            }
        }
    };

}
