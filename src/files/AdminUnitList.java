package files;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    Map<Long,AdminUnit> longToUnitId = new HashMap<>();
    Map<AdminUnit,Long> unitToLongId = new HashMap<>();
    Map<AdminUnit,Long> unitToParentId = new HashMap<>();
    Map<Long,List<AdminUnit>> parentIdToChild = new HashMap<>();


    AdminUnitList(String filename) throws IOException {
        read(filename);
    }

    AdminUnitList() {}

    public void read (String filename) throws IOException {

        CSVReader reader = new CSVReader(filename,",",true);
        while (reader.next()) {
            AdminUnit unit = new AdminUnit(reader.get(2), 0, 0, 0, 0);
            long unitID = reader.getLong(0);
            Long parentId = null;
            if (!reader.get(3).equals(""))
                unit.adminLevel = reader.getInt(3);
            if (!reader.get(4).equals(""))
                unit.population = reader.getDouble(4);
            if (!reader.get(5).equals(""))
                unit.area = reader.getDouble(5);
            if (!reader.get(6).equals(""))
                unit.density = reader.getDouble(6);
            units.add(unit);
            if (!reader.get(1).equals(""))
                parentId = reader.getLong(1);

            longToUnitId.put(unitID, unit);
            unitToLongId.put(unit,unitID);

            List<AdminUnit> children = new ArrayList<>();

            if (parentId != null) {
                unitToParentId.put(unit, parentId);

                if (parentIdToChild.get(parentId) == null) {
                    children.add(unit);
                    parentIdToChild.put(parentId, children);
                }
                else {
                    parentIdToChild.get(parentId).add(unit);
                }
            }
            else
                unitToParentId.put(null, parentId);
        }

        for(var unit : units) {
            unit.parent = longToUnitId.get(unitToParentId.get(unit));
            unit.children = parentIdToChild.get(unitToLongId.get(unit));
        }

        for(var unit : units) {
            if (unit.density == 0 || unit.population == 0)
                unit.fixMissingValues();
        }
    }

    void list(PrintStream out){
        for (var unit : units) {
            out.println(unit.toString());
        }
    }

    void list(PrintStream out,int offset, int limit ){
        for (int i = offset - 1; i < limit + offset - 1; i++)
            out.println(units.get(i).toString());
    }

    AdminUnitList selectByName(String pattern, boolean regex) {
        AdminUnitList ret = new AdminUnitList();

        for (var unit : units) {
            if (regex) {
                if (unit.name.matches(pattern))
                    ret.units.add(unit);
            } else {
                if (unit.name.contains(pattern))
                    ret.units.add(unit);
            }
        }

        return ret;
    }
}

