package com.example.monic.areacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    double length1, length2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = (ImageView) findViewById(R.id.triangle);
        final ImageView image1 = (ImageView) findViewById(R.id.square);
        final ImageView image2 = (ImageView) findViewById(R.id.circle);

        final TextView t1 = (TextView) findViewById(R.id.textView3);
        final TextView t2 = (TextView) findViewById(R.id.edit1);
        final TextView t3 = (TextView) findViewById(R.id.edit2);
        final TextView t4 = (TextView) findViewById(R.id.edit3);
        final TextView t5 = (TextView) findViewById(R.id.textview1);
        final TextView t6 = (TextView) findViewById(R.id.textView2);

        final Button btn1 = (Button) findViewById(R.id.button1);
        final Button btn2 = (Button) findViewById(R.id.button2);

        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t6.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t1.setText("Triangle");
                btn1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            length1 = Double.parseDouble(t2.getText().toString());
                            length2 = Double.parseDouble(t3.getText().toString());
                            if (length1 >= 0.0 && length2 >= 0.0) {
                                double area;
                                area = (0.5) * length1 * length2;
                                t4.setText(Double.toString(area));
                            } else {
                                Log.d("demo", "enter valid length");
                            }
                        } catch (Exception e) {
                            Log.d("demo", "enter valid input");
                        }
                    }
                });
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t1.setText("Square");
                t6.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            length1 = Double.parseDouble(t2.getText().toString());
                            if (length1 >= 0) {
                                double area;
                                area = length1 * length1;
                                t4.setText(Double.toString(area));
                            } else {
                                Log.d("demo", "enter valid length");
                            }
                        } catch (Exception e) {
                            Log.d("demo", "enter Valid input");
                        }
                    }
                });
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t6.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                t1.setText("Circle");
                btn1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            length1 = Double.parseDouble(t2.getText().toString());
                            if (length1 >= 0) {
                                double area;
                                area = (3.14) * length1 * length1;
                                t4.setText(Double.toString(area));
                            } else {
                                Log.d("demo", "enter Valid input");
                            }
                        } catch (Exception e) {
                            Log.d("demo", "enter Valid input");
                        }

                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t2.setText("");
                t3.setText("");
                t1.setText("Select a Shape");
                t4.setText("");
                t6.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
            }
        });

    }
}

