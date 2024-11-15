package com.ylqm.codeshelf.gateway.utils;

import java.util.Map;
import java.util.SortedMap;

public class XmlUtils {

    public static String mapToXml(SortedMap<String, String> sortedMap) {
        StringBuffer sb = new StringBuffer("<Response>");
        for (Map.Entry<String, String> enrty : sortedMap.entrySet()) {
            sb.append("<" + enrty.getKey() + ">");
            sb.append(enrty.getValue());
            sb.append("</" + enrty.getKey() + ">");
        }
        sb.append("</Response>");
        return sb.toString();
    }
}
