package spaceInvaders;

import android.app.Activity;
import android.os.Bundle;

public class InvaderActivity  extends Activity {

    SpInView SpInView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(SpInView);
    }

    protected void onResume(){
        super.onResume();
        SpInView.resume();
    }

    protected void onPause(){
        super.onPause();
        SpInView.pause();
    }
}
