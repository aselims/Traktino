package co.rahala.traktino;

import android.app.Application;

/**
 * Created by aselims on 12/11/15.
 */
public class TraktinoApp extends Application {

    private TraktinoApp app;

    public TraktinoApp getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }
}
