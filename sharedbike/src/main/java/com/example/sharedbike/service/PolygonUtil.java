package com.example.sharedbike.service;

import java.util.ArrayList;
import java.util.List;

public class PolygonUtil {

    // 判断点是否在多边形内部的算法
    public static boolean isPointInPolygon(float x, float y, List<float[]> polygon) {
        int intersectCount = 0;
        for (int i = 0; i < polygon.size(); i++) {
            float[] p1 = polygon.get(i);
            float[] p2 = polygon.get((i + 1) % polygon.size());
            if (((p1[1] > y) != (p2[1] > y)) &&
                    (x < (p2[0] - p1[0]) * (y - p1[1]) / (p2[1] - p1[1]) + p1[0])) {
                intersectCount++;
            }
        }
        return (intersectCount % 2) == 1;
    }

    // 解析经纬度字符串为多边形点列表
    public static List<float[]> parseCoordinates(String coordinates) {
        List<float[]> points = new ArrayList<>();
        String[] coords = coordinates.split("#");
        for (String coord : coords) {
            String[] xy = coord.split(",");
            if (xy.length == 2) {
                points.add(new float[]{Float.parseFloat(xy[0]), Float.parseFloat(xy[1])});
            } else {
                System.err.println("Invalid coordinate format: " + coord);
            }
        }
        // 打印完整的点列表
        for (float[] point : points) {
            System.out.println("[" + point[0] + ", " + point[1] + "]");
        }
        return points;
    }

}

