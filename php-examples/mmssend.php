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
	include ($ONEAPICOMMON.'mms/MMSSend.php');    
	
	$submit=null;
	
	$senderAddress=null;
	$address0=null;
	$address1=null;
	$address2=null;
	$address3=null;
	$address4=null;
	$message=null;
	$clientCorrelator=null;
	$senderName=null;
	$notifyURL=null;
	$callbackData=null;
	
	$username=null;
	$password=null;
	$endpoint=null;

	if (isset($_POST["submit"])) $submit=$_POST["submit"];
	
	if (isset($_POST["senderAddress"])) $senderAddress=$_POST["senderAddress"];
	if (isset($_POST["address0"])) $address0=$_POST["address0"];
	if (isset($_POST["address1"])) $address1=$_POST["address1"];
	if (isset($_POST["address2"])) $address2=$_POST["address2"];
	if (isset($_POST["address3"])) $address3=$_POST["address3"];
	if (isset($_POST["address4"])) $address4=$_POST["address4"];
	if (isset($_POST["message"])) $message=$_POST["message"];
	if (isset($_POST["clientCorrelator"])) $clientCorrelator=$_POST["clientCorrelator"];
	if (isset($_POST["senderName"])) $senderName=$_POST["senderName"];
	if (isset($_POST["notifyURL"])) $notifyURL=$_POST["notifyURL"];
	if (isset($_POST["callbackData"])) $callbackData=$_POST["callbackData"];
	
	if (isset($_POST["username"])) $username=$_POST["username"];
	if (isset($_POST["password"])) $password=$_POST["password"];
	if (isset($_POST["endpoint"])) $endpoint=$_POST["endpoint"];
	
	$endpoints=new ServiceEndpoints();
	
	if (!isset($submit)) {

		$senderAddress="tel:+123456789";
		$address0="tel:+13500000991";
		$address1="tel:+13500000992";
		$message="Hello World!";
		$clientCorrelator="123456";
		$notifyURL="http://localhost/mmsdeliverynotification.php";
		$senderName="ACME Inc.";
		$callbackData="callback-data-goes-here";
	
		$username="Fred.Jones";
		$password="1234";
		$endpoint=$endpoints->getSendMMSEndpoint();
	} else {		
		$endpoints->setSendMMSEndpoint($endpoint);

		$sender = new MMSSend($endpoints, $username, $password);
	
		$address=array();
		$address[0]=$address0;
		$address[1]=$address1;
		$address[2]=$address2;
		$address[3]=$address3;
		$address[4]=$address4;
		
		$attachments=array();
		error_log('Putting attachments into array: '.print_r($_FILES, true));
		if (isset($_FILES) && is_array($_FILES)) {
			if (isset($_FILES['attachment0'])) $attachments[0]=$_FILES['attachment0'];
			if (isset($_FILES['attachment1'])) $attachments[1]=$_FILES['attachment1'];
			if (isset($_FILES['attachment2'])) $attachments[2]=$_FILES['attachment2'];
			if (isset($_FILES['attachment3'])) $attachments[3]=$_FILES['attachment3'];
			if (isset($_FILES['attachment4'])) $attachments[4]=$_FILES['attachment4'];
		}
		
		$mmsResponse = $sender->sendMMS($senderAddress, $address, $message, $attachments, $senderName, $clientCorrelator, $notifyURL, $callbackData);


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
            Outbound MMS API - Send MMS
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="mmssend.php" enctype="multipart/form-data">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">senderAddress</div>
				<div class="ParameterValue"><input type="text" name="senderAddress" value="<?php echo $senderAddress;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [0]</div>
				<div class="ParameterValue"><input type="text" name="address0" value="<?php echo $address0;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">message</div>
				<div class="ParameterValue"><textarea name="message" style="width:400px;height:80px;"><?php echo $message;?></textarea></div>
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
				<div class="ParameterValue"><input type="text" name="address1" value="<?php echo $address1;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [2]</div>
				<div class="ParameterValue"><input type="text" name="address2" value="<?php echo $address2;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [3]</div>
				<div class="ParameterValue"><input type="text" name="address3" value="<?php echo $address3;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [4]</div>
				<div class="ParameterValue"><input type="text" name="address4" value="<?php echo $address4;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">clientCorrelator</div>
				<div class="ParameterValue"><input type="text" name="clientCorrelator" value="<?php echo $clientCorrelator;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">senderName</div>
				<div class="ParameterValue"><input type="text" name="senderName" value="<?php echo $senderName;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">notifyURL</div>
				<div class="ParameterValue"><textarea name="notifyURL" style="width:400px;height:80px;"><?php echo $notifyURL;?></textarea></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">callbackData</div>
				<div class="ParameterValue"><input type="text" name="callbackData" value="<?php echo $callbackData;?>"/></div>
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
				<div class="ParameterLabel">smsResponse.toString()</div>
				<div class="ParameterValue"><?php echo $mmsResponse->toString();?></div>				
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
			<?php if (!is_null($mmsResponse->getResourceReference())) {
			?>
          		<div class="ParameterGroupHeading"><strong>Resource Reference</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">resourceURL</div>
					<div class="ParameterValue"><?php echo $mmsResponse->getResourceReference()->getResourceURL();?></div>				
				</div>
			<?php 
			} 
		} ?>

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
