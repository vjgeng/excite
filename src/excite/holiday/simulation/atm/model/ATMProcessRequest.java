package excite.holiday.simulation.atm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import excite.holiday.simulation.atm.dto.AtmDto;
import excite.holiday.simulation.utils.DBUtils;

public class ATMProcessRequest {

	/*
	 * UPDATE playercareer c INNER JOIN ( SELECT gameplayer, SUM(points) as
	 * total FROM games GROUP BY gameplayer ) x ON c.playercareername =
	 * x.gameplayer SET c.playercareerpoints = x.total
	 */

	private final String UPDATENOTETYPE = "UPDATE noteType SET noteAmount=? WHERE noteId=?";
	private final String GETNOTETYPE = "select noteValue from notetype";
	private final String GETTOTALBALANCE = "select sum(noteValue * noteAmount) as totalBalance from notetype";
	private final String UPDATEBALANCE = "update account a inner join (select sum(notevalue * noteamount) as total from notetype ) t set a.balance = t.total;";
	private final String GETFIFTYNOTEAMOUNT = "select  noteAmount from notetype where noteId=2";
	private final String GETTWENTYNOTEAMOUNT = "select noteAmount from notetype where noteId=1";
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public ATMProcessRequest() {
	}

	public List<AtmDto> queryAvailableNoteType() throws Exception {
		conn = DBUtils.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(GETNOTETYPE);
		List<AtmDto> noteTypeList = new ArrayList<AtmDto>();

		while (rs.next()) {
			AtmDto dto = new AtmDto();
			dto.setTwentyType(rs.getString(1));
			noteTypeList.add(dto);
		}

		return noteTypeList;
	}

	public int queryTotalBalance() throws Exception {
		int balance = 0;
		conn = DBUtils.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(GETTOTALBALANCE);

		while (rs.next()) {
			balance = rs.getInt(1);
		}
		return balance;
	}

	public int getTotalFifty() throws Exception {
		return 50 * getFiftyNote();
	}

	public int getTotalTwenty() throws Exception {
		return 20 * getFiftyNote();
	}

	public boolean validateAccountFund(int fiftyTotalvalue, int amount) {
		boolean isValidFund = true;
		if (fiftyTotalvalue < amount) {
			isValidFund = false;
		}

		return isValidFund;
	}

	public List<AtmDto> makeWithDraw(int amount) throws Exception {

		List<AtmDto> dtoList = new ArrayList<AtmDto>();

		if (getBalance() >= amount) {
			dtoList = withDraw(amount);
		}

		return dtoList;
	}

	public List<AtmDto> withDraw(int amount) throws Exception {
		AtmDto dto = new AtmDto();
		List<AtmDto> dtoList = new ArrayList<AtmDto>();
		boolean updateFlag = false;
		int noteId = 0;
		int twentyNoteAmount = getTwentyNote();
		int fiftyNoteAmount = getFiftyNote();
		int twentyTotalValue = 20 * getTwentyNote();
		int fiftyTotalvalue = 50 * getFiftyNote();

		if (amount % 50 == 0 && fiftyTotalvalue >= amount) {
			noteId = 2;
			int dispenseNote20 = 0;
			int dispenseNote = amount / 50;
			int remainingNote = fiftyNoteAmount - dispenseNote;
			updateNoteType(remainingNote, noteId);
			updateBalance();
			updateFlag = true;
			dto.setTwentyNoteAmount(dispenseNote20);
			dto.setFiftyNoteAmount(dispenseNote);
			dto.setRequestAmount(amount);
			dto.setUpdateFlag(updateFlag);
			dtoList.add(dto);

		} else if (amount % 20 == 0 && twentyTotalValue >= amount) {
			noteId = 1;
			int dispenseNote20 = amount / 20;
			int dispenseNote = 0;
			int remainingNote = twentyNoteAmount - dispenseNote20;
			updateNoteType(remainingNote, noteId);
			updateBalance();
			updateFlag = true;
			dto.setTwentyNoteAmount(dispenseNote20);
			dto.setFiftyNoteAmount(dispenseNote);
			dto.setRequestAmount(amount);
			dto.setUpdateFlag(updateFlag);
			dtoList.add(dto);

		} else {
			int remainOf50Mod = amount % 50;
			int remainingAmount = amount - remainOf50Mod;
			int dispenseNote = remainingAmount / 50;
			int remain50Note = fiftyNoteAmount - dispenseNote;
			int remainAmounddiff = 0;
			int dispenseNote20 = 0;
			int remain20Note = 0;
			int remainOf20Mod = remainOf50Mod % 20;
			if (remainOf20Mod == 0) {
				noteId = 2;
				updateNoteType(remain50Note, noteId);
				remainAmounddiff = amount - remainingAmount;
				dispenseNote20 = remainAmounddiff / 20;
				remain20Note = twentyNoteAmount - dispenseNote20;
				noteId = 1;
				updateNoteType(remain20Note, noteId);
				updateBalance();
				updateFlag = true;
				dto.setFiftyNoteAmount(dispenseNote);
				dto.setTwentyNoteAmount(dispenseNote20);
				dto.setRequestAmount(amount);
				dto.setUpdateFlag(updateFlag);
				dtoList.add(dto);

			} else {
				updateFlag = false;
				dto.setFiftyNoteAmount(0);
				dto.setTwentyNoteAmount(0);
				dto.setUpdateFlag(updateFlag);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	public void updateNoteType(int noteAmount, int noteId) throws Exception {

		conn = DBUtils.getConnection();
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(UPDATENOTETYPE);
		pstmt.setInt(1, noteAmount);
		pstmt.setInt(2, noteId);
		pstmt.executeUpdate();
	}

	public void updateBalance() throws Exception {
		conn = DBUtils.getConnection();
		stmt = conn.createStatement();
		stmt.execute(UPDATEBALANCE);
	}

	public int getBalance() throws Exception {
		return queryTotalBalance();
	}

	public int getTwentyNote() throws Exception {
		int amount = 0;
		conn = DBUtils.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(GETTWENTYNOTEAMOUNT);

		while (rs.next()) {
			amount = rs.getInt(1);
		}
		return amount;
	}

	public int getFiftyNote() throws Exception {
		int amount = 0;
		conn = DBUtils.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(GETFIFTYNOTEAMOUNT);

		while (rs.next()) {
			amount = rs.getInt(1);
		}
		return amount;
	}

}
