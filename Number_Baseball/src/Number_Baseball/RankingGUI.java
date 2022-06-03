package Number_Baseball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class RankingGUI extends JFrame{

    private final Rank rank;

    // 유저가 랭킹 버튼을 눌렀을 때 호출
    RankingGUI()
    {
        rank = new Rank();
        gui();
    }

    // 게임이 끝나고 유저의 정보를 기록 할 때 호출
     RankingGUI(User player)
    {
        rank = new Rank();
        rank.setRanking(player);
    }

    private void gui()
    {
        String[][] contents = new String[rank.getUserCount()][3];
        String[] header = new String[]{"순위","닉네임","시행횟수"};


        for(int i = 0; i < rank.getUserCount(); i++)
            for(int j = 0; j < 3; j++)
                contents[i][j] = "    " + rank.getRanking(i,j);

        JTable table = new JTable(contents, header);
        table.setFont(new Font("메이플스토리", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel mainPanel = new JPanel();

        mainPanel.add(scrollPane);
        table.setEnabled(false);

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RankingGUI.super.dispose();
            }
        };

        super.addWindowListener(windowListener);
        super.add(mainPanel);
        super.setTitle("랭킹");
        super.setSize(640,320);
        super.add(table);
        super.setVisible(true);
    }
}
