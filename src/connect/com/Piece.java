package connect.com;
import java.awt.*;

public class Piece {
    private Color color;
    
    public Piece(Color _color, int _value)
    {
        color = _color;
    }
    public Color getColor()
    {
        return (color);
    }
    public void setColor(Color _color)
    {
        color = _color;
    }    
}
