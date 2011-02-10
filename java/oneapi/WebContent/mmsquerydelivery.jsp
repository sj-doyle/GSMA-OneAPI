<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="org.gsm.oneapi.foundation.JSPUtils"%>    
<%@ page import="org.gsm.oneapi.mms.MMSSend"%>
<%@ page import="org.gsm.oneapi.responsebean.mms.MMSSendDeliveryStatusResponse"%>
<%@ page import="org.gsm.oneapi.responsebean.mms.DeliveryInfoList"%>
<%@ page import="org.gsm.oneapi.endpoints.LocalSandboxEndpoints"%>
<%@ page import="org.gsm.oneapi.endpoints.ServiceEndpoints"%>
<%@ page import="org.gsm.oneapi.foundation.JSONRequest"%>

<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", 0); 

	String submit=JSPUtils.nullOrTrimmed(request.getParameter("submit"));
	String username=JSPUtils.nullOrTrimmed(request.getParameter("username"));
	String password=JSPUtils.nullOrTrimmed(request.getParameter("password"));
	String endpoint=JSPUtils.nullOrTrimmed(request.getParameter("endpoint"));
	
	String senderAddress=JSPUtils.nullOrTrimmed(request.getParameter("senderAddress"));
	String requestId=JSPUtils.nullOrTrimmed(request.getParameter("requestId"));
	
	LocalSandboxEndpoints sandboxEndpoints=new LocalSandboxEndpoints();

	MMSSendDeliveryStatusResponse mmsResponse=null;
	DeliveryInfoList.DeliveryInfo[] deliveryInfo=null;
	
	if (submit==null) {
		
		senderAddress="tel:+123456789";
		requestId="123456";
		
		username="Fred.Jones";
		password="1234";
		endpoint=sandboxEndpoints.getServiceEndpoints().getQueryMMSDeliveryEndpoint();

	} else {
		sandboxEndpoints.setQueryMMSDelivery(endpoint);

		ServiceEndpoints serviceEndpoints=sandboxEndpoints.getServiceEndpoints();
		
		MMSSend me=new MMSSend(serviceEndpoints, username, password);
		
		mmsResponse=me.queryDeliveryStatus(senderAddress, requestId);	
		
		if (mmsResponse!=null && mmsResponse.getDeliveryInfoList()!=null) deliveryInfo=mmsResponse.getDeliveryInfoList().getDeliveryInfo();
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
            Outbound MMS API - Query MMS Delivery Status
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="mmsquerydelivery.jsp">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">senderAddress</div>
				<div class="ParameterValue"><input type="text" name="senderAddress" value="<%if (senderAddress!=null) out.print(senderAddress); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">requestId</div>
				<div class="ParameterValue"><input type="text" name="requestId" value="<%if (requestId!=null) out.print(requestId); %>"/></div>
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
          
          <% if (mmsResponse!=null) { %>
          <h3>Response:</h3>
          	<div class="ParameterGroupHeading"><strong>Response Code</strong></div>          
          	<div class="ParameterRow">
				<div class="ParameterLabel">httpResponseCode</div>
				<div class="ParameterValue"><%=mmsResponse.getHTTPResponseCode() %></div>
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">contentType</div>
				<div class="ParameterValue"><%=mmsResponse.getContentType()%></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">location</div>
				<div class="ParameterValue"><%=mmsResponse.getLocation()%></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">mmsResponse.toString()</div>
				<div class="ParameterValue"><%=mmsResponse.toString()%></div>				
			</div>
			<% if (mmsResponse.getRequestError()!=null) { %>
          		<div class="ParameterGroupHeading"><strong>Request Error</strong></div>
          		<% if (mmsResponse.getRequestError().getPolicyException()!=null) { %>          
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">POLICY EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><%=mmsResponse.getRequestError().getPolicyException().getMessageId()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><%=mmsResponse.getRequestError().getPolicyException().getText()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><%=mmsResponse.getRequestError().getPolicyException().getVariables()%></div>				
					</div>
				<% } 
          		if (mmsResponse.getRequestError().getServiceException()!=null) { %>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">SERVICE EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><%=mmsResponse.getRequestError().getServiceException().getMessageId()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><%=mmsResponse.getRequestError().getServiceException().getText()%></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><%=mmsResponse.getRequestError().getServiceException().getVariables()%></div>				
					</div>
				<% } %>
			<% } %>
			<% if (mmsResponse.getDeliveryInfoList()!=null) { %>
          		<div class="ParameterGroupHeading"><strong>deliveryInfoList</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel"># Delivery Info Responses</div>
					<div class="ParameterValue"><%=deliveryInfo!=null?deliveryInfo.length:0%></div>				
				</div>
				<% if (deliveryInfo!=null && deliveryInfo.length>0) {
					for (int i=0; i<deliveryInfo.length; i++) {
						if (deliveryInfo[i]!=null) {
							
							%>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">&nbsp;</div>
								<div class="ParameterValue">&nbsp;</div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">address [<%=i%>]</div>
								<div class="ParameterValue"><%=deliveryInfo[i].getAddress()%></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">deliveryStatus [<%=i%>]</div>
								<div class="ParameterValue"><%=deliveryInfo[i].getDeliveryStatus()%></div>				
							</div>
							<%
						}
					}
				} %>				
	          	<div class="ParameterRow">
					<div class="ParameterLabel">&nbsp;</div>
					<div class="ParameterValue">&nbsp;</div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">resourceURL</div>
					<div class="ParameterValue"><%=mmsResponse.getDeliveryInfoList().getResourceURL()%></div>				
				</div>
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
