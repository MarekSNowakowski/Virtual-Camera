package main;

import java.awt.*;
import java.util.ArrayList;

import gui.Figure;
import gui.Line;
import gui.Point;
import java.io.*;

public class FigureLoader {
    public static ArrayList<Figure> loadFromFile(String filename) {
        try {
            var fileStream = new FileInputStream(filename);
            var inputStream = new DataInputStream(fileStream);
            var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            String[] splited;

            ArrayList<Line> lines = new ArrayList<>();
            ArrayList<Figure> figures = new ArrayList<>();
            Point start, end;
            Color color = new Color((int)(Math.random() * 0x1000000));
            double[] d = new double[6];
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() == 0) {
                    Figure figure = new Figure(lines);
                    figure.color = color;
                    figures.add(figure);
                    lines.clear();
                    continue;
                }
                if (line.length() == 1) {
                    color = new Color((int)(Math.random() * 0x1000000));
                    lines.clear();
                    continue;
                }
                splited = line.split(";");

                for (int i=0; i<6; i++) {
                    d[i] = Double.parseDouble(splited[i]);
                }
                start = new Point(d[0], d[1], d[2]);
                end = new Point(d[3], d[4], d[5]);

                lines.add(new Line(start, end));
            }
            inputStream.close();
            fileStream.close();
            return figures;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
}