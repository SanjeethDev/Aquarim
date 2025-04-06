package com.sanjeethdev.aquarim;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.sanjeethdev.aquarim.Models.LiquidRecordModel;
import com.sanjeethdev.aquarim.databinding.ActivityAddRecordPopUpBinding;
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
            long datetime;
            double quantity;
            String liquid;
            try (LiquidRecordDbHelper liquidRecordDbHelper = new LiquidRecordDbHelper(this))
            {
                datetime = new Date().getTime();
                quantity = Double.parseDouble(binding.addLiquidQuantity.getText().toString());
                liquid = binding.addLiquidName.getText().toString();
                result = liquidRecordDbHelper.insertRecords(datetime, liquid, quantity);

            }
            if (result)
            {
                Intent output = new Intent(AddRecordPopUp.this, MainActivity.class);
                output.putExtra("insert", new LiquidRecordModel(datetime, quantity, liquid));
                setResult(RESULT_OK, output);
                finish();
            } else
            {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.back.setOnClickListener(view1 -> finish());
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
    }
}