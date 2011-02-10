<?php

class JSONRequest
{
	protected $url;
	protected $method;
	protected $requestParameters;
	protected $requestLength;
	protected $username;
	protected $password;
	protected $acceptType;
	protected $responseBody;
	protected $responseInfo;
	protected $location;
	protected $contentType=null;
	protected $requestBody=null;
	
	public function getResponseBody() { return $this->responseBody; }
	public function getResponseInfo() { return $this->responseInfo; }
	public function getLocation() { return $this->location; }

	public function __construct ($url = null, $username=null, $password=null, $method = 'GET', $requestParameters = null, $contentType=null, $requestBody=null)
	{
		$this->url				= $url;
		$this->method			= $method;
		$this->requestParameters= $requestParameters;
		$this->requestLength	= 0;
		$this->username			= $username;
		$this->password			= $password;
		$this->acceptType		= 'application/json';
		$this->responseBody		= null;
		$this->responseInfo		= null;
		$this->requestBody      = $requestBody;
		
		error_log('Set URL to '.$url);
		error_log('Set username to '.$username);
		error_log('Set password to '.$password);
		error_log('Set method to '.$method);
		if ($contentType) {
			$replacements=array("\\n","\\r","\n","\r");
			$contentType=str_replace($replacements,'',$contentType);
			$this->contentType      = $contentType;
			error_log('Stored content type='.$contentType);
		}

	}

	public function flush ()
	{
		$this->requestParameters= null;
		$this->requestLength	= 0;
		$this->method			= 'GET';
		$this->responseBody		= null;
		$this->responseInfo		= null;
	}

	public function execute ()
	{
		$ch = curl_init();
		$this->setAuth($ch);

		try
		{
			switch (strtoupper($this->method))
			{
				case 'GET':
					$this->executeGet($ch);
					break;
				case 'POST':
					$this->executePost($ch);
					break;
				case 'DELETE':
					$this->executeDelete($ch);
					break;
				default:
					throw new InvalidArgumentException('Current method (' . $this->method . ') is an invalid REST verb.');
			}
		}
		catch (InvalidArgumentException $e)
		{
			curl_close($ch);
			throw $e;
		}
		catch (Exception $e)
		{
			curl_close($ch);
			throw $e;
		}
		
		return $this;

	}

	protected function doExecute (&$curlHandle)
	{
		$this->setCurlOpts($curlHandle);
		$this->responseBody = curl_exec($curlHandle);
		$this->responseInfo	= curl_getinfo($curlHandle);

		curl_close($curlHandle);
	}

	protected function executeGet ($ch)
	{
		$this->doExecute($ch);
	}

	protected function executePost ($ch)
	{
		$data="";
		if (isset($this->requestBody)) {
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $this->requestBody);
		} else if ($this->requestParameters && is_array($this->requestParameters)) {
			for ($i=0; $i<count($this->requestParameters); $i++) {
				$param=$this->requestParameters[$i];
				error_log("Form parameter = ".print_r($param,true));
				$key=$param->getKey();
				$value=$param->getValue();
				error_log("Key=".$key." Value=".$value);
				if (isset($param) && isset($key) && isset($value)) { 
					if ($i>0) $data=$data."&";
					$data=$data.$key."=".urlencode($value);
				}
			}
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
		}		

		$this->doExecute($ch);
	}

	protected function executeDelete ($ch)
	{
		curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'DELETE');

		$this->doExecute($ch);
	}


	protected function setCurlOpts (&$curlHandle)
	{
		curl_setopt($curlHandle, CURLOPT_TIMEOUT, 10);
		curl_setopt($curlHandle, CURLOPT_URL, $this->url);
		curl_setopt($curlHandle, CURLOPT_RETURNTRANSFER, true);
		if (isset($this->requestBody) && isset($this->contentType)) {
			curl_setopt($curlHandle, CURLOPT_HTTPHEADER, array ('Content-Type: '.$this->contentType, 'Expect:', 'Accept: ' . $this->acceptType));
		} else {
			curl_setopt($curlHandle, CURLOPT_HTTPHEADER, array ('Accept: ' . $this->acceptType, 'Expect:'));
		}
		//curl_setopt($curlHandle, CURLOPT_HEADER, true);
		curl_setopt($curlHandle, CURLOPT_HEADERFUNCTION, array(&$this,'readHeader'));
	}

	protected function setAuth (&$curlHandle)
	{
		if ($this->username != null && $this->password != null)
		{
			error_log('Setting authentication to method '.CURLAUTH_BASIC);
			curl_setopt($curlHandle, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
			curl_setopt($curlHandle, CURLOPT_USERPWD, $this->username . ':' . $this->password);
		}
	}
	
	private function readHeader($ch, $header) {
        $location = $this->extractHeader('Location:', '\n', $header);
        if ($location) {
            $this->location = trim($location);
        }
        return strlen($header);
    }

    private function extractHeader($start,$end,$header) {
        $pattern = '/'. $start .'(.*?)'. $end .'/';
        if (preg_match($pattern, $header, $result)) {
            return $result[1];
        } else {
            return false;
        }
    }

	
}

?>
