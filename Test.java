import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stq on 16-7-29.
 */
public class Test{
    public static void main(String[] args) throws Exception {
        ZkClient zk = new ZkClient("127.0.0.1:2181");
        zk.subscribeChildChanges("/zk1", new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                if(currentChilds.size()==0){
                    System.out.println("没有节点了");
                }else{
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    for(String str:currentChilds){
                        sb.append(str).append(",");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    sb.append("]");
                    System.out.println(sb.toString());
                }
            }
        });
        zk.subscribeDataChanges("/zk1", new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("数据节点："+dataPath+"的数据变成了"+data);
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("数据节点"+dataPath+"的数据被删除了");
            }
        });
        Thread.sleep(60*1000*60);

    }

}
