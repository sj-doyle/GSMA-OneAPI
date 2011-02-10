<?php 
	header("Cache-Control: no-store, no-cache, must-revalidate");	
	header("Cache-Control: post-check=0, pre-check=0", false);
	header('Pragma: no-cache');
	header("Expires: Sat, 26 Jul 1997 00:00:00 GMT");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?php 

	$ONEAPICOMMON = dirname(__FILE__)."/oneapi/";

	include ($ONEAPICOMMON.'endpoints/ServiceEndpoints.php');
	include ($ONEAPICOMMON.'mms/MMSRetrieve.php');    
	
	$submit=null;

	$registrationId=null;
	$maxBatchSize=null;
	$resFormat=null;
	
	$username=null;
	$password=null;
	$endpoint=null;

	if (isset($_POST["submit"])) $submit=$_POST["submit"];
	
	if (isset($_POST["registrationId"])) $registrationId=$_POST["registrationId"];
	if (isset($_POST["messageId"])) $messageId=$_POST["messageId"];
	if (isset($_POST["resFormat"])) $resFormat=$_POST["resFormat"];
	
	if (isset($_POST["username"])) $username=$_POST["username"];
	if (isset($_POST["password"])) $password=$_POST["password"];
	if (isset($_POST["endpoint"])) $endpoint=$_POST["endpoint"];
	
	$endpoints=new ServiceEndpoints();
	
	if (!isset($submit)) {

		$registrationId="3456";
		$messageId="msg1";	
		$resFormat="JSON";	
	
		$username="Fred.Jones";
		$password="1234";
		$endpoint=$endpoints->getRetrieveMMSMessageEndpoint();
	} else {		
		$endpoints->setRetrieveMMSMessageEndpoint($endpoint);

		$receiver = new MMSRetrieve($endpoints, $username, $password);
	
		$mmsResponse = $receiver->retrieveMessageContent($registrationId, $messageId, $resFormat);	
	}
	
?>
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

	<?php include("mainmenu.inc"); ?>
        
        <div class="col-760">
        

        <div id="other-main">
          <div class="other-top"></div>
          <div class="other-bottom">
            <div class="left">
          <h3>
            Inbound MMS API - Retrieve Full MMS Message
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="mmsretrievemessage.php">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">registrationId</div>
				<div class="ParameterValue"><input type="text" name="registrationId" value="<?php echo $registrationId;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">messageId</div>
				<div class="ParameterValue"><input type="text" name="messageId" value="<?php echo $messageId;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">resFormat</div>
				<div class="ParameterValue"><input type="text" name="resFormat" value="<?php echo $resFormat;?>"/></div>
			</div>
			<div class="ParameterGroupHeading"><strong>Optional Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">&nbsp;</div>
				<div class="ParameterValue"><em>None</em></div>
			</div>
			<div class="ParameterGroupHeading"><strong>Service Endpoint Information</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">Username</div>
				<div class="ParameterValue"><input type="text" name="username" value="<?php echo $username;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">Password</div>
				<div class="ParameterValue"><input type="text" name="password" value="<?php echo $password;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">Service Endpoint</div>
				<div class="ParameterValue"><textarea name="endpoint" style="width:400px;height:80px;"><?php echo $endpoint;?></textarea></div>
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
          
        <?php if (isset($mmsResponse)) { ?>
          <h3>Response:</h3>
          	<div class="ParameterGroupHeading"><strong>Response Code</strong></div>          
          	<div class="ParameterRow">
				<div class="ParameterLabel">httpResponseCode</div>
				<div class="ParameterValue"><?php echo $mmsResponse->getHTTPResponseCode();?></div>
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">contentType</div>
				<div class="ParameterValue"><?php echo $mmsResponse->getContentType();?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">location</div>
				<div class="ParameterValue"><?php echo $mmsResponse->getLocation()?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">mmsResponse.toString()</div>
				<div class="ParameterValue"><?php echo $mmsResponse->toString();?>/div>				
			</div>
			<?php if (!is_null($mmsResponse->getRequestError())) { ?>
          		<div class="ParameterGroupHeading"><strong>Request Error</strong></div>
          		<?php if (!is_null($mmsResponse->getRequestError()->getPolicyException())) { ?>          
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">POLICY EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $mmsResponse->getRequestError()->getPolicyException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $mmsResponse->getRequestError()->getPolicyException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $mmsResponse->getRequestError()->getPolicyException()->getVariables();?></div>				
					</div>
				<?php } 
          		if (!is_null($mmsResponse->getRequestError().getServiceException())) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">SERVICE EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $mmsResponse->getRequestError()->getServiceException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $mmsResponse->getRequestError()->getServiceException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $mmsResponse->getRequestError()->getServiceException()->getVariables();?></div>				
					</div>
				<?php } ?>
			<?php } ?>

          	<?php if (!is_null($mmsResponse->getInboundMessage())) { ?>
          		<div class="ParameterGroupHeading"><strong>inboundMessage</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">messageId</div>
					<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getMessageId();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">destinationAddress</div>
					<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getDestinationAddress();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">senderAddress</div>
					<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getSenderAddress();?></div>				
				</div>
				<?php if (!is_null($mmsResponse->getInboundMessage()->getInboundMMSMessage())) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">inboundMMSMessage.subject</div>
						<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getInboundMMSMessage()->getSubject();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">inboundMMSMessage.message</div>
						<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getInboundMMSMessage()->getMessage();?></div>				
					</div>
				<?php } ?>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">dateTime</div>
					<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getDateTime();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">resourceURL</div>
					<div class="ParameterValue"><?php echo $mmsResponse->getInboundMessage()->getResourceURL();?></div>				
				</div>
          	<?php } ?>
          	<?php if (!is_null($mmsResponse->getAttachments()) && count($mmsResponse->getAttachments())>0) { ?>
          		<div class="ParameterGroupHeading"><strong>attachments</strong></div>
          		<div class="ParameterRow">
					<div class="ParameterLabel"># attachments</div>
					<div class="ParameterValue"><?php echo count($mmsResponse->getAttachments());?></div>									
				</div>
				<?php
					$attachments=$mmsResponse->getAttachments(); 
					for ($i=0; $i<count($attachments); $i++) { ?>
				    <div class="ParameterRow">
						<div class="ParameterLabel">&nbsp;</div>
						<div class="ParameterValue">&nbsp;</div>				
					</div>
	          		<div class="ParameterRow">
						<div class="ParameterLabel">attachmentName [<?php echo $i;?>]</div>
						<div class="ParameterValue"><?php echo $attachments[$i]->getAttachmentName()?></div>									
					</div>
	          		<div class="ParameterRow">
						<div class="ParameterLabel">attachmentContentType [<?php echo $i;?>]</div>
						<div class="ParameterValue"><?php echo $attachments[$i]->getAttachmentContentType()?></div>									
					</div>
				<?php } ?>
          	<?php } ?>
			
		<?php } ?>

		    <div class="ParameterRow">
				<div class="ParameterLabel">&nbsp;</div>
				<div class="ParameterValue">&nbsp;</div>				
			</div>

        </div></div></div>
        
			</div>

			

      </div>
      
		<?php include('footer.inc'); ?>

    </div>
      


</body>
</html>
