<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="org.gsm.oneapi.foundation.JSPUtils"%>    
<%@ page import="org.gsm.oneapi.location.Locate"%>
<%@ page import="org.gsm.oneapi.responsebean.location.LocationResponse"%>
<%@ page import="org.gsm.oneapi.responsebean.location.TerminalLocation"%>
<%@ page import="org.gsm.oneapi.endpoints.LocalSandboxEndpoints"%>
<%@ page import="org.gsm.oneapi.endpoints.ServiceEndpoints"%>
<%@ page import="org.gsm.oneapi.foundation.JSONRequest"%>

<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", 0); 

	String submit=JSPUtils.nullOrTrimmed(request.getParameter("submit"));
	String address=JSPUtils.nullOrTrimmed(request.getParameter("address"));
	int requestedAccuracy=JSPUtils.parseInt(request.getParameter("requestedAccuracy"));

	String username=JSPUtils.nullOrTrimmed(request.getParameter("username"));
	String password=JSPUtils.nullOrTrimmed(request.getParameter("password"));
	String endpoint=JSPUtils.nullOrTrimmed(request.getParameter("endpoint"));
	
	LocalSandboxEndpoints sandboxEndpoints=new LocalSandboxEndpoints();

	LocationResponse locationResponse=null;
	TerminalLocation[] terminalLocation=null;
	
	if (submit==null) {
		address="tel:+16035558278";
		requestedAccuracy=1000;
		username="Fred.Jones";
		password="1234";
		endpoint=sandboxEndpoints.getServiceEndpoints().getLocationEndpoint();
	} else {		
		sandboxEndpoints.setLocation(endpoint);

		ServiceEndpoints serviceEndpoints=sandboxEndpoints.getServiceEndpoints();
		
		String authorisationHeader=JSONRequest.getAuthorisationHeader(username, password);

		Locate me=new Locate(serviceEndpoints, authorisationHeader);
		
		locationResponse=me.locateTerminal(address, requestedAccuracy);
		if (locationResponse!=null && locationResponse.getTerminalLocationList()!=null) {
			terminalLocation=locationResponse.getTerminalLocationList().getTerminalLocation();
		}		
	}
	
	
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>    
    <title>
      GSM World | OneAPI Toolkit
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="GSM, GSMA, GSM Association, Mobile, Mobile World Congress, Mobile Asia Congress, Mobile Awards, Global Mobile Awards, 3GSM, Mobile Broadband, Spectrum, Development Fund, Mobile Money, Mobile Innovation, Pathfinder, Open Connectivity, Fraud" />
    <meta name="description" content="The GSMA represents the interests of the worldwide mobile communications industry. Spanning 219 countries, the GSMA unites more than 750 of the world&rsquo;s mobile operators, as well as 200 companies in the broader mobile ecosystem." />
    <link rel="stylesheet" href="screen.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="toolkit.css" type="text/css" media="screen" />

    <link rel="shortcut icon" href="#" />
</head>

  <body id="home">

    <div id="container">

      <div id="masthead">
        <p id="logo">
          <a href="index.html">GSM World</a>
        </p>
        <p id="strap-line">
          <strong>Connecting the World</strong>
        </p>
      </div>
      
