package com.cidacs.rl.editor.gui;

import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

public class JSpinnerWithBlankValue extends JSpinner {

    private static final long serialVersionUID = -2440181440569958718L;

    private Object blankValue = new Object();

    public JSpinnerWithBlankValue() {
        super();
        setEditor(getEditor());
    }

    public JSpinnerWithBlankValue(SpinnerModel model) {
        super(model);
        setEditor(getEditor());
    }

    @Override
    public void setEditor(JComponent editor) {
        super.setEditor(editor);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor)
                .getTextField();
        AbstractFormatter oldFormatter = tf.getFormatter();
        tf.setFormatterFactory(new AbstractFormatterFactory() {
            @Override
            public AbstractFormatter getFormatter(JFormattedTextField tf) {
                return new AbstractFormatter() {

                    private static final long serialVersionUID = 6825473100581603557L;

                    @Override
                    public String valueToString(Object value)
                            throws ParseException {
                        return blankValue.equals(value) ? ""
                                : oldFormatter.valueToString(value);
                    }

                    @Override
                    public Object stringToValue(String text)
                            throws ParseException {
                        if ("".equals(text))
                            return blankValue;
                        return oldFormatter.stringToValue(text);
                    }
                };
            }
        });
    }

    public Object getBlankValue() {
        return blankValue;
    }

    public void setBlankValue(Object blankValue) {
        this.blankValue = blankValue;
    }

}
