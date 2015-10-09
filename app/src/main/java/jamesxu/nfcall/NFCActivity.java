package jamesxu.nfcall;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import jamesxu.nfcall.event.GetNFCCardEvent;


/**
 * NFC响应页面
 *
 * @author Jinyang
 */
@SuppressLint("NewApi")

public class NFCActivity extends BaseActivity {

    public static final String TAG = "NFCActivity";
    private NfcAdapter nfcAdapter;

    private String mClientCardNfcId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNfcParse();
        resolveIntent(getIntent());
    }

    private void initNfcParse() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.i(TAG, "---->Nfc error ！！！");
            Toast.makeText(getApplicationContext(), "不支持NFC功能！", Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isEnabled()) {
            Log.i(TAG, "---->Nfc close ！！！");
            Toast.makeText(getApplicationContext(), "请打开NFC功能！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resolveIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "---->onDestroy");
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                showWirelessSettingsDialog();
            }
        }
    }

    private void showWirelessSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("不支持此卡");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
        return;
    }


    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String nfcId = dumpTagData(tag);
            if (!nfcId.isEmpty()) {
                mClientCardNfcId = nfcId;
                Log.i(TAG, "卡的内容" + mClientCardNfcId);
                eventBus.post(new GetNFCCardEvent(mClientCardNfcId));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "识别失败！请重新刷卡！", Toast.LENGTH_LONG).show();
            }

        }
    }


    private String dumpTagData(Parcelable p) {
        Tag tag = (Tag) p;
        byte[] id = tag.getId();
        return hexToHexString(id);
    }


    /**
     * @param b
     * @return
     */
    public static String hexToHexString(byte[] b) {
        int len = b.length;
        int[] x = new int[len];
        String[] y = new String[len];
        StringBuilder str = new StringBuilder();
        int j = 0;
        for (; j < len; j++) {
            x[j] = b[j] & 0xff;
            y[j] = Integer.toHexString(x[j]);
            while (y[j].length() < 2) {
                y[j] = "0" + y[j];
            }
            str.append(y[j]);
            str.append("");
        }
        return new String(str).toUpperCase(Locale.getDefault());
    }

}
