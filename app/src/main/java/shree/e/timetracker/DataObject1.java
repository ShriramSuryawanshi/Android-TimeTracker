package shree.e.timetracker;

public class DataObject1 {
    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;

    DataObject1 (String text1, String text2, String text3, String text4){
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        mText4 = text4;
    }

    public String getmText1() {
        return mText1;
    }
    public String getmText2() {
        return mText2;
    }
    public String getmText3() {
        return mText3;
    }
    public String getmText4() {
        return mText4;
    }
}