package com.example.maurer.sensorstream.Frontend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.maurer.sensorstream.R;
import com.example.maurer.sensorstream.Temperature_stream;

import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Temperatur extends AppCompatActivity {
    public static List<Float> f;
    XYPlot plot;

    public Temperatur() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datenanzeigengeklickt);


        plotting((LinkedList) f);
    }

    public void plotting(LinkedList f) {
        plot = (XYPlot) findViewById(R.id.plot);
        plot.setTitle("Temperatur");
        plot.setDomainLabel("Zeit");
        plot.setRangeLabel("°C");
        List series1Numbers = new LinkedList();

        //Anfangsvariable berechnen: Durchschnittswert von bisherigen Daten
        /*float nr;
        float ges = (float) 0.0;
        for (int i=0;i<f.size();i++)
            ges += (float) f.get(i);
        nr = (ges/f.size());//*/

        series1Numbers.add(0); //Start bei 0°C (Anfangswert)

        for (int i=0;i<f.size();i++){
            Log.i("size",f.get(i)+"");
            series1Numbers.add(f.get(i));
        }
        XYSeries series1 = new SimpleXYSeries(
                series1Numbers, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Temperatur [°C]");
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);

        series1Format.getLinePaint().setPathEffect(new DashPathEffect(new float[]{
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)}, 0));
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1, series1Format);
        Log.i("Temperature", "updated graph");
    }

}