package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teamlazystance.kerorong.touhouenhancegame.R;

/**
 * Created by cps on 2018-03-30.
 */

public class ShopDialogAdapter extends BaseAdapter
{
    private List< String > titleItems;
    private int[] descItems;
    private Context context;

    public ShopDialogAdapter( Context context, List< String > titleItems, int[] descItems )
    {
        this.context = context;
        this.titleItems = titleItems;
        this.descItems = descItems;
    }

    @Override
    public int getCount( )
    {
        return titleItems.size();
    }

    @Override
    public Object getItem( int position )
    {
        return titleItems.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return 0;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        ViewHolder holder;
        if ( convertView == null )
        {
            LayoutInflater inflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate( R.layout.row_shop, parent, false );

            holder = new ViewHolder();
            holder.titleText = convertView.findViewById( R.id.txt_title );
            holder.descText = convertView.findViewById( R.id.txt_desc );

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.reset(position);
        return convertView;
    }

    public class ViewHolder
    {
        public TextView titleText, descText;

        public void reset(int position)
        {
            String desc = GlobalVariables.LIST_PRICES[descItems[position]]+"원";
            titleText.setText(titleItems.get(position));
            descText.setText(desc);

            if (GlobalVariables.PLAYER_HIGHSCORE < descItems[position] && position > 0)
            {
                titleText.setTextColor( Color.argb(138, 0, 0, 0));
                descText.setTextColor( Color.argb(97, 0, 0, 0));
            } else
            {
                titleText.setTextColor( Color.rgb(0, 0, 0));
                descText.setTextColor( Color.rgb(0, 0, 0));
            }
        }
    }
}
