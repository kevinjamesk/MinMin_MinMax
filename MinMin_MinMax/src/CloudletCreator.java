import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

import java.util.ArrayList;

/**
 * CloudletCreator Creates Cloudlets as per the User Requirements.
 * More short tasks then long tasks which makes max-min outperrform min-min
 */
public class CloudletCreator {


    static public ArrayList<Cloudlet> createUserCloudlet(int reqTasks, int brokerId) {
        ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>();


        int pesNumber = 1;
        long[] length = Config.cloudLetLength;
        long fileSize = Config.cloudLetFileSize;
        long outputSize = Config.outPutSize;;
        UtilizationModel utilizationModel = new UtilizationModelFull();


        for (int id = 0; id < reqTasks; id++) {
            Cloudlet task = new Cloudlet(id, length[id], pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            task.setUserId(brokerId);
            cloudletList.add(task);
        }

        System.out.println("SUCCESSFULLY Cloudletlist created :)");

        return cloudletList;

    }

}