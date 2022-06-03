package Number_Baseball;

 class User {

    private final String nickname;  // 현재 사용자의 닉네임
    private int trialsNum;  // 현재 사용자의 시행횟수

     User(String nickname)
    {
        this.nickname = nickname;
    }

    protected String getNickname()
    {
        return nickname;
    }

    protected void setTrialsNum(int trialsNum)
    {
        this.trialsNum = trialsNum;
    }

    protected int getTrialsNum() {
        return trialsNum;
    }
}
