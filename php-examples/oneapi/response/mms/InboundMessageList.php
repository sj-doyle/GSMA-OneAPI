<?php 

class InboundMessageList {

	protected $inboundMessage=null;	
	public function getInboundMessage() { return $this->inboundMessage; }
	public function setInboundMessage($inboundMessage) { $this->inboundMessage=$inboundMessage; }
	
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
			if (property_exists($jsondata, 'inboundMessage')) {
				$inboundMessageArray = $jsondata->inboundMessage;
				if (is_array($inboundMessageArray) && count($inboundMessageArray)>0) {
					$processedInboundMessageArray = array();
					for ($i=0; $i<count($inboundMessageArray); $i++) {
						$inboundMessageElement=$inboundMessageArray[$i];
						$processedInboundMessage=new InboundMessage($inboundMessageElement);
						$processedInboundMessageArray[$i] = $processedInboundMessage;
					}
					$this->inboundMessage = $processedInboundMessageArray;
				}
			}
			if (property_exists($jsondata, 'numberOfMessagesInThisBatch')) {
				$this->numberOfMessagesInThisBatch=$jsondata->numberOfMessagesInThisBatch;
			}
			if (property_exists($jsondata, 'resourceURL')) {
				$this->resourceURL=$jsondata->resourceURL;
			}
			if (property_exists($jsondata, 'totalNumberOfPendingMessages')) {
				$this->totalNumberOfPendingMessages=$jsondata->totalNumberOfPendingMessages;
			}
		}
	}

	public function toString() {
		$buffer='inboundMessage=';
		if (!is_null($this->inboundMessage) && is_array($this->inboundMessage)) {
			$buffer=$buffer.'{';
			for ($i=0; $i<count($this->inboundMessage); $i++) {
				if ($i>0) {
					$buffer=$buffer.', ['.$i.'] = '.$this->inboundMessage[$i]->toString();
				} else {
					$buffer=$buffer.'['.$i.'] = '.$this->inboundMessage[$i]->toString();
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
