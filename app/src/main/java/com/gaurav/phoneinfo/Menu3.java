package com.gaurav.phoneinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.provider.Telephony.ThreadsColumns.ERROR;


/**
 * @author Gaurav
 */
public class Menu3 extends Fragment {

  private TextView modelNameTextView, brandNameTextView, screenSizeTextView, screenResolutionTextView, screenDensityTextView,
  totalRAMTextView, avilRAMTextView, availInternalTextView, availExternalTextView, totalInternalTextView, totalExternalTextView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_menu_3, container, false);
    modelNameTextView = view.findViewById(R.id.model_name_text_view);
    brandNameTextView = view.findViewById(R.id.brand_name_text_view);
    screenSizeTextView = view.findViewById(R.id.screen_size_text_view);
    screenResolutionTextView = view.findViewById(R.id.screen_reso_text_view);
    screenDensityTextView = view.findViewById(R.id.density_text_view);
    totalRAMTextView = view.findViewById(R.id.total_ram_text_view);
    avilRAMTextView = view.findViewById(R.id.avail_ram_text_view);
    availInternalTextView = view.findViewById(R.id.avail_internal_text_view);
    availExternalTextView = view.findViewById(R.id.avail_external_text_view);
    totalInternalTextView = view.findViewById(R.id.total_internal_text_view);
    totalExternalTextView = view.findViewById(R.id.total_external_text_view);
    modelNameTextView.setText("Model Name: "+ Build.MODEL);
    brandNameTextView.setText("Brand Name: "+Build.MANUFACTURER);
    String screenSize = getScreenSize();
    screenSizeTextView.setText("Screen Size: "+screenSize+" inches");
    screenResolution();
    screenDensity();
    long totalRAM = totalRAM();
    String RAM = humanReadableByteCount(totalRAM,true);
    long avilableRAM = avilableRAM();
    totalRAMTextView.setText("Total RAM: "+RAM);
    avilRAMTextView.setText("Available RAM: "+String.valueOf(avilableRAM)+" MB");
    availInternalTextView.setText("Available Internal: "+getAvailableInternalMemorySize());
    availExternalTextView.setText("Available External: "+getAvailableExternalMemorySize());
    totalInternalTextView.setText("Total Internal: "+getTotalInternalMemorySize());
    totalExternalTextView.setText("Total External "+getTotalExternalMemorySize());
    return view;
  }


  public static boolean externalMemoryAvailable() {
    return android.os.Environment.getExternalStorageState().equals(
            android.os.Environment.MEDIA_MOUNTED);
  }

  public static String getTotalExternalMemorySize() {
    if (externalMemoryAvailable()) {
      File path = Environment.getExternalStorageDirectory();
      StatFs stat = new StatFs(path.getPath());
      long blockSize = stat.getBlockSizeLong();
      long totalBlocks = stat.getBlockCountLong();
      return formatSize(totalBlocks * blockSize);
    } else {
      return ERROR;
    }
  }

  public static String getAvailableExternalMemorySize() {
    if (externalMemoryAvailable()) {
      File path = Environment.getExternalStorageDirectory();
      StatFs stat = new StatFs(path.getPath());
      long blockSize = stat.getBlockSizeLong();
      long availableBlocks = stat.getAvailableBlocksLong();
      return formatSize(availableBlocks * blockSize);
    } else {
      return ERROR;
    }
  }

  public static String getTotalInternalMemorySize() {
    File path = Environment.getDataDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSizeLong();
    long totalBlocks = stat.getBlockCountLong();
    return formatSize(totalBlocks * blockSize);
  }

  public static String getAvailableInternalMemorySize() {
    File path = Environment.getDataDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSizeLong();
    long availableBlocks = stat.getAvailableBlocksLong();
    return formatSize(availableBlocks * blockSize);
  }


  public static String formatSize(long size) {
    String suffix = null;

    if (size >= 1024) {
      suffix = " KB";
      size /= 1024;
      if (size >= 1024) {
        suffix = " MB";
        size /= 1024;
      }
    }

    StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

    int commaOffset = resultBuffer.length() - 3;
    while (commaOffset > 0) {
      resultBuffer.insert(commaOffset, ',');
      commaOffset -= 3;
    }

    if (suffix != null) {
      resultBuffer.append(suffix);
    }
    return resultBuffer.toString();
  }

  //-------------------------------------To convert long to bytes ----------------------------------------------------------------
  public static String humanReadableByteCount(long bytes, boolean si) {
    int unit = si ? 1000 : 1024;
    if (bytes < unit) {
      return bytes + " B";
    }
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
  }
//------------------------------------------------------------------------------------------------------------------------------------

  public long avilableRAM(){
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    ActivityManager activityManager = (ActivityManager) (getContext()).getSystemService(ACTIVITY_SERVICE);
    activityManager.getMemoryInfo(mi);
    long availableMegs = mi.availMem / 0x100000L;
    return availableMegs;
  }


    public long totalRAM(){
    long totalMemory;
      ActivityManager actManager = (ActivityManager) ((Activity) getContext()).getSystemService(ACTIVITY_SERVICE);
      ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
      actManager.getMemoryInfo(memInfo);
       totalMemory = memInfo.totalMem;
      return totalMemory;


  }

  public void screenDensity(){
    DisplayMetrics dm = ((Activity) getContext()).getResources().getDisplayMetrics();
    int densityDpi = dm.densityDpi;
    screenDensityTextView.setText("Screen Density: "+String.valueOf(densityDpi)+" dpi");
  }

  public void screenResolution(){
    Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    int height = size.y;
    screenResolutionTextView.setText("Resolution: "+String.valueOf(width)+"*"+String.valueOf(height)+" pixels");
  }

  //----------------------------- To get screen size in inches --------------------------------------------------------
  public String getScreenSize(){
    DisplayMetrics dm = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
    int width=dm.widthPixels;
    int height=dm.heightPixels;
    double wi=(double)width/(double)dm.xdpi;
    double hi=(double)height/(double)dm.ydpi;
    double x = Math.pow(wi,2);
    double y = Math.pow(hi,2);
    double screenInches = Math.sqrt(x+y);
    double valueRounded = Math.round(screenInches * 100D) / 100D;
    String sizeInInches = String.valueOf(valueRounded);
    return sizeInInches;
  }

//-----------------------------------------------------------------------------------


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("DEVICE");
  }
}