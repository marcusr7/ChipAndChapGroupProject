package application;

import persistence.StateLoader;
import persistence.StateSaver;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

public class RunGame extends JPanel {
    private JFrame frame;
    private JButton startNewGame;
    private JButton resumePreviousGame;
    private JPanel buttonsPanel;
    private JLabel title;
    private JPanel labelPanel;
    private static ChipAndChap game;
    private JMenuBar menuBar;

    private ImageIcon playerIcon;
    private JLabel playerLabel;
    private ImageIcon treasureIcon;
    private JLabel treasureLabelOne;
    private JLabel treasureLabelTwo;
    private ImageIcon redKeyIcon;
    private JLabel redKeyLabel;
    private ImageIcon greenKeyIcon;
    private JLabel greenKeyLabel;
    private ImageIcon blueKeyIcon;
    private JLabel blueKeyLabel;
    private JLabel redLockLabel;
    private JLabel blueLockLabel;
    private JLabel greenLockLabel;
    private ImageIcon redLockIcon;
    private ImageIcon blueLockIcon;
    private ImageIcon greenLockIcon;

    private Color brown = new Color(67, 53, 48);

    /**
     * Constructor, sets up the main menu frame
     */
    public RunGame(){
        frame = new JFrame("Chip and Chap");
        frame.add(this);
        // Need to change size to be based off of Screen size
        frame.setMinimumSize(new Dimension(650, 550));
        setup();
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);
    }

    /**
     * Sets up the main menu with a menu and buttons, and adds visual components to main menu
     */
    private void setup(){
        menuBar = new JMenuBar();

        JMenu options = new JMenu("Options");

        JMenuItem rules = new JMenuItem(new AbstractAction("Rules") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = "Use the arrow keys to move the player.\n" +
                        "The aim of the game is to collect all of the chips\n" +
                        "before times runs out.\n" +
                        "Be careful, as there will be monsters trying to stop you\n" +
                        "from achieving your goal";

                JOptionPane.showMessageDialog(null, message, "Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem exit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        JMenu loadState = new JMenu("Load State");
       JMenuItem load = new JMenuItem(new AbstractAction("Load") {
               @Override
               public void actionPerformed(ActionEvent e) {

                   File file = null;
                   file = new File("LastSavedState.json");
                   if(!file.exists()){
                       JOptionPane.showMessageDialog(null, "LastSavedState.json isn't present.");
                   } else {
                       resumeSavedGame(file);
                   }

           }
       });

        options.add(rules);
        options.add(exit);

        loadState.add(load);

        menuBar.add(options);
        menuBar.add(loadState);

        menuBar.setBackground(Color.YELLOW);
        options.setForeground(brown);
        loadState.setForeground(brown);

        playerIcon = new ImageIcon("images/knight_default.png");
        playerLabel = new JLabel();

        treasureIcon = new ImageIcon("images/dungeon/treasure.png");

        treasureLabelOne = new JLabel();
        treasureLabelTwo = new JLabel();

        redKeyIcon = new ImageIcon("images/dungeon/red_key.png");
        greenKeyIcon = new ImageIcon("images/dungeon/green_key.png");
        blueKeyIcon = new ImageIcon("images/dungeon/blue_key.png");

        redKeyLabel = new JLabel();
        greenKeyLabel = new JLabel();
        blueKeyLabel = new JLabel();

        redLockIcon = new ImageIcon("images/dungeon/red_lock.png");
        blueLockIcon = new ImageIcon("images/dungeon/blue_lock.png");
        greenLockIcon = new ImageIcon("images/dungeon/green_lock.png");

        redLockLabel = new JLabel();
        greenLockLabel = new JLabel();
        blueLockLabel = new JLabel();

        treasureLabelOne.setBounds(555, 90, 100, 100);
        treasureLabelOne.setIcon(treasureIcon);

        treasureLabelTwo.setBounds(60, 90, 100, 100);
        treasureLabelTwo.setIcon(treasureIcon);

        playerLabel.setBounds(260, 180, 100, 100);
        playerLabel.setIcon(playerIcon);

        redKeyLabel.setBounds(230, 200, 100, 100);
        redKeyLabel.setIcon(redKeyIcon);

        greenKeyLabel.setBounds(230, 175, 100, 100);
        greenKeyLabel.setIcon(greenKeyIcon);

        blueKeyLabel.setBounds(230, 150, 100, 100);
        blueKeyLabel.setIcon(blueKeyIcon);

        redLockLabel.setBounds(355, 200, 100, 100);
        redLockLabel.setIcon(redLockIcon);

        blueLockLabel.setBounds(355, 150, 100, 100);
        blueLockLabel.setIcon(blueLockIcon);

        greenLockLabel.setBounds(355, 175, 100, 100);
        greenLockLabel.setIcon(greenLockIcon);


        title = new JLabel("CHIP AND CHAP");
        title.setBounds(90, -140, 650, 550);
        title.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        title.setForeground(Color.YELLOW);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        startNewGame = new JButton("Start New Game");
        startNewGame.setBackground(Color.YELLOW);
        startNewGame.setForeground(brown);
        resumePreviousGame = new JButton("Resume Previous Game");
        resumePreviousGame.setBackground(Color.YELLOW);
        resumePreviousGame.setForeground(brown);

        buttonsPanel.setBackground(brown);



        frame.add(title);

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        frame.add(playerLabel);
        frame.add(treasureLabelOne);
        frame.add(treasureLabelTwo);

        frame.add(redKeyLabel);
        frame.add(greenKeyLabel);
        frame.add(blueKeyLabel);

        frame.add(redLockLabel);
        frame.add(blueLockLabel);
        frame.add(greenLockLabel);



        startNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new ChipAndChap();
                //frame.setVisible(false);
            }
        });

        resumePreviousGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = null;
                file = new File("LastSavedState.json");
                if(!file.exists()){
                    JOptionPane.showMessageDialog(null, "LastSavedState.json isn't present.");
                } else {
                    resumeSavedGame(file);
                }
            }
        });

