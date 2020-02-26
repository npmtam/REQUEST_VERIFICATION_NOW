package pageUIs;

public class RequestsPageUIs {
	public static final String QUESTION_FILTER_BUTTON = "//span[text()='Câu hỏi']";
	public static final String DIDNT_ANSWER_THE_QUESTION_OPTION_LINK = "//span[@class='_54nh' and contains(text(),'Chưa trả lời')]";
	public static final String DECLINE_ALL = "//button[@name='decline_all']";
	public static final String NUMBER_OF_REQUESTS_MATCHED_TEXT = "//span[@class=' _50f7']";
	public static final String CONFIRMATION_TEXT_MEMBER_DIDNOT_ANSWER = "//div[contains(text(), 'Bạn sắp từ chối')]";
	public static final String CONFIRM_BUTTON_AT_CONFIRMATION_POPUP = "//button[@action='confirm']";
	public static final String CANCEL_BUTTON_AT_CONFIRMATION_POPUP = "//button[text()='Hủy']";
	public static final String REMOVE_FILTER_LINK = "//a[text()='Xóa bộ lọc']";

	//Member didn't answer
	public static final String NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL = "//span[contains(text(), 'yêu cầu khớp')]";
	public static final String NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL_BACKUP = "//span[contains(text(), 'yêu cầu trùng khớp')]";
	public static final String REQUEST_PRESENT = "//a[@class='_z_3']";

	//Sort request
    public static final String SORT_DROPDOWN_LINK = "//span[text()='Gợi ý' and @class='_55pe']/parent::a";
    public static final String SORT_SUBOPTIONS_LINK = "//span[text()='%s' and @class='_54nh']";
    public static final String OLDEST_OPTIONS_SELECTED = "//div[@class='uiPopover _6a']//span[@class='_55pe' and text()='Cũ nhất trước']";

    //List ID
    public static final String LIST_ID_INPUTTED = "//text[@truncate='200']";
    public static final String DECLINE_BUTTON_FOR_EACH_ID = "//text[@truncate='200' and contains(text(), '%s')]/ancestor::div[@class='_4wsr']/preceding-sibling::div[@class='_4wsp _51xa']/button[@name='decline']";
    public static final String MENU_RULE_DROPDOWN = "//span[text()='Quy tắc']";
}
