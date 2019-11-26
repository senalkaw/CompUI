

import javax.swing.*;


    public class Main {
        public static void main(String[] args) {
            //create new frame
            JFrame frame= new JFrame("Blood Sugar Level Input");
            frame.setSize(700,300);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //add panel to frame
            CompEntry mainPanel = new CompEntry();
            frame.getContentPane().add(mainPanel);

            frame.setVisible(true);
        }
    }

    

