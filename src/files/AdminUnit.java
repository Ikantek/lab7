package files;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children;

    AdminUnit(String newName, int newLevel, double newPopulation, double newArea, double newDensity) {
        name = newName;
        adminLevel = newLevel;
        population = newPopulation;
        area = newArea;
        density = newDensity;
    }

    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("Name: " + name + "\n   ");
        text.append("Population: " + population + "\n      ");
        text.append("Area: " + area + "\n         ");
        text.append("Density: " + density +"\n            ");
        if (parent != null)
            text.append("Parent: " + parent.name + "\n               ");
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                if (i == 0) {
                    text.append("Children: " + children.get(i).name + ", ");
                    i++;
                }
                else
                    text.append(children.get(i).name + ", ");
            }
            text.append("\n");
        }

        return text.toString();
    }

    public void fixMissingValues() {
        try{
            if (parent.density != 0) {
                density = parent.density;
                population = density * area;
            } else {
                parent.fixMissingValues();
                fixMissingValues();
            }
        }
        catch (Exception ex){
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, "There is no parent for this object.", ex);
        }
    }
}
