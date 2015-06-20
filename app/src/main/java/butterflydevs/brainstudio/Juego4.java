package butterflydevs.brainstudio;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterflydevs.brainstudio.extras.MyCustomDialog;


public class Juego4 extends FragmentActivity {


    private Button launchFragment, launchFragment2, launchFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego4);

        launchFragment = (Button) findViewById(R.id.fragmentButton);
        launchFragment2 = (Button) findViewById(R.id.fragmentButton2);
        launchFragment3 = (Button) findViewById(R.id.fragmentButton3);



        launchFragment3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                MyCustomDialog fragment1 = new MyCustomDialog();
               // fragment1.mListener = MainActivity.this;
                fragment1.text = "nombre";
                fragment1.juego=1;
                fragment1.nivel=2;
                fragment1.show(getFragmentManager(), "");
            }

        });
    }


    //Método que se puede llamar desde MyCustomDialog

    public void submitButtonClick(){
        Toast.makeText(this, "Yeeaahhh", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego4, menu);
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

}
