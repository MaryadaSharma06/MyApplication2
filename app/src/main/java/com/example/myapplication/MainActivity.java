package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner fromUnitSpinner, toUnitSpinner;
    EditText inputValue;
    TextView resultText;
    Button convertButton;

    // Supported units
    String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements
        inputValue = findViewById(R.id.inputValue);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Set up the spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        // Button listener
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value to convert.", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(input);
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();

        // If same units
        if (fromUnit.equals(toUnit)) {
            resultText.setText(String.format("%.4f %s", value, toUnit));
            return;
        }

        double valueInMeters = convertToMeters(value, fromUnit);
        double finalResult = convertFromMeters(valueInMeters, toUnit);

        resultText.setText(String.format("%.4f %s", finalResult, toUnit));
    }

    // Converts any unit to meters
    private double convertToMeters(double value, String unit) {
        switch (unit) {
            case "Feet": return value * 0.3048;
            case "Inches": return value * 0.0254;
            case "Centimeters": return value / 100.0;
            case "Meters": return value;
            case "Yards": return value * 0.9144;
            default: return 0;
        }
    }

    // Converts meters to the desired unit
    private double convertFromMeters(double value, String unit) {
        switch (unit) {
            case "Feet": return value / 0.3048;
            case "Inches": return value / 0.0254;
            case "Centimeters": return value * 100.0;
            case "Meters": return value;
            case "Yards": return value / 0.9144;
            default: return 0;
        }
    }
}
