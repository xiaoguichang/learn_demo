package com.xiaogch.es;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/2 19:51 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */

@Component
public class EsAddIndex {

    @Autowired
    private EsTransportClient esTransportClient;

    public void addIndex() {
        TransportClient transportClient = esTransportClient.getTransportClient();

        transportClient.prepareIndex().setSource();
    }
}
