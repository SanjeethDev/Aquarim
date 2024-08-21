package com.sanjeethdev.aquarim;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.sanjeethdev.aquarim.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecordItemInterface
{
    private ActivityMainBinding binding;
    private LiquidRecordAdapter liquidRecordAdapter;
    private ArrayList<LiquidRecordModel> data;
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
    activityResult ->
    {
        if (activityResult.getResultCode() == Activity.RESULT_OK)
        {
            if (activityResult.getData() != null)
            {
                if (activityResult.getData().hasExtra("delete"))
                {
                    int position = activityResult.getData().getIntExtra("delete", -1);
                    if (position >= 0)
                    {
                        liquidRecordAdapter.notifyItemRemoved(position);
                        data.remove(position);
                    }
                } else if (activityResult.getData().hasExtra("insert"))
                {
                    data.add(0, (LiquidRecordModel) activityResult.getData().getSerializableExtra("insert"));
                    liquidRecordAdapter.notifyItemInserted(0);
                    binding.mainRecordView.scrollToPosition(0);
                } else
                {
                    Log.d("DEBUG", "function not found.");
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // View binding configuration.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewBinding = binding.getRoot();
        setContentView(viewBinding);
        binding.mainProgressView.setPivotY(0);
        getRecords();
        // OnClickListener for main add button.
        binding.mainActionButton.setOnClickListener(view ->
        {
            resultLauncher.launch(new Intent(this, AddRecordPopUp.class));
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
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
        bundle.putInt("position", position);
        intent.putExtra("bundle", bundle);
        resultLauncher.launch(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    // Gets records from databases and sets it to adapter to display.
    private void getRecords() {
        // Test code to look at the data and views.
        data = new ArrayList<>();
        binding.mainRecordView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor;
        try (LiquidRecordDbHelper liquidRecordDbHelper = new LiquidRecordDbHelper(this))
        {
            cursor = liquidRecordDbHelper.readRecords();
            // Take the records stored in cursor and process it to views.
            if (cursor.getCount() == 0)
            {
                Toast.makeText(this, "Empty History", Toast.LENGTH_SHORT).show();
            } else
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        data.add(new LiquidRecordModel(
                                cursor.getLong(1),
                                cursor.getDouble(3),
                                cursor.getString(2)));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        }
        // Set adapter for the recycler view (after the data has been populated).
        liquidRecordAdapter = new LiquidRecordAdapter(data, this);
        binding.mainRecordView.setAdapter(liquidRecordAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}