//        rules.addActionListener(this::actionPerformed);
//        exit.addActionListener(this::actionPerformed);

        buttonsPanel.add(Box.createRigidArea(new Dimension(200, 290)));
        buttonsPanel.add(startNewGame);

        buttonsPanel.add(Box.createRigidArea(new Dimension(-100, 25)));
        buttonsPanel.add(resumePreviousGame);

        frame.add(buttonsPanel);

        File launchInfo = new File("launchInfo.json");
        if(launchInfo.exists()){
            frame.setVisible(false);

            //Reading the JSON file into a String
            StringReader stringReader = null;
            try {
                //Using byte data to do so.
                FileInputStream inputStream = new FileInputStream(launchInfo);
                byte[] data = new byte[(int) launchInfo.length()];
                inputStream.read(data);
                stringReader = new StringReader(new String(data, "UTF-8"));
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(stringReader == null){
                return;
            }

            //Reading JSON file
            JsonReader jsonReader = Json.createReader(stringReader);
            JsonObject root = jsonReader.readObject();
            if(!root.containsKey("Resume")){
                return;
            }
            if(root.getInt("Resume") == -1){
                resumeSavedGame(new File("LastSavedState.json"));
            } else if (root.getInt("Resume") == 11){
                System.out.println("Here");
                resumeSavedGame(new File("LastSavedState.json"));
            }
            else if (root.getInt("Resume") > 0) {
                loadFromLastLevel(root.getInt("Resume"));
            }


        } else {
            frame.setVisible(true);
        }

    }

    /**
     * Loads a game from the given file
     * @param file JSON file containing game information
     */
    public static void resumeSavedGame(File file){
        if(file.exists()) {
            StateLoader stateLoader = new StateLoader(file);
            game = stateLoader.loadState();
        } else {
            JOptionPane.showMessageDialog(null, "LastSavedState.json isn't present.");
        }
    }



//        startNewGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                game = new ChipAndChap();
//                //frame.setVisible(false);
//            }
//        });
//
//        resumePreviousGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                final JFileChooser fc = new JFileChooser();
////                JFileChooser fileChooser = new JFileChooser();
////                FileNameExtensionFilter filter = new FileNameExtensionFilter(
////                        "JSON Files", "json");
////                fileChooser.setFileFilter(filter);
////                int returnVal = fileChooser.showOpenDialog(null);
////                if(returnVal == JFileChooser.APPROVE_OPTION) {
////                    StateLoader stateLoader = new StateLoader(
////                            new File(fileChooser.getSelectedFile().getName())
////                    );
////                    stateLoader.loadState();
////                    frame.setVisible(false);
////                }
//
//                StateLoader stateLoader = new StateLoader(
//                        new File("LastSavedState.son")
//                );
//                game = stateLoader.loadState();
//            }
//        });



    /**
     * Launches a new ChipAndChap instance, with the level set to the given level
     * @param level int Level to start game at
     */
    public static void loadFromLastLevel(int level){
        game = new ChipAndChap(level);
    }

    /**
     * Main class, runs the game
     * @param args
     */
    public static void main(String[] args) {
        new RunGame();
    }
}
