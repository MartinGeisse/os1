package name.martingeisse.os.system.gfx.message;

public final class SetColorRequest extends GraphicsRequest {

    public final int r, g, b;

    public SetColorRequest(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
