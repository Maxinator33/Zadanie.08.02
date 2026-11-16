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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    // "odbiornik odpowiedzi" z drugiego ekranu - musi być polem klasy,
    // bo rejestracja musi istnieć zanim ekran stanie się widoczny
    private final ActivityResultLauncher<Intent> summaryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // 1. przypadek: użytkownik potwierdził - pokazujemy numer
                    String number = result.getData()
                            .getStringExtra(SummaryActivity.EXTRA_NUMBER);
                    Snackbar.make(findViewById(R.id.main),
                            getString(R.string.registered, number),
                            Snackbar.LENGTH_LONG).show();
                } else {
                    // 2. przypadek: zrezygnował (Wstecz albo POPRAW) - krótka informacja
                    Toast.makeText(this, R.string.cancelled, Toast.LENGTH_SHORT).show();
                }
            });

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

    private boolean validateForm() {
        if (spCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_category, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etDescription.getText().toString().trim().isEmpty()) {
            etDescription.setError(getString(R.string.error_description));
            etDescription.requestFocus();
            return false;
        }

        return true;
    }

    private void openSummary() {
        if (!validateForm()) {
            return;
        }

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra(SummaryActivity.EXTRA_CATEGORY, spCategory.getSelectedItem().toString());
        intent.putExtra(SummaryActivity.EXTRA_DESCRIPTION, etDescription.getText().toString().trim());
        intent.putExtra(SummaryActivity.EXTRA_PRIORITY, sbPriority.getProgress() + 1);
        intent.putExtra(SummaryActivity.EXTRA_URGENT, cbUrgent.isChecked());

        // uruchamiamy przez launcher, żeby móc odebrać odpowiedź
        summaryLauncher.launch(intent);
    }
}