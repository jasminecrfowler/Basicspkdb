package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText textN1 = findViewById(R.id.editText_Num1);
        EditText textN2 = findViewById(R.id.editText_Num2);
        EditText textANS = findViewById(R.id.editText_NumAns);

        setButtonListener(R.id.b_Add, textN1, textN2, textANS, "add");
        setButtonListener(R.id.b_Subtract, textN1, textN2, textANS, "subtract");
        setButtonListener(R.id.b_Multiply, textN1, textN2, textANS, "multiply");
        setButtonListener(R.id.b_Divide, textN1, textN2, textANS, "divide");
    }

    private void setButtonListener(int buttonId, EditText num1, EditText num2, EditText answer, String operation) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double d1 = 0.0, d2 = 0.0, result = 0.0;

                try {
                    d1 = Double.parseDouble(num1.getText().toString());
                    d2 = Double.parseDouble(num2.getText().toString());
                } catch (NumberFormatException e) {
                    Log.w("Calculator", "Invalid input");
                    answer.setText("Invalid input");
                    return;
                }

                switch (operation) {
                    case "add":
                        result = d1 + d2;
                        break;
                    case "subtract":
                        result = d1 - d2;
                        break;
                    case "multiply":
                        result = d1 * d2;
                        break;
                    case "divide":
                        if (d2 != 0) {
                            result = d1 / d2;
                        } else {
                            answer.setText("Cannot divide by zero");
                            return;
                        }
                        break;
                }

                answer.setText(String.valueOf(result));
                Log.d("Calculator", "Result of " + operation + " : " + result);
            }
        });
    }
}
