package jianqiang.com.plugin1;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.jianqiang.mypluginlibrary.AppConstants;

public class MainActivity extends BasePluginActivity {

    private static final String TAG = "Client-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //startActivity，插件内跳转
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppConstants.PROXY_VIEW_ACTION);
                intent.putExtra(AppConstants.EXTRA_DEX_PATH, dexPath);
                intent.putExtra(AppConstants.EXTRA_CLASS, "jianqiang.com.plugin1.SecondActivity");
                startActivity(intent);
            }
        });
    }
}