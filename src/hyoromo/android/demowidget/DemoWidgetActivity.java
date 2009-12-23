package hyoromo.android.demowidget;

import hyoromo.android.demowidget.image.GridViewActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DemoWidgetActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClickMainBtnImage(View v) {
        Intent intent = new Intent(this, GridViewActivity.class);
        startActivity(intent);
    }
}