(function($) { 


    $.fn.oneapi= function(endpoint) {
        // TODO return a list of supported functions for a given endpoint
    };
    
    $.fn.oneapi.charge = function(charge) {					

     var endpoint = 'http://localhost:4567/v2_1/payment/' + charge.address + '/transactions/amount';
         this.referenceCode=charge.referenceCode;
         this.transactionStatus="";
         this.transactionId=""; 
         this.transactionURL="";
         
         // add in the default value
         charge[transactioOperationStatus]='charged';
         
         var transaction;
         
		 $.ajax({
		 type: 'POST',
		 url: endpoint,
		 async: false,
		 context: this,
		 dataType: "json",
		 data: charge,
		 beforeSend: function()
		 {
			 // TODO allow this function to be addressed
		 },
		 success: function(data)
		 {
			transaction = data;
		 },
		 error: function(data)
		 {
			 // TODO add error formatting of any OneAPI service or policy exceptions
			 alert('Error');
		 },
		 complete: function(data)
		 {
            // creates a handle on the result of the charge
            this.transactionStatus=transaction.transactionStatus;
            this.transactionId=transaction.transactionId;
            this.transactionURL=transaction.transactionURL;
		 }
		 
		 });
			 
	}; // end of fn.oneapi.chargeblock
    
    $.fn.oneapi.location = function(address, name) {
        var endpoint="http://networkapis.herokuapp.com//v2/location/queries/location";
        this.name = name;
		this.address = address;
		this.latitude = "";
		this.longitude = "";
		this.altitude = "";
		
        var oneapilocation;
        
		 $.ajax({
		 type: 'GET',
		 url: endpoint,
		 async: false,
		 context: this,
		 dataType: "json",
		 data: { "address": this.address },
		 beforeSend: function()
		 {
			 // TODO allow this function to be addressed
		 },
		 success: function(data)
		 {
			oneapilocation = data;
		 },
		 error: function(data)
		 {
			 // TODO add error formatting of any OneAPI service or policy exceptions
		 },
		 complete: function(data)
		 {
            this.longitude=oneapilocation.terminalLocationList.terminalLocation.currentLocation.longitude;
            this.latitude=oneapilocation.terminalLocationList.terminalLocation.currentLocation.latitude;
            this.altitude=oneapilocation.terminalLocationList.terminalLocation.currentLocation.altitude;
            this.address=oneapilocation.terminalLocationList.terminalLocation.address;
		 }
		 
		 });
			 
	}; // end of fn.oneapi.locate block

    $.fn.oneapi.sms = function(senderAddress, recipientList, message) {
        var endpoint="http://localhost:4567/v2/smsmessaging/outbound/" + senderAddress + "/requests";
        this.message = message;
		this.senderAddress = address;
		this.recipientList = recipientList;
		

        
		 $.ajax({
		 type: 'POST',
		 url: endpoint,
		 async: false,
		 context: this,
		 dataType: "json",
		 data: { "senderAddress": this.address, "recipientList": this.recipientList, "message": this.message }, // TODO add multiple recipients support
		 beforeSend: function()
		 {
			 // TODO allow this function to be addressed
		 },
		 success: function(data)
		 {
			oneapimessage = data;
		 },
		 error: function(data)
		 {
			 // TODO add error formatting of any OneAPI service or policy exceptions
		 },
		 complete: function(data)
		 {
            this.messageURL=oneapimessage.resourceReference,resourceURL;
		 }
		 
		 });
			 
	}; // end of fn.oneapi.sms block


})(jQuery);