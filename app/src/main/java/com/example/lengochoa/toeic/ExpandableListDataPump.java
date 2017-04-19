package com.example.lengochoa.toeic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LeNgocHoa on 2017-03-08.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData(String temp) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> test = new ArrayList<String>();
        test.add("Test 1");
        test.add("Test 2");
        test.add("Test 3");
        test.add("Test 4");

        expandableListDetail.put(temp, test);
        return expandableListDetail;
    }
}
