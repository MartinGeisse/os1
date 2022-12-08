package name.martingeisse.os.system.gfx;

public final class DrawRectangleRequest extends GraphicsRequest {

    public final int x, y, w, h, r, g, b;

    public DrawRectangleRequest(int x, int y, int w, int h, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
        this.g = g;
        this.b = b;
    }

}
