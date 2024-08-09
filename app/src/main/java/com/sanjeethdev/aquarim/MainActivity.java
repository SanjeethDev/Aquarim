package com.sanjeethdev.aquarim;

import static com.sanjeethdev.aquarim.LiquidRecordContract.Entry.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.sanjeethdev.aquarim.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecordItemInterface
{
    private ActivityMainBinding binding;
    private LiquidRecordAdapter liquidRecordAdapter;
    private ArrayList<LiquidRecordModel> data;

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // View binding configuration.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewBinding = binding.getRoot();
        setContentView(viewBinding);

        // OnClickListener for main add button.
        binding.mainActionButton.setOnClickListener(view ->
        {
            // Visible = 0; Invisible = 4; Gone = 8
            if (binding.mainAddDialog.getVisibility() == View.GONE)
            {
                binding.mainAddDialog.setVisibility(View.VISIBLE);
                binding.mainActionButton.animate()
                        .rotation(45)
                        .setDuration(250)
                        .start();

                binding.mainLiquidName.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        binding.mainAddEntry.setEnabled(charSequence.length() > 0 && binding.mainLiquidQuantity.getText().length() > 0);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        binding.mainAddEntry.setEnabled(charSequence.length() > 0 && binding.mainLiquidQuantity.getText().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                        binding.mainAddEntry.setEnabled(editable.length() > 0 && binding.mainLiquidQuantity.getText().length() > 0);
                    }
                });

                binding.mainLiquidQuantity.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        binding.mainAddEntry.setEnabled(charSequence.length() > 0 && binding.mainLiquidName.getText().length() > 0);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        binding.mainAddEntry.setEnabled(charSequence.length() > 0 && binding.mainLiquidName.getText().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                        binding.mainAddEntry.setEnabled(editable.length() > 0 && binding.mainLiquidName.getText().length() > 0);
                    }
                });

                // OnClickListener for add button
                binding.mainAddEntry.setOnClickListener(view1 ->
                {
                    LiquidRecordDbHelper liquidRecordDbHelper = new LiquidRecordDbHelper(this);
                    boolean result = liquidRecordDbHelper.insertRecords(
                            new Date().getTime(),
                            binding.mainLiquidName.getText().toString(),
                            Double.parseDouble(binding.mainLiquidQuantity.getText().toString()));
                    if (result)
                    {
                        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                binding.mainAddDialog.setVisibility(View.GONE);
                binding.mainActionButton.animate()
                        .rotation(0)
                        .setDuration(250)
                        .start();
            }
        });
    }

    // Item click interface implementation for record view.
    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(this, RecordItemPopUp.class);
        Bundle bundle = new Bundle();
        bundle.putString("liquid", data.get(position).getLiquid());
        bundle.putDouble("quantity", data.get(position).getQuantity());
        bundle.putLong("datetime", data.get(position).getDatetime());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private void getRecords() {
        // Test code to look at the data and views.
        data = new ArrayList<>();
        binding.mainRecordView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor;
        try (LiquidRecordDbHelper liquidRecordDbHelper = new LiquidRecordDbHelper(this))
        {
            cursor = liquidRecordDbHelper.readRecords();
            Log.d("DEBUG", "onCreate: " + Arrays.toString(cursor.getColumnNames()));
            Log.d("DEBUG", "onCreate: " + cursor.getCount());

            // Take the records stored in cursor and process it to views.
            if (cursor.getCount() == 0)
            {
                Toast.makeText(this, "Empty History", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        Log.d("DEBUG", "data: " + new Date(cursor.getLong(1)) + " " + cursor.getDouble(3) + " " + cursor.getString(2));
                        data.add(new LiquidRecordModel(
                                cursor.getLong(1),
                                cursor.getDouble(3),
                                cursor.getString(2)));
                    }
                    while (cursor.moveToNext());
                }
            }
            cursor.close();
        }

        // Set adapter for the recycler view (after the data has been populated).
        liquidRecordAdapter = new LiquidRecordAdapter(data, this);
        binding.mainRecordView.setAdapter(liquidRecordAdapter);
    }
}