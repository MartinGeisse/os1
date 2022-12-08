package name.martingeisse.os;

import name.martingeisse.os.core.Bootstrap;
import name.martingeisse.os.system.InitCode;

public class Main {

    public static void main(String[] args) {
        Bootstrap.bootstrap(new InitCode());
    }

}
