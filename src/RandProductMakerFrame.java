import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMakerFrame extends JFrame {
    String name, description, id;
    double cost;
    static RandomAccessFile randomAccessFile;
    String path = "src/ProductData.txt";
    File selectedFile = new File(path);
    int records = 0;
    JPanel mainPnl, productPnl, controlPnl;
    JLabel nameLbl, descLbl, idLbl, costLbl, recordCountLbl;
    static JTextField nameTF, descTF, idTF, costTF, recordCountTF;
    JButton quitBtn;
    JButton addBtn;
    JButton clearBtn;
    public RandProductMakerFrame(){

        setTitle("Product Maker");
        setSize(1080, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();

        setVisible(true);
    }
    private void createGUI(){
       mainPnl = new JPanel();
       productPnl = new JPanel();
       controlPnl = new JPanel();

       mainPnl.add(productPnl);
       mainPnl.add(controlPnl);

       add(mainPnl);

       createProductPnl();
       createControlPnl();

    }
    private void createProductPnl()
    {
        productPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Product Information"));
        productPnl.setLayout(new GridLayout(3, 4));

        nameLbl = new JLabel("Name: ", JLabel.RIGHT);
        nameLbl.setSize(15,20);
        nameLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        nameTF = new JTextField(10);
        nameTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        descLbl = new JLabel("Description: ", JLabel.RIGHT);
        descLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        descTF = new JTextField(15);
        descTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));


        idLbl = new JLabel("ID: ", JLabel.RIGHT);
        idLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        idTF = new JTextField(8);
        idTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        costLbl = new JLabel("Cost", JLabel.RIGHT);
        costLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        costTF = new JTextField(5);
        costTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        recordCountLbl = new JLabel("Record Count: ", JLabel.RIGHT);
        recordCountTF = new JTextField(5);
        recordCountLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,20));
        recordCountTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        recordCountTF.setEditable(false);

        productPnl.add(nameLbl);
        productPnl.add(nameTF);
        productPnl.add(descLbl);
        productPnl.add(descTF);
        productPnl.add(idLbl);
        productPnl.add(idTF);
        productPnl.add(costLbl);
        productPnl.add(costTF);
        productPnl.add(recordCountLbl);
        productPnl.add(recordCountTF);



    }

    private void createControlPnl(){

        controlPnl.setLayout(new GridLayout(1, 3));
        addBtn = new JButton("Add");
        addBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,18));
        addBtn.addActionListener((ActionEvent ae) ->{
            if(inputValid()){
                name = namePadding(nameTF.getText());
                description = descPadding(descTF.getText());
                id = idPadding(idTF.getText());
                cost = Double.parseDouble(costTF.getText());
                try {
                    randomAccessFile = new RandomAccessFile(selectedFile, "rw");
                    randomAccessFile.seek(randomAccessFile.length());
                    randomAccessFile.writeBytes(id + ", " + name + ", " + description + ", " + String.format("%.2f", cost) + "\n");
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                records++;
                recordCountTF.setText(String.valueOf(records));
                nameTF.setText(null);
                descTF.setText(null);
                idTF.setText(null);
                costTF.setText(null);
                JOptionPane.showMessageDialog(this, "Record has been written to file", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (!inputValid()){
                JOptionPane.showMessageDialog(this, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,18));
        clearBtn.addActionListener((ActionEvent ae) ->{
            nameTF.setText(null);
            descTF.setText(null);
            idTF.setText(null);
            costTF.setText(null);
            recordCountTF.setText(null);
            JOptionPane.showMessageDialog(this, "Form Cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,18));
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));
        controlPnl.add(addBtn);
        controlPnl.add(clearBtn);
        controlPnl.add(quitBtn);
    }
    public static String namePadding(String name){
        do{
            name += " ";
        }while(name.length() < 35);
        return name;
    }
    public static String descPadding(String desc){
        do{
            desc += " ";
        }while(desc.length() < 75);
        return desc;
    }
    public static String idPadding(String id){
        do{
            id += " ";
        }while(id.length() < 6);
        return id;
    }
    public static boolean inputValid(){
        String name = nameTF.getText();
        String desc = descTF.getText();
        String id = idTF.getText();
        double cost = Double.parseDouble(costTF.getText());
        if(name.length() <= 35 && desc.length() <= 75 && id.length() <= 6 && costTF != null){
            return true;
        }
        else{
            return false;
        }
    }
}
