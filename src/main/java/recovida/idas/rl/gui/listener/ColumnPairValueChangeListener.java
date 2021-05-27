package recovida.idas.rl.gui.listener;

import java.util.EventListener;

public interface ColumnPairValueChangeListener extends EventListener {

    void changed(int rowIndex, String key, Object value);
}
