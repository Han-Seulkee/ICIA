package beans;

public class MovieInfo {
	private String GN_NUMBER;      // 테이블 컬럼명하고 같게 하기 위해 대문자로 함
	private String MV_NUMBER;
	private String MV_TITLE;
	private String MV_PLOT;
	private String MV_GPA;
	private String GN_NAME;
	
	public String getGN_NAME() {
		return GN_NAME;
	}
	public void setGN_NAME(String gN_NAME) {
		GN_NAME = gN_NAME;
	}
	public String getGN_NUMBER() {
		return GN_NUMBER;
	}
	public void setGN_NUMBER(String gN_NUMBER) {
		GN_NUMBER = gN_NUMBER;
	}
	public String getMV_NUMBER() {
		return MV_NUMBER;
	}
	public void setMV_NUMBER(String mV_NUMBER) {
		MV_NUMBER = mV_NUMBER;
	}
	public String getMV_TITLE() {
		return MV_TITLE;
	}
	public void setMV_TITLE(String mV_TITLE) {
		MV_TITLE = mV_TITLE;
	}
	public String getMV_PLOT() {
		return MV_PLOT;
	}
	public void setMV_PLOT(String mV_PLOT) {
		MV_PLOT = mV_PLOT;
	}
	public String getMV_GPA() {
		return MV_GPA;
	}
	public void setMV_GPA(String mV_GPA) {
		MV_GPA = mV_GPA;
	}
	
	
}
