package com.example.contactv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.TextView;
import java.util.Date;

public class call_history extends AppCompatActivity {
    TextView textView = null;
    String[] samen, samep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        textView = (TextView) findViewById(R.id.textview_call);
        Bundle bundle = this.getIntent().getExtras();
        samen = bundle.getStringArray("samename");
        samep = bundle.getStringArray("samephone");
        getCallDetails();
    }
    private void getCallDetails() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CALL_LOG"},  REQUEST_CODE_ASK_PERMISSIONS);
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_CALL_LOG"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        StringBuffer sb = new StringBuffer();
        String order = CallLog.Calls.DEFAULT_SORT_ORDER;
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, order);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
                default:
                    dir = "UNKNOW";
                    break;
            }
            String a = phNumber;
            for(int j = 0; j<samep.length; j++) {
                if (phNumber.compareTo(samep[j]) == 0) {
                    a = samen[j];
                    break;
                }
            }
            sb.append("\nCONTACT: " + a + " \nCall Type: " + dir + " \nCall Date: " + callDayTime + " \nCall duration in sec : " + callDuration);
            sb.append("\n----------------------------------");
        }
        textView.setText(sb);
    }
}