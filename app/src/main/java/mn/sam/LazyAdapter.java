package mn.sam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private int type;
    private ImageView imIcon;
    private TextView tvDate;
    private TextView tvTemperature;
    private TextView tvCity;
    private HashMap<String, String> weather;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d, int type) {
        activity = a;
        data = d;

        this.type = type;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);

        imIcon = vi.findViewById(R.id.list_image);
        tvDate = vi.findViewById(R.id.tvDate);
        tvTemperature = vi.findViewById(R.id.tvTemperature);
        tvCity = vi.findViewById(R.id.tvCity);

        weather = data.get(position);

        tvDate.setText(weather.get(Helper.KEY_DATE));
        tvTemperature.setText(weather.get(Helper.KEY_TEMPERATURE));
        String icon = "";
        if (weather.get(Helper.KEY_IMAGE) != null) {
            icon = weather.get(Helper.KEY_IMAGE).toLowerCase();
        }
        if (icon.contains("sun"))
            imIcon.setImageResource(R.drawable.sunny);
        else if (icon.contains("cloud"))
            imIcon.setImageResource(R.drawable.partly_cloudy);

        tvCity.setText(weather.get(Helper.KEY_CITY));

        return vi;
    }
}
