package com.netnam.prime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 08/02/2015.
 */
public class ClusterMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SingleThreadMain.class);
        MasterNodeMain.main(new String[0]);
//        WorkerNodeMain.main(new String[]{"1000"});
//        WorkerNodeMain.main(new String[]{"1001"});
        WorkerNodeMain.main(new String[0]);

    }
}
