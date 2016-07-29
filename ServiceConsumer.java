import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stq on 16-7-29.
 */
public class ServiceConsumer {
    private List<String> serverList = new ArrayList<String>();

    private String serviceName ="service-A";

    //初始化服务地址信息
    public void init(){
        String zkServerList ="127.0.0.1:2181";
        String SERVICE_PATH="/configcenter/"+serviceName;//服务节点路径
        ZkClient zkClient = new ZkClient(zkServerList);

        boolean serviceExists =zkClient.exists(SERVICE_PATH);
        if(serviceExists){
            serverList =zkClient.getChildren(SERVICE_PATH);
        }else{
            throw new RuntimeException("service not exist!");
        }

        //注册事件监听
        zkClient.subscribeChildChanges(SERVICE_PATH,new IZkChildListener(){
            //@Override
            public void handleChildChange(String parentPath, List<String> currentChilds)throws Exception{
                serverList = currentChilds;
            }
        });
    }


    //消费服务
    public void consume(){
        //通过负责均衡算法，得到一台服务器进行调用
        int index = getRandomNum(0,1);
        System.out.println("调用" + serverList.get(index)+"提供的服务：" + serviceName);
    }

    public int getRandomNum(int min,int max){
        Random rdm = new Random();
        return rdm.nextInt(1);
    }

    public static void main(String[] args)throws Exception {
        ServiceConsumer consumer = new ServiceConsumer();

        consumer.init();
        consumer.consume();

    }
}
