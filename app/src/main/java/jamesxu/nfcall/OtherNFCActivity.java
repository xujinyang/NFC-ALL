package jamesxu.nfcall;

import android.os.Bundle;
import android.widget.TextView;

import jamesxu.nfcall.event.GetNFCCardEvent;

/**
 * Created by james on 9/10/15.
 */
public class OtherNFCActivity extends BaseActivity {

    private TextView cardTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        cardTextView = (TextView) findViewById(R.id.nfc_textview);
        ((TextView) findViewById(R.id.nfc_title)).setText("选择手机中其他应用处理NFC响应");
    }

    /**
     * 外部刷卡，获取用户
     * 延迟500
     *
     * @param event
     */
    public void onEventMainThread(final GetNFCCardEvent event) {
        cardTextView.setText(event.getCard());
    }

}
