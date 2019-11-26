
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

    public class CompEntry extends JPanel {
        JLabel label1 = new JLabel();
        //    DocumentListener date_l;
        JTextField info = new JTextField(10);

        public CompEntry(){
            add(label1);
            add(info);
        }

        public void newEntry(String prompt){
            label1.setText(prompt);
        }

        public void setInfo(String data){
            info.setText(data);
        }

        public String getInfo(){
            System.out.println(info.getText());//store data
            return info.getText();
        }

    }


