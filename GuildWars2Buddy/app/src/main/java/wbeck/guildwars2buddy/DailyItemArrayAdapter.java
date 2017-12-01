package wbeck.guildwars2buddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import wbeck.guildwars2buddy.R;
import wbeck.guildwars2buddy.Structures.DailyListItem;

/**
 * Created by Index on 30/11/2017.
 */

public class DailyItemArrayAdapter extends ArrayAdapter<DailyListItem> {

    public DailyItemArrayAdapter(Context context, ArrayList<DailyListItem> items) {
            super(context, 0, items);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        DailyListItem item = getItem(position);

        if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.daily_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.dailyName);
        TextView desc = (TextView) convertView.findViewById(R.id.dailyDescription);
        TextView req = (TextView) convertView.findViewById(R.id.dailyRequirements);


        name.setText(item.name);
        desc.setText(item.description);
        req.setText(item.requirements);

        return convertView;


        }
}