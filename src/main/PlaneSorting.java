package main;
import gui.Figure;
import gui.Point;
import java.util.ArrayList;

public class PlaneSorting {
    public static ArrayList<Figure> Sort(ArrayList<Figure> figures) {

        boolean swapped;

        for (int i = 0; i < figures.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < figures.size() - i - 1; j++) {
                boolean performSwap = checkFigurePosition(j, figures);  // checking with plane equation

                if (performSwap) {  // swapping
                    Figure temp = figures.get(j);
                    figures.set(j, figures.get(j + 1));
                    figures.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return figures;
    }

    private static boolean checkFigurePosition(int index, ArrayList<Figure> figures) {
        int sameSide = 0;
        int otherSide = 0;

        double[] plane = figures.get(index).getPlaneEqation();

        ArrayList<Point> points = figures.get(index+1).getPoints();

        int numberOfPoints = points.size();

        for (int i = 0; i < numberOfPoints; i++)
        {
            double location = plane[0] * points.get(i).getX() + plane[1] * points.get(i).getY()
                    + plane[2] * points.get(i).getZ() + plane[3];

            if (location * plane[3] > 0)
            {
                sameSide++;
            }
            else {
                otherSide++;
            }
        }

        if (sameSide == numberOfPoints) {
            return false;
        } else if (otherSide == numberOfPoints) {
            return true;
        } else {
            return checkReverse(index+1, figures);
        }
    }

    private static boolean checkReverse(int index, ArrayList<Figure> figures) {
        int sameSide = 0;

        double[] plane = figures.get(index).getPlaneEqation();

        double[] observer = new double[]{0, 0, 0, 1};
        double locationObserver = plane[0] * observer[0] + plane[1] * observer[1]
                + plane[2] * observer[2] + plane[3] * observer[3];

        ArrayList<Point> points = figures.get(index-1).getPoints();

        int numberOfPoints = points.size();
        for (int i = 0; i < numberOfPoints; i++)
        {
            double location = plane[0] * points.get(i).getX() + plane[1] * points.get(i).getY()
                    + plane[2] * points.get(i).getZ() + plane[3];

            if (location * locationObserver >= 0)
            {
                sameSide++;
            }
        }

        return sameSide == numberOfPoints;
    }
}