package com.sanjeethdev.aquarim;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.sanjeethdev.aquarim.Adapters.LiquidRecordAdapter;
import com.sanjeethdev.aquarim.Interfaces.RecordItemInterface;
import com.sanjeethdev.aquarim.Models.LiquidRecordModel;
import com.sanjeethdev.aquarim.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecordItemInterface
{
    private ActivityMainBinding binding;
    private LiquidRecordAdapter liquidRecordAdapter;
    private ArrayList<LiquidRecordModel> data;

    // Updates the UI after any record updates
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            activityResult ->
            {
                if (!(activityResult.getResultCode() == Activity.RESULT_OK))
                {
                    Log.d("DEBUG", "activityResult: " + activityResult.getResultCode());
                    return;
                }
                editRecordList(activityResult);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // View binding configuration.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewBinding = binding.getRoot();
        setContentView(viewBinding);

        // Get values
        data = new ArrayList<>();
        liquidRecordAdapter = new LiquidRecordAdapter(data, this);
        binding.mainRecordView.setAdapter(liquidRecordAdapter);
        getRecords();
        goalPercentage();

        // OnClickListener for main add button.
        binding.mainAddButton.setOnClickListener(view ->
        {
            resultLauncher.launch(new Intent(this, AddRecordPopUp.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
        });

        // This applies the rounded effect for the record view and water level
        // anything inside the container gets clipped
        binding.mainRecordView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.mainRecordView.setClipToOutline(true);
        binding.mainLiquidLevel.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.mainLiquidLevel.setClipToOutline(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        goalPercentage();
    }

    // Each item in the list of record view get put in a editable popup box
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
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultLauncher.launch(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    // Calculate the percentage of the daily goal based on today's records.
    public void goalPercentage()
    {
        int weight = 55; // hardcoded for now
        int total = weight * 35;
        double sum = 0;
        for (LiquidRecordModel record : data)
        {
            sum += record.getQuantity();
        }

        if (sum == 0)
        {
            binding.mainRecordViewText.setVisibility(View.VISIBLE);
            binding.mainProgressBarTextContainer.setVisibility(View.VISIBLE);
        } else
        {
            binding.mainRecordViewText.setVisibility(View.GONE);
            binding.mainProgressBarTextContainer.setVisibility(View.GONE);
        }
        // current / total * 100
        ObjectAnimator progressObjectAnimator = ObjectAnimator.ofInt(binding.mainProgressBar, "progress", (int) ((sum / total) * 10000));
        progressObjectAnimator.setInterpolator(new DecelerateInterpolator());
        progressObjectAnimator.setDuration(750);
        progressObjectAnimator.start();
    }

    // Gets records from databases and sets it to adapter to display.
    private void getRecords()
    {
        // Test code to look at the data and views.
        binding.mainRecordView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor;
        try (LiquidRecordDbHelper liquidRecordDbHelper = new LiquidRecordDbHelper(this))
        {
            cursor = liquidRecordDbHelper.todayRecords();
            // Take the records stored in cursor and process it to views.
            if (cursor.getCount() == 0)
            {
                Toast.makeText(this, "Empty History", Toast.LENGTH_SHORT).show();
                return;
            }

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
            cursor.close();
        } catch (Exception exception)
        {
            Log.d("DEBUG", "getRecords: exception" + exception.getLocalizedMessage());
        }
    }

    // Changes the record list items based on the operation
    private void editRecordList(ActivityResult activityResult)
    {
        if (activityResult.getData() == null)
        {
            Log.d("DEBUG", "changeRecordList: result " + null);
            return;
        }

        String action;
        if (activityResult.getData().hasExtra("delete"))
        {
            action = "delete";
        } else if (activityResult.getData().hasExtra("insert"))
        {
            action = "insert";
        } else
        {
            action = "not defined";
        }

        switch (action)
        {
            case "delete":
                int position = activityResult.getData().getIntExtra("delete", -1);
                if (position >= 0)
                {
                    liquidRecordAdapter.notifyItemRemoved(position);
                    data.remove(position);
                }
                break;

            case "insert":
                data.add(0, (LiquidRecordModel) activityResult.getData().getSerializableExtra("insert"));
                liquidRecordAdapter.notifyItemInserted(0);
                binding.mainRecordView.scrollToPosition(0);
                break;

            default:
                Log.d("DEBUG", "function not found.");
                break;
        }
    }
}