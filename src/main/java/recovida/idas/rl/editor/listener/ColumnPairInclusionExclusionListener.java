package recovida.idas.rl.editor.listener;

import java.util.EventListener;

public interface ColumnPairInclusionExclusionListener extends EventListener {

    void insertedColumnPair(int index, Object[] columnPairData);

    void deletedColumnPair(int index, Object[] columnPairData);

}
