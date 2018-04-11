package com.xiaogch.common.netty.example.discard;

import com.xiaogch.common.netty.example.server.DiscardServer;
import org.junit.Test;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/12 17:59 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class DiscardServerTest {

    @Test
    public void testRun() {
        DiscardServer server = new DiscardServer((short) 8080);
        server.run();
    }
}