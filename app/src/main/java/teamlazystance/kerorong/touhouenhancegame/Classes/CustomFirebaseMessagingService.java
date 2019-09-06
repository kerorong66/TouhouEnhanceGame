package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;

/**
 * Created by cps on 2018-04-03.
 */

public class CustomFirebaseMessagingService extends FirebaseMessagingService
{
    private final static String TAG = "FirebaseMessagingServ";

    @Override
    public void onMessageReceived( RemoteMessage remoteMessage )
    {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if ( notification != null )
        {
            String title = notification.getTitle();
            String body = notification.getBody();
            Log.d( TAG, "Received Title: " + title + " / Body: " + body );
        }

        if (remoteMessage.getData() != null)
        {
            try
            {
                receiveNotification(remoteMessage);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void receiveNotification(RemoteMessage message) throws JSONException
    {

    }
}
