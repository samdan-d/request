package mn.sam;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class FiveCity extends Helper {
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
            parseB(URL_CURRENT + "&" + MODE_JSON + "&q=" + cities[0]);
            parseB(URL_CURRENT + "&" + MODE_JSON + "&q=" + cities[1]);
            parseB(URL_CURRENT + "&" + MODE_JSON + "&q=" + cities[2]);
            parseB(URL_CURRENT + "&" + MODE_JSON + "&q=" + cities[3]);
            parseB(URL_CURRENT + "&" + MODE_JSON + "&q=" + cities[4]);
            return null;
        }

        protected void onPostExecute(Void result) {
            setAdapter();
        }
    }
}