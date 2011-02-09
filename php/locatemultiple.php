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
	$address0=null;
	$address1=null;
	$address2=null;
	$address3=null;
	$address4=null;
	$requestedAccuracy=null;
	
	$username=null;
	$password=null;
	$endpoint=null;

	if (isset($_POST["submit"])) $submit=$_POST["submit"];
	if (isset($_POST["address0"])) $address0=$_POST["address0"];
	if (isset($_POST["address1"])) $address1=$_POST["address1"];
	if (isset($_POST["address2"])) $address2=$_POST["address2"];
	if (isset($_POST["address3"])) $address3=$_POST["address3"];
	if (isset($_POST["address4"])) $address4=$_POST["address4"];
	if (isset($_POST["requestedAccuracy"])) $requestedAccuracy=$_POST["requestedAccuracy"];

	if (isset($_POST["username"])) $username=$_POST["username"];
	if (isset($_POST["password"])) $password=$_POST["password"];
	if (isset($_POST["endpoint"])) $endpoint=$_POST["endpoint"];
	
	$endpoints=new ServiceEndpoints();
	
	if (!isset($submit)) {
		$address0="tel:+445550000";
		$address1="tel:+16035550001";
		$address2="tel:+3315550002";
		$address3="tel:+35315550003";
		$address4="tel:07945550004";
		$requestedAccuracy=1000;
		$username="Fred.Jones";
		$password="1234";
		$endpoint=$endpoints->getLocationEndpoint();
	} else {		
		$endpoints->setLocationEndpoint($endpoint);

		$locator = new Locate($endpoints, $username, $password);
	
		$address=array();
		$address[0]=$address0;
		$address[1]=$address1;
		$address[2]=$address2;
		$address[3]=$address3;
		$address[4]=$address4;
		
		$locationResponse=$locator->locateMultipleTerminals($address, $requestedAccuracy);
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
            Locate Multiple Terminals
          </h3>
          <p>
      
		</p>
		
		<form method="post" action="locatemultiple.php">
			<div class="ParameterGroupHeading"><strong>Mandatory Parameters</strong></div>
			<div class="ParameterRow">
				<div class="ParameterLabel">address [0]</div>
				<div class="ParameterValue"><input type="text" name="address0" value="<?php echo $address0;?>"/></div>
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
							<div class="ParameterLabel">address [<?php echo $i;?>]</div>
							<div class="ParameterValue"><?php echo $terminalLocation[$i]->getAddress();?></div>				
						</div>					
			          	<div class="ParameterRow">
							<div class="ParameterLabel">locationRetrievalStatus [<?php echo $i;?>]</div>
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
