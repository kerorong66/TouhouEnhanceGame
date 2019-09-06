package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by cps on 2018-04-03.
 */

public class CustomFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private final static String TAG = "FirebaseInstanceIDServ";

    @Override
    public void onTokenRefresh() {
        Log.d(TAG, FirebaseInstanceId.getInstance().getToken());

        // TODO: 이후 생성등록된 토큰을 서버에 보내 저장해 두었다가 추가 작업을 할 수 있도록 한다.
    }
}
