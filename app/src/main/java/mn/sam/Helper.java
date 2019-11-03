package mn.sam;

import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Helper extends AppCompatActivity implements View.OnClickListener {
    protected ArrayList<HashMap<String, String>> weathers;
    protected ListView lvList;
    protected LazyAdapter adapter;

    final String[] cities = new String[]{
            "Ulaanbaatar", "London", "Toronto", "New York", "Tokyo"
    };

    static final String KEY_IMAGE = "image";
    static final String KEY_DATE = "date";
    static final String KEY_TEMPERATURE = "temperature";

    static final String URL = "https://api.openweathermap.org/data/2.5/forecast?appid=569fd1a76d65132814ac30ffbf1ae225&units=metric";
    static final String MODE_XML = "mode=xml";
    static final String MODE_JSON = "mode=json";

    @Override
    public void onClick(View view) {
        System.out.println("CLICKED!!");
    }

    public void addWeather(String image, String date, String temperature) {
        HashMap<String, String> weather = new HashMap<>();
        weather.put(KEY_IMAGE, image);
        weather.put(KEY_DATE, date);
        weather.put(KEY_TEMPERATURE, temperature);

        weathers.add(weather);
    }

    public void clearWeather() {
        weathers.clear();
    }

    public void parse(String weatherURL) {
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            HttpURLConnection conn = (HttpURLConnection) new URL(weatherURL).openConnection();
            conn.setRequestMethod("GET");
            Log.d("REQQQQQQQQQQQQQQQQQQ", "?????????????????");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("REQQQQQQQQQQQQQQQQQQ", "The response is: " + response);
            stream = conn.getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            HashMap<String, String> weather = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                System.out.println("?????????????????????????????????????????????");
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("forecast")) {
                            weather = new HashMap<>();
                        } else if (weather != null) {
                            if (name.equals("time")) {
                                weather.put(KEY_DATE, parser.getAttributeValue(null, "from"));
                            } else if (name.equals("symbol")) {
                                weather.put(KEY_DATE, parser.getAttributeValue(null, "name"));
                            } else if (name.equals("temperature")) {
                                weather.put(KEY_DATE, parser.getAttributeValue(null, "value"));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("time") && weather != null) {
                            weathers.add(weather);
                        }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
