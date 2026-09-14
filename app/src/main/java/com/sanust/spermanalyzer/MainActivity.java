package com.sanust.spermanalyzer;
import android.app.*;import android.os.*;import android.Manifest;
import android.content.*;import android.content.pm.*;import android.net.Uri;
import android.webkit.*;import android.provider.MediaStore;
public class MainActivity extends Activity{
WebView w;ValueCallback<Uri[]> cb;static final int PICK=2001;static final int CAM=2002;
public void onCreate(Bundle b){super.onCreate(b);
w=new WebView(this);setContentView(w);
WebSettings s=w.getSettings();
s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);
s.setAllowFileAccess(true);s.setAllowContentAccess(true);
s.setMediaPlaybackRequiresUserGesture(false);
w.setWebViewClient(new WebViewClient());
w.addJavascriptInterface(new Object(){
@android.webkit.JavascriptInterface
public void openGallery(){
Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
i.setType("video/*");
try{startActivityForResult(i,PICK);}catch(Exception e){}}
@android.webkit.JavascriptInterface
public void openCamera(){
Intent i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
i.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);
try{startActivityForResult(i,CAM);}catch(Exception e){}}
},"Android");
w.setWebChromeClient(new WebChromeClient(){
public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> c,FileChooserParams p){
if(cb!=null)cb.onReceiveValue(null);cb=c;
Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
i.setType("video/*");
try{startActivityForResult(i,PICK);return true;}catch(Exception e){cb=null;return false;}}});
if(Build.VERSION.SDK_INT>=23){
String[]perms={Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_MEDIA_VIDEO};
requestPermissions(perms,9);}
w.loadUrl("file:///android_asset/index.html");}
protected void onActivityResult(int r,int result,Intent d){super.onActivityResult(r,result,d);
Uri uri=null;
if(result==RESULT_OK&&d!=null&&d.getData()!=null)uri=d.getData();
if(r==PICK&&cb!=null){cb.onReceiveValue(uri!=null?new Uri[]{uri}:null);cb=null;}
else if(r==CAM&&cb!=null){cb.onReceiveValue(uri!=null?new Uri[]{uri}:null);cb=null;}}}
