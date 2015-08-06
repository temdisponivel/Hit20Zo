package hit20zo.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;

public abstract class Scene extends Activity implements Runnable {
	private Handler _handler = null;
	protected boolean update = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Hit20zo();
		_handler = new Handler();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		_handler.post(this);
	}
	
	abstract protected void update();
	
	@Override
	public void run() {
		this.update();
		if (update) _handler.post(this);
	}
}
