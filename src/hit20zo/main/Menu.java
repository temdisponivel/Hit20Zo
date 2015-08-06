package hit20zo.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class Menu extends Scene implements OnClickListener {

	private Button btnStart = null;
	private Button btnQuit = null;
	private CheckBox chkSound = null;
	private CheckBox chkVibrate = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu);
		this.FetchComponents();
		this.BindListner();
	}
	
	private void FetchComponents() {
		btnStart = (Button) this.findViewById(R.id.btn_start);
		btnQuit = (Button) this.findViewById(R.id.btn_quit);
		chkSound = (CheckBox) this.findViewById(R.id.chk_sound);
		chkVibrate = (CheckBox) this.findViewById(R.id.chk_vibrate);
	}
	
	private void BindListner() {
		btnStart.setOnClickListener(this);
		btnQuit.setOnClickListener(this);
	}
	
	@Override
	protected void update() {		
	}

	@Override
	public void onClick(View view) {
		if (view == btnStart) {
			Hit20zo.instance.sound = chkSound.isChecked();
			Hit20zo.instance.vibrate = chkVibrate.isChecked();			
			this.startActivityForResult(new Intent(this.getApplicationContext(), Game.class), 0);
			this.update = false;
		}
		else if (view == btnQuit) {
			this.finish();
			System.exit(0);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Hit20zo.instance.Reset();
	}
}
