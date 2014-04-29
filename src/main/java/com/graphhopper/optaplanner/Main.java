package com.graphhopper.optaplanner;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.util.CmdArgs;
import com.graphhopper.util.StopWatch;
import com.graphhopper.util.shapes.GHPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Karich
 */
public class Main {

    public static void main(String[] args) throws IOException {               
        GraphHopper hopper = new GraphHopper().
                init(CmdArgs.read(args)).
                // we only need distance & time
                setEnableInstructions(false).
                importOrLoad();
        new Main().startUI(hopper);
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private void startUI(GraphHopper hopper) {
        String vehicle = hopper.getEncodingManager().getSingle().toString();
        List<GHPoint> list = new ArrayList<GHPoint>();
        list.add(new GHPoint(52.598043, 13.359375));
        list.add(new GHPoint(52.546296, 13.546143));
        list.add(new GHPoint(52.48947, 13.419113));
        list.add(new GHPoint(52.438851, 13.258438));

        int len = list.size();
        double[][] distanceMatrix = new double[len][len];
        long[][] timeMatrix = new long[len][len];
        int counter = 0;
        StopWatch sw = new StopWatch().start();
        for (int fromIndex = 0; fromIndex < len; fromIndex++) {
            distanceMatrix[fromIndex] = new double[len];
            for (int toIndex = 0; toIndex < len; toIndex++) {
                if (fromIndex == toIndex) {
                    distanceMatrix[fromIndex][toIndex] = 0;
                    timeMatrix[fromIndex][toIndex] = 0;
                    continue;
                }

                GHRequest req = new GHRequest(list.get(fromIndex), list.get(toIndex)).
                        setVehicle(vehicle).
                        setWeighting("fastest");

                GHResponse res = hopper.route(req);
                distanceMatrix[fromIndex][toIndex] = res.getDistance();
                timeMatrix[fromIndex][toIndex] = res.getMillis();
                counter++;
            }
            logger.info(Arrays.toString(timeMatrix[fromIndex]));
        }

        logger.info(counter + " routes calculated, took: " + sw.stop().getSeconds());        
        // TODO start optaplanner VRP example UI with created data
    }
}
