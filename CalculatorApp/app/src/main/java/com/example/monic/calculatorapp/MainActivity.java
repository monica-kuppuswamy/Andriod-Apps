package com.example.monic.calculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    double temp1, temp2;
    boolean operatorEnabled = false;
    char operator = '#';
    boolean resultdot=false;
    boolean resultafter=false;
    boolean infinityset=false;
    boolean operatordot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView display = (TextView) findViewById(R.id.edit1);
        final Button buttonAc = (Button) findViewById(R.id.buttonAC);
        final Button buttonDiv = (Button) findViewById(R.id.buttonDIV);
        final Button buttonMul = (Button) findViewById(R.id.buttonMUL);
        final Button buttonSub = (Button) findViewById(R.id.buttonSUB);
        final Button buttonAdd = (Button) findViewById(R.id.buttonADD);
        final Button buttonEqual = (Button) findViewById(R.id.buttonEQUAL);
        final Button buttonDot = (Button) findViewById(R.id.buttonDOT);

        final Button button01 = (Button) findViewById(R.id.button1);
        final Button button02 = (Button) findViewById(R.id.button2);
        final Button button03 = (Button) findViewById(R.id.button3);
        final Button button04 = (Button) findViewById(R.id.button4);
        final Button button05 = (Button) findViewById(R.id.button5);
        final Button button06 = (Button) findViewById(R.id.button6);
        final Button button07 = (Button) findViewById(R.id.button7);
        final Button button08 = (Button) findViewById(R.id.button8);
        final Button button09 = (Button) findViewById(R.id.button9);
        final Button button00 = (Button) findViewById(R.id.button0);
        try {
            button01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("1");
                        display.setText(number);
                        infinityset = false;
                    }

                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }

                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("1");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("1");
                            display.setText(number);
                        }
                    }
                }
            });

            button02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("2");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("2");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("2");
                            display.setText(number);
                        }
                    }
                }
            });

            button03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("3");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("3");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("3");
                            display.setText(number);
                        }
                    }
                }
            });

            button04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("4");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("4");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("4");
                            display.setText(number);
                        }
                    }
                }
            });

            button05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("5");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("5");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("5");
                            display.setText(number);
                        }
                    }
                }
            });

            button06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("6");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("6");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("6");
                            display.setText(number);
                        }
                    }
                }
            });

            button07.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("7");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("7");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("7");
                            display.setText(number);
                        }
                    }
                }
            });

            button08.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("8");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("8");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("8");
                            display.setText(number);
                        }
                    }
                }
            });

            button09.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("9");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("9");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("9");
                            display.setText(number);
                        }
                    }
                }
            });

            button00.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("0");
                        display.setText(number);
                        infinityset = false;
                    }
                    else {
                        if (operatorEnabled) {
                            display.setText(" ");
                            operatorEnabled = false;
                        }
                        if (resultafter) {
                            display.setText(" ");
                            resultafter = false;
                            resultdot = false;
                        }
                        StringBuilder number = new StringBuilder();
                        if (display.getText().toString().equals("0")) {
                            display.setText(" ");
                            number.append("0");
                            display.setText(number);
                        } else {
                            number.append(display.getText());
                            number.append("0");
                            display.setText(number);
                        }
                    }
                }
            });

            buttonDot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infinityset && operatorEnabled)
                    {
                        display.setText("Error");
                    }
                    else if(infinityset && !operatorEnabled)
                    {
                        StringBuilder number = new StringBuilder();

                        display.setText(" ");
                        number.append("0.");
                        display.setText(number);
                        infinityset = false;
                    }
                    else if(display.getText().toString().contains("."))
                    {

                    }
                    else {
                        if (resultdot) {
                            display.setText("0.");
                            resultdot = false;
                        }
//                        else if (operatordot){
//                            display.setText("0.");
//                            operatordot = false;
//
//                        }
                        else
                        {
                            StringBuilder number = new StringBuilder();
                            number.append(display.getText());
                            number.append(".");
                            display.setText(number);
                        }
                    }
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(display.getText().toString().equals(""))
                    {
                        display.setText("0");
                    }
                    if(infinityset)
                    {
                        display.setText("Error");
                        operatorEnabled=true;
                    }
                    else {
                        operatorEnabled = true;
                        operatordot=true;
                        operator = '+';
                        temp1 = Double.parseDouble(display.getText().toString());
                    }
                }
            });

            buttonSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(display.getText().toString().equals(""))
                    {
                        display.setText("0");
                    }

                    if(infinityset)
                    {
                        display.setText("Error");
                        operatorEnabled=true;
                    }
                    else {
                        operatorEnabled = true;
                        operatordot=true;
                        operator = '-';
                        temp1 = Double.parseDouble(display.getText().toString());
                    }
                }
            });

            buttonMul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(display.getText().toString().equals(""))
                    {
                        display.setText("0");
                    }
                    if(infinityset)
                    {
                        display.setText("Error");
                        operatorEnabled=true;
                    }
                    else {
                        operatorEnabled = true;
                        operatordot=true;
                        operator = '*';
                        temp1 = Double.parseDouble(display.getText().toString());
                    }
                }
            });

            buttonDiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(display.getText().toString().equals(""))
                    {
                        display.setText("0");
                    }
                    if(infinityset)
                    {
                        display.setText("Error");
                        operatorEnabled=true;
                    }
                    else {
                        operatorEnabled = true;
                        operatordot=true;
                        operator = '/';
                        temp1 = Double.parseDouble(display.getText().toString());
                    }
                }
            });

            buttonEqual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(display.getText().toString().equals(""))
                    {
                        display.setText("0");
                    }
                    else if (infinityset) {
                        display.setText("Error");
                        operatorEnabled = false;
                    }
                    else {
                        double result = 0.0;
                        temp2 = Double.parseDouble(display.getText().toString());
                        switch (operator) {
                            case '+':
                                result = temp1 + temp2;
                                break;
                            case '-':
                                result = temp1 - temp2;
                                break;
                            case '*':
                                result = temp1 * temp2;
                                break;
                            case '/':
                                result = temp1 / temp2;
                                break;
                            default:
                                result = Double.parseDouble(display.getText().toString());
                                break;
                        }
                        if (Double.isInfinite(result) || Double.isNaN(result)) {
                            display.setText("Error");
                            Log.d("demo", "invalid operation");
                            Toast.makeText(MainActivity.this, "invalid operation", Toast.LENGTH_SHORT).show();
                            resultdot = true;
                            resultafter = true;
                            infinityset = true;
                        } else {
                            String resultstr = String.valueOf(result);
                            Log.d("demo",resultstr);
                            Log.d("demo",result +"");
                            if(resultstr.contains(".")) {
                                if (resultstr.length() > 15) {

                                    //just convert to  string using %f format
                                    String p = String.format("%.14f", result) ;
                                    if((p.length()<=15 && p.contains(".")) || p.length()<=14)
                                    {
                                        display.setText(p);
                                        resultdot = true;
                                        resultafter = true;
                                    }
                                    else if(p.contains(".") && p.indexOf(".")<14){

                                        // trucate to 14 character
                                        display.setText(p.substring(0, Math.min(resultstr.length(), 14)));
                                        resultdot = true;
                                        resultafter = true;
                                    }
                                    else {

                                        display.setText("Digits limit Exceeded");
                                        infinityset=true;
                                        resultdot = true;
                                        resultafter = true;
                                    }

                                } else {
                                    display.setText(Double.toString(result));
                                    resultdot = true;
                                    resultafter = true;
                                }
                            }
                            else
                            {
                                if(resultstr.length()>14)
                                {
                                    display.setText("Digits limit Exceeded");
                                    infinityset=true;
                                    resultdot = true;
                                    resultafter = true;
                                }
                                else {
                                    display.setText(Double.toString(result));
                                    resultdot = true;
                                    resultafter = true;
                                }

                            }
                        }

                    }
                }


            });

            buttonAc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    display.setText("0");
                    operatorEnabled = false;
                    temp1 = 0.0;
                    temp2 = 0.0;
                    operator = '#';
                    infinityset=false;
                    resultdot=false;
                    resultafter=false;
                }
            });
        }
        catch(Exception e)
        {
            Log.d("demo","Invalid operation");
            Toast.makeText(MainActivity.this, "Enter a valid input", Toast.LENGTH_SHORT).show();
        }

    }
}

