package com.gaurav.phoneinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * @author Gaurav
 */
public class Menu3 extends Fragment {

  private TextView modelNameTextView, brandNameTextView, screenSizeTextView, screenResolutionTextView, screenDensityTextView,
  totalRAMTextView, avilRAMTextView;

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
    modelNameTextView.setText("Model Name: "+ Build.MODEL);
    brandNameTextView.setText("Brand Name: "+Build.MANUFACTURER);
    String screenSize = getScreenSize();
    screenSizeTextView.setText("Screen Size: "+screenSize+" inches");
    screenResolution();
    screenDensity();
    long totalRAM = totalRAM();
    totalRAMTextView.setText("Total RAM: "+String.valueOf(totalRAM()));
    avilRAMTextView.setText("Available RAM: "+Runtime.getRuntime().freeMemory());
    return view;
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
    screenResolutionTextView.setText("Screen Resolution: "+String.valueOf(width)+"*"+String.valueOf(height)+" pixels");
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