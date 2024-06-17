package com.example.sharedbike.service;

import java.util.ArrayList;
import java.util.List;

public class PolygonUtil {

    public static List<float[]> parseCoordinates(String coordinates) {
        List<float[]> polygon = new ArrayList<>();
        String[] points = coordinates.split("#");
        for (String point : points) {
            String[] xy = point.split(",");
            float x = Float.parseFloat(xy[0]);
            float y = Float.parseFloat(xy[1]);
            polygon.add(new float[]{x, y});
        }
        return polygon;
    }

    public static boolean isPointInPolygon(float x, float y, List<float[]> polygon) {
        int intersectCount = 0;
        for (int i = 0; i < polygon.size() - 1; i++) {
            float[] p1 = polygon.get(i);
            float[] p2 = polygon.get(i + 1);
            if (rayIntersectsSegment(x, y, p1, p2)) {
                intersectCount++;
            }
        }
        return (intersectCount % 2) == 1; // odd count means inside polygon
    }

    private static boolean rayIntersectsSegment(float x, float y, float[] p1, float[] p2) {
        if (p1[1] > p2[1]) {
            float[] temp = p1;
            p1 = p2;
            p2 = temp;
        }
        if (y == p1[1] || y == p2[1]) y += 0.0001F;
        if (y < p1[1] || y > p2[1]) return false;
        if (x >= Math.max(p1[0], p2[0])) return false;
        if (x < Math.min(p1[0], p2[0])) return true;

        float m = (p2[0] - p1[0]) / (p2[1] - p1[1]);
        float xIntersect = p1[0] + (y - p1[1]) * m;

        return x < xIntersect;
    }
}
