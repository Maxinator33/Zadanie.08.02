package com.example.usterki;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SummaryActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.example.usterki.CATEGORY";
    public static final String EXTRA_DESCRIPTION = "com.example.usterki.DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.usterki.PRIORITY";
    public static final String EXTRA_URGENT = "com.example.usterki.URGENT";
    public static final String EXTRA_NUMBER = "com.example.usterki.NUMBER";

    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvSummary = findViewById(R.id.tvSummary);
        tvSummary.setText(buildSummary());

        findViewById(R.id.btnConfirm).setOnClickListener(v -> confirm());
        findViewById(R.id.btnEdit).setOnClickListener(v -> finish());
        findViewById(R.id.btnShare).setOnClickListener(v -> share());
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

    private void confirm() {
        String number = "UST/" + (1000 + new Random().nextInt(9000));

        Intent result = new Intent();
        result.putExtra(EXTRA_NUMBER, number);
        setResult(RESULT_OK, result);
        finish();
    }

    // intencja NIEJAWNA: mówimy tylko "wyślij tekst", a system sam znajduje aplikacje
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, tvSummary.getText().toString());

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } catch (ActivityNotFoundException e) {
            // brak aplikacji do udostępniania = komunikat zamiast awarii
            Toast.makeText(this, R.string.error_no_app, Toast.LENGTH_SHORT).show();
        }
    }
}