package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private TextView myText;
    private Button button, button2, button3, button4, button5, button6;
    int totalPresses = 0;
    private static int PRAG = 4;
    boolean isServiceActive = false;
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("presses")) {
                totalPresses = savedInstanceState.getInt("presses");
            } else {
                savedInstanceState.putInt("presses", totalPresses);
            }
        }

        intentFilter.addAction("button.text");

        Toast.makeText(this, "Total number of button presses: " + totalPresses, Toast.LENGTH_LONG).show();

        myText = (TextView)findViewById(R.id.myText);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);

        button.setOnClickListener(buttonClickListener);
        button2.setOnClickListener(buttonClickListener);
        button3.setOnClickListener(buttonClickListener);
        button4.setOnClickListener(buttonClickListener);
        button5.setOnClickListener(buttonClickListener);
        button6.setOnClickListener(buttonClickListener);
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            totalPresses++;

            switch(view.getId()) {
                case R.id.button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                    String newScreenText = myText.getText().toString();
                    intent.putExtra("text", newScreenText);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.button2:
                    String text = button2.getText().toString();
                    myText.setText(myText.getText() + text + ", ");
                    break;
                case R.id.button3:
                    String text2 = button3.getText().toString();
                    myText.setText(myText.getText() + text2 + ", ");
                    break;
                case R.id.button4:
                    String text3 = button4.getText().toString();
                    myText.setText(myText.getText() + text3 + ", ");
                    break;
                case R.id.button5:
                    String text4 = button5.getText().toString();
                    myText.setText(myText.getText() + text4 + ", ");
                    break;
                case R.id.button6:
                    String text5 = button6.getText().toString();
                    myText.setText(myText.getText() + text5 + ", ");
                    break;
           }

            if (totalPresses > PRAG && !isServiceActive) {
                isServiceActive = true;
                Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent2.putExtra("text", myText.getText());
                getApplicationContext().startService(intent2);
                registerReceiver(messageBroadcastReceiver, intentFilter);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            myText.setText("");
            totalPresses = 0;
            isServiceActive = false;
            Intent intent2 = new Intent(this, PracticalTest01Service.class);
            stopService(intent2);
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent2 = new Intent(this, PracticalTest01Service.class);
        isServiceActive = false;
        stopService(intent2);
        super.onDestroy();
    }
}