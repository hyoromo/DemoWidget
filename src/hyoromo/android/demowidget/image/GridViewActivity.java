package hyoromo.android.demowidget.image;

import hyoromo.android.demowidget.R;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewActivity extends Activity {
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
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.image_grid_rowdata, mData);
        IconicAdapter adapter = new IconicAdapter();
        for (String path : mData) {
            adapter.add(listDatas(path));
        }

        // GridViewを作成してアダプターをセット
        GridView gv = (GridView) findViewById(R.id.image_grid);
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
    private class IconicAdapter extends ArrayAdapter<ListData> {
        private final LayoutInflater mInflater;

        IconicAdapter() {
            super(mContext, 0);
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * 画面に表示される毎に呼び出される
         * 
         * @param position
         *            : 表示する対象Listの一覧を上から数えたときの番号
         * @param convertView
         *            : 表示する対象ListのView
         * @param parent
         *            : 知らん
         */
        final public View getView(int position, View convertView, ViewGroup parent) {
            ListStorage listStorage;
            View row = convertView;

            // Listの画像イメージを作成
            if (row == null || row.getTag() == null) {
                row = mInflater.inflate(R.layout.image_grid_rowdata, parent, false);
                listStorage = new ListStorage();
                listStorage.img = (ImageView) row.findViewById(R.id.image_grid_img);
                row.setTag(listStorage);
            } else {
                listStorage = (ListStorage) row.getTag();
            }

            // Listに画像設定
            ListData listData = getItem(position);
            if (listData != null) {
                if (listData != null && listStorage.img != null) {
                    listStorage.img.setImageBitmap(listData.bitmap);
                    listStorage.url = listData.url;
                } else {
                    listStorage.img.setImageBitmap(null);
                    listStorage.url = "";
                }
            }

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
}
