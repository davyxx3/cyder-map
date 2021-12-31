package cyderx.com.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartPageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = new Thread(() -> {
            try {
                // 使程序休眠一秒
                Thread.sleep(1000);
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        myThread.start();//启动线程
    }
}
