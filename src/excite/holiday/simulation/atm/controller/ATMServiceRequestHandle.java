package excite.holiday.simulation.atm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excite.holiday.simulation.atm.dto.AtmDto;
import excite.holiday.simulation.atm.model.ATMProcessRequest;

/**
 * Servlet implementation class ATMServiceRequestHandle
 */
@WebServlet("/atmServiceRequestHandle")
public class ATMServiceRequestHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ATMProcessRequest atm = new ATMProcessRequest();
		List<AtmDto> getNoteTypeList = new ArrayList<AtmDto>();

		try {
			getNoteTypeList = atm.queryAvailableNoteType();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("getNoteTypeList", getNoteTypeList);
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AtmDto dto = new AtmDto();
		ATMProcessRequest atm = new ATMProcessRequest();
		int withdrawRequestAmount = Integer.parseInt(request.getParameter("amountRequest"));
		int dispenseNote = 0;
		int dispenseNote20 = 0;
		int requestAmount = 0;
		boolean updateFlag = false;
		try {
			List<AtmDto> dtoList = atm.makeWithDraw(withdrawRequestAmount);
			for (AtmDto atmDto : dtoList) {
				dispenseNote20 = atmDto.getTwentyNoteAmount();
				dispenseNote = atmDto.getFiftyNoteAmount();
				requestAmount = atmDto.getRequestAmount();
				updateFlag = atmDto.isUpdateFlag();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (updateFlag) {

			request.setAttribute("message", dispenseNote);
			request.setAttribute("dispensenote20", dispenseNote20);
			request.setAttribute("requestamount", requestAmount);
			RequestDispatcher view = request.getRequestDispatcher("withdrawconfirm.jsp");
			view.forward(request, response);
		} else {

			request.setAttribute("message", "Insufficient Fund in Account");
			RequestDispatcher view = request.getRequestDispatcher("warning.jsp");
			view.forward(request, response);
		}

	}

}
