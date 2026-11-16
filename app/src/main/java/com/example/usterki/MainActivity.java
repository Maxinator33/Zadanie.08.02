package com.example.usterki;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        spCategory = findViewById(R.id.spCategory);
        etDescription = findViewById(R.id.etDescription);
        sbPriority = findViewById(R.id.sbPriority);
        tvPriorityValue = findViewById(R.id.tvPriorityValue);
        cbUrgent = findViewById(R.id.cbUrgent);

        setUpCategorySpinner();
        setUpPrioritySeekBar();

        findViewById(R.id.btnNext).setOnClickListener(v -> openSummary());
    }

    private void setUpCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);
    }

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

    // sprawdzamy dane PRZED przejściem na drugi ekran
    // na pierwszym błędzie pokazujemy komunikat i przerywamy (return false)
    private boolean validateForm() {
        if (spCategory.getSelectedItemPosition() == 0) {   // 0 = "-- wybierz kategorię --"
            Toast.makeText(this, R.string.error_category, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etDescription.getText().toString().trim().isEmpty()) {
            etDescription.setError(getString(R.string.error_description));  // czerwony błąd w polu
            etDescription.requestFocus();
            return false;
        }

        return true;
    }

    private void openSummary() {
        if (!validateForm()) {
            return;     // dane złe, nie przechodzimy dalej
        }

        Intent intent = new Intent(this, SummaryActivity.class);

        // 4 dodatki: tekst, tekst, liczba (int), prawda/fałsz (boolean)
        intent.putExtra(SummaryActivity.EXTRA_CATEGORY, spCategory.getSelectedItem().toString());
        intent.putExtra(SummaryActivity.EXTRA_DESCRIPTION, etDescription.getText().toString().trim());
        intent.putExtra(SummaryActivity.EXTRA_PRIORITY, sbPriority.getProgress() + 1);
        intent.putExtra(SummaryActivity.EXTRA_URGENT, cbUrgent.isChecked());

        startActivity(intent);
    }
}