package name.martingeisse.os.system.gfx.message;

public final class DrawRectangleRequest extends GraphicsRequest {

    public final int x, y, w, h;

    public DrawRectangleRequest(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
