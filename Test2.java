import org.I0Itec.zkclient.ZkClient;

/**
 * Created by stq on 16-7-29.
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        String path = "/zk1";
        ZkClient zk = new ZkClient("127.0.0.1:2181");
        for(int i=0;i<100;i++){
            zk.writeData(path,"aa_1");
            Thread.sleep(500);
        }
    }

}
