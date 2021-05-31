package recovida.idas.rl.gui.ui.field;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import recovida.idas.rl.gui.lang.MessageProvider;

public class JSpinnerWithBlankValue extends JSpinner {

    private static final long serialVersionUID = -2440181440569958718L;

    private Object blankValue = new Object();
    private String blankText = "\u200b";
    private String decimalFormatPattern;
    private NumberFormat numberFormat;
    private NumberFormatter numberFormatter;

    public JSpinnerWithBlankValue(SpinnerModel model, String decimalFormatPattern) {
        super(model);
        this.decimalFormatPattern = decimalFormatPattern;
        refreshLocaleFormat();
        setEditor(getEditor());
    }
    
    private class CustomFormatter extends NumberFormatter {
        
        // Based on "NumberEditorFormatter"
        
        private static final long serialVersionUID = -6275164348037924598L;
        private final SpinnerNumberModel model;

        CustomFormatter(SpinnerNumberModel model, NumberFormat format) {
            super(format);
            this.model = model;
            setValueClass(model.getValue().getClass());
        }

        @SuppressWarnings("rawtypes")
        public void setMinimum(Comparable min) {
            model.setMinimum(min);
        }

        public Comparable<?> getMinimum() {
            return model.getMinimum();
        }

        @SuppressWarnings("rawtypes")
        public void setMaximum(Comparable max) {
            model.setMaximum(max);
        }

        public Comparable<?> getMaximum() {
            return model.getMaximum();
        }
        
        @Override
        public String valueToString(Object value) throws ParseException {
            return blankValue.equals(value) ? blankText
                                : super.valueToString(value);
        }
        
        @Override
        public Object stringToValue(String text) throws ParseException {
            return blankText.equals(text) ? blankValue : super.stringToValue(text);
        }
    }
    
    public void refreshLocaleFormat() {
        numberFormat = NumberFormat.getInstance(MessageProvider.getLocale());
        if (numberFormat instanceof DecimalFormat)
            ((DecimalFormat) numberFormat).applyPattern(decimalFormatPattern);
        numberFormatter = new CustomFormatter((SpinnerNumberModel) getModel(), numberFormat);
        setEditor(getEditor());
    }

    @Override
    public void setEditor(JComponent editor) {
        super.setEditor(editor);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
    }

    public Object getBlankValue() {
        return blankValue;
    }

    public void setBlankValue(Object blankValue) {
        this.blankValue = blankValue;
    }

    @Override
    public void requestFocus() {
        ((DefaultEditor) getEditor()).getTextField().requestFocus();
    }

}
