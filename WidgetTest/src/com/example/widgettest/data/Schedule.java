package com.example.widgettest.data;

/**
 * @author furukawa
 * スケジュールデータクラス
 */
public class Schedule {

	private int id = 0;
	private int scheduleYear = 0;
	private int scheduleMonth = 0;
	private int scheduleDay = 0;
	private int scheduleHour = 0;
	private int scheduleMinute = 0;
	private int additionalCondition = 0;
	private int displayCondition = 0;
	private int importance = 0;
	private boolean displayTelopFlag = false;
	private String scheduleTitle = "";
	private String scheduleText = "";
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getScheduleYear() {
		return scheduleYear;
	}
	
	public void setScheduleYear(int scheduleYear) {
		this.scheduleYear = scheduleYear;
	}
	
	public String getScheduleYearInScreen() {
		return String.format("0000", scheduleYear);
	}
	
	public int getScheduleMonth() {
		return scheduleMonth;
	}
	
	public void setScheduleMonth(int scheduleMonth) {
		this.scheduleMonth = scheduleMonth;
	}
	
	public String getScheduleMonthInScreen() {
		return String.format("00", scheduleMonth);
	}
	
	public int getScheduleDay() {
		return scheduleDay;
	}
	
	public void setScheduleDay(int scheduleDay) {
		this.scheduleDay = scheduleDay;
	}
	
	public String getScheduleDayInScreen() {
		return String.format("00", scheduleDay);
	}
	
	public int getScheduleHour() {
		return scheduleHour;
	}
	
	public void setScheduleHour(int scheduleHour) {
		this.scheduleHour = scheduleHour;
	}
	
	public String getScheduleHourInScreen() {
		return String.format("00", scheduleHour);
	}
	
	public int getScheduleMinute() {
		return scheduleMinute;
	}
	
	public void setScheduleMinute(int scheduleMinute) {
		this.scheduleMinute = scheduleMinute;
	}
	
	public String getScheduleMinuteInScreen() {
		return String.format("00", scheduleMinute);
	}
	
	public int getAdditionalCondition() {
		return additionalCondition;
	}
	
	public void setAdditionalCondition(int additionalCondition) {
		this.additionalCondition = additionalCondition;
	}
	
	public String getAdditionalConditionInScreen() {
		String value = "";
		switch (additionalCondition) {
		case AdditionalCondition.SHINE:
			value = "晴れ";
			break;
		case AdditionalCondition.CLOUD:
			value = "曇り";
			break;
		case AdditionalCondition.RAIN:
			value = "雨";
			break;
		}
		return value;
	}
	
	public int getDisplayCondition() {
		return displayCondition;
	}
	
	public void setDisplayCondition(int displayCondition) {
		this.displayCondition = displayCondition;
	}
	
	public String getDisplayConditionInScreen() {
		String value = "";
		switch (displayCondition) {
		case DisplayCondition.ONLY:
			value = "のみ";
			break;
		case DisplayCondition.OTHER_THAN:
			value = "以外";
			break;
		}
		return value;
	}
	
	public int getImportance() {
		return importance;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
	}
	
	public String getImportanceInScreen() {
		String value = "";
		switch (importance) {
		case Importance.LOW:
			value = "低";
			break;
		case Importance.MIDDLE:
			value = "中";
			break;
		case Importance.HIGH:
			value = "高";
			break;
		}
		return value;
	}
	
	public boolean getDisplayTelopFlag() {
		return displayTelopFlag;
	}
	
	public void setDisplayTelopFlag(boolean displayTelopFlag) {
		this.displayTelopFlag = displayTelopFlag;
	}
	
	public String getDisplayTelopFlagInScreen() {
		String value = "表示なし";
		if (displayTelopFlag) {
			value = "表示あり";
		}
		return value;
	}
	
	public String getScheduleTitle() {
		return scheduleTitle;
	}
	
	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}
	
	public String getScheduleText() {
		return scheduleText;
	}
	
	public void setScheduleText(String scheduleText) {
		this.scheduleText = scheduleText;
	}

	public class AdditionalCondition {
		//設定なし
		public final static int NONE = 0;
		//晴れ
		public final static int SHINE = 1;
		//曇り
		public final static int CLOUD = 2;
		//雨
		public final static int RAIN = 3;	
	}
	
	public class DisplayCondition {
		//設定なし
		public final static int NONE = 0;
		//のみ
		public final static int ONLY = 1;
		//以外
		public final static int OTHER_THAN = 2;
	}
	
	public class Importance {
		//設定なし
		public final static int NONE = 0;
		//低
		public final static int LOW = 1;
		//中
		public final static int MIDDLE = 2;
		//高
		public final static int HIGH = 3;
		
	}
}
