/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jzendle
 */
public class SectionFile {

    public static Map<String, List<String>> parse(String fileName) throws IOException {
        Map map = new HashMap();

        try (BufferedReader buff = new BufferedReader(new FileReader(fileName))) {
            String line;
            List list = new ArrayList(); // have list for any unassociated lines up top - discarded
            while ((line = buff.readLine()) != null) {

                if (line.trim().startsWith("#")) {
                    continue;
                }

                if (line.startsWith("[") && line.trim().endsWith("]")) {
                    String key = line.substring(1, line.trim().length() - 1);
                    list = new ArrayList();
                    map.put(key, list);

                } else {
                    list.add(line);
                }

            }
        }
        
        return map;
    }
}
