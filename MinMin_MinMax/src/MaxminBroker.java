import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;

/**
 * A Broker that schedules Tasks to the VMs
 */
public class MaxminBroker extends DatacenterBroker {

    public MaxminBroker(String name) throws Exception {
        super(name);
        // TODO Auto-generated constructor stub
    }

    //scheduling function


    public void scheduleTaskstoVms() {


        int reqVms = vmList.size();

        ArrayList<Cloudlet> clist = new ArrayList<Cloudlet>();
        ArrayList<Vm> vlist = new ArrayList<Vm>();

        for (Cloudlet cloudlet : getCloudletList()) clist.add(cloudlet);
        for (Vm vm : getVmList()) vlist.add(vm);


        double execTime[][] = new double[clist.size()][reqVms];
        double R_Time[] = new double[reqVms];

        for (int i = 0; i < clist.size(); i++) {
            for (int j = 0; j < reqVms; j++) {
                execTime[i][j] = getExecTime(clist.get(i), vlist.get(j));
            }
        }


        while (clist.size() != 0) {


            double[] minTime = new double[clist.size()];
            int[] vmIndex = new int[clist.size()];

            for (int i = 0; i < clist.size(); i++) {


                int cloundletID = clist.get(i).getCloudletId();
                double min = Double.MAX_VALUE;
                int index = -1;

                for (int j = 0; j < vlist.size(); j++) {
                    if (execTime[cloundletID][j] + R_Time[j] < min) {
                        min = execTime[cloundletID][j] + R_Time[j];
                        index = j;
                    } else if (execTime[cloundletID][j] + R_Time[j] == min) {
                        if (R_Time[j] < R_Time[index]) {
                            index = j;
                        }
                    }
                }

                minTime[i] = min;
                vmIndex[i] = index;
            }


            int index = -1;
            Double max = Double.MIN_VALUE;

            for (int i = 0; i < clist.size(); i++) {
                if (minTime[i] > max) {
                    max = minTime[i];
                    index = i;
                }
            }


            int c_id = clist.get(index).getCloudletId();
            int vm_id = vmIndex[index];

            bindCloudletToVm(c_id, vm_id);

            R_Time[vm_id] += execTime[c_id][vm_id];

            clist.remove(index);

        }


    }


    private double getExecTime(Cloudlet cloudlet, Vm vm) {
        return cloudlet.getCloudletLength() / (vm.getMips() * vm.getNumberOfPes());
    }
}
