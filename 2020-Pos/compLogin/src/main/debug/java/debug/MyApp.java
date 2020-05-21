package debug;

import com.billy.cc.core.component.CC;
import com.mike.baselib.base.BaseApplication;

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
public class MyApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }
}
