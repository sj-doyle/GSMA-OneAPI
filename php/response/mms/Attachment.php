<?php

class Attachment {

	protected $attachmentName=null;
	protected $attachmentContentType=null;
	protected $attachmentData=null;

	public function getAttachmentName() { return $this->attachmentName; }
	public function getAttachmentContentType() { return $this->attachmentContentType; }
	public function getAttachmentData() { return $this->attachmentData; }
	
	public function setAttachmentName($attachmentName) { $this->attachmentName=$attachmentName; }
	public function setAttachmentContentType($attachmentContentType) { $this->attachmentContentType=$attachmentContentType; }
	public function setAttachmentData($attachmentData) { $this->attachmentData=$attachmentData; }
	
	public function __construct($attachmentName=null, $attachmentContentType=null, $attachmentData=null) {
		$this->attachmentName=$attachmentName;
		$this->attachmentContentType=$attachmentContentType;
		$this->attachmentData=$attachmentData;		
	}		

	public function toString() {
		$buffer='attachmentName='.$this->attachmentName.', attachmentContentType='.$this->attachmentContentType;
		return $buffer;
	}

}

?>
