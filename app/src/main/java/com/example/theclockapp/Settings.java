//Omkar Kulkarni
//119225596
package com.example.theclockapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    //color array for saving the color preference
    String[] colors = { "Select a color","Red", "Black", "Green", "Blue", "Yellow" };
    //theme color array for saving the app theme preference
    String[] themeColor = { "Select a theme","Dark", "Light" };

    Spinner spnr1,spnr2,spnr3,spnr4;

    ImageButton saveBtn,clrBtn;

    String HColor="",MColor="",TColor="",SColor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spnr1 = (Spinner)findViewById(R.id.spinner);
        spnr2 = (Spinner)findViewById(R.id.spinner2);
        spnr3 = (Spinner)findViewById(R.id.spinner3);
        spnr4 = (Spinner)findViewById(R.id.spinner4);

        saveBtn = findViewById(R.id.save);
        clrBtn = findViewById(R.id.reset);

        //adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,colors);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnr1.setAdapter(adapter);
        //adapter for spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,colors);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnr2.setAdapter(adapter1);
        //adapter for spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,colors);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnr3.setAdapter(adapter2);
        //adapter for spinner
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,themeColor);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnr4.setAdapter(adapter3);

        //Spinner action to save the color pref
        spnr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem() == "Select a color"){

                }else{
                    HColor = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner action to save the color pref
        spnr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem() == "Select a color"){

                }else{
                    MColor = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner action to save the color pref
        spnr3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem() == "Select a color"){

                }else{
                    SColor = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner action to save the color pref
        spnr4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem() == "Select a theme"){

                }else{
                    TColor = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //save button action for saving the color preferences in SharedPreferences "Color Settings"
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("ColourSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                if (HColor != ""){
                    //saving hour hand color to Hcolor
                    editor.putString("Hcolour", HColor);


                }
                if (MColor != ""){
                    //saving min hand color to Mcolor
                    editor.putString("Mcolour", MColor);

                }
                if (SColor != ""){
                    //saving Sec hand color to Scolor
                    editor.putString("Scolour", SColor);

                }
                if (TColor != ""){
                    //saving theme color  to Tcolor
                    editor.putString("Tcolour", TColor);


                }
                Toast.makeText(Settings.this, "Your Clock is tweaked!!!", Toast.LENGTH_SHORT).show();
                editor.commit();
                Intent intent = new Intent( Settings.this,MainActivity.class );
                startActivity( intent );

            }
        });

        //button action to clear all the SharedPreferences
        clrBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Display toast when clearing the Shared Pref
                Toast.makeText(Settings.this, "Values are back to original", Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = getSharedPreferences("ColourSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent( Settings.this,MainActivity.class );
                startActivity( intent );


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( Color.parseColor( "#212121" ));
        }
    }
}

