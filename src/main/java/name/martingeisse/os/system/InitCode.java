package name.martingeisse.os.system;

import name.martingeisse.os.application.ApplicationCode;
import name.martingeisse.os.core.Code;
import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.ProcessContext;
import name.martingeisse.os.core.message.dispatcher.ApplicationDispatcher;
import name.martingeisse.os.system.gfx.GraphicsDriverCode;
import name.martingeisse.os.system.gfx.GraphicsRequest;

public class InitCode implements Code {

    @Override
    public void run(ProcessContext context) {

        Process graphicsDriver = context.startChildProcess(new GraphicsDriverCode());
        ApplicationDispatcher dispatcher = new ApplicationDispatcher();
        dispatcher.addMapping(GraphicsRequest.class, graphicsDriver);

        context.startChildProcess(new ApplicationCode(), dispatcher);

    }

}