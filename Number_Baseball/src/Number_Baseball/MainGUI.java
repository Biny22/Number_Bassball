package Number_Baseball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainGUI extends JFrame {

    private Num_Baseball nb;
    private JPanel padPanel;
    private User player;

    private boolean is_run;  // 게임이 진행 중인지 확인하는 변수
    private JButton startBtn;
    private JTextField padText;  // 게임진행
    private JTextArea gameLog;  // 유저가 입력한 숫자
    private JButton[] numPad;  // 키패드

     MainGUI() {
        Font font = new Font("메이플스토리", Font.PLAIN, 15);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);

        is_run = false;
        gui();
    }

    private void gui()  // 전체 gui
    {
        JPanel mainPanel = new JPanel(); // 메인 패널
        JPanel infoPanel = new JPanel(); // 글자 패널
        JPanel gamePanel = new JPanel();

        setGamePanel(gamePanel);
        setInfoPanel(infoPanel);

        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(infoPanel);
        mainPanel.add(gamePanel);


        super.add(mainPanel);
        super.setTitle("숫자야구");
        super.setVisible(true);
        super.setSize(960, 720);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
    }

    private void setInfoPanel(JPanel infoPanel)  // 좌측 gui
    {
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));

        gameLog = new JTextArea(); // 좌측 게임 진행을 위한 필드
        JScrollPane scrollPane = new JScrollPane(gameLog);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("메이플스토리", Font.PLAIN, 20));

        JButton rankButton = new JButton("랭킹"); // 랭킹을 볼 수 있는 버튼
        rankButton.setFont(new Font("메이플스토리", Font.PLAIN, 30));

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == rankButton) {
                    new RankingGUI();
                }
            }
        };
        rankButton.addActionListener(actionListener);

        JPanel rankPanel = new JPanel();
        rankPanel.setLayout(new BoxLayout(rankPanel,BoxLayout.X_AXIS));
        rankPanel.add(rankButton);

        infoPanel.add(scrollPane);
        infoPanel.add(rankPanel);
    }

    private void setGamePanel(JPanel gamePanel)  // 우측 gui
    {
        gamePanel.setLayout(new GridLayout(3, 1));
        padText = new JTextField(); // 숫자패드에 입력된 값을 출력
        padText.setFont(new Font("메이플스토리", Font.BOLD, 128));

        padText.setHorizontalAlignment(SwingConstants.CENTER);
        padText.setEditable(false);

        numPad = new JButton[12];
        padPanel = new JPanel();
        padPanel.setLayout(new GridLayout(4, 3));
        setNumPad();

        startBtn = new JButton("시작");
        JButton endBtn = new JButton("종료");

        startBtn.setFont(new Font("메이플스토리", Font.BOLD, 25));
        endBtn.setFont(new Font("메이플스토리", Font.BOLD, 25));

        setButton(startBtn,endBtn);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridBagLayout());
        btnPanel.add(startBtn);
        btnPanel.add(endBtn);

        gamePanel.add(padText);
        gamePanel.add(padPanel);
        gamePanel.add(btnPanel);
    }

    private void setButton(JButton startBtn, JButton endBtn)
    {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == startBtn)
                {
                    String nick;

                    do {
                        nick = JOptionPane.showInputDialog("닉네임을 입력 해주세요.");
                    }
                    while (nick == null || nick.contains(" "));

                    nb = new Num_Baseball();
                    player = new User(nick);
                    is_run = true;
                    setBtnEnables(numPad);
                    setGameLog("start");

                } else if (e.getSource() == endBtn)
                {
                    System.exit(0);
                }
            }
        };

        startBtn.addActionListener(actionListener);
        endBtn.addActionListener(actionListener);
    }

    private void setNumPad()
    {
        int num = 1;

        for (int i = 0; i < numPad.length; i++) {

            numPad[i] = new JButton();
            numPad[i].setFont(new Font("메이플스토리", Font.BOLD, 20));
            numPad[i].setFocusable(false);
            numPad[i].setEnabled(false);
            padPanel.add(numPad[i]);
            numPad_Action(numPad[i]);

            numPad[i].setText("" + num);

            if (num == 10)
                numPad[i].setText("0");
            else if (num == 11)
                numPad[i].setText("OK");
            else if (num == 12)
                numPad[i].setText("DEL");

            num += 1;
        }
    }

    private void infoMessage() // 3개의 숫자가 아닌 경우 에러메세지
    {

        JOptionPane.showMessageDialog
                (this, "숫자를 3개 입력해주세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setBtnEnables(JButton[] buttons) // 숫자 패드를 on/off 하는 메소드
    {
        for (JButton button : buttons)
            button.setEnabled(is_run);

        startBtn.setEnabled(!is_run);
    }

    private void numPad_Action(JButton button) // 키패드에 이벤트 넣어주는 메소드
    {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (button.getText()) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                    case "0":
                        String num = button.getText();
                        padText.setText(padText.getText() + button.getText());
                        button.setEnabled(false);
                        nb.setPlayerNum(nb.getSize(), Integer.parseInt(num));
                        nb.setSize(nb.getSize()+1);
                        break;

                    case "OK":
                        if(nb.getSize() == 3)
                        {
                            nb.setTrialsNum(nb.getTrialsNum()+1);

                            setBtnEnables(numPad); // 해당 버튼을 on
                            nb.setSize(0);
                            padText.setText("");
                            setGameLog("ok");

                            if (!nb.referee())  // 스트라이크 판별
                                setGameLog("clear");
                            else
                                setGameLog("wrong");

                        }

                        else
                        {
                            infoMessage();
                        }

                        break;

                    case "DEL":
                        padText.setText("");
                        nb.setSize(0);
                        setBtnEnables(numPad);
                        nb.resetPlayNum();

                        break;
                }
            }
        };

        button.addActionListener(actionListener);
    }

    private void setGameLog(String msg)
    {
        switch (msg)
        {
            case "start" :
            {
                gameLog.setText("");
                gameLog.append("게임이 시작 되었습니다.\n\n");
            }
            break;

            case "ok" :
            {
                for (int i = 0; i < 3; i++)
                    gameLog.append("" + nb.getPlayerNum(i));
                gameLog.append(" 를 입력 했습니다. \n");
            }
            break;

            case "wrong" :
            {
                gameLog.append(nb.getMsg() + " . \n\n");
            }
            break;

            case "clear" :
            {
                gameLog.append("\n 게임 승리! \n");
                gameLog.append("시행 횟수: " + nb.getTrialsNum() + " . \n");
                is_run = false;
                setBtnEnables(numPad);
                player.setTrialsNum(nb.getTrialsNum());
                new RankingGUI(player);
            }
            break;
        }
    }
}



