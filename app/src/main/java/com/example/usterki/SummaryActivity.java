package com.example.usterki;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SummaryActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.example.usterki.CATEGORY";
    public static final String EXTRA_DESCRIPTION = "com.example.usterki.DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.usterki.PRIORITY";
    public static final String EXTRA_URGENT = "com.example.usterki.URGENT";
    // klucz odpowiedzi odsyłanej do pierwszego ekranu
    public static final String EXTRA_NUMBER = "com.example.usterki.NUMBER";

    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvSummary = findViewById(R.id.tvSummary);
        tvSummary.setText(buildSummary());

        findViewById(R.id.btnConfirm).setOnClickListener(v -> confirm());
        findViewById(R.id.btnEdit).setOnClickListener(v -> finish());   // finish bez setResult = rezygnacja
    }

    private String buildSummary() {
        Intent intent = getIntent();

        String category = intent.getStringExtra(EXTRA_CATEGORY);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        int priority = intent.getIntExtra(EXTRA_PRIORITY, 1);
        boolean urgent = intent.getBooleanExtra(EXTRA_URGENT, false);

        String urgentText = urgent
                ? getString(R.string.yes_short)
                : getString(R.string.no_short);

        return getString(R.string.summary_format, category, description, priority, urgentText);
    }

    // POTWIERDŹ: losujemy numer, odsyłamy go i zamykamy ekran
    private void confirm() {
        String number = "UST/" + (1000 + new Random().nextInt(9000));

        Intent result = new Intent();
        result.putExtra(EXTRA_NUMBER, number);
        setResult(RESULT_OK, result);   // ustaw odpowiedź "OK"
        finish();                       // finish() wysyła odpowiedź
    }
}