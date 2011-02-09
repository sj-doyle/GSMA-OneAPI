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
	include ($ONEAPICOMMON.'payment/Reservation.php');    
	
	$username=null;
	$password=null;
 	$endpoint=null;

	$endUserId=null;
	$referenceCode=null;
	$referenceSequence=null;

	if (isset($_POST["submit"])) $submit=$_POST["submit"];
	if (isset($_POST["username"])) $username=$_POST["username"];
	if (isset($_POST["password"])) $password=$_POST["password"];
	if (isset($_POST["endpoint"])) $endpoint=$_POST["endpoint"];
	
	if (isset($_POST["endUserId"])) $endUserId=$_POST["endUserId"];
	if (isset($_POST["referenceCode"])) $referenceCode=$_POST["referenceCode"];
	if (isset($_POST["referenceSequence"])) $referenceSequence=$_POST["referenceSequence"];

	$endpoints=new ServiceEndpoints();
	
	$paymentResponse=null;
	
	if (!isset($submit)) {
		$username="Fred.Jones";
		$password="1234";
		$endpoint=$endpoints->getAmountReservationReleaseEndpoint();

		$endUserId="tel:+123456789";
		$referenceCode="REF-12345";
		$referenceSequence=4;

	} else {		
		$endpoints->setAmountReservationReleaseEndpoint($endpoint);

		$reservation = new Reservation($endpoints, $username, $password);
	
		$paymentResponse=$reservation->releaseReservation($endUserId, $referenceCode, $referenceSequence);						
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
            Payment API - Release Payment Reservation
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="paymentreleasereservation.php">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">endUserId</div>
				<div class="ParameterValue"><input type="text" name="endUserId" value="<?php echo $endUserId;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">referenceCode</div>
				<div class="ParameterValue"><input type="text" name="referenceCode" value="<?php echo $referenceCode;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">referenceSequence</div>
				<div class="ParameterValue"><input type="text" name="referenceSequence" value="<?php echo $referenceSequence;?>"/></div>
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
          
        <?php if (isset($paymentResponse)) { 
        	$amountReservationTransaction=$paymentResponse->getAmountReservationTransaction();
        	?>
          	<div class="ParameterGroupHeading"><strong>Response Code</strong></div>          
          	<div class="ParameterRow">
				<div class="ParameterLabel">httpResponseCode</div>
				<div class="ParameterValue"><?php echo $paymentResponse->getHTTPResponseCode();?></div>
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">contentType</div>
				<div class="ParameterValue"><?php echo $paymentResponse->getContentType();?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">location</div>
				<div class="ParameterValue"><?php echo $paymentResponse->getLocation()?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">paymentResponse.toString()</div>
				<div class="ParameterValue"><?php echo $paymentResponse->toString();?></div>				
			</div>
			<?php if (!is_null($paymentResponse->getRequestError())) { ?>
          		<div class="ParameterGroupHeading"><strong>Request Error</strong></div>
          		<?php if (!is_null($paymentResponse->getRequestError()->getPolicyException())) { ?>          
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">POLICY EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $paymentResponse->getRequestError()->getPolicyException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $paymentResponse->getRequestError()->getPolicyException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $paymentResponse->getRequestError()->getPolicyException()->getVariables();?></div>				
					</div>
				<?php } 
          		if (!is_null($paymentResponse->getRequestError().getServiceException())) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">SERVICE EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $paymentResponse->getRequestError()->getServiceException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $paymentResponse->getRequestError()->getServiceException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $paymentResponse->getRequestError()->getServiceException()->getVariables();?></div>				
					</div>
				<?php } ?>
			<?php } ?>
			<?php if (isset($amountReservationTransaction)) { ?>
          		<div class="ParameterGroupHeading"><strong>amountReservationTransaction Response</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">clientCorrelator</div>
					<div class="ParameterValue"><?php echo $amountReservationTransaction->getClientCorrelator();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">endUserId</div>
					<div class="ParameterValue"><?php echo $amountReservationTransaction->getEndUserId();?></div>				
				</div>
				<?php if ($amountReservationTransaction->getPaymentAmount()) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">paymentAmount.amountReserved</div>
						<div class="ParameterValue"><?php echo $amountReservationTransaction->getPaymentAmount()->getAmountReserved();?></div>				
					</div>		
					<?php if ($amountReservationTransaction->getPaymentAmount()->getChargingInformation()) { ?>
			          	<div class="ParameterRow">
							<div class="ParameterLabel">paymentAmount.chargingInformation.amount</div>
							<div class="ParameterValue"><?php echo $amountReservationTransaction->getPaymentAmount()->getChargingInformation()->getAmount();?></div>				
						</div>
			          	<div class="ParameterRow">
							<div class="ParameterLabel">paymentAmount.chargingInformation.currency</div>
							<div class="ParameterValue"><?php echo $amountReservationTransaction->getPaymentAmount()->getChargingInformation()->getCurrency();?></div>				
						</div>
			          	<div class="ParameterRow">
							<div class="ParameterLabel">paymentAmount.chargingInformation.description</div>
							<div class="ParameterValue"><?php echo $amountReservationTransaction->getPaymentAmount()->getChargingInformation()->getDescription();?></div>				
						</div>		
					<?php } ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">paymentAmount.totalAmountCharged</div>
						<div class="ParameterValue"><?php echo $amountReservationTransaction->getPaymentAmount()->getTotalAmountCharged();?></div>				
					</div>		
				<?php } ?>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">referenceCode</div>
					<div class="ParameterValue"><?php echo $amountReservationTransaction->getReferenceCode();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">resourceURL</div>
					<div class="ParameterValue"><?php echo $amountReservationTransaction->getResourceURL();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">serverReferenceCode</div>
					<div class="ParameterValue"><?php echo $amountReservationTransaction->getServerReferenceCode();?></div>				
				</div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel">transactionOperationStatus</div>
					<div class="ParameterValue"><?php echo $amountReservationTransaction->getTransactionOperationStatus();?></div>				
				</div>
			<?php }
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
