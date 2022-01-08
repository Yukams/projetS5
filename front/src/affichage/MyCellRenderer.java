package affichage;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MyCellRenderer extends DefaultTableCellRenderer {

    public MyCellRenderer() {

        super();
    }

    @Override
    public Color getSelectionBackground(){
        return (null);
    }
}