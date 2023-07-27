package com.parag.newapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.parag.newapp.R;
import com.parag.newapp.Utils.ProgressUtil;

public class WebViewActivity extends AppCompatActivity {

    public String url = "";
    WebView mWebView;
    ProgressUtil loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getStringExtra("url");

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loader = new ProgressUtil(this);
        loader.show();

        mWebView = findViewById(R.id.webView);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress > 80)
                    loader.dismiss();
            }
        });


//        mWebView.setWebChromeClient(new WebChromeClient() {
//            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//                callback.invoke(origin, true, false);
//            }
//
//            // For 3.0+ Devices (Start)
//            // onActivityResult attached before constructor
//            protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
//            }
//
//
//            // For Lollipop 5.0+ Devices
//            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//                if (uploadMessage != null) {
//                    uploadMessage.onReceiveValue(null);
//                    uploadMessage = null;
//                }
//
//                uploadMessage = filePathCallback;
//
//                Intent intent = fileChooserParams.createIntent();
//                try {
//                    startActivityForResult(intent, REQUEST_SELECT_FILE);
//                } catch (ActivityNotFoundException e) {
//                    uploadMessage = null;
//                    Toast.makeText(getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                return true;
//            }
//
//            //For Android 4.1 only
//            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                mUploadMessage = uploadMsg;
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
//            }
//
//            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
//            }
//        });

        mWebView.loadUrl(url);


        /*===================================Android Old Code without Product Uploading====================================*/
//        webView = (WebView) findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        webView.getSettings().setUserAgentString("CityBazarOnline");
////        webView.getSettings().setDomStorageEnabled(true);
////        webView.getSettings().setSupportZoom(false);
////        webView.getSettings().setAllowFileAccess(true);
////        webView.getSettings().setAllowContentAccess(true);
//
//
//
//        webView.setWebViewClient(new xWebViewClient());
//        webView.setWebChromeClient(new WebChromeClient() {
//            // For 3.0+ Devices (Start)
//            // onActivityResult attached before constructor
//            protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
////                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
//            }
//
//
//            // For Lollipop 5.0+ Devices
//            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//                if (uploadMessage != null) {
//                    uploadMessage.onReceiveValue(null);
//                    uploadMessage = null;
//                }
//
//                uploadMessage = filePathCallback;
//
//                Intent intent = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    intent = fileChooserParams.createIntent();
//                }
//                try {
//                    startActivityForResult(intent, REQUEST_SELECT_FILE);
//                } catch (ActivityNotFoundException e) {
//                    uploadMessage = null;
//                    return false;
//                }
//                return true;
//            }
//
//            //For Android 4.1 only
//            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                mUploadMessage = uploadMsg;
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true);
////                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
//            }
//
//            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
////                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true);
////                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
//            }
//
//            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//                callback.invoke(origin, true, false);
//            }
//        });
//
//
//        webView.loadUrl(url);

        /*===================================Android Old Code without Product Uploading====================================*/

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (requestCode == REQUEST_SELECT_FILE) {
//                if (uploadMessage == null)
//                    return;
//                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
//                uploadMessage = null;
//            }
//        } else if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (null == mUploadMessage)
//                return;
//            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
//            // Use RESULT_OK only if you're implementing WebView inside an Activity
//            Uri result = intent == null || resultCode != WebViewActivity.RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        } else
//            Toast.makeText(getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
//    }


    /*===================================Android Old Code without Product Uploading====================================*/
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (requestCode == REQUEST_SELECT_FILE) {
//                if (uploadMessage == null)
//                    return;
//                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
//                uploadMessage = null;
//            }
//        } else if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (null == mUploadMessage)
//                return;
//            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
//            // Use RESULT_OK only if you're implementing WebView inside an Activity
//            Uri result = intent == null || resultCode != WebViewActivity.RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        }
//
//        super.onActivityResult(requestCode, resultCode, intent);
//
//    }
    /*===================================Android Old Code without Product Uploading====================================*/

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            mWebView.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private class xWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }


}