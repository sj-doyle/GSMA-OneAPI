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
	include ($ONEAPICOMMON.'sms/SMSSend.php');    
	
	$submit=null;

	$senderAddress=null;
	$notifyURL=null;
	$clientCorrelator=null;
	$callbackData=null;
	
	$username=null;
	$password=null;
	$endpoint=null;

	if (isset($_POST["submit"])) $submit=$_POST["submit"];
	
	if (isset($_POST["senderAddress"])) $senderAddress=$_POST["senderAddress"];
	if (isset($_POST["notifyURL"])) $notifyURL=$_POST["notifyURL"];
	if (isset($_POST["clientCorrelator"])) $clientCorrelator=$_POST["clientCorrelator"];
	if (isset($_POST["callbackData"])) $callbackData=$_POST["callbackData"];
	
	if (isset($_POST["username"])) $username=$_POST["username"];
	if (isset($_POST["password"])) $password=$_POST["password"];
	if (isset($_POST["endpoint"])) $endpoint=$_POST["endpoint"];
	
	$endpoints=new ServiceEndpoints();
	
	if (!isset($submit)) {

		$senderAddress="tel:+123456789";
		$notifyURL="http://localhost/smsdeliverynotification.php";
		$callbackData="doSomething()";
		$clientCorrelator="123456";
	
		$username="Fred.Jones";
		$password="1234";
		$endpoint=$endpoints->getSMSDeliverySubscriptionsEndpoint();
	} else {		
		$endpoints->setSMSDeliverySubscriptionsEndpoint($endpoint);

		$sender = new SMSSend($endpoints, $username, $password);
	
		$smsResponse = $sender->subscribeToDeliveryNotifications($senderAddress, $clientCorrelator, $notifyURL, $callbackData);	
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
            Outbound SMS API - Subscribe To SMS Delivery Notifications
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="smssubscribedeliveryreceipts.php">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">senderAddress</div>
				<div class="ParameterValue"><input type="text" name="senderAddress" value="<?php echo $senderAddress;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">notifyURL</div>
				<div class="ParameterValue"><textarea name="notifyURL" style="width:400px;height:80px;"><?php echo $notifyURL;?></textarea></div>
			</div>
			<div class="ParameterGroupHeading"><strong>Optional Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">clientCorrelator</div>
				<div class="ParameterValue"><input type="text" name="clientCorrelator" value="<?php echo $clientCorrelator;?>"/></div>
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
          
        <?php if (isset($smsResponse)) { ?>
          <h3>Response:</h3>
          	<div class="ParameterGroupHeading"><strong>Response Code</strong></div>          
          	<div class="ParameterRow">
				<div class="ParameterLabel">httpResponseCode</div>
				<div class="ParameterValue"><?php echo $smsResponse->getHTTPResponseCode();?></div>
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">contentType</div>
				<div class="ParameterValue"><?php echo $smsResponse->getContentType();?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">location</div>
				<div class="ParameterValue"><?php echo $smsResponse->getLocation()?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">smsResponse.toString()</div>
				<div class="ParameterValue"><?php echo $smsResponse->toString();?></div>				
			</div>
			<?php if (!is_null($smsResponse->getRequestError())) { ?>
          		<div class="ParameterGroupHeading"><strong>Request Error</strong></div>
          		<?php if (!is_null($smsResponse->getRequestError()->getPolicyException())) { ?>          
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">POLICY EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $smsResponse->getRequestError()->getPolicyException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $smsResponse->getRequestError()->getPolicyException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $smsResponse->getRequestError()->getPolicyException()->getVariables();?></div>				
					</div>
				<?php } 
          		if (!is_null($smsResponse->getRequestError().getServiceException())) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">SERVICE EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $smsResponse->getRequestError()->getServiceException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $smsResponse->getRequestError()->getServiceException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $smsResponse->getRequestError()->getServiceException()->getVariables();?></div>				
					</div>
				<?php } ?>
			<?php } ?>
			<?php if (!is_null($smsResponse->getDeliveryReceiptSubscription())) { ?>
          		<div class="ParameterGroupHeading"><strong>DeliveryReceiptSubscription</strong></div>
				<?php if (!is_null($smsResponse->getDeliveryReceiptSubscription()->getCallbackReference())) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">callbackReference.callbackData</div>
						<div class="ParameterValue"><?php echo $smsResponse->getDeliveryReceiptSubscription()->getCallbackReference()->getCallbackData();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">callbackReference.notifyURL</div>
						<div class="ParameterValue"><?php echo $smsResponse->getDeliveryReceiptSubscription()->getCallbackReference()->getNotifyURL();?></div>				
					</div>
				<?php } ?>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">resourceURL</div>
					<div class="ParameterValue"><?php echo $smsResponse->getDeliveryReceiptSubscription()->getResourceURL();?></div>				
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
