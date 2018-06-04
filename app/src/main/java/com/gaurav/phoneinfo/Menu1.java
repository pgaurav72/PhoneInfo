package com.gaurav.phoneinfo;

import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author Gaurav
 */
public class Menu1 extends Fragment {

  private TextView deviceNameTextView, osVersionTextView, apiLevelTextView, securityPathTextView, buildIdTextView,
          javaVMTextView, openGlESTextView, kernalArchTextView, rootAccessTextView;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_menu_1, container, false);
    deviceNameTextView = view.findViewById(R.id.device_name_text_view);
    osVersionTextView = view.findViewById(R.id.os_version_text_view);
    apiLevelTextView = view.findViewById(R.id.api_level_text_view);
    securityPathTextView = view.findViewById(R.id.security_patch_level_text_view);
    buildIdTextView = view.findViewById(R.id.build_id_text_view);
    javaVMTextView = view.findViewById(R.id.java_vm_text_view);
    openGlESTextView = view.findViewById(R.id.opengl_es_text_view);
    kernalArchTextView = view.findViewById(R.id.kernal_arch_text_view);
    rootAccessTextView = view.findViewById(R.id.root_access_text_view);
    String returnValue = getDeviceName();
    deviceNameTextView.setText(returnValue);
    osVersionTextView.setText("OS Version: "+Build.VERSION.RELEASE);
    apiLevelTextView.setText("API Level: "+String.valueOf(android.os.Build.VERSION.SDK_INT));
    securityPathTextView.setText("Security Patch Level: "+android.os.Build.VERSION.SECURITY_PATCH);
    buildIdTextView.setText("Build Id: "+Build.ID);
    javaVMTextView.setText("Java VM: "+System.getProperty("java.vm.name"));
    openGlESTextView.setText("OpenGL ES: "+ GLES20.glGetString(GLES20.GL_RENDERER));
    kernalArchTextView.setText("Kernal architecture: "+System.getProperty("os.arch"));
    Boolean rootAccess = checkRooted();
    if (rootAccess) {
      rootAccessTextView.setText("Root Access: Yes");
    }else if (!rootAccess){
      rootAccessTextView.setText("Root Access: No");
    }


    //returning our layout file
    //change R.layout.yourlayoutfilename for each of your fragments
    return view;
  }

  //------------------------------------------Check root access ---------------------------------


  public static boolean checkRooted()
  {
    try
    {
      Process p = Runtime.getRuntime().exec("su", null, new File("/"));
      DataOutputStream os = new DataOutputStream( p.getOutputStream());
      os.writeBytes("pwd\n");
      os.writeBytes("exit\n");
      os.flush();
      p.waitFor();
      p.destroy();
    }
    catch (Exception e)
    {
      return false;
    }

    return true;
  }

//------------------------------------------------------------------------------------------

  // executes a command on the system
  private static boolean canExecuteCommand(String command) {
    boolean executedSuccesfully;
    try {
      Runtime.getRuntime().exec(command);
      executedSuccesfully = true;
    } catch (Exception e) {
      executedSuccesfully = false;
    }

    return executedSuccesfully;
  }
  //------------------------------------------------------------------------------


  public static String getDeviceName() {
    String manufacturer = Build.MANUFACTURER;
    String model = Build.MODEL;
    if (model.startsWith(manufacturer)) {
      return capitalize(model);
    }
    return capitalize(manufacturer) + " " + model;
  }

  private static String capitalize(String str) {
    if (TextUtils.isEmpty(str)) {
      return str;
    }
    char[] arr = str.toCharArray();
    boolean capitalizeNext = true;

    StringBuilder phrase = new StringBuilder();
    for (char c : arr) {
      if (capitalizeNext && Character.isLetter(c)) {
        phrase.append(Character.toUpperCase(c));
        capitalizeNext = false;
        continue;
      } else if (Character.isWhitespace(c)) {
        capitalizeNext = true;
      }
      phrase.append(c);
    }

    return phrase.toString();
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("SYSTEM");
  }
}