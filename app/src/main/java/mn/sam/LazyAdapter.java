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

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;

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

        ImageView imIcon = vi.findViewById(R.id.list_image);
        TextView tvDate = vi.findViewById(R.id.tvDate);
        TextView tvTemperatur = vi.findViewById(R.id.tvTemperature);

        HashMap<String, String> weather = new HashMap<>();
        weather = data.get(position);

        tvDate.setText(weather.get(Helper.KEY_DATE));
        tvTemperatur.setText(weather.get(Helper.KEY_TEMPERATURE));
        switch (weather.get(Helper.KEY_IMAGE).toLowerCase()) {
            case "sunny":
                imIcon.setImageResource(R.drawable.sunny);
                break;
            case "partly_cloudy":
                imIcon.setImageResource(R.drawable.partly_cloudy);
                break;
        }
        return vi;
    }
}
