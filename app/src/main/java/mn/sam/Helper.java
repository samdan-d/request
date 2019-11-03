package mn.sam;

import android.util.Xml;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    static final String KEY_CITY = "city";

    static final String URL = "https://api.openweathermap.org/data/2.5/forecast?appid=569fd1a76d65132814ac30ffbf1ae225&units=metric";
    static final String URL_CURRENT = "https://api.openweathermap.org/data/2.5/weather?appid=569fd1a76d65132814ac30ffbf1ae225&units=metric";
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

    protected void setAdapter() {
        adapter = new LazyAdapter(this, weathers, 1);
        lvList.setAdapter(adapter);
    }

    public void parseA(String weatherURL) {
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        HashMap<String, String> weather = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(weatherURL).openConnection();
            conn.setRequestMethod("GET");
            stream = conn.getInputStream();
            parser.setInput(stream, null);
            int counter = 0;
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("time")) {
                            counter++;
                            weather = new HashMap<>();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date newDate = format.parse(parser.getAttributeValue(null, "from"));

                            format = new SimpleDateFormat("yyyy-MM-dd");
                            String date = format.format(newDate);
                            weather.put(KEY_DATE, date);
                        } else if (name.equals("symbol")) {
                            weather.put(KEY_IMAGE, parser.getAttributeValue(null, "name"));
                        } else if (name.equals("temperature")) {
                            weather.put(KEY_TEMPERATURE, parser.getAttributeValue(null, "value") + "°C");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("time") && weather != null && counter % 8 == 0) {
                            weathers.add(weather);
                        }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
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

    public void parseB(String weatherURL) {
        InputStream stream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(weatherURL).openConnection();
            conn.setRequestMethod("GET");
            stream = conn.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            JSONObject json = (JSONObject) new JSONParser().parse(sb.toString());

            HashMap<String, String> weather = new HashMap<>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            JSONArray w = (JSONArray) json.get("weather");
            JSONObject wObj = (JSONObject) w.get(0);
            JSONObject main = (JSONObject) json.get("main");

            weather.put(KEY_DATE, format.format(new Date()));
            weather.put(KEY_IMAGE, wObj.get("main").toString());
            weather.put(KEY_TEMPERATURE, main.get("temp") + "°C");
            weather.put(KEY_CITY, json.get("name").toString());

            weathers.add(weather);

        } catch (Exception e) {
            // In your production code handle any errors and catch the individual exceptions
            e.printStackTrace();
        }
    }

    public void parseC(String weatherURL) {
        InputStream stream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(weatherURL).openConnection();
            conn.setRequestMethod("GET");
            stream = conn.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            JSONObject json = (JSONObject) new JSONParser().parse(sb.toString());

            HashMap<String, String> weather;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            JSONArray list = (JSONArray) json.get("list");

            for (int i = 0; i < 8; i++) {
                weather = new HashMap<>();

                JSONObject listData = (JSONObject) list.get(i);
                JSONArray w = (JSONArray) listData.get("weather");
                JSONObject wObj = (JSONObject) w.get(0);
                JSONObject main = (JSONObject) listData.get("main");

                Date newDate = format.parse(listData.get("dt_txt").toString());
                format = new SimpleDateFormat("yyyy-MM-dd HH");

                weather.put(KEY_DATE, format.format(newDate));
                weather.put(KEY_IMAGE, wObj.get("main").toString());
                weather.put(KEY_TEMPERATURE, main.get("temp") + "°C");

                weathers.add(weather);
            }

        } catch (Exception e) {
            // In your production code handle any errors and catch the individual exceptions
            e.printStackTrace();
        }
    }
}
