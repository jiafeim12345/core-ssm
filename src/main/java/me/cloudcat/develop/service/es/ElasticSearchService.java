package me.cloudcat.develop.service.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ElasticSearch业务类
 *
 * @Author: jiafeim12345@163.com
 * @Date: 2018/5/2/002 21:06
 */
@Service
public class ElasticSearchService {

  private Logger logger = LoggerFactory.getLogger("elasticsearch");

  private static final String HOST = "47.98.34.171";

  // http请求的端口是9200，客户端是9300
  private static final int PORT = 9300;

  TransportClient client = null;

  /**
   * 建立es连接
   *
   * @throws UnknownHostException
   */
  public void getConnection() throws UnknownHostException {
    client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(
        new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));
    logger.info("Elasticsearch connect info:" + client.toString());
  }

  /**
   * 关闭连接
   */
  public void close() {
    if (client != null) {
      client.close();
      logger.info("连接断开...");
    }
  }

  /**
   * 创建索引库
   */
  public void addIndex() throws IOException {
    IndexResponse response = client.prepareIndex("core", "user", "1")
        .setSource(XContentFactory.jsonBuilder().startObject()
            .field("username", "张三")
            .field("desc", "建筑工程师")
            .field("age", 20)
            .endObject()).get();
    logger.info("索引名称：" + response.getIndex() + " 类型：" + response.getType());
  }

}
