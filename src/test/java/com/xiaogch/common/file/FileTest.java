package com.xiaogch.common.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/11 15:24 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class FileTest {

    public static void main(String[] args) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("G:\\data\\python").register(watchService , StandardWatchEventKinds.ENTRY_CREATE);

        Thread thread = new Thread(()->{
            try {
                WatchKey watchKey = watchService.take();
                List<File> fileList = new ArrayList<>();

                watchKey.pollEvents().forEach(watchEvent -> {
//                    watchEvent.context().
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
