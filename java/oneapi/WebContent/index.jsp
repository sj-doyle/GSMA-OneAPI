<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
            OneAPI Open Source Toolkit Home
          </h3>
          <p>
      
		</p>
		
          <p>This is a set of example JSP pages showing how to use the JSP example API. Each OneAPI function has its own page which allows a call to be made to the OneAPI server.</p>
          <p>Each example page provides an HTML form into which OneAPI parameters can be entered - for convenience example values are included for most parameters.</p>
          <p>Please refer to the OneAPI Interface descriptions:</p>
          <ul>
          <li><a href="https://gsma.securespsite.com/access/Access%20API%20Wiki/Payment%20RESTful%20API.aspx">Payment RESTful API</a> - charge mobile network users for your services</li>
          <li><a href="https://gsma.securespsite.com/access/Access%20API%20Wiki/SMS%20RESTful%20API.aspx">SMS RESTful API</a> - send and receive SMS via your application</li>
          <li><a href="https://gsma.securespsite.com/access/Access%20API%20Wiki/MMS%20RESTful%20API.aspx">MMS RESTful API</a> - send and receive MMS via your application</li>
          <li><a href="https://gsma.securespsite.com/access/Access%20API%20Wiki/Location%20RESTful%20API.aspx">Location RESTful API</a> - get the location of one or more mobile network users</li>
          </ul>
          
          <h3>Example Page Layout</h3>
          
          <p>When you initially load each example page there are three sections of form parameters:</p>
          <ul>
          <li>Mandatory OneAPI parameters for the function</li>
          <li>Optional OneAPI parameters for the function</li>
          <li>The 'network service endpoint' URL and the username/ password for authentication</li>
          </ul>
          <p>You can change any of these values and this will affect the request sent to the OneAPI server.</p>
          
          <p>Once you have submitted the OneAPI request the server response will be displayed:</p>
          <ul>
          <li>The HTTP response code - see the OneAPI documentation for the expected value</li>
          <li>The HTTP response Content Type header - in most cases the response will be 'application/json'</li>
          <li>Either an error message or</li>
          <li>The data returned to the application by the OneAPI server as a Java object</li>
          </ul>
		

        </div></div></div>
        
			</div>

			

      </div>
      
		<%@ include file="footer.inc" %>

    </div>
      


</body>
</html>
