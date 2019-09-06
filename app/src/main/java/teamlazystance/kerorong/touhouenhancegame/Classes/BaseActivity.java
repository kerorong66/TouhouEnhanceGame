package teamlazystance.kerorong.touhouenhancegame.Classes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by cps on 2018-04-03.
 */

public class BaseActivity extends AppCompatActivity {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	protected void attachBaseContext (Context newBase) {
		super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
	}
	
	public boolean isSignedIn () {
		return GoogleSignIn.getLastSignedInAccount(this) != null;
	}
}
