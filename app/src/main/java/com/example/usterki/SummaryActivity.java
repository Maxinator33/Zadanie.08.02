package com.example.usterki;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    // klucze definiuje ekran, który dane odbiera
    public static final String EXTRA_CATEGORY = "com.example.usterki.CATEGORY";
    public static final String EXTRA_DESCRIPTION = "com.example.usterki.DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.usterki.PRIORITY";
    public static final String EXTRA_URGENT = "com.example.usterki.URGENT";

    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvSummary = findViewById(R.id.tvSummary);
        tvSummary.setText(buildSummary());

        findViewById(R.id.btnEdit).setOnClickListener(v -> finish());
    }

    // wyciągamy dane z intencji i składamy z nich tekst podsumowania
    private String buildSummary() {
        Intent intent = getIntent();

        String category = intent.getStringExtra(EXTRA_CATEGORY);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        int priority = intent.getIntExtra(EXTRA_PRIORITY, 1);           // 1 = gdyby brakło
        boolean urgent = intent.getBooleanExtra(EXTRA_URGENT, false);   // false = gdyby brakło

        String urgentText = urgent
                ? getString(R.string.yes_short)
                : getString(R.string.no_short);

        return getString(R.string.summary_format, category, description, priority, urgentText);
    }
}