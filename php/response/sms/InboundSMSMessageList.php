<?php 

class InboundSMSMessageList {

	protected $inboundSMSMessage=null;
	
	public function getInboundSMSMessage() { return $this->inboundSMSMessage; }
	public function setInboundSMSMessage($inboundSMSMessage) { $this->inboundSMSMessage=$inboundSMSMessage; }
	
	protected $numberOfMessagesInThisBatch=null;
	protected $resourceURL=null;
	protected $totalNumberOfPendingMessages=null;
			
	public function getNumberOfMessagesInThisBatch() { return $this->numberOfMessagesInThisBatch; }
	public function getResourceURL() { return $this->resourceURL; }
	public function getTotalNumberOfPendingMessages() { return $this->totalNumberOfPendingMessages; }
	
	public function setNumberOfMessagesInThisBatch($numberOfMessagesInThisBatch) { $this->numberOfMessagesInThisBatch=$numberOfMessagesInThisBatch; }
	public function setResourceURL($resourceURL) { $this->resourceURL=$resourceURL; }
	public function setTotalNumberOfPendingMessages($totalNumberOfPendingMessages) { $this->totalNumberOfPendingMessages=$totalNumberOfPendingMessages; }

	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'inboundSMSMessage')) {
				$inboundSMSMessageArray = $jsondata->inboundSMSMessage;
				if (is_array($inboundSMSMessageArray) && count($inboundSMSMessageArray)>0) {
					$processedInboundSMSMessageArray = array();
					for ($i=0; $i<count($inboundSMSMessageArray); $i++) {
						$inboundSMSMessageElement=$inboundSMSMessageArray[$i];
						$processedInboundSMSMessage=new InboundSMSMessage($inboundSMSMessageElement);
						$processedInboundSMSMessageArray[$i] = $processedInboundSMSMessage;
					}
					$this->inboundSMSMessage = $processedInboundSMSMessageArray;
				}
			}
			if (property_exists($jsondata, 'numberOfMessagesInThisBatch')) {
				$this->numberOfMessagesInThisBatch=(int) $jsondata->numberOfMessagesInThisBatch;
			}
			if (property_exists($jsondata, 'resourceURL')) {
				$this->resourceURL=$jsondata->resourceURL;
			}
			if (property_exists($jsondata, 'totalNumberOfPendingMessages')) {
				$this->totalNumberOfPendingMessages=(int) $jsondata->totalNumberOfPendingMessages;
			}
		}
	}
	
	public function toString() {
		$buffer='inboundSMSMessage=';
		if (!is_null($this->inboundSMSMessage) && is_array($this->inboundSMSMessage)) {
			$buffer=$buffer.'{';
			for ($i=0; $i<count($this->inboundSMSMessage); $i++) {
				if ($i>0) {
					$buffer=$buffer.', ['.$i.'] = '.$this->inboundSMSMessage[$i]->toString();
				} else {
					$buffer=$buffer.'['.$i.'] = '.$this->inboundSMSMessage[$i]->toString();
				}			
			}
			$buffer=$buffer.'}';
		}
		$buffer=$buffer.', numberOfMessagesInThisBatch='.$this->numberOfMessagesInThisBatch.
						', resourceURL='.$this->resourceURL.
						', totalNumberOfPendingMessages='.$this->totalNumberOfPendingMessages;
		return $buffer;
	}
	
	
}

?>	
