package hit20zo.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameOver extends Scene implements OnClickListener {
	
	private ImageView imgLose = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.gameover);
		this.FetchComponents();
		this.update = false;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		imgLose.setImageResource(R.drawable.hole_with_20zo);
	}
	
	private void FetchComponents() {
		imgLose = (ImageView) this.findViewById(R.id.img_lose);
		LinearLayout linear = (LinearLayout) this.findViewById(R.id.linear_gameover);
		linear.setOnClickListener(this);
	}

	@Override
	protected void update() {}

	@Override
	public void onClick(View v) {
		this.finish();
	}
}
