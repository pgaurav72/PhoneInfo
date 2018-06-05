package com.gaurav.phoneinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.os.Build;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.glGetString;


/**
 * @author Gaurav
 */
public class Menu2 extends Fragment {

  private TextView availableProcessorTextView, archTextView, gpuVenderTextView, gpuRenderTextView, gpuVersionTextView, gpuExtensionTextView;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_menu_2, container, false);

    availableProcessorTextView = view.findViewById(R.id.avil_pro_text_view);
    archTextView = view.findViewById(R.id.architecture_text_view);
    gpuVenderTextView = view.findViewById(R.id.gpu_vender_text_view);
    gpuRenderTextView = view.findViewById(R.id.gpu_render_text_view);
    gpuVersionTextView = view.findViewById(R.id.gpu_version_text_view);
    gpuExtensionTextView = view.findViewById(R.id.gpu_extension_text_view);
    availableProcessorTextView.setText("Cores: "+String.valueOf(Runtime.getRuntime().availableProcessors()));
    archTextView.setText("Architecture: "+System.getProperty("os.arch"));
    GPUInfo();


    //returning our layout file
    //change R.layout.yourlayoutfilename for each of your fragments
    return view;
  }

  //------------------------------------------------------- GPU INFORMATION -----------------------------
  public void GPUInfo(){
    gpuVenderTextView.setText("GPU Vender: "+GLES10.glGetString( GLES10.GL_RENDERER));
    gpuRenderTextView.setText("GPU Render: "+GLES10.glGetString( GLES10.GL_VENDOR));
    gpuVersionTextView.setText("GPU Version: "+getGlVersion(getContext()));
    gpuExtensionTextView.setText("GPU Extension: "+ GLES10.glGetString(GLES10.GL_EXTENSIONS));
  }

  public static String getGlVersion(Context ctx) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
      ConfigurationInfo configurationInfo = am.getDeviceConfigurationInfo();
      return configurationInfo.getGlEsVersion();
    } else {
      return GLES10.glGetString(GLES10.GL_VERSION);
    }
  }
//-------------------------------------------------------------------------------------------------


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("SOC");
  }
}