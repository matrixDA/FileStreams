import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RandProductSearchFrame extends JFrame {
    JPanel mainPnl, searchPnl, titlePnl, controlPnl;
    JTextField searchTF;
    JTextArea textArea;
    JScrollPane pane;
    JLabel searchLbl;
    JButton searchBtn, quitBtn;
    String res;
    ArrayList<String> arrayList = new ArrayList<>();
    StringBuffer stringBuffer;
    Stream<String> stream;
    String path = "src/ProductData.txt";
    File selecetedfile = new File(path);
    public RandProductSearchFrame() {

        setTitle("Random Product Search");
        setSize(980, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();

        setVisible(true);
    }

    public void createGUI()
    {
        mainPnl = new JPanel();
        titlePnl = new JPanel();
        searchPnl = new JPanel();
        controlPnl = new JPanel();

        mainPnl.setLayout(new BorderLayout());
        mainPnl.add(titlePnl, BorderLayout.NORTH);
        mainPnl.add(searchPnl, BorderLayout.CENTER);
        mainPnl.add(controlPnl,BorderLayout.SOUTH);

        add(mainPnl);

        createNorthPnl();
        createCenterPnl();
        createSouthPnl();
    }

    public void createNorthPnl(){
        searchLbl = new JLabel("Search Product Name: ");
        searchLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        searchTF = new JTextField("", 20);
        searchTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        titlePnl.add(searchLbl);
        titlePnl.add(searchTF);
    }
    public void createCenterPnl(){
        textArea = new JTextArea(30, 60);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textArea.setEditable(false);
        pane = new JScrollPane(textArea);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        searchPnl.add(pane);
    }
    public void createSouthPnl(){
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) ->{
            System.exit(0);
        });
        searchBtn = new JButton("Search");
        searchBtn.addActionListener((ActionEvent ae ) ->{
            res = searchTF.getText();
            if(res.isEmpty()){
                JOptionPane.showMessageDialog(this, "Search field is empty, cannot search!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                stringBuffer = new StringBuffer();
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(selecetedfile, "rw");
                    while(randomAccessFile.getFilePointer() < randomAccessFile.length()){
                        arrayList.add(randomAccessFile.readLine() + "\n");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stream = arrayList.stream().filter(s -> s.contains(res));
            stream.forEach(s -> textArea.append(s));
        });
        controlPnl.add(searchBtn);
        controlPnl.add(quitBtn);
    }


}
