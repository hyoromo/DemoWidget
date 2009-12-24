package hyoromo.android.demowidget.image;

import hyoromo.android.demowidget.Log;
import hyoromo.android.demowidget.R;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * GridViewを使った簡単なデモ
 * ・PATHに入っている画像を一覧で表示させます。
 * 
 * @author hyoromo
 */
public class GridViewActivity extends Activity implements AdapterView.OnItemClickListener {
    static final int REQUEST_OK = 1;
    private static final String PATH = "/sdcard/DCIM/Camera/";
    private static Context mContext;
    private static String[] mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_grid);
        mContext = getApplicationContext();

        // 表示画像のパス取得
        imagePath();

        // GridViewに表示させるためのアダプターを作成
        ImageAdapter adapter = new ImageAdapter();
        for (String path : mData) {
            adapter.add(listDatas(path));
        }

        // GridViewを作成してアダプターをセット
        GridView gv = (GridView) findViewById(R.id.image_grid);
        gv.setOnItemClickListener(this);
        gv.setAdapter(adapter);
    }

    /**
     * 画面上に表示させる画像のパスを取得
     */
    private void imagePath() {
        // ディレクトリ下のファイルを全てのパスを取得
        File file = new File(PATH);
        mData = file.list();
    }

    private ListData listDatas(String data) {
        ListData listData = new ListData();
        listData.url = PATH + data;
        listData.bitmap = BitmapFactory.decodeFile(listData.url);

        return listData;
    }

    /**
     * ArrayAdapterを拡張したクラス。 画像を一覧表示させている。
     * 
     * @author hyoromo
     */
    private class ImageAdapter extends ArrayAdapter<ListData> {
        private final LayoutInflater mInflater;

        ImageAdapter() {
            super(mContext, 0);
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * 画面に表示される毎に呼び出される
         */
        final public View getView(int position, View convertView, ViewGroup parent) {
            ListStorage listStorage;
            View row = convertView;

            // Listの画像イメージを作成
            if (row == null || row.getTag() == null) {
                row = mInflater.inflate(R.layout.image_grid_rowdata, parent, false);
                listStorage = new ListStorage();
                listStorage.img = (ImageView) row.findViewById(R.id.image_grid_img);
                listStorage.img.setLayoutParams(new GridView.LayoutParams(85, 85));
                row.setTag(listStorage);
            } else {
                listStorage = (ListStorage) row.getTag();
            }

            // Listに画像設定
            ListData listData = getItem(position);
            listStorage.url = listData.url;
            listStorage.img.setImageBitmap(listData.bitmap);

            return row;
        }
    }

    public final class ListData {
        public String url;
        public Bitmap bitmap;
    }

    private class ListStorage {
        ImageView img;
        String url;
    }

    /**
     * 画像がクリックされたら呼ばれる
     */
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ImageView img = (ImageView) v.findViewById(R.id.image_grid_img);
        ListStorage listStorage = (ListStorage) img.getTag();
        Log.v(listStorage.url);

        // コールバック付きで遷移
        Intent intent = new Intent(mContext, ImageViewActivity.class);
        intent.putExtra("PATH", listStorage.url);
        startActivityForResult(intent, REQUEST_OK);
    }

    /**
     * 遷移先から戻った時に呼び出される
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getStringExtra("TEXT"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }
}
