package com.sanust.spermanalyzer;
import android.app.*;import android.os.*;import android.Manifest;
import android.content.*;import android.content.pm.*;import android.net.Uri;
import android.webkit.*;import android.view.*;
public class MainActivity extends Activity{
WebView w;ValueCallback<Uri[]> cb;static final int PICK=2001;
public void onCreate(Bundle b){super.onCreate(b);
w=new WebView(this);setContentView(w);
WebSettings s=w.getSettings();
s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);
s.setAllowFileAccess(true);s.setAllowContentAccess(true);
s.setMediaPlaybackRequiresUserGesture(false);
w.setWebViewClient(new WebViewClient());
w.setWebChromeClient(new WebChromeClient(){
public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> c,FileChooserParams p){
if(cb!=null)cb.onReceiveValue(null);cb=c;
Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);
i.addCategory(Intent.CATEGORY_OPENABLE);i.setType("video/*");
try{startActivityForResult(i,PICK);return true;}catch(Exception e){cb=null;return false;}}});
if(Build.VERSION.SDK_INT>=23&&checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
requestPermissions(new String[]{Manifest.permission.CAMERA},9);
w.loadUrl("file:///android_asset/index.html");}
protected void onActivityResult(int r,int result,Intent d){super.onActivityResult(r,result,d);
if(r==PICK&&cb!=null){Uri[]x=null;
if(result==RESULT_OK&&d!=null&&d.getData()!=null)x=new Uri[]{d.getData()};
cb.onReceiveValue(x);cb=null;}}}
