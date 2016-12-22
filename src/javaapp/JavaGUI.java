package javaapp;

/**
 *
 * @author Jan Kowal x15009939
 */

// imports
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

// class init
public class JavaGUI extends JFrame {
    
    // creating objects and variables;
    private final JTextField name = new JTextField();
    private final ButtonGroup choice = new ButtonGroup();
    private final JRadioButton[] radio = new JRadioButton[3];
    private final JTextField[][] txt = new JTextField[3][6];
    private final JTextField[] resField = new JTextField[6];
    private final ArrayList<ArrayList<Integer>> results = new ArrayList<>();
    private ArrayList<JavaWinner> win = new ArrayList<>();
    private final JButton play = new JButton(); 
    private final JButton clear = new JButton(); 
    private final JButton save = new JButton(); 
    private final JLabel top5 = new JLabel();
    private final JLabel userMessage = new JLabel();
    private final JLabel author = new JLabel();
    private final JLabel bg = new JLabel(); 
    private int numberOfLines;
    private boolean canSave = false;
    
    // init object of JavaLotto class
    JavaLotto lotto = new JavaLotto();
    
    // constructor for JavaGUI
    public JavaGUI() {
        
        /* please uncomment this, then comment line lotto.run() on 255 and become a millionaire :)
        lotto.run();
        for (int i = 0; i < 6; i++) {
            System.out.println(lotto.getResults().get(i));
        }
        */
        
        loadWinner(); // loading winners method
        initComponents(); // init GUI components method
    }
    /* Key Adapter object with method keyTyped collecting typed character
    this method will help to prevent wrong user user input on JTextFields
    */
    KeyAdapter myKey = new KeyAdapter() {
    @Override
        public void keyTyped(KeyEvent e) {
            JTextField textField = (JTextField) e.getSource(); // getting source of JTextField object calling this function
            String text = textField.getText(); // Local variable getting text from textfield in use.
            char c = e.getKeyChar(); // variable getting typed character
            if (text.length() >= 2 ){ // limit textfield to 2 characters
                e.consume();
            }if (!(Character.isDigit(c))){ // limit textfield to only numbers
                e.consume();
            }if (Character.getNumericValue(c) == 0 && text.length() < 1){ // preventing from typing 0 first
                e.consume();
            }if (text.length() > 0 && Character.getNumericValue(text.charAt(0))==4 && Character.getNumericValue(c) > 7){//preventing to type more than 7 after 4
                e.consume();
            }if (text.length() > 0 && Character.getNumericValue(text.charAt(0))>4){ // preventing to type something after number bigger than 5.
                e.consume();
            }
        }
    };
    // method initiating GUI components
    private void initComponents() {
                      
        // init the JFrame features
        getContentPane().setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Lotto");
        setSize(756, 550);
        setResizable(false);
 
        //init cjeckbuttons with checkbutton 1 set selected as default
        for (int i = 0; i < radio.length; i++) {
            radio[i] = new JRadioButton();
            radio[i].setBounds(388+(i*50), 217, 50, 30);
            radio[i].setOpaque(false);
            radio[i].setBorder(null);
            radio[i].setFont(new Font("Calibri", 1, 18));
            radio[i].setText(String.valueOf(i+1));
            if(i==0){
                radio[i].setSelected(true);
            }
            choice.add(radio[i]);
            getContentPane().add(radio[i]);
            switch (i) {
                case 0:
                    radio[i].addActionListener(this::radio1ActionPerformed);
                    break;
                case 1:
                    radio[i].addActionListener(this::radio2ActionPerformed);
                    break;
                default:
                    radio[i].addActionListener(this::radio3ActionPerformed);
                    break;
            }
            


        }
        
        
        //init name text field
        name.setFont(new Font("Calibri", 1, 18));
        name.setForeground(new Color(0, 0, 0));
        name.setBorder(null);
        name.setCaretColor(new Color(34, 83, 120));
        name.setOpaque(false);
        getContentPane().add(name);
        name.setBounds(352, 180, 170, 30);


        //this loop will create JTextfield objects 2-dimensional array [i] representing line and [k] number.
        for (int i = 0; i < 3; i++){
             for (int k = 0; k < 6; k++){
                txt[i][k] = new JTextField();
                txt[i][k].setFont(new java.awt.Font("Calibri", 1, 18));
                txt[i][k].setForeground(new Color(0, 0, 0));
                txt[i][k].setHorizontalAlignment(JTextField.CENTER);
                txt[i][k].setBorder(null);
                txt[i][k].setCaretColor(new Color(34, 83, 120));
                txt[i][k].setOpaque(false);
                if(i>0){
                    txt[i][k].setEditable(false);
                }
                getContentPane().add(txt[i][k]);
                txt[i][k].setBounds(267+(k*43), 257+(i*50), 30, 30);
                txt[i][k].addKeyListener(myKey);
            }
        }

        
        //loop creating result fields, lotto resaults will be displayed there.
        for (int i = 0; i < resField.length; i++){
            resField[i] = new JTextField();
            resField[i].setEditable(false);
            resField[i].setFont(new java.awt.Font("Calibri", 1, 18));
            resField[i].setForeground(new Color(0, 0, 0));
            resField[i].setHorizontalAlignment(JTextField.CENTER);
            resField[i].setBorder(null);
            resField[i].setCaretColor(new Color(34, 83, 120));
            resField[i].setOpaque(false);
            getContentPane().add(resField[i]);
            resField[i].setBounds(267+(i*43), 410, 30, 30);
        }
        
        // init userMessage JLabel this label will display messages to user
        userMessage.setForeground(new java.awt.Color(0, 0, 0));
        userMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userMessage.setToolTipText("");
        getContentPane().add(userMessage);
        userMessage.setBounds(32, 475, 700, 20);
        userMessage.setFont(new java.awt.Font("Calibri", 1, 20));
        
        // init top 5 JLabel - will display top 5 winners.
        top5.setForeground(new java.awt.Color(0, 0, 0));
        top5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        top5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        top5.setToolTipText("");
        getContentPane().add(top5);
        top5.setBounds(35, 220, 140, 150);
        top5.setFont(new java.awt.Font("Calibri", 1, 15));
        
        //play buton init
        play.setIcon(new ImageIcon(getClass().getResource("/javaapp/play.png"))); 
        play.setBorder(null);
        play.setContentAreaFilled(false);
        play.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
        play.setOpaque(false);
        play.setRolloverIcon(new ImageIcon(getClass().getResource("/javaapp/playh.png"))); 
        play.addActionListener(this::playActionPerformed);
        getContentPane().add(play);
        play.setBounds(530, 355, 105, 35);
        
        // clear button init
        clear.setIcon(new ImageIcon(getClass().getResource("/javaapp/clear.png"))); 
        clear.setBorder(null);
        clear.setContentAreaFilled(false);
        clear.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
        clear.setOpaque(false);
        clear.setRolloverIcon(new ImageIcon(getClass().getResource("/javaapp/clearh.png"))); 
        clear.addActionListener(this::clearActionPerformed);
        getContentPane().add(clear);
        clear.setBounds(530, 315, 105, 35);
        
        // save button init
        save.setIcon(new ImageIcon(getClass().getResource("/javaapp/save.png"))); // NOI18N
        save.setBorder(null);
        save.setContentAreaFilled(false);
        save.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
        save.setOpaque(false);
        save.setRolloverIcon(new ImageIcon(getClass().getResource("/javaapp/saveh.png"))); // NOI18N
        save.addActionListener(this::saveActionPerformed);
        getContentPane().add(save);
        save.setBounds(530, 407, 105, 35);
        
        // author label init
        author.setForeground(new java.awt.Color(0, 0, 0));
        author.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        author.setText("Jan Kowal © 2016");
        author.setToolTipText("");
        getContentPane().add(author);
        author.setBounds(610, 500, 130, 16);
        author.setFont(new java.awt.Font("Calibri", 1, 16));
        
        // used JLabel with customized background instead of JPanel to make better visuals of application
        bg.setIcon(new ImageIcon(getClass().getResource("/javaapp/layout.png"))); 
        getContentPane().add(bg);
        bg.setBounds(0, 0, 756, 544);
        setLocationRelativeTo(null);
        
        
    } 
    
    
    //play button action performed method
    private void playActionPerformed(java.awt.event.ActionEvent evt) {
        // checking which radio button is currently selected
        if(radio[0].isSelected()){
            numberOfLines=1;
        }else if(radio[1].isSelected()){
            numberOfLines=2;
        }else{
            numberOfLines=3;
        }
        if(!(name.getText().isEmpty())){ // checking if name is provided
            if(emptyFields(numberOfLines)){ // checking if there is some empty fields in all lines selected
                if(fieldSimilarity(numberOfLines)){ // checking if there is some numbers repeating in one line - for all lines selected
                    lotto.run(); // running lottery
                    lottoPrint(); // printing results 
                    checkUserNumbers(numberOfLines); //checking if user won something
                }else{
                    userMessage.setText("Please do not repeat numbers in one line!");
                }
            }else{
                userMessage.setText("Please fill all the fields of lines you selected to fill!");
            }
        }else{
            userMessage.setText("Please enter your name!");
        }
    }
    // clear button action performed method
    private void clearActionPerformed(java.awt.event.ActionEvent evt) {
        clearAll();
    }
    // clearing all number fields method
    private void clearAll(){
        userMessage.setText("");
        for (int i = 0; i < txt.length; i++){
            for (int j = 0; j < txt[i].length; j++) {
                txt[i][j].setText("");
            }
        }
        for (int i = 0; i < resField.length; i++) {
            resField[i].setText("");
        }
    }
    
