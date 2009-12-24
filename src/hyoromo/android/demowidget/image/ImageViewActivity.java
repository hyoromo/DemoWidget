package hyoromo.android.demowidget.image;

import hyoromo.android.demowidget.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_image);

        // Intentで渡された値を取得
        Intent intent = getIntent();
        String path = intent.getStringExtra("PATH");

        // 画像表示
        ImageView img = (ImageView) findViewById(R.id.image_image_img);
        Bitmap bmp = BitmapFactory.decodeFile(path);
        img.setImageBitmap(bmp);
    }

    public void onClickImageImageOk(View v) {
        // 呼び出し元のonActivityResultメソッドで受け取るステータス/データ
        Intent intent = new Intent();
        intent.putExtra("TEXT", "OK!");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
