package com.hogan.sample.message;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * wujun
 * 2020/01/06 19:46
 */
public class BatchMessageProducer {

    public static class ListSplitter implements Iterator<List<Message>> {

        private static final int SIZE_LIMIT = 1000 * 1000;

        private final List<Message> messages;

        private int curIndex;

        public ListSplitter(List<Message> messages) {
            this.messages = messages;
        }

        public boolean hasNext() {
            return curIndex < messages.size();
        }


        public List<Message> next() {
            int nextIndex;
            int totalSize = 0;
            for (nextIndex = curIndex; nextIndex < messages.size(); nextIndex++) {
                int tmpSize = 0;
                Message message = messages.get(nextIndex);
                tmpSize += message.getTopic().length();
                tmpSize += message.getBody().length;
                Map<String, String> properties = message.getProperties();
                for (Map.Entry<String, String> entry : properties.entrySet()) {
                    tmpSize += entry.getKey().length();
                    tmpSize += entry.getValue().length();
                }
                if (tmpSize > SIZE_LIMIT) {
                    if (nextIndex == curIndex) {
                        nextIndex++;
                        break;
                    }
                }
                totalSize += tmpSize;
                if (totalSize > SIZE_LIMIT) {
                    break;
                }
            }
            List<Message> subList = messages.subList(curIndex, nextIndex);
            curIndex = nextIndex;
            return subList;
        }
    }

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("wujun");
        producer.setNamesrvAddr("172.16.30.97:9876");
        producer.start();
        String topic = "mro";
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(topic, "tag_a", "order001", "hello_world1".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "tag_a", "order001", "hello_world2".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "tag_a", "order001", "hello_world3".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            producer.send(splitter.next());
        }
        System.out.println("批量发送消息结束！");
        producer.shutdown();
    }
}
