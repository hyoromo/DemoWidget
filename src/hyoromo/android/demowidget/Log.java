package hyoromo.android.demowidget;

public class Log {
    public static final String TAG = "DemoWidget";

    public static void v(String msg) {
        android.util.Log.v(TAG, msg);
    }

    public static void d(String msg) {
        android.util.Log.d(TAG, msg);
    }

    public static void i(String msg) {
        android.util.Log.i(TAG, msg);
    }

    public static void w(String msg) {
        android.util.Log.w(TAG, msg);
    }

    public static void e(String msg) {
        android.util.Log.e(TAG, msg);
    }
}