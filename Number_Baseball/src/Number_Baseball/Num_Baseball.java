package Number_Baseball;

import java.util.Arrays;
import java.util.Random;

 class Num_Baseball {

    private final int[] NUM; // 플레이어가 맞춰야 할 숫자
    private final int[] playerNum;  // 플레이어의 숫자
    private String message;  // 반환할 메세지
    private int trialsNum;  // 플레이어의 시행횟수
    private int size; // 플레이어의 숫자의 사이즈


     Num_Baseball()
    {
        NUM = new int[3];
        playerNum = new int[10];
        trialsNum = 0;
        size = 0;

        setNUM();
    }

    // 랜덤한 숫자를 뽑아주는 메소드
    private void setNUM()
    {
        int num;
        Random random = new Random();

        for(int i = 0; i < NUM.length; i++)
        {
            num = random.nextInt(10);

            while(!contains(NUM,num))
            {
                num = random.nextInt(10);
            }
            NUM[i] = num;
        }
    }

    // 전에 뽑은 숫자와 지금 뽑은 숫자가 같은지 확인, 중복 시 false
    private boolean contains(int[] arr, int num)
    {
        for (int j : arr)
            if (j == num)
                return false;

        return true;
    }

    // 스트라이크, 볼, 아웃을 판단하는 메소드
    protected boolean referee()
    {
        int strike, ball, out;
        strike = strike();
        ball = ball();
        out = 0;

        if(strike == 0 && ball == 0)
        {
            out += 1;
            message = outMsg(out);
        }

        else
        {
            message = strikeMsg(strike,ball);
        }

        return strike != 3;
    }

    // 스트라이크를 판단하는 메소드
    private int strike()
    {
        int num = 0;

        for(int i = 0; i < NUM.length; i++)
        {
            if(NUM[i] == playerNum[i])
                num += 1;
        }

        return num;
    }

    // 볼을 판단하는 메소드
    private int ball()
    {
        int num = 0;

        for (int i = 0; i < NUM.length; i++)
        {
            for (int j = 0; j < NUM.length; j++)
            {
                if (NUM[i] == playerNum[j] && i != j)
                        num += 1;
            }
        }       return num;
    }

    private String outMsg(int out)
    {
        return "Out: "+ out;
    }

    private String strikeMsg(int strike, int ball)
    {
        String str1 = "Strike : " + strike;
        String str2 = " ball : " + ball;
        return str1 + str2;
    }

    protected void setSize(int size)
    {
        this.size = size;
    }

    protected int getSize()
    {
        return size;
    }

    protected void setTrialsNum(int trialsNum)
    {
        this.trialsNum = trialsNum;
    }

    protected int getTrialsNum()
    {
        return trialsNum;
    }

    protected void setPlayerNum(int idx, int num)
    {
        playerNum[idx] = num;
    }

    protected int getPlayerNum(int idx)
    {
        return playerNum[idx];
    }

    protected void resetPlayNum()
    {
        Arrays.fill(playerNum,-1);
    }

    protected String getMsg()
    {
        return message;
    }

}

