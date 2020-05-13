package pageUIs;

public class RequestsPageUIs {
	public static final String QUESTION_FILTER_BUTTON = "//span[text()='Questions']";
	public static final String DIDNT_ANSWER_THE_QUESTION_OPTION_LINK = "//a[@class='_54nc']//span[contains(text(), 'Did')]";
	public static final String DECLINE_ALL = "//button[@name='decline_all']";
	public static final String NUMBER_OF_REQUESTS_MATCHED_TEXT = "//span[@class=' _50f7']";
	public static final String CONFIRMATION_TEXT_MEMBER_DIDNOT_ANSWER = "//div[contains(text(), 'about to decline')]";
	public static final String CONFIRM_BUTTON_AT_CONFIRMATION_POPUP = "//button[@action='confirm']";
	public static final String CANCEL_BUTTON_AT_CONFIRMATION_POPUP = "//button[text()='Cancel']";
	public static final String REMOVE_FILTER_LINK = "//a[text()='Clear Filters']";

	//Member didn't answer
	public static final String NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL = "//span[contains(text(), 'Matching Requests')]";
	public static final String NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL_BACKUP = "//span[contains(text(), 'Matching Request')]";
	public static final String REQUEST_PRESENT = "//a[@class='_z_3']";

	//Sort request
    public static final String SORT_DROPDOWN_LINK = "//span[text()='Suggested' and @class='_55pe']/parent::a";
    public static final String SORT_SUBOPTIONS_LINK = "//span[text()='%s' and @class='_54nh']";
    public static final String OLDEST_OPTIONS_SELECTED = "//div[@class='uiPopover _6a']//span[@class='_55pe' and text()='Oldest First']";

    //List ID
    public static final String LIST_ID_INPUTTED = "//text[@truncate='200']";
    public static final String DECLINE_BUTTON_FOR_EACH_ID = "//text[contains(text(), '%s')]/ancestor::div[@class='_42ef']//button[@name='approve']";
    public static final String APPROVE_BUTTON_FOR_EACH_ID = "//text[contains(text(), '%s')]/ancestor::div[@class='_42ef']//button[@name='decline']";
    public static final String REQUESTS_TEXT = "//div[@class='_7ghg']/span[text()='Member Request']";
}
