package gui;

import java.util.ArrayList;

public class Figure {
    private ArrayList<Line> lines = new ArrayList<>();

    public Figure(ArrayList<Line> figureLines) {
        for (Line line : figureLines){
            lines.add(Line.copy(line));
        }
    }

    public ArrayList<Line> GetLines() {
        return lines;
    }
}
