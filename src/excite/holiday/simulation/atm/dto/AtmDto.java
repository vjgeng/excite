package excite.holiday.simulation.atm.dto;


public class AtmDto {
	int requestAmount;
	String twentyType;
	int twentyNoteAmount;
	int fiftyNoteAmount;
	int twentyNoteTotal;
	int fiftyNoteTotal;
	boolean updateFlag;
	String message;

	public int getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(int requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getTwentyType() {
		return twentyType;
	}

	public void setTwentyType(String twentyType) {
		this.twentyType = twentyType;
	}

	public int getTwentyNoteAmount() {
		return twentyNoteAmount;
	}

	public void setTwentyNoteAmount(int twentyNoteAmount) {
		this.twentyNoteAmount = twentyNoteAmount;
	}

	public int getFiftyNoteAmount() {
		return fiftyNoteAmount;
	}

	public void setFiftyNoteAmount(int fiftyNoteAmount) {
		this.fiftyNoteAmount = fiftyNoteAmount;
	}

	public int getTwentyNoteTotal() {
		return twentyNoteTotal;
	}

	public void setTwentyNoteTotal(int twentyNoteTotal) {
		this.twentyNoteTotal = twentyNoteTotal;
	}

	public int getFiftyNoteTotal() {
		return fiftyNoteTotal;
	}

	public void setFiftyNoteTotal(int fiftyNoteTotal) {
		this.fiftyNoteTotal = fiftyNoteTotal;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
