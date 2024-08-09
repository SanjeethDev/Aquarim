package com.sanjeethdev.aquarim;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sanjeethdev.aquarim.databinding.ActivityMainBinding;
import com.sanjeethdev.aquarim.databinding.ActivityRecordItemPopUpBinding;

import java.util.Objects;

public class RecordItemPopUp extends AppCompatActivity
{

    private ActivityRecordItemPopUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordItemPopUpBinding.inflate(getLayoutInflater());
        View viewBinding = binding.getRoot();
        setContentView(viewBinding);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width*0.9), (int) (height*0.15));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        Bundle bundle = Objects.requireNonNull(getIntent().getExtras()).getBundle("bundle");
        assert bundle != null;
        Log.d("DEBUG", "onCreate: " + bundle.getString("liquid"));
        binding.liquidRecordItemLiquid.setText(bundle.getString("liquid"));
        binding.liquidRecordItemQuantity.setText(String.valueOf(bundle.getDouble("quantity")));
        binding.liquidRecordItemDatetime.setText(String.valueOf(bundle.getLong("datetime")));

        binding.recordPopupDelete.setOnClickListener(view ->
        {
            LiquidRecordDbHelper recordDbHelper = new LiquidRecordDbHelper(this);
            if (recordDbHelper.deleteRecord(bundle.getLong("datetime")))
            {
                Toast.makeText(this, "Successful deleted.", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}