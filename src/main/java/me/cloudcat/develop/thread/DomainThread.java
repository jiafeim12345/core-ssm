package me.cloudcat.develop.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.cloudcat.develop.Constant;
import me.cloudcat.develop.entity.message.OutputObject;
import me.cloudcat.develop.redis.RedisMap;
import me.cloudcat.develop.redis.RedisMapFactory;
import me.cloudcat.develop.service.DomainService;
import me.cloudcat.develop.utils.ThreadUtils;
import me.cloudcat.develop.websocket.handler.ChatWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 域名查询线程
 *
 * @Author: zhenzhong.wang
 * @Time: 2018/3/12 17:34
 */
@Component
public class DomainThread {

    private static Logger logger = LoggerFactory.getLogger("domain");

    @Autowired
    ChatWebSocketHandler socketHandler;

    @Autowired
    DomainService domainService;

    static RedisMapFactory redisFactory;
    static RedisMap domainMap;
    static RedisMap configMap;

    @Autowired
    public void setRedisFactory(RedisMapFactory redisFactory) {
        this.redisFactory = redisFactory;
        domainMap = redisFactory.getRedisMap("domain");
        configMap = redisFactory.getRedisMap("config");
    }

    public void execute() {
        new Worker().start();
    }

    class Worker extends Thread {
        @Override
        public void run() {

            while (ThreadUtils.getObserver() >= 0) {
                // 观察者为0的情况，清空redis中domain
                if (ThreadUtils.getObserver() == 0) {
                    if (domainService.getDomainRecords() != null) {
                        domainService.clearDomain();
                    }
                    ThreadUtils.sleep(10);
                    continue;
                }

                // 观察者数量大于0的情况
                OutputObject opo = new OutputObject();
                String domainStr = "";
                try {
                    domainStr = domainService.getWanWangDomain();
                } catch (Exception e) {
                    opo.setCode(Constant.RESPONSE_CODE_500);
                    opo.setMessage("查询出现异常，请刷新页面并检查Cookie，如果异常仍然存在，请联系球球！");
                    socketHandler.sendMessageToAll(opo);
                    logger.error("查询出现异常，请刷新页面并检查Cookie，如果异常仍然存在，请联系球球\n{}！", e.getMessage());
                    continue;
                }
                JSONObject domainJson = JSON.parseObject(domainStr);
                // 当前域名总数
                Integer currentTotal = (Integer) domainJson.get("Total");

                JSONArray rows = (JSONArray) domainJson.get("Rows");
                // 查询过于频繁,等待六秒钟后查询
                if (currentTotal == 0) {
                    ThreadUtils.sleep(6);
                    ThreadUtils.sleep(ThreadUtils.getMinTime(), ThreadUtils.getMaxTime());
                    continue;
                }
                Integer domainTotal = (Integer) domainMap.get("total");
                if (domainTotal.equals(currentTotal)) {
                    continue;
                }
                // 域名总数增加
                if (currentTotal > domainTotal) {
                    HashMap<String, Object> resultMap = new HashMap<>();
                    // 更新记录总数
                    domainMap.put("total", currentTotal);
                    resultMap.put("Rows", domainService.getNewDomain(rows));
                    resultMap.put("Total", currentTotal.toString());
                    // 更新域名记录
                    domainService.updateDomainRecords(rows);
                    // 封装返回消息
                    opo.setCode(Constant.RESPONSE_CODE_200);
                    opo.setResult(resultMap);
                    socketHandler.sendMessageToAll(opo);
                }
                // 等待后查询
                ThreadUtils.sleep(ThreadUtils.getMinTime(), ThreadUtils.getMaxTime());
            }
        }

    }
}
