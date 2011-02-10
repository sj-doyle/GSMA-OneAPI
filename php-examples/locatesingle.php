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
	include ($ONEAPICOMMON.'location/Locate.php');    
	
	$submit=null;
	$address=null;
	$requestedAccuracy=null;
	
	$username=null;
	$password=null;
	$endpoint=null;

	if (isset($_POST["submit"])) $submit=$_POST["submit"];
	if (isset($_POST["address"])) $address=$_POST["address"];
	if (isset($_POST["requestedAccuracy"])) $requestedAccuracy=$_POST["requestedAccuracy"];

	if (isset($_POST["username"])) $username=$_POST["username"];
	if (isset($_POST["password"])) $password=$_POST["password"];
	if (isset($_POST["endpoint"])) $endpoint=$_POST["endpoint"];
	
	$endpoints=new ServiceEndpoints();
	
	if (!isset($submit)) {
		$address="tel:+16035558278";
		$requestedAccuracy=1000;
		$username="Fred.Jones";
		$password="1234";
		$endpoint=$endpoints->getLocationEndpoint();
	} else {		
		$endpoints->setLocationEndpoint($endpoint);

		$locator = new Locate($endpoints, $username, $password);
	
		$locationResponse=$locator->locateTerminal($address, $requestedAccuracy);
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
            Locate Single Terminal
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="locatesingle.php">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address</div>
				<div class="ParameterValue"><input type="text" name="address" value="<?php echo $address;?>"/></div>
			</div>
			<div class="ParameterRow">
				<div class="ParameterLabel">requestedAccuracy</div>
				<div class="ParameterValue"><input type="text" name="requestedAccuracy" value="<?php echo $requestedAccuracy;?>"/></div>
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
          
        <?php if (isset($locationResponse)) { ?>
          <h3>Response:</h3>
          	<div class="ParameterGroupHeading"><strong>Response Code</strong></div>          
          	<div class="ParameterRow">
				<div class="ParameterLabel">httpResponseCode</div>
				<div class="ParameterValue"><?php echo $locationResponse->getHTTPResponseCode();?></div>
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">contentType</div>
				<div class="ParameterValue"><?php echo $locationResponse->getContentType();?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">location</div>
				<div class="ParameterValue"><?php echo $locationResponse->getLocation()?></div>				
			</div>
          	<div class="ParameterRow">
				<div class="ParameterLabel">locationResponse.toString()</div>
				<div class="ParameterValue"><?php echo $locationResponse->toString();?></div>				
			</div>
			<?php if (!is_null($locationResponse->getRequestError())) { ?>
          		<div class="ParameterGroupHeading"><strong>Request Error</strong></div>
          		<?php if (!is_null($locationResponse->getRequestError()->getPolicyException())) { ?>          
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">POLICY EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $locationResponse->getRequestError()->getPolicyException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $locationResponse->getRequestError()->getPolicyException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $locationResponse->getRequestError()->getPolicyException()->getVariables();?></div>				
					</div>
				<?php } 
          		if (!is_null($locationResponse->getRequestError().getServiceException())) { ?>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">exceptionType</div>
						<div class="ParameterValue">SERVICE EXCEPTION</div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">messageId</div>
						<div class="ParameterValue"><?php echo $locationResponse->getRequestError()->getServiceException()->getMessageId();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">text </div>
						<div class="ParameterValue"><?php echo $locationResponse->getRequestError()->getServiceException()->getText();?></div>				
					</div>
		          	<div class="ParameterRow">
						<div class="ParameterLabel">variables</div>
						<div class="ParameterValue"><?php echo $locationResponse->getRequestError()->getServiceException()->getVariables();?></div>				
					</div>
				<?php } ?>
			<?php } ?>
			<?php if (!is_null($locationResponse->getTerminalLocationList())) {
				$terminalLocation=$locationResponse->getTerminalLocationList()->getTerminalLocation();
				$numResponses=0;
				if (!is_null($terminalLocation)) $numResponses=count($terminalLocation); 
			?>
          		<div class="ParameterGroupHeading"><strong>Terminal Location List</strong></div>
	          	<div class="ParameterRow">
					<div class="ParameterLabel"># Location Responses</div>
					<div class="ParameterValue"><?php echo $numResponses;?></div>				
				</div>
				<?php if ($numResponses>0) {
					for ($i=0; $i<$numResponses; $i++) { ?>
						<div class="ParameterRow">
							<div class="ParameterLabel">&nbsp;</div>
							<div class="ParameterValue">&nbsp;</div>				
						</div>
			          	<div class="ParameterRow">
							<div class="ParameterLabel">address</div>
							<div class="ParameterValue"><?php echo $terminalLocation[$i]->getAddress();?></div>				
						</div>					
			          	<div class="ParameterRow">
							<div class="ParameterLabel">locationRetrievalStatus</div>
							<div class="ParameterValue"><?php echo $terminalLocation[$i]->getLocationRetrievalStatus();?></div>				
						</div>					
					<?php
						if (!is_null($terminalLocation[$i]->getCurrentLocation())) {
							$currentLocation=$terminalLocation[$i]->getCurrentLocation();
							?>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">accuracy [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $currentLocation->getAccuracy();?></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">altitude [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $currentLocation->getAltitude();?></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">latitude [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $currentLocation->getLatitude();?></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">longitude [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $currentLocation->getLongitude();?></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">timestamp [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $currentLocation->getTimestamp();?></div>				
							</div>
							<?php
						}
						if (!is_null($terminalLocation[$i]->getErrorInformation()) && !is_null($terminalLocation[$i]->getErrorInformation()->getServiceException())) {
							$requestError=$terminalLocation[$i]->getErrorInformation();
							?>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">serviceException.messageId [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $requestError->getServiceException()->getMessageId();?></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">serviceException.text [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $requestError->getServiceException()->getText();?></div>				
							</div>
				          	<div class="ParameterRow">
								<div class="ParameterLabel">serviceException.variables [<?php echo $i;?>]</div>
								<div class="ParameterValue"><?php echo $requestError->getServiceException()->getVariables();?></div>				
							</div>
							<?php
						}
					}				
				} 
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
