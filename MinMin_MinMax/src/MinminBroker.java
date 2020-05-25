import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A Broker that schedules Tasks to the VMs
 * as per MinMin Scheduling Policy
 */
public class MinminBroker extends DatacenterBroker {

    public MinminBroker(String name) throws Exception {
        super(name);
    }


    public void scheduleTaskstoVms() {

        int reqVms = vmList.size();

        ArrayList<Cloudlet> clist = new ArrayList<Cloudlet>();
        ArrayList<Vm> vlist = new ArrayList<Vm>();

        for (Cloudlet cloudlet : getCloudletList()) clist.add(cloudlet);
        for (Vm vm : getVmList()) vlist.add(vm);

        double excutionTime[][] = new double[clist.size()][reqVms];
        double R_Time[] = new double[reqVms];

        for (int i = 0; i < clist.size(); i++) {
            for (int j = 0; j < reqVms; j++) {
                excutionTime[i][j] = getCompletionTime(clist.get(i), vlist.get(j));
            }
        }


        while (clist.size() != 0) {


            int minCloudlet = -1;

            double min = Double.MAX_VALUE;
            for (int i = 0; i < clist.size(); i++) {
                int c_id = clist.get(i).getCloudletId();
                for (int j = 0; j < vlist.size(); j++) {
                    if (excutionTime[c_id][j] + R_Time[j] < min) {
                        min = excutionTime[c_id][j] + R_Time[j];
                        minCloudlet = i;
                    }
                }
            }


            double temp[][] = new double[vlist.size()][3];
            int c_id = clist.get(minCloudlet).getCloudletId();

            for (int i = 0; i < vlist.size(); i++) {
                temp[i][0] = excutionTime[c_id][i] + R_Time[i];
                temp[i][1] = R_Time[i];
                temp[i][2] = i;
            }

            Arrays.sort(temp, new Comparator<double[]>() {
                @Override
                public int compare(double[] o1, double[] o2) {
                    if (o1[0] != o2[0]) return Double.compare(o1[1], o2[1]);
                    else return Double.compare(o1[0], o2[0]);
                }
            });

            int minVm = (int) temp[0][2];

            R_Time[minVm] += excutionTime[c_id][minVm];

            bindCloudletToVm(c_id, minVm);
            clist.remove(minCloudlet);

        }


    }


    private double getCompletionTime(Cloudlet cloudlet, Vm vm) {
        double waitingTime = cloudlet.getWaitingTime();
        double execTime = getExecTime(cloudlet, vm);
        double completionTime = execTime + waitingTime;
        return completionTime;
    }

    private double getExecTime(Cloudlet cloudlet, Vm vm) {
        return cloudlet.getCloudletLength() / (vm.getMips() * vm.getNumberOfPes());
    }
}
