package Number_Baseball;

import java.io.*;

 class Rank {

    private String[][] ranking;  // 유저의 순위를 담는 배열
    private int userCount;  // ranking 유저의 숫자

     Rank()
    {
        ranking = new String[10][3];
        readRanking();
    }

    // 유저가 랭킹 안에 들어 갈 수 있는지 확인
    protected void setRanking(User player)
    {
        int place = 11;

        // 랭킹에 어떠한 유저도 없을 경우
        if(userCount == 0)
        {
            String[] token = {1+"", player.getNickname(),player.getTrialsNum()+""};
            ranking[0] = token;
            userCount++;
            writeRanking();
        }

        else
        {
            for(int i = userCount-1; 0 <= i; i--) // 플레이어가 들어갈 자리찾기
            {
                // 플레이어의 시행횟수가 더 낮을 때
                if(player.getTrialsNum() < Integer.parseInt(ranking[i][2]))
                {
                    if(i == 0)
                        place = 0;

                    else
                        continue;
                }

                // 플레이어의 시행횟수와 같을 때
                else if(Integer.parseInt(ranking[i][2]) == player.getTrialsNum())
                {
                    place = i+1;
                    break;
                }

                // 플레이어의 시행횟수가 많지만 랭킹이 10개 미만일 때
                else if(userCount < 10 && Integer.parseInt(ranking[i][2]) < player.getTrialsNum())
                {
                    place = i+1;
                    break;
                }

                // 플레이어의 시행횟수가 순위권에 들어가지 못할 때
                else
                    break;
            }

            if(place < 10)
                shiftRanking(player, place);
        }
    }

    // setRanking 에서 호출, 순위 안에 유저의 정보를 넣어주고 기존 순위를 시프팅하는 메소드
    private void shiftRanking(User player, int playerPlace)
    {
        if(playerPlace == userCount)  // 플레이어의 랭킹위치가 배열의 마지막 랭킹보다 클 때
        {
            String[] token = {playerPlace+1+"", player.getNickname(),player.getTrialsNum()+""};
            ranking[userCount] = token;
        }

        else // 밀어야 할 때
        {
            for(int i = userCount-1; 0 <= i; i--)
            {
                if(i != 9 && playerPlace < i)  // 플레이어의 순위가 더 높아서 시프트하기
                {
                    ranking[i+1][0] = Integer.parseInt(ranking[i][0])+1 +"";
                    ranking[i+1][1] = ranking[i][1];
                    ranking[i+1][2] = ranking[i][2];
                }

                else if(playerPlace == i) // 플레이어의 순위일 때
                {
                    if(i != 9)
                    {
                        ranking[i+1][0] = Integer.parseInt(ranking[i][0])+1+"";
                        ranking[i+1][1] = ranking[i][1];
                        ranking[i+1][2] = ranking[i][2];
                    }

                    String[] token = {Integer.parseInt(ranking[i][0])+"", player.getNickname(),player.getTrialsNum()+""};
                    ranking[i] = token;
                    break;
                }
            }
        }

        if(userCount < 10)
            userCount++;

        writeRanking();
    }

    // BufferedWriter 를 통해 파일에 순위 저장
    private void writeRanking()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter
                    (new FileWriter("C:\\Users\\pc\\Number_Baseball\\src\\RankingList\\list.txt"));

            String str;
            int count = 0;

            for(int i = 0; i < userCount; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    writer.write(ranking[i][j] + " ");
                }
                writer.write("\n");
            }

            userCount = count;

            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("파일이 없습니다.");
        }
        catch (IOException e)
        {
            System.out.println("읽어오기 불가능");
        }
    }

    // BufferedReader 를 통해 파일에 저장되어 있는 순위를 ranking 에 저장
    private void readRanking()
    {
        try
        {
            BufferedReader reader = new BufferedReader
                    (new FileReader("C:\\Users\\pc\\Number_Baseball\\src\\RankingList\\list.txt"));

            String str;
            int count = 0;

            while ((str = reader.readLine()) != null)
            {
                String[] tokens = str.split(" ");

                System.arraycopy(tokens, 0, ranking[count], 0, tokens.length);

                count++;
            }

            userCount = count;

            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("파일이 없습니다.");
        }
        catch (IOException e)
        {
            System.out.println("읽어오기 불가능");
        }
    }

    protected int getUserCount()
    {
        return userCount;
    }

    protected String getRanking(int i, int j) {
        return ranking[i][j];
    }
}
