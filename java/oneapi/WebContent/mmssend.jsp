<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="org.gsm.oneapi.foundation.JSPUtils"%>    
<%@ page import="org.gsm.oneapi.mms.MMSSend"%>
<%@ page import="org.gsm.oneapi.responsebean.mms.MMSSendResponse"%>
<%@ page import="org.gsm.oneapi.endpoints.LocalSandboxEndpoints"%>
<%@ page import="org.gsm.oneapi.endpoints.ServiceEndpoints"%>
<%@ page import="org.gsm.oneapi.foundation.JSONRequest"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.fileupload.FileUploadException"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.util.ArrayList"%>

<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", 0); 

	String submit=JSPUtils.nullOrTrimmed(request.getParameter("submit"));
	String username=JSPUtils.nullOrTrimmed(request.getParameter("username"));
	String password=JSPUtils.nullOrTrimmed(request.getParameter("password"));
	String endpoint=JSPUtils.nullOrTrimmed(request.getParameter("endpoint"));
	
	String senderAddress=JSPUtils.nullOrTrimmed(request.getParameter("senderAddress"));
	String address0=JSPUtils.nullOrTrimmed(request.getParameter("address0"));
	String address1=JSPUtils.nullOrTrimmed(request.getParameter("address1"));
	String address2=JSPUtils.nullOrTrimmed(request.getParameter("address2"));
	String address3=JSPUtils.nullOrTrimmed(request.getParameter("address3"));
	String address4=JSPUtils.nullOrTrimmed(request.getParameter("address4"));
	String message=JSPUtils.nullOrTrimmed(request.getParameter("message"));
	String clientCorrelator=JSPUtils.nullOrTrimmed(request.getParameter("clientCorrelator"));
	String senderName=JSPUtils.nullOrTrimmed(request.getParameter("senderName"));
	String notifyURL=JSPUtils.nullOrTrimmed(request.getParameter("notifyURL"));
	String callbackData=JSPUtils.nullOrTrimmed(request.getParameter("callbackData"));
	
	FileItem attachment0=null;
	FileItem attachment1=null;
	FileItem attachment2=null;
	FileItem attachment3=null;
	FileItem attachment4=null;
	
	ArrayList<FileItem> attachments=new ArrayList<FileItem>();
	
	/*
	 * Form fields need to be processed through Apache Commons File Upload
	 */
	 
	DiskFileItemFactory factory = new DiskFileItemFactory();

	// Set factory constraints
	factory.setSizeThreshold(10*1024*1024);

	// Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload(factory);

	// Set overall request size constraint
	upload.setSizeMax(50*1024*1024);

	// Parse the request
	try {
		List<FileItem> items = (List<FileItem>) upload.parseRequest(request);
		
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();

		    if (item.isFormField() && item.getFieldName()!=null) {
		    	String name = item.getFieldName();
		        String value = item.getString();
		        System.out.println("PARAMETER...Field Name="+name+" value="+value);
		        
		        if (name.equals("submit")) submit=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("username")) username=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("password")) password=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("endpoint")) endpoint=JSPUtils.nullOrTrimmed(value);
		        
		        if (name.equals("senderAddress")) senderAddress=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("address0")) address0=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("address1")) address1=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("address2")) address2=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("address3")) address3=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("address4")) address4=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("message")) message=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("clientCorrelator")) clientCorrelator=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("senderName")) senderName=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("notifyURL")) notifyURL=JSPUtils.nullOrTrimmed(value);
		        if (name.equals("callbackData")) callbackData=JSPUtils.nullOrTrimmed(value);

		    } else {
		    	String fieldName = item.getFieldName();
		        String fileName = item.getName();
		        String fileType = item.getContentType();
		        System.out.println("FILE...Field Name="+fieldName+" fileName="+fileName+" fileType="+fileType);
		        if (fieldName!=null && item.getSize()>0) {
		        	if (fieldName.equals("attachment0")) attachments.add(item);
		        	if (fieldName.equals("attachment1")) attachments.add(item);
		        	if (fieldName.equals("attachment2")) attachments.add(item);
		        	if (fieldName.equals("attachment3")) attachments.add(item);
		        	if (fieldName.equals("attachment4")) attachments.add(item);
		        }
		    }
		}
	} catch (FileUploadException e) {
	}
	
	LocalSandboxEndpoints sandboxEndpoints=new LocalSandboxEndpoints();

	MMSSendResponse mmsResponse=null;
	
	if (submit==null) {
		
		senderAddress="tel:+123456789";
		address0="tel:+13500000991";
		address1="tel:+13500000992";
		message="Hello World!";
		clientCorrelator="123456";
		notifyURL="http://application.example.com/notifications/DeliveryInfoNotification";
		senderName="ACME Inc.";
		callbackData="some-data-useful-to-the-requester";
		
		username="Fred.Jones";
		password="1234";
		endpoint=sandboxEndpoints.getServiceEndpoints().getSendMMSEndpoint();

	} else {
		sandboxEndpoints.setSendMMS(endpoint);

		ServiceEndpoints serviceEndpoints=sandboxEndpoints.getServiceEndpoints();
		
		String authorisationHeader=JSONRequest.getAuthorisationHeader(username, password);
		
		MMSSend me=new MMSSend(serviceEndpoints, authorisationHeader);
		
		String[] addressList={address0, address1, address2, address3, address4};
		
		mmsResponse=me.sendMMS(senderAddress, addressList, message, attachments, senderName, clientCorrelator, notifyURL, callbackData);
			
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
            Outbound MMS API - Send MMS
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="mmssend.jsp" enctype="multipart/form-data">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">senderAddress</div>
				<div class="ParameterValue"><input type="text" name="senderAddress" value="<%if (senderAddress!=null) out.print(senderAddress); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [0]</div>
				<div class="ParameterValue"><input type="text" name="address0" value="<%if (address0!=null) out.print(address0); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">message</div>
				<div class="ParameterValue"><textarea name="message" style="width:400px;height:80px;"><%if (message!=null) out.print(message); %></textarea></div>
			</div>
			<div class="ParameterGroupHeading"><strong>Optional Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">attachment [0]</div>
				<div class="ParameterValue"><input type="file" name="attachment0"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">attachment [1]</div>
				<div class="ParameterValue"><input type="file" name="attachment1"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">attachment [2]</div>
				<div class="ParameterValue"><input type="file" name="attachment2"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">attachment [3]</div>
				<div class="ParameterValue"><input type="file" name="attachment3"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">attachment [4]</div>
				<div class="ParameterValue"><input type="file" name="attachment4"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [1]</div>
				<div class="ParameterValue"><input type="text" name="address1" value="<%if (address1!=null) out.print(address1); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [2]</div>
				<div class="ParameterValue"><input type="text" name="address2" value="<%if (address2!=null) out.print(address2); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [3]</div>
				<div class="ParameterValue"><input type="text" name="address3" value="<%if (address3!=null) out.print(address3); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [4]</div>
				<div class="ParameterValue"><input type="text" name="address4" value="<%if (address4!=null) out.print(address4); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">clientCorrelator</div>
				<div class="ParameterValue"><input type="text" name="clientCorrelator" value="<%if (clientCorrelator!=null) out.print(clientCorrelator); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">senderName</div>
				<div class="ParameterValue"><input type="text" name="senderName" value="<%if (senderName!=null) out.print(senderName); %>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">notifyURL</div>
				<div class="ParameterValue"><textarea name="notifyURL" style="width:400px;height:80px;"><%if (notifyURL!=null) out.print(notifyURL); %></textarea></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">callbackData</div>
				<div class="ParameterValue"><input type="text" name="callbackData" value="<%if (callbackData!=null) out.print(callbackData); %>"/></div>
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
			<% if (mmsResponse.getResourceReference()!=null) { %>
          		<div class="ParameterGroupHeading"><strong>resourceReference</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">resourceURL</div>
					<div class="ParameterValue"><%=mmsResponse.getResourceReference().getResourceURL()%></div>				
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