    // save button action performed method
    private void saveActionPerformed(java.awt.event.ActionEvent evt) {
        if(canSave){
            saveWinner();
        }else{
            userMessage.setText("You can't save your result you didn't win.");
        }
    }
    
    // radio button 1 action prevent to enter numbers in other lines than 1.
    private void radio1ActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i = 1; i < txt.length; i++) {
            for (int j = 0; j < txt[i].length; j++){
                txt[i][j].setEditable(false);
                txt[i][j].setText("");
            }
        }
    }
    
    // radio button 1 action prevent to enter numbers in other lines than 1 and 2.
    private void radio2ActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i = 0; i < txt[1].length; i++) {
            txt[1][i].setEditable(true);

        }
        for (int i = 0; i < txt[2].length; i++) {
            txt[2][i].setEditable(false);
            txt[2][i].setText("");
        }
    }
    
    // allows to enter numbers in every line
    private void radio3ActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i = 1; i < txt.length; i++) {
            for (int j = 0; j < txt[i].length; j++){
                txt[i][j].setEditable(true);
            }
        }
    }
    
    // method checking if fields are empty returning boolean expression back.
    private boolean emptyFields(int numberOfLines){
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < txt[i].length; j++) {
                if(txt[i][j].getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }
    
    // method checking if there is any number repeating in one line
    private boolean fieldSimilarity(int numberOfLines){
        int counter=0;
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < txt[i].length; j++) {
                int k = 0;
                while(k<txt[i].length){
                    int num1 = Integer.parseInt(txt[i][j].getText());
                    int num2 = Integer.parseInt(txt[i][k].getText());
                    if(num1==num2){
                        counter++; 
                    }
                    k++;
                }
            }
        }
        return counter <= numberOfLines*6;
    }
    
    /*
    method is adding numbers from user matching results to 2 dimensional array list
    first dimension represents lines and second represents numbers in this line.
    */
    private void checkUserNumbers(int numberOfLines){
        results.clear();
        for (int i = 0; i < numberOfLines; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                int num = Integer.parseInt(txt[i][j].getText());
                if(lotto.getResults().contains(num)){
                    temp.add(j);
                }
            }
            results.add(temp);
        }
        /*
        switch statement printing message for user according to how many lines he selected.
        it also check if user won 50 euro or more than user is able to save the result by save
        button ( canSave boolean value to true )
        */
        switch (numberOfLines) {
            case 1:
                if(results.get(0).size()>=3){
                    canSave = true;
                }
                userMessage.setText(
                        "You guessed: " + results.get(0).size() +" numbers on 1st Line. You won: €"+
                        totalWin(numberOfLines)
                        );
                break;
            case 2:
                if(results.get(0).size()>=3||results.get(1).size()>=3){
                    canSave = true;
                }
                userMessage.setText(
                        "You guessed: " + results.get(0).size() +" numbers on 1st Line, and "
                        + results.get(1).size() + " on 2nd. You won: €" +
                        totalWin(numberOfLines)
                );
                break;
            default:
                if(results.get(0).size()>=3||results.get(1).size()>=3||results.get(2).size()>=3){
                    canSave = true;
                }
                userMessage.setText(
                        "You guessed: " + results.get(0).size() +" numbers on 1st, "
                        + results.get(1).size() + " on 2nd, and " + results.get(2).size()+
                        " on 3rd. You won: €" + 
                        totalWin(numberOfLines)
                );
                break;
        }

    }
    
    //printing lotto secret numbers results
    private void lottoPrint(){
        for (int i = 0; i < lotto.getResults().size(); i++) {
            int temp = (int) lotto.getResults().get(i);
            resField[i].setText(Integer.toString(temp));
        }
    }
    // this method summing all money user won.
    private int totalWin(int NumberOfLines){
        switch (numberOfLines) {
            case 1:
                return lotto.getWinning(results.get(0).size());
            case 2:
                return lotto.getWinning(results.get(0).size())+lotto.getWinning(results.get(1).size());
            default:
                return lotto.getWinning(results.get(0).size())+lotto.getWinning(results.get(1).size())+lotto.getWinning(results.get(2).size());
        }
    }
    // method to save user result if it's 50 euro or more.
    private void saveWinner(){
        try {
            File file = new File("winners.dat"); 
           
            win.add(new JavaWinner(name.getText(), totalWin(numberOfLines)));
            
            // arraylist sorting option by totalWin integer
            win.sort((JavaWinner o1, JavaWinner o2) -> {
                return o2.getWinning() - o1.getWinning();
            });
            
            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fo);
            
            os.writeObject(win);
            os.close();
            userMessage.setText("Your record was saved successfully!");
            loadWinner();
            canSave=false;
        } catch (IOException ex){
            System.out.println(ex);
        } 
    }
    // this method loading winners.dat and extracting data of win ArrayList from there.
    private void loadWinner(){
        try{
            File file = new File ("winners.dat");
            FileInputStream iStream = new FileInputStream (file);
            ObjectInputStream objectS = new ObjectInputStream (iStream);
            
            win = (ArrayList) objectS.readObject();
            iStream.close ();

            
        }catch (ClassNotFoundException ex) {
            System.out.println("Can't load Winner List...");
        }catch(IOException ex){
            System.out.println("Error reading from File");
        }
        top5(); // calling top5 method after finish
    }
    
    //this method printing top 5 winners
    private void top5(){
        StringBuilder str = new StringBuilder();
        str.append("<html>");
        for (int i = 0; i < win.size(); i++) {
            str.append(i+1).append(". ").append(win.get(i).getName())
                    .append(" €").append(String.valueOf(win.get(i).getWinning()))
                    .append("<br>");
            if(i==4){
                break;
            }
        }
        str.append("</html>");
        top5.setText(str.toString());
    }
    
}
