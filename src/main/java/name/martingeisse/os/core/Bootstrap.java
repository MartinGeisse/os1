package name.martingeisse.os.core;

public final class Bootstrap {

    private Bootstrap() {
    }

    public static void bootstrap(Code initCode) {
        Process initProcess = new Process(null, initCode);
        initProcess.start();
    }

}
