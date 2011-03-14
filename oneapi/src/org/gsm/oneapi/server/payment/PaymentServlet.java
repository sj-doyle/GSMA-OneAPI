package org.gsm.oneapi.server.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gsm.oneapi.location.Locate;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.server.OneAPIServlet;

/**
 * No functional use at present 
 */
public class PaymentServlet extends OneAPIServlet {
	static Logger logger=Logger.getLogger(PaymentServlet.class);

	private static final long serialVersionUID = -5819947050596857914L;

//	public static boolean validRequest(HttpServletRequest request, HttpServletResponse response, String[] requestParts, String apiSection, String apiName) {
//		boolean valid=true;
//		
//		boolean failedAuthentication=isAuthorizationFailed(request, response);
//		
//		if (!failedAuthentication) {		
//			if (requestParts!=null) {
//				for (int i=0; i<requestParts.length; i++) {
//					System.out.println("["+i+"] = "+requestParts[i]);
//				}
//			}
//			
//			if (requestParts==null || requestParts.length<5) {
//				valid=false;
//				sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0002", "Request is missing required URI components", null);
//			} else if (requestParts[1]==null || !requestParts[1].equals(apiSection)) {
//				valid=false;
//				sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0002", "Request URI includes incorrect API section. Expected "+apiSection, requestParts[1]);
//			} else if (requestParts[3]==null || !requestParts[3].equals("transactions")) {
//				valid=false;
//				sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0002", "Request URI expected to specify 'transactions'", requestParts[3]);
//			} else if (requestParts[4]==null || !requestParts[4].equals(apiName)) {
//				valid=false;
//				sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0002", "Request URI includes incorrect API function.Expected "+apiName, requestParts[4]);
//			}
//		} else {
//			valid=false;
//		}
//		
//		return valid;
//	}

}
