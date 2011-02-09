<%
	System.out.println("Have invoked callback");
	System.out.println("Request content type="+request.getContentType());
	
	ServletInputStream inputStream=request.getInputStream();
	
	System.out.println("Request body:\n");
	int c;
	while ((c=inputStream.read())!=-1) {
		System.out.print((char) c);
	}

	System.out.println("\nEND OF REQUEST BODY");
	response.setStatus(200);
%>