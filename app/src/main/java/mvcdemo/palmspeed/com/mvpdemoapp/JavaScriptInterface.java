package mvcdemo.palmspeed.com.mvpdemoapp;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JavaScriptInterface {
    private AboutActivity parentActivity;
    private WebView mWebView;

    public JavaScriptInterface(AboutActivity _activity, WebView _webView)  {
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
    public void calcSomething(int x, int y){
        this.parentActivity.changeText("Result is : " + (x * y));
    }
}