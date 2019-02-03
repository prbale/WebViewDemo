package mvcdemo.palmspeed.com.mvpdemoapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity {

    WebView myWebView;
    TextView myResultView;
    EditText num1;
    EditText num2;

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

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        Button btnSimple = (Button)this.findViewById(R.id.calculate);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate(Integer.parseInt(String.valueOf(num1.getText())), Integer.parseInt(String.valueOf(num2.getText())));
            }
        });

    }

    public void changeText(String someText){
        myWebView.loadUrl("javascript:document.getElementById('test1').innerHTML = '<strong>"+someText+"</strong>'");
    }

    public void calculate(int val1, int val2){
        myWebView.loadUrl("javascript:window.MyHandler.setResult( calculate("+val1+","+val2+") )");
    }

    public String getFirstNumber() {
        return num1.getText().toString();
    }

    public String getSecondNumber() {
        return num2.getText().toString();
    }

    public void javascriptCallFinished(final int val){
        Toast.makeText(this, "Callback got val: " + val, Toast.LENGTH_LONG).show();
        runOnUiThread(new Runnable() {
            public void run() {
                myResultView.setText(String.format(getString(R.string.result), val));
            }
        });
    }
}


