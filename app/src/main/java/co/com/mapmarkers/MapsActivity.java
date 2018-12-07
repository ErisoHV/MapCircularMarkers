package co.com.mapmarkers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public static Bitmap getCircledBitmap(String hexColor, int diameter) {
        Bitmap output = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, output.getWidth(), output.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(Color.parseColor(hexColor));
        canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(output, rect, rect, paint);
        return output;
    }

    public Bitmap addBorderToCircularBitmap(Bitmap srcBitmap, int borderWidth, int borderColor){
        int dstBitmapWidth = srcBitmap.getWidth() +  borderWidth * 2;
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawBitmap(srcBitmap, borderWidth, borderWidth, null);
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setAlpha(60);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getWidth() / 2, // cy
                canvas.getWidth()/2 - borderWidth / 2, // Radius
                paint // Paint
        );
        srcBitmap.recycle();
        return dstBitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        LatLng simpsonDesert = new LatLng(-24.57000, 137.4200);
        LatLng adelaida = new LatLng(-34.9288666, 138.59863);

        Bitmap bmp = getCircledBitmap("#cb42f4", 25);
        bmp = addBorderToCircularBitmap(bmp, 3, Color.BLACK);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp)).title("Sydney"));

        bmp = getCircledBitmap("#ff7c26", 25);
        bmp = addBorderToCircularBitmap(bmp, 3, Color.BLACK);
        mMap.addMarker(new MarkerOptions().position(simpsonDesert)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp)).title("Simpson desert"));

        bmp = getCircledBitmap("#23f7ff", 25);
        bmp = addBorderToCircularBitmap(bmp, 3, Color.BLACK);
        mMap.addMarker(new MarkerOptions().position(adelaida)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp)).title("Adelaida"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
