package com.xiaogch.es;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/2 19:46 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Component
public class EsTransportClient {

    protected static final Logger LOGGER = LogManager.getLogger(EsTransportClient.class);

    @Value("${es.connect.str}")
    private String connectStr;

    @Value("${es.cluster.name}")
    private String clusterName;

    private TransportClient transportClient;

    @PostConstruct
    public void init() throws UnknownHostException {
//        getTransportClient();
    }

    /**
     * 获取elasticsearch 客户端
     * @return
     */
    public TransportClient getTransportClient() {
        Assert.hasText(connectStr , "connectStr must be not null or empty");
        Assert.hasText(clusterName , "clusterName must be not null or empty");
        LOGGER.info("getTransportClient begin , connectStr={} , clusterName={}" ,connectStr , clusterName);
        if (transportClient != null) {
            LOGGER.info("getTransportClient end , TransportClient={}" ,transportClient);
            return transportClient;
        }

        synchronized (this) {
            if (transportClient != null) {
                LOGGER.info("getTransportClient end , TransportClient={}" ,transportClient);
                return transportClient;
            }
            String[] connects = connectStr.split(",");
            Settings settings = Settings.builder().put("cluster.name", clusterName).build();
            transportClient = new PreBuiltTransportClient(settings);
            for (String temp : connects) {
                String[] ipAndPort = temp.split(":");
                if (ipAndPort.length != 2) {
                    break;
                }
                String host = ipAndPort[0];
                int port = Integer.valueOf(ipAndPort[1]);
                InetSocketAddress address = new InetSocketAddress(host , port);
                transportClient.addTransportAddress( new TransportAddress(address));
            }
            return transportClient;
        }
    }
}
