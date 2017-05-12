package org.openintents.keyboardcts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import org.openintents.keyboardcts.databinding.ActivityMainBinding;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setData(new Data());
        binding.input1.addTextChangedListener(new CopyTextWatcher(binding.result1));
        binding.input2.addTextChangedListener(new CopyTextWatcher(binding.result2));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportData();
                Snackbar.make(view, R.string.report_sent, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void reportData() {
        String reportString = getDataToShare();
        FirebaseCrash.log(reportString);
        FirebaseCrash.report(new RuntimeException(reportString));
    }

    public String getDataToShare() {
        return Locale.getDefault().toString() +
                    ", " +
                    imPackageName() +
                    ": " +
                    binding.input1.getText() +
                    "=>" +
                    binding.result1.getText() +
                    " " +
                    binding.input2.getText() +
                    "=>" +
                    binding.result2.getText();
    }

    private String imPackageName() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> iMList = imm.getEnabledInputMethodList();
        String id = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.DEFAULT_INPUT_METHOD
        );

        for (InputMethodInfo imi : iMList) {
            if (imi.getId().equals(id)) {
                return imi.getPackageName();
            }
        }
        return "-unknown-";
    }

    private class CopyTextWatcher implements TextWatcher {
        private final TextView textView;

        public CopyTextWatcher(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            double number;
            try {
                number = numberFormat.parse(s.toString()).doubleValue();
            } catch (ParseException e) {
                FirebaseCrash.report(e);
                number = 0;
            }
            textView.setText(String.valueOf(number));
            binding.dataToShare.setText(getDataToShare());
        }
    }
}
