package butterflydevs.brainstudio;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;


public class eliminame extends ActionBarActivity {

    private RoundCornerProgressBar progressTwo;
    private int progress1 = 5;
    private int progress2 = 100;

    private Handler handler;

    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminame);


        progressTwo = (RoundCornerProgressBar) findViewById(R.id.progress_two);
        progressTwo.setBackgroundColor(Color.TRANSPARENT);


        updateProgressTwo();
        handler = new Handler();
        r = new Runnable(){

            int currV = 0;
            boolean go = true;

            public void run(){

                /*
                if(currV == 60 && go)
                    go = false;
                else if(currV == -60 && !go)
                    go = true;

                if(go)
                    currV++;
                else
                    currV--;
                */
                if(currV<46)
                    currV++;



                updateProgressTwo();
                //  meter2.setValues(currV, currV*2, currV*3);

                handler.postDelayed(this, 300);

            }
        };

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eliminame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateProgressTwo() {
        progress2-=1;
        progressTwo.setProgress(progress2);
        progressTwo.setSecondaryProgress(progress2 + 2);
        System.out.println(progress2);
        updateProgressTwoColor();
        if(progress2==0)
            handler.removeCallbacks(r);
    }
    private void updateProgressTwoColor() {
        if(progress2 <= 20) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress2 > 20 && progress2 <= 50) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress2 > 50) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(r, 300);
    }
    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(r);
    }

}
