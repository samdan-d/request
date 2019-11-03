package mn.sam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Helper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnFiveDay)).setOnClickListener(this);
        (findViewById(R.id.btnFiveCity)).setOnClickListener(this);
        (findViewById(R.id.btnHourly)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnFiveDay:
                intent.setClass(this, FiveDay.class);
                break;
            case R.id.btnFiveCity:
                intent.setClass(this, FiveCity.class);
                break;
            case R.id.btnHourly:
                intent.setClass(this, Hourly.class);
                break;
            default:
                intent.setClass(this, this.getClass());
        }
        startActivity(intent);
    }
}
