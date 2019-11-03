package mn.sam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.net.URL;
import java.util.ArrayList;

public class FiveDay extends Helper {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.five_day);
        lvList = findViewById(R.id.lvList);

        // initialize weathers
        weathers = new ArrayList<>();

        addWeather("sunny", "2019-11-03", "10C");

        new DownloadWeather().execute();
    }

    protected void setAdapter() {
        adapter = new LazyAdapter(this, weathers);
        lvList.setAdapter(adapter);
    }

    class DownloadWeather extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
//            System.out.println(URL + "&" + MODE_XML + "&q=" + cities[0]);
//            parse(URL + "&" + MODE_XML + "&q=" + cities[0]);
            parse("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
            return null;
        }

        protected void onPostExecute(Void result) {
            System.out.println("");
            setAdapter();
        }
    }
}
