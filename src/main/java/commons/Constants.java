package commons;

public class Constants {
    static String rootFolder = System.getProperty("user.dir");
    static String fileName = "ShipperID.csv";

    //INFO
    public static final String URL = "https://www.facebook.com/";
    public static String EMAIL = null;
    public static String PASSWORD = null;
    public static final String ACCOUNT_FILE = "login.txt";
    public static final String GROUP_ID = "822546994752924";

    public static final String NEWEST_SORT = "Newest first";
    public static final String LATEST_SORT = "Oldest first";

    public static final String FILE_PATH =  rootFolder + "\\src\\test\\resources\\" + fileName;
    public static final String COMMA_DELIMITER = ",";
    public static final String NEW_LINE_SEPARATOR = "\n";

    public static String REQUEST_ID = null;
    public static String STATUS = null;

    public static final String DECLINE = "DECLINE";
    public static final String NEED_CONFIRMATION = "Need Confirmation";

    //SQL
    public static final String HOST_NAME = "localhost";
    public static final String INSTANCE_NAME = "SQLEXPRESS";
    public static final String DATABASE = "REQUEST_VERIFICATION_NOW";
    public static final String DB_USERNAME = "sa";
    public static final String DB_PASSWORD = "sa";
    public static String REQUEST_STATUS = null;
    public static boolean isTheIDExisted;

}
