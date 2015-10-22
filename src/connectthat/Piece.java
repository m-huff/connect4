package connectthat;
import java.awt.*;

public class Piece {
    private Color color;
    
    Piece(Color _color, int _value)
    {
        color = _color;
    }
    Color getColor()
    {
        return (color);
    }
    void setColor(Color _color)
    {
        color = _color;
    }    
}
