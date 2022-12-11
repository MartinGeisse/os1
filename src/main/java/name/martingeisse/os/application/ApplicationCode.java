package name.martingeisse.os.application;

import name.martingeisse.os.core.Code;
import name.martingeisse.os.core.ProcessContext;
import name.martingeisse.os.core.message.subscription.ClientSubscriptionCycle;
import name.martingeisse.os.system.gfx.DrawRectangleRequest;
import name.martingeisse.os.system.gfx.KeyInputInitiation;

public class ApplicationCode implements Code {

    @Override
    public void run(ProcessContext context) throws InterruptedException {
        int x = 0;
        ClientSubscriptionCycle keySubscription = context.subscribe(new KeyInputInitiation());
        //noinspection InfiniteLoopStatement
        while (true) {
            while (keySubscription.pollResponse() != null) {
                x = 0;
            }
            context.sendRequest(new DrawRectangleRequest(0, 0, 640, 480, 0, 0, 0));
            context.sendRequest(new DrawRectangleRequest(x, 100, 50, 50, 255, 0, 0));
            //noinspection BusyWait
            Thread.sleep(10);
            x++;
        }
    }

}
