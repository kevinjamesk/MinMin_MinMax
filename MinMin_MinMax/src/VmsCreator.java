import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;

;


/**
 * VmsCreator Creates VM Lists as per the User Requirements.
 */
public class VmsCreator {

    //vmlist creator function
    static public ArrayList<Vm> createRequiredVms(int reqVms, int brokerId) {

        ArrayList<Vm> vmlist = new ArrayList<Vm>();
        int[] mips = Config.vmMips;
        long size = Config.vmSize;
        int ram = Config.vmRAM; //vm memory (MB)
        long bw = Config.vmBW;
        int[] pesNumber = Config.vmPes;
        String vmm = "Xen"; //VMM name


        for (int vmid = 0; vmid < reqVms; vmid++) {
            vmlist.add(new Vm(vmid, brokerId, mips[vmid], pesNumber[vmid], ram, bw,
                    size, vmm, new CloudletSchedulerTimeShared()));
        }

        System.out.println("VmsCreator function Executed... SUCCESS:)");
        return vmlist;

    }

}
