package com.sanjeethdev.aquarim;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sanjeethdev.aquarim.databinding.ActivityAddRecordPopUpBinding;
import com.sanjeethdev.aquarim.databinding.ActivityRecordItemPopUpBinding;

import java.util.Date;

public class AddRecordPopUp extends AppCompatActivity
{
    private ActivityAddRecordPopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecordPopUpBinding.inflate(getLayoutInflater());
        View viewBinding = binding.getRoot();
        setContentView(viewBinding);

        binding.addLiquidName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                binding.addLiquid.setEnabled(charSequence.length() > 0
                        && binding.addLiquidQuantity.getText().length() > 0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                binding.addLiquid.setEnabled(charSequence.length() > 0
                        && binding.addLiquidQuantity.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                binding.addLiquid.setEnabled(editable.length() > 0
                        && binding.addLiquidQuantity.getText().length() > 0);
            }
        });

        binding.addLiquidQuantity.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                binding.addLiquid.setEnabled(charSequence.length() > 0
                        && binding.addLiquidName.getText().length() > 0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                binding.addLiquid.setEnabled(charSequence.length() > 0
                        && binding.addLiquidName.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                binding.addLiquid.setEnabled(editable.length() > 0
                        && binding.addLiquidName.getText().length() > 0);
            }
        });

        // OnClickListener for add button.
        binding.addLiquid.setOnClickListener(view1 ->
        {
            boolean result;
            try (LiquidRecordDbHelper liquidRecordDbHelper = new LiquidRecordDbHelper(this))
            {
                result = liquidRecordDbHelper.insertRecords(
                        new Date().getTime(),
                        binding.addLiquidName.getText().toString(),
                        Double.parseDouble(binding.addLiquidQuantity.getText().toString()));
            }
            if (result)
            {
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                finish();
            } else
            {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
    }
}