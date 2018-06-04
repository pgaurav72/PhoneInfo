package com.gaurav.phoneinfo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * @author Gaurav
 */
public class Menu2 extends Fragment {

  private TextView availableProcessorTextView, archTextView, cpuFrquencyTextView;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_menu_2, container, false);

    availableProcessorTextView = view.findViewById(R.id.avil_pro_text_view);
    archTextView = view.findViewById(R.id.architecture_text_view);
    cpuFrquencyTextView = view.findViewById(R.id.cpufreq_text_view);
    availableProcessorTextView.setText("Cores: "+String.valueOf(Runtime.getRuntime().availableProcessors()));
    archTextView.setText("Architecture: "+System.getProperty("os.arch"));
    cpuFrequecy();

    //returning our layout file
    //change R.layout.yourlayoutfilename for each of your fragments
    return view;
  }

  public void cpuFrequecy(){
    //------------------------------------------ CPU Frequency ---------------------------------
    try {

      String cpuMaxFreq = "";
      RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
      cpuMaxFreq = reader.readLine();
      cpuFrquencyTextView.setText("Clock speed" + cpuMaxFreq);
      reader.close();

    }catch (Exception e){
      e.printStackTrace();
    }
    //-------------------------------------------------------------------------------------------------
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
    getActivity().setTitle("SOC");
  }
}