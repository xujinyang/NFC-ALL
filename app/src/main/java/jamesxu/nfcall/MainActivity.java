package jamesxu.nfcall;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startOtherNfcActivity(View view) {
        startActivity(OtherNFCActivity.class);
    }

    public void startMyNfcActivity(View view) {
        startActivity(MyNFCActivity.class);
    }


}
