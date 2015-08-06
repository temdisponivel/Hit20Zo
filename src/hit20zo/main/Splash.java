package hit20zo.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class Splash extends Scene {
	
	private long _start = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.splash);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		_start = SystemClock.uptimeMillis();
	}
	
	@Override
	protected void update() {
		if (SystemClock.uptimeMillis() - _start > 1000 && update)	{
			this.update = false;
			this.finish();
			this.startActivity(new Intent(this.getApplicationContext(), Menu.class));
		}
	}
}
