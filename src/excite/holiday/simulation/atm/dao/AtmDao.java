package excite.holiday.simulation.atm.dao;

public class AtmDao {

	int twentyNoteAmount;
	int fiftyNoteAmount;
	int twentyNoteTotal;
	int fiftyNoteTotal;
	boolean updateFlag;

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

}
