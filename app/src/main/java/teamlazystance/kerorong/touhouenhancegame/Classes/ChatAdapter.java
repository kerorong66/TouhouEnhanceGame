package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teamlazystance.kerorong.touhouenhancegame.R;

/**
 * Created by cps on 2018-04-03.
 */

public class ChatAdapter extends BaseAdapter
{
    private Context context;
    private List< ChatData > items;

    public ChatAdapter( Context context, List< ChatData > items )
    {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount( )
    {
        return items.size();
    }

    @Override
    public Object getItem( int position )
    {
        return items.get( position );
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
            convertView = inflater.inflate( R.layout.row_chat, parent, false );

            holder = new ViewHolder();
            holder.userNameText = convertView.findViewById( R.id.txt_username );
            holder.messageText = convertView.findViewById( R.id.txt_message );

            convertView.setTag( holder );
        } else
            holder = ( ViewHolder ) convertView.getTag();

        holder.reset( position );
        return convertView;
    }

    private class ViewHolder
    {
        public StrokeTextView userNameText, messageText;

        public void reset( int position )
        {
            userNameText.setText( items.get( position ).getUserName() );
            userNameText.setTextColor( items.get( position ).getTextColor() );
            messageText.setText( items.get( position ).getMessage() );
        }
    }
}
