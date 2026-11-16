package com.example.usterki;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spCategory;
    private EditText etDescription;
    private SeekBar sbPriority;
    private TextView tvPriorityValue;
    private CheckBox cbUrgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // łączymy kod z elementami z XML
        spCategory = findViewById(R.id.spCategory);
        etDescription = findViewById(R.id.etDescription);
        sbPriority = findViewById(R.id.sbPriority);
        tvPriorityValue = findViewById(R.id.tvPriorityValue);
        cbUrgent = findViewById(R.id.cbUrgent);

        setUpCategorySpinner();
        setUpPrioritySeekBar();

        findViewById(R.id.btnNext).setOnClickListener(v -> openSummary());
    }

    // lista kategorii ze strings.xml do rozwijanej listy
    private void setUpCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
    }

    // suwak liczy od 0 do 4, a użytkownikowi pokazujemy 1 do 5 (dlatego +1)
    private void setUpPrioritySeekBar() {
        tvPriorityValue.setText(String.valueOf(sbPriority.getProgress() + 1));
        sbPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPriorityValue.setText(String.valueOf(progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void openSummary() {
        // intencja jawna: wprost wskazujemy ekran do otwarcia
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }
}