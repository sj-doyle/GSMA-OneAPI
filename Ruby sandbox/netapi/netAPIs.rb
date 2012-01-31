##
# 
#
# = Network RESTful APIs sandbox
#
#   This Ruby sandbox uses the Sinatra gem to handle RESTful API calls to stubbed network enablers.
#   Charge to bill, check transaction status, locate user(s) and send SMS/query SMS status supported
#   in this release.
#   All handlers are stubs but the routes can be reused and implemented as live code.
#   OneAPI definitions are reused where available.

require 'rubygems'
require 'sinatra'
require 'json'
require 'singleton'
require "bundler/setup"

get '/' do
  File.read(File.join('public','index.html'))
end


## 
# OneAPI payment operations
class Receipts
  include Singleton
  $receipts = Hash.new
  def addReceipt(transId,jsonTrans)
    $receipts["#{transId}"] = jsonTrans
  end
  def getReceipt(transId)
    return $receipts["#{transId}"]
  end
end

receipts = Receipts.instance

post '/v2_1/payment/*/transactions/amount' do # => creates a new charge. * matches the MSISDN or ACR in the URI
  content_type :json, :charset => 'utf-8'

  class Transaction
    def initialize(endUserId, code, referenceCode, callback=false)
      @endUserId = endUserId
      @code = code
      @referenceCode = referenceCode 
      $callback = callback
      $transId = rand(1000)                               
    end 
    
    def transId
      return $transId 
    end
    
    def to_json(*a)     
      {"amountTransaction" => {
        "endUserId" => @endUserId,
        "paymentAmount" =>{
          "chargingInformation" => {"code" => @code, "description" => @description},
             "totalAmountCharged"=> "0" # the payment is only 'processing' at this point so nothing charged
         },
         "referenceCode" => @referenceCode,
         "serverReferenceCode" =>"#{(0...6).map{65.+(rand(25)).chr}.join}", # just an example, can be any string
         "resourceURL" => "http://onemorefortheroad.heroku.com/2/payment/#{@endUserId}/transactions/amount/#{$transId}",
         "transactionOperationStatus" => "Processing"
     }}.to_json(*a)
    end    
    
    def jsonResponse
      jsonTrans = self.to_json
      return jsonTrans
    end
    
  end 
  
  trans = Transaction.new(params[:splat][0],params[:code], params[:referenceCode], params[:callback])
  jsonTrans = trans.jsonResponse
  receipts.addReceipt(trans.transId,jsonTrans) # make sure the transactionId can be queried (i.e. has the user paid?)
  jsonTrans # OK, send the JSON response...

end       
  

get '/v2_1/payment/*/transactions/amount/:transId' do |transId| # => queries the status of a charge receipt
    headers \
  content_type :json, :charset => 'utf-8'
  response.headers["Access-Control-Allow-Origin"] = "*"
  
  # TODO trap 404 exception where the tid has not been created
  # TODO add switch to force 'refused' status so that non-paying customer can be handled
    $receipt = receipts.getReceipt(transId) || raise(not_found)
    # for this sandbox we'll pretend the user has now been charged, 
    # and also that the 'code' that was originally passed maps to €1.99 .
    $recJson = Hash.new
    $recJson = JSON.parse($receipt)
  
    $recJson["amountTransaction"]["transactionOperationStatus"] = "Charged"
    $recJson["amountTransaction"]["paymentAmount"]["totalAmountCharged"] = "1.99"
    $recJson["amountTransaction"]["paymentAmount"]["currency"] = "EUR"
  
    $recJson.to_json
end


# Location functions
# 
#

# OneAPI location operations
get '/v2/location/queries/location' do # => query a terminal location

  content_type :json, :charset => 'utf-8'
  response.headers["Access-Control-Allow-Origin"] = "*"
  
  class TerminalLocation
    def initialize(address,accuracy, altitude, latitude, longitude, timestamp)
      @address = address
      @accuracy = accuracy
      @altitude = altitude
      @latitude = latitude
      @longitude = longitude
      @timestamp = timestamp 
    end 
    
    def json_string
	   loc = "{\"terminalLocationList\": {\"terminalLocation\": {\"address\":\"#{@address}\",\"currentLocation\": {\"latitude\":\"#{@latitude}\",\"timestamp\":\"#{@timestamp}\",\"accuracy\":\"#{@accuracy}\",\"altitude\":\"#{@altitude}\",\"longitude\":\"#{@longitude}\"},\"locationRetrievalStatus\": \"Retrieved\"}}}"
	   return loc
	end
  end

  # TODO add switches here to force an exception if requested by params
  t = Time.new

  lat =""
  lng=""
  reqAcc = params[:requestedAccuracy] # this is simply ignored in the sandbox
  addr = params[:address]
  # Now for some numbers that will return actual real world locations.
  # To facilitate test development, these locations are all in London's West End.
  # First, home of the World's first consulting detective...
  if addr == "12345"
    lat = "51.5237587"
    lng = "-0.1583642"
  elsif addr == "23456" # ...and Edgeware Rd Tube
    lat = "51.519866"
    lng = "-0.167627"  
  elsif addr == "34567" # ...and Bond St Tube
    lat = "51.513830"
    lng = "-0.149281"  
  elsif addr == "45678" # ...and middle of Regents Park
    lat = "51.528210"
    lng = "-0.149989"  
  else
    # TODO add exception handling here for invalid addresses or those who have not 'opted in'
  end
  @TerminalLoc = TerminalLocation.new(params[:address],"500","5",lat,lng,t)
  @TerminalLoc.json_string
end

## 
# Google example
# == Google Maps example
# 
get '/locationExample' do
  File.read(File.join('public','LocationTest.html'))
end

##
# SMS
# == SMS functions
# === Send an SMS from your web application to one or more end users.
# Methods: POST
# The message and target address are POST parameters. The sender address is included in the URI
# === Query SMS status
# Resource: 
#
# The initial response will populate the messageURL value of the pointer. 
# This allows you to query the SMS status with a GET to that messageURL.


post '/v2/smsmessaging/outbound/*/requests' do |sender|
    requestId = rand(1000)
    
     senderAddress = params[:splat][0]
     message = params[:message]
     address = params[:address] # TODO support multiple recipients
    
     res = {"resourceReference"=> {"resourceURL" => "/v2/smsmessaging/outbound/#{senderAddress}/requests/#{requestId}"}}.to_json()
     res
end
# error mapping for unregognised URIs
not_found do
  'We cannot find that URI - check your URI follows the specified syntax, and if you are querying a transaction that you are including the valid transactionID in the path' 
end