<div id="content">

	<%@ include file="mainmenu.inc" %>
        
        <div class="col-760">
        

        <div id="other-main">
          <div class="other-top"></div>
          <div class="other-bottom">
            <div class="left">
          <h3>
            Locate Single Terminal
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="locatesingle.jsp">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address</div>
				<div class="ParameterValue"><input type="text" name="address" value="<%if (address!=null) out.print(address); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">requestedAccuracy</div>
				<div class="ParameterValue"><input type="text" name="requestedAccuracy" value="<%=requestedAccuracy %>"/></div>
			</div>
			<div class="ParameterGroupHeading"><strong>Optional Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">&nbsp;</div>
				<div class="ParameterValue"><em>None</em></div>
			</div>
			<div class="ParameterGroupHeading"><strong>Service Endpoint Information</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">Username</div>
				<div class="ParameterValue"><input type="text" name="username" value="<%if (username!=null) out.print(username); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">Password</div>
				<div class="ParameterValue"><input type="text" name="password" value="<%if (password!=null) out.print(password); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">Service Endpoint</div>
				<div class="ParameterValue"><textarea name="endpoint" style="width:400px;height:80px;"><%if (endpoint!=null) out.print(endpoint); %></textarea></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">&nbsp;</div>
				<div class="ParameterValue"><hr width="50%"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">&nbsp;</div>
				<div class="ParameterValue"><input type="submit" name="submit" value="Submit"/></div>
			</div>

		</form>
          
          <% if (locationResponse!=null) { %>
          <h3>Response:</h3>
          	<div class="ParameterGroupHeading"><strong>Response Code</strong></div>          
          	<div class="ParameterRow">
				<div class="ParameterLabel">httpResponseCode</div>
				<div class="ParameterValue"><%=locationResponse.getHTTPResponseCode() %></div>
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">contentType</div>
				<div class="ParameterValue"><%=locationResponse.getContentType()%></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">location</div>
				<div class="ParameterValue"><%=locationResponse.getLocation()%></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">locationResponse.toString()</div>
				<div class="ParameterValue"><%=locationResponse.toString()%></div>				
			</div>
			<% if (locationResponse.getRequestError()!=null) { %>
          		<div class="ParameterGroupHeading"><strong>Request Error</strong></div>
          		<% if (locationResponse.getRequestError().getPolicyException()!=null) { %>          
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">POLICY EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><%=locationResponse.getRequestError().getPolicyException().getMessageId()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><%=locationResponse.getRequestError().getPolicyException().getText()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><%=locationResponse.getRequestError().getPolicyException().getVariables()%></div>				
					</div>
				<% } 
          		if (locationResponse.getRequestError().getServiceException()!=null) { %>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">SERVICE EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><%=locationResponse.getRequestError().getServiceException().getMessageId()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><%=locationResponse.getRequestError().getServiceException().getText()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><%=locationResponse.getRequestError().getServiceException().getVariables()%></div>				
					</div>
				<% } %>
			<% } %>
			<% if (locationResponse.getTerminalLocationList()!=null) { %>
          		<div class="ParameterGroupHeading"><strong>Terminal Location List</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel"># Location Responses</div>
					<div class="ParameterValue"><%=terminalLocation!=null?terminalLocation.length:0%></div>				
				</div>
				<% if (terminalLocation!=null && terminalLocation.length>0) {
					for (int i=0; i<terminalLocation.length; i++) {
						if (terminalLocation[i].getCurrentLocation()!=null) {
							TerminalLocation.CurrentLocation currentLocation=terminalLocation[i].getCurrentLocation();
							%>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">&nbsp;</div>
								<div class="ParameterValue">&nbsp;</div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">accuracy [<%=i%>]</div>
								<div class="ParameterValue"><%=currentLocation.getAccuracy()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">altitude [<%=i%>]</div>
								<div class="ParameterValue"><%=currentLocation.getAltitude()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">latitude [<%=i%>]</div>
								<div class="ParameterValue"><%=currentLocation.getLatitude()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">longitude [<%=i%>]</div>
								<div class="ParameterValue"><%=currentLocation.getLongitude()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">timestamp [<%=i%>]</div>
								<div class="ParameterValue"><%=currentLocation.getTimestamp()%></div>				
							</div>
							<%
						}
						if (terminalLocation[i].getErrorInformation()!=null && terminalLocation[i].getErrorInformation().getServiceException()!=null) {
							TerminalLocation.RequestError requestError=terminalLocation[i].getErrorInformation();
							%>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">&nbsp;</div>
								<div class="ParameterValue">&nbsp;</div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">serviceException.messageId [<%=i%>]</div>
								<div class="ParameterValue"><%=requestError.getServiceException().getMessageId()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">serviceException.text [<%=i%>]</div>
								<div class="ParameterValue"><%=requestError.getServiceException().getText()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">serviceException.variables [<%=i%>]</div>
								<div class="ParameterValue"><%=requestError.getServiceException().getVariables()%></div>				
							</div>
							<%
						}
					}				
				} %>
			<% } %>
							
          
          
          <% } %>

		    <div class="ParameterRow">
				<div class="ParameterLabel">&nbsp;</div>
				<div class="ParameterValue">&nbsp;</div>				
			</div>

        </div></div></div>
        
			</div>

			

      </div>
      
		<%@ include file="footer.inc" %>

    </div>
      


</body>
</html>
