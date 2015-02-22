package bg.uni_sofia.fmi.oopjava.project.exceptions;
/**
 * Disclaimer: Mostly NOT my code!
 * I found it on the Internet and edited it to suit my needs.
 * Link to the original file can be found here:
 * https://svn.apache.org/repos/asf/openjpa/trunk/openjpa-examples/openbooks/src/main/java/jpa/tools/swing/ErrorDialog.java
*/


/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * A dialog to display runtime error.
 * 
 * @author Pinaki Poddar
 *
 */

@SuppressWarnings("serial")
public class ErrorDialog extends JDialog {
    private static Dimension MESSAGE_SIZE    = new Dimension(500,130);
    private static Dimension STACKTRACE_SIZE = new Dimension(500,180);
    private static Dimension TOTAL_SIZE      = new Dimension(500,350);
    
    
    static String NEWLINE = "\r\n";
    static String INDENT  = "    ";
    
    private boolean      _showingDetails;
    private JComponent _message;
    private JComponent   _main;
    private JScrollPane  _details;
    private JTextPane    _stacktrace;
    private final Throwable _error;
    
    /**
     * Creates a modal dialog to display the given exception message.
     * 
     * @param t the exception to display
     */
    public ErrorDialog(Throwable t) {
        this(null, null, t);
    }

    /**
     *
     * @param owner
     * @param t
     */
    public ErrorDialog(JComponent owner, Throwable t) {
        this(owner, null, t);
    }
    
    /**
     * Creates a modal dialog to display the given exception message.
     * 
     * @param owner if non-null, then the dialog is positioned (centered) w.r.t. this component
     * @param icon
     * @param t the exception to display
     */
    public ErrorDialog(JComponent owner, Icon icon, Throwable t) {
        super();
        setResizable(false);
        setTitle(t.getClass().getName());
        setModal(true);
        if (icon != null && icon instanceof ImageIcon) 
            setIconImage(((ImageIcon)icon).getImage());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        _error = t;
        _message = createErrorMessage(_error);
        _main    = createContent();
        getContentPane().add(_main);

        pack();
        setLocationRelativeTo(owner);
    }
    
    /**
     * Creates the display with the top-level exception message 
     * followed by a pane (that toggles) for detailed stack traces.
     *  
     * @param t a non-null exception
     */
    JComponent createContent() {
        final JButton showDetails = new JButton("Show Details >>");
        showDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_showingDetails) {
                     _main.remove(_details);
                     _main.validate();
                     _main.setPreferredSize(MESSAGE_SIZE);
                } else {
                    if (_details == null) {
                        _details = createDetailedMessage(_error);
                        StringBuilder buffer = new StringBuilder();
                        _stacktrace.setText(generateStackTrace(_error, buffer).toString());
                        _stacktrace.setBackground(_main.getBackground());
                        _stacktrace.setPreferredSize(STACKTRACE_SIZE);
                    }
                    _main.add(_details, BorderLayout.CENTER);
                    _main.validate();
                    _main.setPreferredSize(TOTAL_SIZE);
                }
                _showingDetails = !_showingDetails;
                showDetails.setText(_showingDetails ? "<< Hide Details" : "Show Details >>");
                ErrorDialog.this.pack();
            }
        });
        JPanel messagePanel = new JPanel();
      
        _message.setBackground(messagePanel.getBackground());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(showDetails);
        buttonPanel.add(Box.createHorizontalGlue());
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        messagePanel.add(_message, BorderLayout.CENTER);
        messagePanel.add(buttonPanel, BorderLayout.SOUTH);
        messagePanel.setPreferredSize(MESSAGE_SIZE);
        
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.add(messagePanel, BorderLayout.NORTH);
        return main;
    }
    
    /**
     * Creates a non-editable widget to display the error message.
     * 
     */
    JComponent createErrorMessage(Throwable t) {
        String txt = t.getLocalizedMessage();
        txt = txt + "\n" + "Please send the details to the developer!";
        JEditorPane message = new JEditorPane();
        message.setFont(new Font("Verdana", Font.BOLD, 11));
        message.setForeground(Color.RED);
        message.setContentType("text/plain");
        message.setEditable(false);
        message.setText(txt);
        return message;
    }
    
    /**
     * Creates a non-editable widget to display the detailed stack trace.
     */
    JScrollPane createDetailedMessage(Throwable t) {
        _stacktrace = new JTextPane();
        _stacktrace.setEditable(false);
        _stacktrace.setForeground(Color.red);
        JScrollPane pane = new JScrollPane(_stacktrace, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        return pane;
    }
    
    /**
     * Recursively print the stack trace on the given buffer.
     */    
    StringBuilder generateStackTrace(Throwable t, StringBuilder buffer) {
        buffer.append(t.getClass().getName() + ": " + t.getMessage() + NEWLINE);
        buffer.append(toString(t.getStackTrace()));
        Throwable cause = t.getCause();
        if (cause !=null && cause != t) {
            generateStackTrace(cause, buffer);
        }
        return buffer;
    }
    
    StringBuilder toString(StackTraceElement[] traces) {
        StringBuilder error = new StringBuilder();
        for (StackTraceElement e : traces) {
            String str = e.toString();
            error.append(INDENT).append(str).append(NEWLINE);
        }
        return error;
    }
}
