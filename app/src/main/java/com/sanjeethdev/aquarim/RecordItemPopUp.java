package com.sanjeethdev.aquarim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.sanjeethdev.aquarim.databinding.ActivityRecordItemPopUpBinding;
import java.util.Objects;

public class RecordItemPopUp extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityRecordItemPopUpBinding binding = ActivityRecordItemPopUpBinding.inflate(getLayoutInflater());
        View viewBinding = binding.getRoot();
        setContentView(viewBinding);

        Bundle bundle = Objects.requireNonNull(getIntent().getExtras()).getBundle("bundle");
        assert bundle != null;
        binding.liquidRecordItemLiquid.setText(bundle.getString("liquid"));
        binding.liquidRecordItemQuantity.setText(String.valueOf(bundle.getDouble("quantity")));
        binding.liquidRecordItemDatetime.setText(String.valueOf(bundle.getLong("datetime")));

        binding.recordPopupDelete.setOnClickListener(view ->
        {
            try (LiquidRecordDbHelper recordDbHelper = new LiquidRecordDbHelper(this))
            {
                // Call delete record function and check the result.
                if (recordDbHelper.deleteRecord(bundle.getLong("datetime")))
                {
                    Toast.makeText(this, "Successful deleted.", Toast.LENGTH_SHORT).show();
                    Intent output = new Intent(RecordItemPopUp.this, MainActivity.class);
                    output.putExtra("delete", bundle.getInt("position"));
                    setResult(RESULT_OK, output);
                    finish();
                } else
                {
                    Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}