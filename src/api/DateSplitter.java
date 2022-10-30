package api;

public class DateSplitter {
    
    private String date;
    private String year;
    private String month;
    private String day;

    public void setDate(String date){
        this.date = date;
    }

    public void execute() {
        String data_array[] = date.split("-");
        
        try {
            year = data_array[0];
            month = data_array[1];
            day = data_array[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("日付のフォーマットが異なります");
        }
    }

    public int getYear() {
        return Integer.parseInt(year);
    }

    public int getMonth() {
        return Integer.parseInt(month);
    }

    public int getDay() {
        return Integer.parseInt(day);
    }
}


