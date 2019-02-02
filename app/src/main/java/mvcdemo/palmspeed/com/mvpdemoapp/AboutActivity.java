package mvcdemo.palmspeed.com.mvpdemoapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Reference link: http://hongchaozhang.github.io/GitBlogs/code/2015/07/28/Communication-between-WebView-and-native-android.html
 */

public class AboutActivity extends AppCompatActivity {

    WebView myWebView;
    TextView myResultView;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        myResultView = (TextView)this.findViewById(R.id.myResult);
        myWebView = (WebView)this.findViewById(R.id.myWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/index.html");
        myWebView.addJavascriptInterface(new JavaScriptInterface(this, myWebView), "MyHandler");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        Button btnSimple = (Button)this.findViewById(R.id.btnSimple);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeText("Gooooood Mooorning!");
            }
        });

        Button btnSet = (Button)this.findViewById(R.id.btnCalc);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callJavaScriptFunctionAndGetResultBack(333, 444);
            }
        });

    }

    public void changeText(String someText){
        myWebView.loadUrl("javascript:document.getElementById('test1').innerHTML = '<strong>"+someText+"</strong>'");
    }

    public void callJavaScriptFunctionAndGetResultBack(int val1, int val2){
        myWebView.loadUrl("javascript:window.MyHandler.setResult( addSomething("+val1+","+val2+") )");
    }

    public void javascriptCallFinished(final int val){
        Toast.makeText(this, "Callback got val: " + val, Toast.LENGTH_LONG).show();

        // I need to run set operation of UI on the main thread.
        // therefore, the above parameter "val" must be final
        runOnUiThread(new Runnable() {
            public void run() {
                myResultView.setText(String.format(getString(R.string.result), val));
            }
        });
    }
}


