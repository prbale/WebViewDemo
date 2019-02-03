package mvcdemo.palmspeed.com.mvpdemoapp;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class JavaScriptInterface {
    private CalculateActivity parentActivity;
    private WebView mWebView;

    public JavaScriptInterface(CalculateActivity _activity, WebView _webView)  {
        parentActivity = _activity;
        mWebView = _webView;
    }

    @JavascriptInterface
    public void loadURL(String url) {
        final String u = url;
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(u);
            }
        });
    }

    @JavascriptInterface
    public void setResult(int val){
        this.parentActivity.javascriptCallFinished(val);
    }

    @JavascriptInterface
    public int getFirstNumber() {
        Toast.makeText(this.parentActivity, this.parentActivity.getFirstNumber(), Toast.LENGTH_LONG).show();
        return Integer.parseInt(this.parentActivity.getFirstNumber());
    }

    @JavascriptInterface
    public int getSecondNumber() {
        Toast.makeText(this.parentActivity, this.parentActivity.getSecondNumber(), Toast.LENGTH_LONG).show();
        return Integer.parseInt(this.parentActivity.getSecondNumber());
    }


    @JavascriptInterface
    public void calculate(int x, int y){
        this.parentActivity.changeText("Result is : " + (x * y));
    }
}