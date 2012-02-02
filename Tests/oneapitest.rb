require 'rest_client'
require 'json'
require 'json-schema'
require 'colored'


=begin
Basic test harness for the OneAPI Location

Tests:
Behaviour of Apigee endpoints for each resource (SMS, Payment, Location)
 - correct HTTP response code
 - correct JSON structure
 
Does not test: 
Data validity (i.e. end to end test with real data/customers)
Load/stress/soak/destructive testing
Latency or performance

Usage:
ruby -rubygems oneapitest.rb
(puts to stdout so just pipe result to a file if you want)
=end

def fail(message)
 puts message
 exit
end 

# Location tests

$*[0].nil? ? fail("Usage: ruby -rubygems oneapitest <URL of location endpoint in quotes>") : locEndpoint = $*[0]

response = RestClient.get locEndpoint, {:params => {'address' => '12345'}}

# parse the response
contType = response.headers[:content_type]
code = response.code
resJson = JSON.parse(response)

# report the results 
contTypeResult = (contType == "application/json;charset=utf-8" ? "PASS" : "FAIL")
codeResult = (code == 200 ? "PASS" : "FAIL")

def getJsonResult(json)
	begin
	  JSON::Validator.validate!('locationSchema.json', json)
	  return "PASS"
	rescue JSON::Schema::ValidationError
	  return $!.message
	end
end

if contTypeResult == "PASS" && codeResult == "PASS" && getJsonResult(resJson) == "PASS" then overallResult = "PASS"
else overallResult = "FAIL"
end

puts '========================='
puts 'Get location of one user '
puts '========================='
if overallResult=="PASS" then puts overallResult.green else puts overallResult.red end
puts 'HTTP response code: ' + response.code.to_s
puts '==>' + codeResult 
puts 'Response content type:' + contType.to_s
puts '==>' + contTypeResult
puts 'JSON response:'
puts response
puts '==>' + getJsonResult(resJson)
