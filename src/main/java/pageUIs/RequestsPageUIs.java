package pageUIs;

public class RequestsPageUIs {
	public static final String QUESTION_FILTER_BUTTON = "//span[text()='Questions']";
	public static final String DIDNT_ANSWER_THE_QUESTION_OPTION_LINK = "//div[@role='menu']//span[contains(text(), 'Did')]";
	public static final String CLEAR_FILTER_DIDNT_ANSWER_QUESTION = "//span[contains(text(), 'Did')]/parent::div/following-sibling::div/div[@aria-label='Clear']";
	public static final String DECLINE_ALL = "//div[@aria-label='Decline All']";
	public static final String NO_RESULT = "//span[contains(text(), 'We Didn')]";
	public static final String NUMBER_OF_REQUESTS_MATCHED_TEXT = "//span[@class=' _50f7']";
	public static final String CONFIRMATION_TEXT_MEMBER_DIDNOT_ANSWER = "//div[contains(text(), 'about to decline')]";
	public static final String CONFIRM_BUTTON_AT_CONFIRMATION_POPUP = "//div[@aria-label='Confirm' and @tabindex='0']";
	public static final String CANCEL_BUTTON_AT_CONFIRMATION_POPUP = "//button[text()='Cancel']";

	//Member didn't answer
	public static final String NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL = "//span[text()='Matching Requests']//strong/span[2]";
	public static final String NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL_BACKUP = "//span[contains(text(), 'matching request')]";
	public static final String REQUEST_PRESENT = "//a[@class='_z_3']";

	//Sort request
    public static final String SORT_DROPDOWN_LINK = "//span[text()='Suggested' and @class='_55pe']/parent::a";
    public static final String SORT_SUBOPTIONS_LINK = "//span[text()='%s' and @class='_54nh']";
    public static final String OLDEST_OPTIONS_SELECTED = "//div[@class='uiPopover _6a']//span[@class='_55pe' and text()='Oldest first']";
    public static final String SORT_OPTIONS = "//span[text()='Suggested']";

    //List ID
    public static final String LIST_ID_INPUTTED = "//Span[contains(text(), 'Vui l')]/following-sibling::div/span";
    public static final String DECLINE_BUTTON_FOR_EACH_ID = "//span[text()='%s']/ancestor::div[@class='orn4t191']/preceding-sibling::div//div[@aria-label='Decline']";
    public static final String APPROVE_BUTTON_FOR_EACH_ID = "//span[text()='%s']/ancestor::div[@class='orn4t191']/preceding-sibling::div//div[@aria-label='Approve']";
    public static final String MATCHING_REQUESTS_TEXT = "//span[text()='Matching Requests']";
}
