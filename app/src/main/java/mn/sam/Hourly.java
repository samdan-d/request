package mn.sam;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Hourly extends Helper {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.five_day);
        lvList = findViewById(R.id.lvList);

        // initialize weathers
        weathers = new ArrayList<>();

        new DownloadWeather().execute();
    }

    private class DownloadWeather extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            parseC(URL + "&" + MODE_JSON + "&q=" + cities[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            setAdapter();
        }
    }
}

