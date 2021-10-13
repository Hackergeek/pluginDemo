package jianqiang.com.plugin1;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BasePluginActivity {

    private static final String TAG = "Client-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        that.setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
    }
}