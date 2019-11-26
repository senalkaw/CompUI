//public class CompUI {

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

    public class CompUI extends JPanel {
        //create all components
        protected CompEntry date = new CompEntry();
        protected CompEntry time = new CompEntry();
        protected CompEntry bsl = new CompEntry();
        protected CompEntry Carbs = new CompEntry();
        protected CompEntry Meds = new CompEntry();
        protected JButton back = new JButton("Back");
        protected JButton enter = new JButton("Enter");
        protected JButton localDate = new JButton("Current Date");
        protected JButton localTime = new JButton("Current Time");
        protected JSlider Exerciselevel = new JSlider(1,10);
        protected JPanel panel1 = new JPanel();
        protected JPanel panel2 = new JPanel();
        protected JPanel panel3 = new JPanel();
        protected JPanel panel4 = new JPanel();
        protected JPanel sliderPanel = new JPanel();

        public CompUI(){
            //set labels of entries
            date.newEntry("Date: ");
            time.newEntry("Time: ");
            bsl.newEntry("Blood Sugar Level: ");
            Carbs.newEntry("Carbohydrates(g): ");
            Meds.newEntry ("Medicine: ");

            ChangeListener ExerciselevelCL = new ChangeListener() {   //slider to indicate level of activity
                @Override
                public void stateChanged(ChangeEvent e) {
                    System.out.println("Slider 1 changed ");
                }
            };
            Exerciselevel.addChangeListener(ExerciselevelCL);

            //button actions for back and enter
            back.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println("Back to view");     //change once other pages are ready
                }
            });
            enter.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println("Enter log");        //change once other pages are ready
                    String input_date = date.getInfo();     //save input data
                    String input_time = time.getInfo();
                    String input_bsl = bsl.getInfo();

                    // SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");//convert string input date to sql.date format
                    java.util.Date date = null;
                    try {
                        date = sdf1.parse(input_date);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");//convert string input time to sql.time format
                    java.util.Date time= null;
                    try {
                        time=df.parse(input_time);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    java.sql.Time sqltime = new java.sql.Time(time.getTime());

                    int ibsl = Integer.parseInt(input_bsl);//convert string bsl to integer


                   // Connection conn = connectDB.connect();//set the connect
                    long id = 0;
                    try {
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO patients(date,time,bsl)" + "VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        stmt.setDate(1, sqlDate);
                        stmt.setTime(2, sqltime);
                        stmt.setInt(3, ibsl); //insert date into the table
                        int affectedRows = stmt.executeUpdate();
                        System.out.println(affectedRows);
                        // check the affected rows
                        if (affectedRows > 0) {
                            // get the ID back
                            try (ResultSet rs = stmt.getGeneratedKeys()) {
                                if (rs.next()) {
                                    id = rs.getLong(1);//get the autogenerated id for postgresql
                                }
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());

                    }
                }
            });

            //button actions to get local time and date
            localDate.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    String d = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy"));
                    date.setInfo(d);
                }
            });
            localTime.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    String t = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                    time.setInfo(t);            //collect data to db
                }
            });


            //add components with layout
            panel1.add(back);
            panel2.add(date);
            panel2.add(time);
            panel2.add(bsl);
            panel2.add(Carbs);
            panel2.add(Meds);
            panel3.add(localDate);
            panel3.add(localTime);
            panel4.add(enter);

            GridLayout layout = new GridLayout(4,1);
            setLayout(layout);
            add(panel1);
            add(panel2);
            add(panel3);
            add(panel4);

        sliderPanel.setLayout(new GridLayout(1,1));
        sliderPanel.add(Exerciselevel);
        }
    }

//}
