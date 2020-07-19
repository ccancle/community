package com.nowcoder.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @program: community
 * @description: 阻塞队列测试类
 * @author: Macchac
 * @create: 2020-07-16 15:39
 **/
public class BlockingQueueTests {
    public static void main(String[] args) {
        //底层数组 定义长度10
        BlockingQueue queue = new ArrayBlockingQueue(10);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();


    }
}

/**
 * 生产者线程
 */
class Producer implements Runnable{

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i= 0;i<100;i++){
                //每隔20毫秒生成一个数
                Thread.sleep(20);
                queue.put(i);
                System.out.println(Thread.currentThread().getName()+"生产：" +queue.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
/**
 * 消费者线程
 * */
class Consumer implements Runnable{
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true){
                //随机间隔xx时间 消费者大概率比生产者消费能力若
                Thread.sleep(new Random().nextInt(1000));
                queue.take();
                System.out.println(Thread.currentThread().getName()+"消费" + queue.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}