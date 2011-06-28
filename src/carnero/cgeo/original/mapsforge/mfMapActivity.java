package carnero.cgeo.original.mapsforge;

import org.mapsforge.android.maps.MapActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import carnero.cgeo.original.mapcommon.MapBase;
import carnero.cgeo.original.map;
import carnero.cgeo.original.mapinterfaces.ActivityImpl;


public class mfMapActivity extends MapActivity implements ActivityImpl {

	private MapBase mapBase;
	
	public mfMapActivity() {
		mapBase = new map(this);
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}
	
	@Override
	protected void onCreate(Bundle icicle) {
		mapBase.onCreate(icicle);
	}

	@Override
	protected void onDestroy() {
		mapBase.onDestroy();
	}

	@Override
	protected void onPause() {
		mapBase.onPause();
	}

	@Override
	protected void onResume() {
		mapBase.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return mapBase.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mapBase.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return mapBase.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onStop() {
		mapBase.onStop();
	}

	@Override
	public void superOnCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean superOnCreateOptionsMenu(Menu menu) {
		return superOnCreateOptionsMenu(menu);
	}

	@Override
	public void superOnDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean superOnOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void superOnResume() {
		super.onResume();
	}

	@Override
	public boolean superOnPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void switchMyLoc(View view) {
		mapBase.switchMyLoc(view);
	}

	@Override
	public void goHome(View view) {
		mapBase.goHome(view);
	}
}
