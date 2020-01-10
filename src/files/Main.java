package files;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AdminUnitList unit = new AdminUnitList("admin-units.csv");

        System.out.println("\n\n----- NEW QUERY-----\n\n");
        unit.list(System.out,1,100);
        System.out.println("\n\n----- NEW QUERY-----\n\n");
        unit.selectByName("Tomaszów.*i$",true).list(System.out);
        System.out.println("\n\n----- NEW QUERY-----\n\n");
        unit.selectByName("Tomaszów",false).list(System.out);

    }
}