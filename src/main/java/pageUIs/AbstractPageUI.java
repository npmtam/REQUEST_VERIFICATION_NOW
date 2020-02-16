package pageUIs;

public class AbstractPageUI {
	
	public static final String HEADER_LINKS = "//div[@class='header']//a[contains(text(), '%s')]";
	public static final String HEADER_MENU_DYNAMIC = "//ul[@class='top-menu notmobile']//a[contains(text(), '')]";
	public static final String HEADER_SUB_MENU_DYNAMIC = "//ul[@class='top-menu notmobile']//a[text()='%s']";
	public static final String FOOTER_LINKS = "//div[@class='footer']//a[text()='%s']";
	public static final String SUB_PAGE_TITLE = "//div[@class='page-title']/h1";
}
 