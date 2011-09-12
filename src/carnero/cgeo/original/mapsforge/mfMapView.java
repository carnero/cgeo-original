package carnero.cgeo.original.mapsforge;

import org.mapsforge.android.maps.GeoPoint;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.MapViewMode;
import org.mapsforge.android.maps.Overlay;
import org.mapsforge.android.maps.Projection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import carnero.cgeo.original.libs.Settings;
import carnero.cgeo.original.mapcommon.MapOverlay;
import carnero.cgeo.original.mapcommon.UsersOverlay;
import carnero.cgeo.original.mapinterfaces.GeoPointImpl;
import carnero.cgeo.original.mapinterfaces.MapControllerImpl;
import carnero.cgeo.original.mapinterfaces.MapProjectionImpl;
import carnero.cgeo.original.mapinterfaces.MapViewImpl;
import carnero.cgeo.original.mapinterfaces.OverlayImpl;

public class mfMapView extends MapView implements MapViewImpl {

	public mfMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void draw(Canvas canvas) {
		try {
			if (getMapZoomLevel() >= 22) { // to avoid too close zoom level (mostly on Samsung Galaxy S series)
				getController().setZoom(22);
			}

			super.draw(canvas);
		} catch (Exception e) {
			Log.e(Settings.tag, "cgMapView.draw: " + e.toString());
		}
	}

	@Override
	public void displayZoomControls(boolean takeFocus) {
		// nothing to do here
	}

	@Override
	public MapControllerImpl getMapController() {
		return new mfMapController(getController());
	}

	@Override
	public GeoPointImpl getMapViewCenter() {
		GeoPoint point = getMapCenter();
		return new mfGeoPoint(point.getLatitudeE6(), point.getLongitudeE6());
	}

	@Override
	public void addOverlay(OverlayImpl ovl) {
		getOverlays().add((Overlay)ovl);
	}

	@Override
	public void clearOverlays() {
		getOverlays().clear();
	}

	@Override
	public MapProjectionImpl getMapProjection() {
		return new mfMapProjection(getProjection());
	}

	@Override
	public MapOverlay createAddMapOverlay(Settings settings,
			Context context, Drawable drawable, boolean fromDetailIntent) {
		
		mfCacheOverlay ovl = new mfCacheOverlay(settings, context, drawable, fromDetailIntent);
		getOverlays().add(ovl);
		return ovl.getBase();
	}
	
	@Override
	public UsersOverlay createAddUsersOverlay(Context context, Drawable markerIn) {
		mfUsersOverlay ovl = new mfUsersOverlay(context, markerIn);
		getOverlays().add(ovl);
		return ovl.getBase();
	}

	@Override
	public int getLatitudeSpan() {

		Projection projection = getProjection();
		
		GeoPoint low = projection.fromPixels(0, 0);
		GeoPoint high = projection.fromPixels(0, getHeight());

		return Math.abs(high.getLatitudeE6() - low.getLatitudeE6());
	}

	@Override
	public int getLongitudeSpan() {
		Projection projection = getProjection();
		
		GeoPoint low = projection.fromPixels(0, 0);
		GeoPoint high = projection.fromPixels(getWidth(), 0);

		return Math.abs(high.getLongitudeE6() - low.getLongitudeE6());
	}

	@Override
	public boolean isSatellite() {
		return false;
	}

	@Override
	public void preLoad() {
		// Nothing to do here
	}

	@Override
	public void setSatellite(boolean b) {
		// Nothing to do here
	}

	@Override
	public int getMapZoomLevel() {
		return getZoomLevel();
	}

	@Override
	public boolean needsScaleOverlay() {
		return false;
	}

	@Override
	public void setBuiltinScale(boolean b) {
		setScaleBar(b);
	}

	@Override
	public void setMapSource(Settings settings) {

		setMapViewMode(MapViewMode.MAPNIK_TILE_DOWNLOAD);
		
		switch(settings.mapProvider) {
			case mapsforgeMapnik:
				// is default
				break;
			case mapsforgeOsmarender:
				setMapViewMode(MapViewMode.OSMARENDER_TILE_DOWNLOAD);
				break;
			case mapsforgeOffline:
				try {
					setMapViewMode(MapViewMode.CANVAS_RENDERER);
					super.setMapFile(settings.getMapFile());
				} catch (Exception e) {
					Log.e(Settings.tag, "mfMapView.setMapSource: " + e.toString());
				}
		}
	}
}
